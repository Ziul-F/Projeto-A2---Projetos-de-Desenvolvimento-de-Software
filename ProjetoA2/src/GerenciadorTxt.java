import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GerenciadorTxt {

    List<String> linhas;
    Scanner scanner = new Scanner(System.in);
    String filePath = "Pokedex.txt";

    public List<String> getLinhas() {
        return linhas;
    }

    public void lerArquivoPokemon(){
        

        try{
            linhas = Files.readAllLines(Paths.get(filePath));
        }
        catch(FileNotFoundException e){
            System.out.println("arquivo nao encontrado");
        }
        catch(IOException e){
            System.out.println("alguma coisa nao esta certa");
        }
    }

    public void ListarPokemon (){
        lerArquivoPokemon();
        for(String linha: linhas){
            System.out.println(linha);
        }
    };
}
