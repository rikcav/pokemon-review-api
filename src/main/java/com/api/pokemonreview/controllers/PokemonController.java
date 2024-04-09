package com.api.pokemonreview.controllers;

import com.api.pokemonreview.dtos.PokemonDTO;
import com.api.pokemonreview.dtos.PokemonGetAllResponse;
import com.api.pokemonreview.services.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class PokemonController {
    private final IPokemonService pokemonService;

    @Autowired
    public PokemonController(IPokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("pokemon")
    public ResponseEntity<PokemonGetAllResponse> getAllPokemon(
            @RequestParam(
                    value = "pageNo",
                    defaultValue = "0",
                    required = false)
            int pageNo,
            @RequestParam(
                    value = "pageSize",
                    defaultValue = "10",
                    required = false)
            int pageSize) {
        PokemonGetAllResponse pokemonResponse = pokemonService.listAllPokemon(pageNo, pageSize);
        return new ResponseEntity<>(pokemonResponse, HttpStatus.OK);
    }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<PokemonDTO> getPokemon(@PathVariable int id) {
        PokemonDTO pokemonDTO = pokemonService.getPokemon(id);
        return new ResponseEntity<>(pokemonDTO, HttpStatus.OK);
    }

    @PostMapping("pokemon")
    public ResponseEntity<PokemonDTO> createPokemon(@RequestBody PokemonDTO pokemonDTO) {
        PokemonDTO createdPokemon = pokemonService.createPokemon(pokemonDTO);
        return new ResponseEntity<>(createdPokemon, HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}")
    public ResponseEntity<PokemonDTO> updatePokemon(@PathVariable int id, @RequestBody PokemonDTO pokemonDTO) {
        PokemonDTO updatedPokemon = pokemonService.updatePokemon(id, pokemonDTO);
        return new ResponseEntity<>(updatedPokemon, HttpStatus.OK);
    }

    @DeleteMapping("pokemon/{id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable int id) {
        pokemonService.deletePokemon(id);
        return ResponseEntity.noContent().build();
    }
}