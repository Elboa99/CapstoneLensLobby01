package kennyboateng.CapstoneLensLobby01.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kennyboateng.CapstoneLensLobby01.entities.Utente;
import kennyboateng.CapstoneLensLobby01.exceptions.UnauthorizedException;
import kennyboateng.CapstoneLensLobby01.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteService utenteService;  // Cambiato a UtenteService perché gli utenti includono fotografi

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore inserisci correttamente il token nell'Authorization Header");
        }

        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);  // Verifica il token JWT

        Long utenteId = jwtTools.getUtenteIdFromToken(accessToken);  // Cambia il metodo per ottenere l'ID utente dal token
        Utente currentUtente = utenteService.findUtenteById(utenteId);  // Carica l'utente dal servizio

        if (currentUtente != null) {
            // Crea il token di autenticazione con i ruoli (authorities) dell'utente
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    currentUtente,
                    null,
                    currentUtente.getAuthorities()  // Usa le authorities dell'utente
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new UnauthorizedException("Utente non trovato.");
        }

        filterChain.doFilter(request, response);  // Continua con la catena di filtri
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Esclude determinate rotte dalla verifica JWT (es. rotte di login o registrazione)
        return new AntPathMatcher().match("/authorization/**", request.getServletPath());
    }
}
