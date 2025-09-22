import java.util.ArrayList;
import java.util.List;

public class Treinador {
    private int treinadorId;
    private String nome;
    private List<Pokemon> pokemonList;

    public Treinador(int treinadorId,String nome){
        this.treinadorId = treinadorId;
        this.nome = nome;
        this.pokemonList = new ArrayList<>();
    }

    public int getTreinadorId() {
        return treinadorId;
    }

    public String getNome() {
        return nome;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }
}
