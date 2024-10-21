package kennyboateng.CapstoneLensLobby01.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kennyboateng.CapstoneLensLobby01.entities.Utente;
import kennyboateng.CapstoneLensLobby01.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    // Creazione del token per l'utente (fotografo o altro)
    public String createToken(Utente utente) {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))  // Valido per 7 giorni
                .setSubject(String.valueOf(utente.getId()))  // Usa l'ID dell'utente come soggetto
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))  // Firma con la chiave segreta
                .compact();
    }

    // Verifica il token ricevuto
    public void verifyToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))  // Chiave segreta per la firma
                    .build()
                    .parseClaimsJws(token);  // Parsea il token per verificarne la validità
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi col token! Per favore effettua di nuovo il login.");
        }
    }

    // Estrai l'ID utente dal token
    public Long getUtenteIdFromToken(String token) {
        return Long.parseLong(Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))  // Chiave per decodificare il token
                .build()
                .parseClaimsJws(token)  // Parsing del token
                .getBody()
                .getSubject());  // Ottiene il subject (l'ID dell'utente) dal corpo del token
    }
}
