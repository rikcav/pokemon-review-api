package com.api.pokemonreview.services.impl;

import com.api.pokemonreview.dtos.PokemonDTO;
import com.api.pokemonreview.dtos.PokemonGetAllResponse;
import com.api.pokemonreview.exceptions.PokemonNotFoundException;
import com.api.pokemonreview.models.Pokemon;
import com.api.pokemonreview.repositories.PokemonRepository;
import com.api.pokemonreview.services.IPokemonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonServiceImpl implements IPokemonService {
    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonGetAllResponse listAllPokemon(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemonPage = pokemonRepository.findAll(pageable);
        List<Pokemon> pokemonList = pokemonPage.getContent();

        List<PokemonDTO> content = pokemonList.stream()
                .map(PokemonServiceImpl::mapToDTO)
                .toList();

        PokemonGetAllResponse pokemonResponse = new PokemonGetAllResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pokemonPage.getNumber());
        pokemonResponse.setPageSize(pokemonPage.getSize());
        pokemonResponse.setTotalElements(pokemonPage.getTotalElements());
        pokemonResponse.setTotalPages(pokemonPage.getTotalPages());
        pokemonResponse.setLast(pokemonPage.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDTO getPokemon(int id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found."));
        return mapToDTO(pokemon);
    }

    @Override
    public PokemonDTO createPokemon(PokemonDTO pokemonDTO) {
        Pokemon pokemon = mapToEntity(pokemonDTO);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        return mapToDTO(savedPokemon);
    }

    @Override
    public PokemonDTO updatePokemon(int id, PokemonDTO pokemonDTO) {
        PokemonDTO foundPokemon = getPokemon(id);
        BeanUtils.copyProperties(pokemonDTO, foundPokemon);
        foundPokemon.setId(id);

        return createPokemon(foundPokemon);
    }

    @Override
    public void deletePokemon(int id) {
        PokemonDTO pokemonDTO = getPokemon(id);
        Pokemon pokemon = mapToEntity(pokemonDTO);

        pokemonRepository.delete(pokemon);
    }

    private static PokemonDTO mapToDTO(Pokemon pokemon) {
        PokemonDTO pokemonDTO = new PokemonDTO();
        BeanUtils.copyProperties(pokemon, pokemonDTO);

        return pokemonDTO;
    }

    private static Pokemon mapToEntity(PokemonDTO pokemonDTO) {
        Pokemon pokemon = new Pokemon();
        BeanUtils.copyProperties(pokemonDTO, pokemon);

        return pokemon;
    }
}
