import Service.GerenciadorPokemon;
import Service.Habilidade;
import Service.PokemonNaoEncontradoException;
import org.junit.*;

public class TestComandos {
    // TODO: start to study jUnit tests.

    @Test
    public void testFormatador(){
        String nome1 = GerenciadorPokemon.formatarNome("pikachu");
        String nome2 = GerenciadorPokemon.formatarNome("CHARMANDER");
        String nome3 = GerenciadorPokemon.formatarNome("vEnUsAuR");
        String nome4 = GerenciadorPokemon.formatarNome("nidoraN");

        Assert.assertEquals("Pikachu", nome1);
        Assert.assertEquals("Charmander", nome2);
        Assert.assertEquals("Venusaur", nome3);
        Assert.assertEquals("Nidoran", nome4);
    }


    @Test
    public void testPokemonNaoEncontradoException() {

        String mensagemErro = "O Pokémon 'Mewtwo' não foi encontrado.";

        PokemonNaoEncontradoException excecao = new PokemonNaoEncontradoException(mensagemErro);

        Assert.assertTrue(excecao instanceof RuntimeException);
        Assert.assertEquals(mensagemErro, excecao.getMessage());
    }

    @Test
    public void testHabilidadeToString() {
        String nome = "Ember";
        String descricao = "It deals damage and has a 10% chance to burn the target.";
        Habilidade habilidade = new Habilidade(nome, descricao);

        String outputEsperado = "Ember (It deals damage and has a 10% chance to burn the target.)";
        String outputReal = habilidade.toString();

        Assert.assertEquals(outputEsperado, outputReal);
    }
}
