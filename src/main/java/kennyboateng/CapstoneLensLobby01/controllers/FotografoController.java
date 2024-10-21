package kennyboateng.CapstoneLensLobby01.controllers;

import kennyboateng.CapstoneLensLobby01.entities.Fotografo;
import kennyboateng.CapstoneLensLobby01.exceptions.BadRequestException;
import kennyboateng.CapstoneLensLobby01.exceptions.NotFoundException;
import kennyboateng.CapstoneLensLobby01.payloads.FotografiPayloadDTO;
import kennyboateng.CapstoneLensLobby01.payloads.FotografoResponseDTO;
import kennyboateng.CapstoneLensLobby01.services.FotografoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/fotografi")
public class FotografoController {

    @Autowired
    private FotografoService fotografoService;

    @PostMapping("/register")
    public FotografoResponseDTO registerFotografo(@RequestBody FotografiPayloadDTO body) {
        Fotografo savedFotografo = fotografoService.registerFotografo(body);
        return new FotografoResponseDTO(
                savedFotografo.getId(),
                savedFotografo.getUtente().getUsername(),
                savedFotografo.getUtente().getNome(),
                savedFotografo.getUtente().getCognome(),
                savedFotografo.getUtente().getEmail(),
                savedFotografo.getBiografia(),
                savedFotografo.getUtente().getAvatar()
        );
    }

    @GetMapping("/{id}")
    public FotografoResponseDTO getFotografoById(@PathVariable Long id) {
        Fotografo fotografo = fotografoService.findFotografoById(id)
                .orElseThrow(() -> new NotFoundException("Fotografo non trovato con id: " + id));
        return new FotografoResponseDTO(
                fotografo.getId(),
                fotografo.getUtente().getUsername(),
                fotografo.getUtente().getNome(),
                fotografo.getUtente().getCognome(),
                fotografo.getUtente().getEmail(),
                fotografo.getBiografia(),
                fotografo.getUtente().getAvatar()
        );
    }
}

