package com.api.pokemonreview.services;

import com.api.pokemonreview.dtos.AuthResponseDTO;
import com.api.pokemonreview.dtos.LoginDTO;
import com.api.pokemonreview.dtos.RegisterDTO;

public interface IAuthService {
    AuthResponseDTO login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
