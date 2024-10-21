package kennyboateng.CapstoneLensLobby01.services;

import kennyboateng.CapstoneLensLobby01.entities.Utente;
import kennyboateng.CapstoneLensLobby01.exceptions.UnauthorizedException;
import kennyboateng.CapstoneLensLobby01.payloads.UtenteLoginDTO;
import kennyboateng.CapstoneLensLobby01.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    private UtenteService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiEGeneraToken(UtenteLoginDTO body){
        Utente found = this.utentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())){
            return this.jwtTools.createToken(found);
        } else{
            throw new UnauthorizedException("Errore nelle credenziali che hai fornito");
        }
    }
}

