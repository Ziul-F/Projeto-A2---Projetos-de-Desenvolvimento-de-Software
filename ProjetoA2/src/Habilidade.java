public class Habilidade {
    private String nome;
    private String descricao;

    public Habilidade(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }
     @Override
    public String toString() {
        return this.nome + " (" + this.descricao + ")";
    }
}
