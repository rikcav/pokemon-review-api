package com.api.pokemonreview.services;

import com.api.pokemonreview.dtos.ReviewDTO;
import com.api.pokemonreview.dtos.ReviewGetAllResponse;

import java.util.List;

public interface IReviewService {
    ReviewGetAllResponse listAllReviews(int pageNo, int pageSize);

    List<ReviewDTO> getReviewsByPokemonId(int pokemonId);

    ReviewDTO getReview(int id);

    ReviewDTO createReview(int pokemonId, ReviewDTO reviewDTO);

    ReviewDTO updateReview(int pokemonId, int id, ReviewDTO reviewDTO);

    void deleteReview(int pokemonId, int id);
}
