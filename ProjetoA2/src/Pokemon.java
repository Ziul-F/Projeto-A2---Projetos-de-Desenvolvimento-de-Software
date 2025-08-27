import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
    private String nome;
    private String tipo;
    private Integer nivel;
    private List<Habilidade> habilidadeList;

    public Pokemon(String nome, String tipo, Integer nivel) {
        this.nome = nome;
        this.tipo = tipo;
        this.nivel = nivel;
        this.habilidadeList = new ArrayList<>();
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
        return habilidadeList;
    }
    public void adicionarHabilidade(Habilidade habilidade){
        this.habilidadeList.add(habilidade);
    }

}
