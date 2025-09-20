package com.iplacex.discografia.discos;

import com.iplacex.discografia.artistas.IArtistaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository discoRepo;
    private final IArtistaRepository artistaRepo;

    public DiscoController(IDiscoRepository discoRepo, IArtistaRepository artistaRepo) {
        this.discoRepo = discoRepo;
        this.artistaRepo = artistaRepo;
    }

    @PostMapping(value="/disco", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepo.existsById(disco.idArtista)) {
            return ResponseEntity.badRequest().body("El artista no existe");
        }
        return ResponseEntity.ok(discoRepo.save(disco));
    }

    @GetMapping(value="/discos", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return ResponseEntity.ok(discoRepo.findAll());
    }

    @GetMapping(value="/disco/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleGetDiscoRequest(@PathVariable String id) {
        return discoRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value="/artista/{id}/discos", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        return ResponseEntity.ok(discoRepo.findDiscosByIdArtista(id));
    }
}
