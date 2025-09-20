package com.iplacex.discografia.artistas;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    private final IArtistaRepository repo;

    public ArtistaController(IArtistaRepository repo) {
        this.repo = repo;
    }

    @PostMapping(value="/artista", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        return ResponseEntity.ok(repo.save(artista));
    }

    @GetMapping(value="/artistas", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping(value="/artista/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleGetArtistaRequest(@PathVariable String id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value="/artista/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        artista._id = id;
        return ResponseEntity.ok(repo.save(artista));
    }

    @DeleteMapping(value="/artista/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.ok("Artista eliminado con éxito");
    }
}
