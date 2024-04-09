package com.api.pokemonreview.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PokemonGetAllResponse {
    private List<PokemonDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
