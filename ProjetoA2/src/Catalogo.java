public abstract interface Catalogo {
    void adicionarPokemon();

    void buscarPokemon() throws PokemonNaoEncontradoException;

    void listarPokemon();

    void mudarInformacao();

    void deletarIformacao();

}
