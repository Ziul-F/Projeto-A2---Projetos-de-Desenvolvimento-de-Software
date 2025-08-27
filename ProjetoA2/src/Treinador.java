import java.util.ArrayList;
import java.util.List;

public class Treinador {
    private String nome;
    private List<Pokemon> pokemonList;

    public Treinador(String nome){
        this.nome = nome;
        this.pokemonList = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void adicionarPokemon(Pokemon pokemon){
        this.pokemonList.add(pokemon);
    }
}
