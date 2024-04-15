package com.api.pokemonreview.services;

import com.api.pokemonreview.dtos.LoginDTO;
import com.api.pokemonreview.dtos.RegisterDTO;

public interface IAuthService {
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
