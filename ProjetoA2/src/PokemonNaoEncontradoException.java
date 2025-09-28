public class PokemonNaoEncontradoException extends RuntimeException  {
    public PokemonNaoEncontradoException(String message) {
        super(message);
    }
    public PokemonNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}