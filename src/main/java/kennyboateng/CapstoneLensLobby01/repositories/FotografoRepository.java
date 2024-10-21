package kennyboateng.CapstoneLensLobby01.repositories;

import kennyboateng.CapstoneLensLobby01.entities.Fotografo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FotografoRepository extends JpaRepository<Fotografo, Long> {
}