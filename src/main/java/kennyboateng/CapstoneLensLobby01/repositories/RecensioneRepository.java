package kennyboateng.CapstoneLensLobby01.repositories;

import kennyboateng.CapstoneLensLobby01.entities.Recensione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

    List<Recensione> findByFotografoId(Long fotografoId);
}