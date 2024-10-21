package kennyboateng.CapstoneLensLobby01.repositories;

import kennyboateng.CapstoneLensLobby01.entities.Immagine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImmagineRepository extends JpaRepository<Immagine,Long> {
    List<Immagine> findByFotografoId(Long fotografoId);
    List<Immagine> findByCategoriaId(Long categoriaId);

}
