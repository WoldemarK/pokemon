package org.example.pokemon.service;

import org.example.pokemon.dto.PokemonDto;
import org.example.pokemon.dto.PokemonResponse;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getAllPokemon(int pageNo, int pageSize);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemonId(int id);
}
