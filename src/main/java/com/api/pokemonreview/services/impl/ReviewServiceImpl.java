package com.api.pokemonreview.services.impl;

import com.api.pokemonreview.dtos.ReviewDTO;
import com.api.pokemonreview.dtos.ReviewGetAllResponse;
import com.api.pokemonreview.exceptions.PokemonNotFoundException;
import com.api.pokemonreview.exceptions.ReviewNotFoundException;
import com.api.pokemonreview.models.Pokemon;
import com.api.pokemonreview.models.Review;
import com.api.pokemonreview.repositories.PokemonRepository;
import com.api.pokemonreview.repositories.ReviewRepository;
import com.api.pokemonreview.services.IReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewGetAllResponse listAllReviews(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        List<Review> reviewList = reviewPage.getContent();

        List<ReviewDTO> content = reviewList.stream()
                .map(ReviewServiceImpl::mapToDTO)
                .toList();

        ReviewGetAllResponse reviewResponse = new ReviewGetAllResponse();
        reviewResponse.setContent(content);
        reviewResponse.setPageNo(reviewPage.getNumber());
        reviewResponse.setPageSize(reviewPage.getSize());
        reviewResponse.setTotalElements(reviewPage.getTotalElements());
        reviewResponse.setTotalPages(reviewPage.getTotalPages());
        reviewResponse.setLast(reviewPage.isLast());

        return reviewResponse;
    }

    @Override
    public List<ReviewDTO> getReviewsByPokemonId(int pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);

        return reviews.stream()
                .map(ReviewServiceImpl::mapToDTO)
                .toList();
    }

    @Override
    public ReviewDTO getReview(int id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review could not be found."));
        return mapToDTO(review);
    }

    @Override
    public ReviewDTO createReview(int pokemonId, ReviewDTO reviewDTO) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found."));

        Review review = mapToEntity(reviewDTO);
        review.setPokemon(pokemon);

        Review savedReview = reviewRepository.save(review);
        return mapToDTO(savedReview);
    }

    @Override
    public ReviewDTO updateReview(int id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review could not be found."));
        int pokemonId = review.getPokemon().getId();

        ReviewDTO foundReview = getReview(id);
        BeanUtils.copyProperties(reviewDTO, foundReview);
        foundReview.setId(id);

        return createReview(pokemonId, foundReview);
    }

    @Override
    public void deleteReview(int id) {
        ReviewDTO reviewDTO = getReview(id);
        Review review = mapToEntity(reviewDTO);

        reviewRepository.delete(review);
    }

    private static ReviewDTO mapToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        BeanUtils.copyProperties(review, reviewDTO);

        return reviewDTO;
    }

    private static Review mapToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewDTO, review);

        return review;
    }
}
