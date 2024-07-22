package org.example.pokemon.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.pokemon.dto.ReviewDto;
import org.example.pokemon.exceptions.PokemonNotFoundException;
import org.example.pokemon.exceptions.ReviewNotFoundException;
import org.example.pokemon.model.Pokemon;
import org.example.pokemon.model.Review;
import org.example.pokemon.repository.PokemonRepository;
import org.example.pokemon.repository.ReviewRepository;
import org.example.pokemon.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {

        Review review = mapToEntity(reviewDto);

        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow
                (
                        () -> new PokemonNotFoundException("Pokemon with associated review not found")
                );

        review.setPokemon(pokemon);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {
        List<Review> reviews = reviewRepository.findByPokemonId(id);

        return reviews
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow
                (
                        () -> new PokemonNotFoundException("Pokemon with associated review not found")
                );

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow
                        (
                                () -> new ReviewNotFoundException("Review with associate pokemon not found")
                        );

        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belond to a pokemon");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow
                (
                        () -> new PokemonNotFoundException("Pokemon with associated review not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new ReviewNotFoundException("Review with associate pokemon not found")
                );

        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updateReview = reviewRepository.save(review);

        return mapToDto(updateReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow
                (
                        () -> new PokemonNotFoundException("Pokemon with associated review not found")
                );

        Review review = reviewRepository.findById(reviewId).orElseThrow
                (
                        () -> new ReviewNotFoundException("Review with associate pokemon not found")
                );

        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
