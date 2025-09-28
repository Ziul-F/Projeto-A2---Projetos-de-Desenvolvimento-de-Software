import org.junit.*;

public class TestComandos {
    // TODO: start to study jUnit tests.

    @Test
    public void test(){ 
        GerenciadorPokemon gerenciadorPokemon = new GerenciadorPokemon();
        Assert.assertEquals(true, gerenciadorPokemon.TestUnitarioGerenciador());
        GerenciadorTreinador GerenciadorTreinador = new GerenciadorTreinador();
        Assert.assertEquals(true, GerenciadorTreinador.testGereciadorTreinador());
        GerenciadorTxt gerenciadorTxt = new GerenciadorTxt();
        Assert.assertEquals(true, gerenciadorTxt.testGereciadorTxt());
    }


}
