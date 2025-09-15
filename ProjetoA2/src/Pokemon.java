import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pokemon {
    private int idPokemon;
    private String nome;
    private String tipo;
    private Integer nivel;
    private List<Habilidade> habilidades;

    public Pokemon(int idPokemon, String nome, String tipo, Integer nivel,List<Habilidade> habilidades) {
        this.idPokemon = idPokemon;
        this.nome = nome;
        this.tipo = tipo;
        this.nivel = nivel;
        this.habilidades = habilidades;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getNivel() {
        return nivel;
    }

    public List<Habilidade> getHabilidadeList() {
        return habilidades;
    }
    public void adicionarHabilidade(Habilidade habilidade){
        this.habilidades.add(habilidade);
    }
    public int getIdPokemon() {
        return idPokemon;
    }




    

}
