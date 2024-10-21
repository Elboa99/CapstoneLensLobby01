package kennyboateng.CapstoneLensLobby01.repositories;

import kennyboateng.CapstoneLensLobby01.entities.Immagine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImmagineRepository extends JpaRepository<Immagine,Long> {


}
