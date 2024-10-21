package kennyboateng.CapstoneLensLobby01.controllers;

import kennyboateng.CapstoneLensLobby01.entities.Utente;
import kennyboateng.CapstoneLensLobby01.exceptions.BadRequestException;
import kennyboateng.CapstoneLensLobby01.payloads.UtenteResponseDTO;
import kennyboateng.CapstoneLensLobby01.payloads.UtentiPayloadDTO;
import kennyboateng.CapstoneLensLobby01.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    // Recupera tutti gli utenti con paginazione e ordinamento
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.utenteService.findAll(page, size, sortBy);
    }

    // Trova utente tramite ID
    @GetMapping("/{utenteId}")
    public UtenteResponseDTO findById(@PathVariable Long utenteId) {
        Utente found = this.utenteService.findUtenteById(utenteId);
        return new UtenteResponseDTO(found.getId(), found.getUsername(), found.getEmail());
    }

    // Registra un nuovo utente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteResponseDTO save(@RequestBody @Validated UtentiPayloadDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload: " + messages);
        }

        Utente savedUtente = this.utenteService.saveUtente(new Utente(
                body.username(),
                body.email(),
                body.password(),
                body.nome(),
                body.cognome(),
                body.avatar()
        ));
        return new UtenteResponseDTO(savedUtente.getId(), savedUtente.getUsername(), savedUtente.getEmail());
    }

    // Aggiorna utente tramite ID
    @PutMapping("/{utenteId}")
    public UtenteResponseDTO findByIdAndUpdate(@PathVariable Long utenteId, @RequestBody @Validated UtentiPayloadDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload: " + messages);
        }

        Utente updatedUtente = this.utenteService.findByIdAndUpdate(utenteId, body);
        return new UtenteResponseDTO(updatedUtente.getId(), updatedUtente.getUsername(), updatedUtente.getEmail());
    }

    // Cancella un utente tramite ID
    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long utenteId) {
        this.utenteService.findByIdAndDeleteUtente(utenteId);
    }


    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser, @RequestBody UtentiPayloadDTO body) {
        return this.utenteService.findByIdAndUpdate(currentAuthenticatedUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.utenteService.findByIdAndDeleteUtente(currentAuthenticatedUser.getId());
    }

    @PatchMapping("/me")
    public Utente uploadAvatarPic(@AuthenticationPrincipal Utente utente, @RequestParam("pic") MultipartFile pic) throws IOException {
        return this.utenteService.uploadAvatarPic(utente.getId(), pic);
    }

    // Trova utente tramite email
    @GetMapping("/email/{email}")
    public UtenteResponseDTO findByEmail(@PathVariable String email) {
        Utente found = this.utenteService.findByEmail(email);
        return new UtenteResponseDTO(found.getId(), found.getUsername(), found.getEmail());
    }

    //********filtri******************//

    @GetMapping("/cerca")
    public ResponseEntity<List<Utente>> cercaUtenti(@RequestParam String nome, @RequestParam String cognome) {
        List<Utente> utenti = utenteService.cercaUtenti(nome, cognome);
        return ResponseEntity.ok(utenti);
    }
}
