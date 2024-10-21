package kennyboateng.CapstoneLensLobby01.services;

import kennyboateng.CapstoneLensLobby01.entities.Categoria;
import kennyboateng.CapstoneLensLobby01.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Optional<Categoria> findCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    public List<Categoria> findCategorieByNome(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Categoria> findAllCategorie() {
        return categoriaRepository.findAll();
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Long id, Categoria updatedCategoria) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(updatedCategoria.getNome());
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow(() -> new RuntimeException("Categoria non trovata con id: " + id));
    }


    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
