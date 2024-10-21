package kennyboateng.CapstoneLensLobby01.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import kennyboateng.CapstoneLensLobby01.entities.Utente;
import kennyboateng.CapstoneLensLobby01.exceptions.BadRequestException;
import kennyboateng.CapstoneLensLobby01.exceptions.NotFoundException;
import kennyboateng.CapstoneLensLobby01.payloads.UtentiPayloadDTO;
import kennyboateng.CapstoneLensLobby01.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinary;

    // Recupera tutti gli utenti con paginazione e ordinamento
    public Page<Utente> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    // Trova utente tramite ID
    public Utente findUtenteById(Long utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(String.valueOf(utenteId)));
    }

    // Aggiorna utente tramite ID
    public Utente findByIdAndUpdate(Long utenteId, UtentiPayloadDTO body) {
        Utente found = this.findUtenteById(utenteId);
        found.setUsername(body.username());
        found.setEmail(body.email());
        if (!body.password().isEmpty()) {
            found.setPassword(bcrypt.encode(body.password()));
        }
        found.setNome(body.nome());
        found.setCognome(body.cognome());

        return utenteRepository.save(found);
    }

    // Registra un nuovo utente
    public Utente saveUtente(Utente body) {
        if (utenteRepository.existsByEmail(body.getEmail())) {  // Usa getEmail()
            throw new BadRequestException("L'email " + body.getEmail() + " è già in uso");
        }
        Utente newUtente = new Utente(
                body.getUsername(),  // Usa getUsername()
                body.getEmail(),     // Usa getEmail()
                bcrypt.encode(body.getPassword()),  // Usa getPassword()
                body.getNome(),  // Usa getNome()
                body.getCognome(),  // Usa getCognome()
                null
        );
        return this.utenteRepository.save(newUtente);
    }

    // Cancella un utente tramite ID
    public void findByIdAndDeleteUtente(Long utenteId) {
        Utente found = this.findUtenteById(utenteId);
        this.utenteRepository.delete(found);
    }

    // Trova utente tramite email
    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    // Carica immagine avatar su Cloudinary e aggiorna utente
    public Utente uploadAvatarPic(Long utenteId, MultipartFile pic) throws IOException {
        Utente found = this.findUtenteById(utenteId);
        String url = (String) cloudinary.uploader().upload(pic.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        return this.utenteRepository.save(found);
    }

    //canclla utente
    public void deleteUtente(Long id) {
        this.utenteRepository.deleteById(id);
    }
    public boolean existsByEmail(String email) {
        return utenteRepository.existsByEmail(email);
    }

}