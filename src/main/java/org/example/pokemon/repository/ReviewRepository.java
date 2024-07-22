package org.example.pokemon.repository;

import org.example.pokemon.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByPokemonId(int pokemonId);
}
