public abstract interface Catalogo {
    void adicionarPokemon(Pokemon pokemon);

    void buscarPokemon() throws PokemonNaoEncontradoException;

    void listarPokemon();

    void mudarInformacao();

    void deletarIformacao();

}
