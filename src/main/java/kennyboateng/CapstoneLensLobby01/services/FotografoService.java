package kennyboateng.CapstoneLensLobby01.services;

import kennyboateng.CapstoneLensLobby01.entities.Fotografo;
import kennyboateng.CapstoneLensLobby01.entities.Utente;
import kennyboateng.CapstoneLensLobby01.enums.Role;
import kennyboateng.CapstoneLensLobby01.payloads.FotografiPayloadDTO;
import kennyboateng.CapstoneLensLobby01.repositories.CategoriaRepository;
import kennyboateng.CapstoneLensLobby01.repositories.FotografoRepository;
import kennyboateng.CapstoneLensLobby01.repositories.ImmagineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotografoService {

    @Autowired
    private FotografoRepository fotografoRepository;
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImmagineRepository immagineRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Optional<Fotografo> findFotografoById(Long id) {
        return fotografoRepository.findById(id);
    }

    public List<Fotografo> findAllFotografi() {
        return fotografoRepository.findAll();
    }

    public Fotografo registerFotografo(FotografiPayloadDTO fotografoDTO) {
        // Verifica se l'email è già in uso tramite l'UtenteService
        if (utenteService.existsByEmail(fotografoDTO.email())) {
            throw new RuntimeException("Email già in uso.");
        }

        // Crea un nuovo utente associato al fotografo
        Utente newUtente = new Utente(
                fotografoDTO.username(),
                fotografoDTO.email(),
                bcrypt.encode(fotografoDTO.password()),
                fotografoDTO.nome(),
                fotografoDTO.cognome(),
                null  // Potresti gestire qui l'avatar se necessario
        );
        newUtente.setRuolo(Role.USER);

        // Salva il nuovo utente nel database
        Utente savedUtente = utenteService.saveUtente(newUtente);

        // Crea il fotografo e lo associa all'utente
        Fotografo newFotografo = new Fotografo();
        newFotografo.setBiografia(fotografoDTO.biografia());
        newFotografo.setUtente(savedUtente);

        return fotografoRepository.save(newFotografo);
    }

    public Fotografo updateFotografo(Long id, FotografiPayloadDTO updatedFotografo) {
        return fotografoRepository.findById(id)
                .map(fotografo -> {
                    // Aggiorna le informazioni dell'utente associato
                    Utente utente = fotografo.getUtente();
                    utente.setNome(updatedFotografo.nome());
                    utente.setUsername(updatedFotografo.username());
                    utente.setEmail(updatedFotografo.email());
                    if (updatedFotografo.password() != null && !updatedFotografo.password().isEmpty()) {
                        utente.setPassword(bcrypt.encode(updatedFotografo.password()));
                    }
                    utenteService.saveUtente(utente);


                    fotografo.setBiografia(updatedFotografo.biografia());

                    return fotografoRepository.save(fotografo);
                })
                .orElseThrow(() -> new RuntimeException("Fotografo non trovato con id: " + id));
    }

    public void deleteFotografo(Long id) {
        fotografoRepository.findById(id).ifPresent(fotografo -> {

            utenteService.deleteUtente(fotografo.getUtente().getId());

            fotografoRepository.delete(fotografo);
        });
    }

    public List<Fotografo> findByUsername(String username) {
        return fotografoRepository.findByUtente_UsernameContainingIgnoreCase(username);
    }

}
