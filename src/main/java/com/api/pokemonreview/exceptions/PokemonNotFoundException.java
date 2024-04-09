package com.api.pokemonreview.exceptions;

import java.io.Serial;

public class PokemonNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public PokemonNotFoundException(String message) {
        super(message);
    }
}
