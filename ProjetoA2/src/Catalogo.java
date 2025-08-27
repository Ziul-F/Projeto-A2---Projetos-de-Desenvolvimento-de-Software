public interface Catalogo {
    void adicionarPokemon(Pokemon pokemon);

    Pokemon buscarPokemon(String nome) throws PokemonNaoEncontradoException;

    void listarPokemon();
}
