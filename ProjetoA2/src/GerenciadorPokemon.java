import java.util.HashMap;
import java.util.Map;
public class GerenciadorPokemon implements Catalogo {

    private Map<String, Pokemon> catalogo = new HashMap<>();

    @Override
    public void adicionarPokemon(Pokemon pokemon) {
        catalogo.put(pokemon.getNome(), pokemon);
    }

    @Override
    public Pokemon buscarPokemon(String nome) throws PokemonNaoEncontradoException {
        Pokemon pokemon = catalogo.get(nome);
        if(pokemon == null){
            throw new PokemonNaoEncontradoException("O Pokémon com o nome '" + nome + "' não foi encontrado.");
        }

        return pokemon;
    }

    @Override
    public void listarPokemon() {
        System.out.println("--- Catálogo de Pokémon ---");
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum Pokémon encontrado no catálogo.");
            return;
        }

        for (Pokemon p : catalogo.values()) {
            System.out.println("Nome: " + p.getNome());
            System.out.println("Tipo: " + p.getTipo());
            System.out.println("Nível: " + p.getNivel());
            System.out.println("---");
        }
    }
}
