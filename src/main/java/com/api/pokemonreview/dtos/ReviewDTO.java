package com.api.pokemonreview.dtos;

import com.api.pokemonreview.models.Pokemon;
import lombok.Data;

@Data
public class ReviewDTO {
    private int id;
    private String title;
    private String content;
    private int stars;
}
