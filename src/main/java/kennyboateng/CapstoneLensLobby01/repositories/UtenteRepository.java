package kennyboateng.CapstoneLensLobby01.repositories;

import kennyboateng.CapstoneLensLobby01.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
}
