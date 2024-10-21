package kennyboateng.CapstoneLensLobby01.controllers;

import kennyboateng.CapstoneLensLobby01.entities.Immagine;
import kennyboateng.CapstoneLensLobby01.services.ImmagineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/immagini")
public class ImmagineController {

    @Autowired
    private ImmagineService immagineService;


    @GetMapping
    public List<Immagine> getAllImmagini() {
        return immagineService.findAllImmagini();
    }


    @GetMapping("/{id}")
    public Immagine getImmagineById(@PathVariable Long id) {
        return immagineService.findImmagineById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Immagine non trovata con id: " + id));
    }

    // Crea un'immagine con dati EXIF
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Immagine createImmagine(@RequestParam("file") MultipartFile file, @RequestParam("fotografoId") Long fotografoId, @RequestParam("categoriaId") Long categoriaId) throws Exception {
        return immagineService.saveImmagineWithExif(file, fotografoId, categoriaId);
    }


    @PutMapping("/{id}")
    public Immagine updateImmagine(@PathVariable Long id, @RequestBody Immagine immagine) throws Exception {
        return immagineService.updateImmagine(id, immagine)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Immagine non trovata con id: " + id));
    }


    @GetMapping("/by-fotografo/{fotografoId}")
    public List<Immagine> getImmaginiByFotografo(@PathVariable Long fotografoId) {
        return immagineService.findImmaginiByFotografoId(fotografoId);
    }


    @GetMapping("/by-categoria/{categoriaId}")
    public List<Immagine> getImmaginiByCategoria(@PathVariable Long categoriaId) {
        return immagineService.findImmaginiByCategoriaId(categoriaId);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImmagine(@PathVariable Long id) {
        immagineService.deleteImmagine(id);
    }

//***FILTRI**///

    @GetMapping("/search-by-camera")
    public ResponseEntity<List<Immagine>> getByCameraMakeAndModel(@RequestParam String make, @RequestParam String model) {
        List<Immagine> immagini = immagineService.getImmaginiByCameraDetails(make, model);
        return ResponseEntity.ok(immagini);
    }
}
