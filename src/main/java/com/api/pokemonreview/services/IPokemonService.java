package com.api.pokemonreview.services;

import com.api.pokemonreview.dtos.PokemonDTO;
import com.api.pokemonreview.dtos.PokemonGetAllResponse;

public interface IPokemonService {
    PokemonGetAllResponse listAllPokemon(int pageNo, int pageSize);

    PokemonDTO getPokemon(int id);

    PokemonDTO createPokemon(PokemonDTO pokemonDTO);

    PokemonDTO updatePokemon(int id, PokemonDTO pokemonDTO);

    void deletePokemon(int id);
}
