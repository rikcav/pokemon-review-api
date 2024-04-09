package com.api.pokemonreview.repositories;

import com.api.pokemonreview.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}
