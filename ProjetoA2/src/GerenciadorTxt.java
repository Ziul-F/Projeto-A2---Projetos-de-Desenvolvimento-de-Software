import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GerenciadorTxt {

    List<String> linhas = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    String filePath = "Pokedex.txt";
    String treinadorPath = "Treinadores.txt";

    public List<String> getLinhas() {
        return linhas;
    }

    public void lerArquivoPokemon(){
        

        try {
            linhas = Files.readAllLines(Paths.get(filePath), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo. Verifique a codificação.");
            e.printStackTrace();
        }
    }

    public void ListarPokemon (){
        lerArquivoPokemon();
        for(String linha: linhas){
            System.out.println(linha);
        }
    };

    public void deletarLinhaPokemon(){
        String linhaParaRemover;
        System.out.println("Qual o nome do pokemon que você quer apagar? ");
        linhaParaRemover = scanner.nextLine(); 
        boolean temMaiuscula = false;

        for (char c : linhaParaRemover.toCharArray()) {
            if (Character.isUpperCase(c)) {
                temMaiuscula = true;
            }
        }

        if (temMaiuscula == false) {
            String firstLetter = linhaParaRemover.substring(0, 1).toUpperCase();
            String restOfStr = linhaParaRemover.substring(1);
            linhaParaRemover = firstLetter + restOfStr;
        }
        

        List<String> linhas = new ArrayList<>();
        

        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.contains("Nome: " + linhaParaRemover + ";")) {
                    linhas.add(linha);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        }
        catch(IOException e){}
    }

    public void lerArquivoTreinador(){
        

        try {
            linhas = Files.readAllLines(Paths.get(treinadorPath), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo. Verifique a codificação.");
            e.printStackTrace();
        }
    }

    public void ListarTreinadores (){
        lerArquivoTreinador();
        for(String linha: linhas){
            System.out.println(linha);
        }
    };

    public void deletarLinhaTreinador(){
        String linhaParaRemover;
        System.out.println("Qual o nome que você quer apagar? ");
        linhaParaRemover = scanner.nextLine(); 
        boolean temMaiuscula = false;

        for (char c : linhaParaRemover.toCharArray()) {
            if (Character.isUpperCase(c)) {
                temMaiuscula = true;
            }
        }

        if (temMaiuscula == false) {
            String firstLetter = linhaParaRemover.substring(0, 1).toUpperCase();
            String restOfStr = linhaParaRemover.substring(1);
            linhaParaRemover = firstLetter + restOfStr;
        }
        

        List<String> linhas = new ArrayList<>();
        

        
        try (BufferedReader reader = new BufferedReader(new FileReader(treinadorPath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.contains("Nome: " + linhaParaRemover + ";")) {
                    linhas.add(linha);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(treinadorPath))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        }
        catch(IOException e){}
    }


}
