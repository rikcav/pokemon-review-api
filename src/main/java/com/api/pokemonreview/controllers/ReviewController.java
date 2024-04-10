package com.api.pokemonreview.controllers;

import com.api.pokemonreview.dtos.ReviewDTO;
import com.api.pokemonreview.dtos.ReviewGetAllResponse;
import com.api.pokemonreview.services.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    private final IReviewService reviewService;

    @Autowired
    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("reviews")
    public ResponseEntity<ReviewGetAllResponse> allReviews(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)
            int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)
            int pageSize
    ) {
        ReviewGetAllResponse allReviewsResponse = reviewService.listAllReviews(pageNo, pageSize);
        return new ResponseEntity<>(allReviewsResponse, HttpStatus.OK);
    }

    @GetMapping("pokemon/{pokemonId}/reviews")
    public ResponseEntity<List<ReviewDTO>> reviewsByPokemon(@PathVariable int pokemonId) {
        List<ReviewDTO> reviewsDTO = reviewService.getReviewsByPokemonId(pokemonId);
        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @GetMapping("review/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable int id) {
        ReviewDTO reviewDTO = reviewService.getReview(id);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @PostMapping("/pokemon/{pokemonId}/review")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable int pokemonId, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO responseDTO = reviewService.createReview(pokemonId, reviewDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{pokemonId}/review/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable int pokemonId, @PathVariable int id, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedDTO = reviewService.updateReview(pokemonId, id, reviewDTO);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @DeleteMapping("pokemon/{pokemonId}/review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable int pokemonId, @PathVariable int id) {
        reviewService.deleteReview(pokemonId, id);
        return ResponseEntity.noContent().build();
    }
}
