import org.junit.*;

public class TestComandos {
    // TODO: start to study jUnit tests.

    @Test
    public void testFormatador(){ 
        String nome1 = gerenciador.formatarNome("pikachu");
        String nome2 = gerenciador.formatarNome("CHARMANDER");
        String nome3 = gerenciador.formatarNome("vEnUsAuR");
        String nome4 = gerenciador.formatarNome("nidoraN");

        Assert.AssertEquals("Pikachu", nome1);
        Assert.AssertEquals("Charmander", nome2);
        Assert.AssertEquals("Venusaur", nome3);
        Assert.AssertEquals("Nidoran", nome4);
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
