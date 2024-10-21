package kennyboateng.CapstoneLensLobby01.controllers;

import kennyboateng.CapstoneLensLobby01.entities.Utente;
import kennyboateng.CapstoneLensLobby01.enums.Role;
import kennyboateng.CapstoneLensLobby01.exceptions.BadRequestException;
import kennyboateng.CapstoneLensLobby01.payloads.UtenteLoginDTO;
import kennyboateng.CapstoneLensLobby01.payloads.UtenteLoginResponseDTO;
import kennyboateng.CapstoneLensLobby01.payloads.UtenteResponseDTO;
import kennyboateng.CapstoneLensLobby01.payloads.UtentiPayloadDTO;
import kennyboateng.CapstoneLensLobby01.services.AuthorizationService;
import kennyboateng.CapstoneLensLobby01.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UtenteService utentiService;

    @Autowired
    private PasswordEncoder bcrypt;

    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody UtenteLoginDTO body){
        // Trova l'utente tramite l'email
        Utente found = this.utentiService.findByEmail(body.email());
        Role role = found.getRuolo();
        Long utenteId = found.getId(); // Usa Long per l'ID dell'utente
        String token = this.authorizationService.checkCredenzialiEGeneraToken(body);

        return new UtenteLoginResponseDTO(token, role, utenteId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteResponseDTO register(@RequestBody @Validated UtentiPayloadDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }

        // Converti il DTO in un'entità Utente
        Utente newUtente = new Utente(
                body.username(),
                body.email(),
                bcrypt.encode(body.password()),
                body.nome(),
                body.cognome(),
                null  // Potresti gestire l'avatar qui se necessario
        );

        // Salva il nuovo utente nel database
        Utente savedUtente = this.utentiService.saveUtente(newUtente);

        return new UtenteResponseDTO(savedUtente.getId());
    }
}
