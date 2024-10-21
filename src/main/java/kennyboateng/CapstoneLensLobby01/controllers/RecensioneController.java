package kennyboateng.CapstoneLensLobby01.controllers;

import kennyboateng.CapstoneLensLobby01.entities.Recensione;
import kennyboateng.CapstoneLensLobby01.services.RecensioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recensioni")
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;


    @GetMapping
    public List<Recensione> getAllRecensioni() {
        return recensioneService.findAllRecensioni();
    }


    @GetMapping("/{id}")
    public Recensione getRecensioneById(@PathVariable Long id) {
        return recensioneService.findRecensioneById(id)
                .orElseThrow(() -> new RuntimeException("Recensione non trovata con id: " + id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recensione createRecensione(@RequestBody Recensione recensione) {
        return recensioneService.saveRecensione(recensione);
    }


    @PutMapping("/{id}")
    public Recensione updateRecensione(@PathVariable Long id, @RequestBody Recensione recensione) {
        return recensioneService.updateRecensione(id, recensione)
                .orElseThrow(() -> new RuntimeException("Recensione non trovata con id: " + id));
    }

    @GetMapping("/fotografo/{fotografoId}")
    public List<Recensione> getRecensioniByFotografoId(@PathVariable Long fotografoId) {
        return recensioneService.findRecensioniByFotografoId(fotografoId);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecensione(@PathVariable Long id) {
        recensioneService.deleteRecensione(id);
    }
}