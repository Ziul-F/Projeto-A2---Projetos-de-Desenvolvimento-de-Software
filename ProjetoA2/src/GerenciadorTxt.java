import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    Logs log = new Logs();

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

    public boolean deletarLinhaTreinador(Logs log) {
        String linhaParaRemover;
        System.out.println("------------------------------------------");
        System.out.println("Qual o nome que você quer apagar? ");
        linhaParaRemover = scanner.nextLine(); 
        
        if (linhaParaRemover != null && !linhaParaRemover.isEmpty()) {
            linhaParaRemover = linhaParaRemover.substring(0, 1).toUpperCase() + linhaParaRemover.substring(1).toLowerCase();
        }
        
        List<String> linhas = new ArrayList<>();
        String linhaDeletada = null; 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(treinadorPath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains("Nome: " + linhaParaRemover + ";")) {
                    linhaDeletada = linha; 
                } else {
                    linhas.add(linha);
                }
            }
        } catch(IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return false; 
        }
        
        if (linhaDeletada != null) { 
            int idTreinador = -1; 
            
            try {
                int start = linhaDeletada.indexOf("Id: ") + 4;
                int end = linhaDeletada.indexOf(";", start);
                String idStr = linhaDeletada.substring(start, end).trim();
                idTreinador = Integer.parseInt(idStr);
            } catch (Exception e) {
                System.err.println("ERRO: Não foi possível extrair o ID da linha deletada. Log não registrado.");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(treinadorPath))) {
                for (String linha : linhas) {
                    writer.write(linha);
                    writer.newLine();
                }
            } catch(IOException e) {
                System.err.println("ERRO: Falha na reescrita do arquivo (deleção).");
                return false; 
            }
            

            if (idTreinador != -1) {
                Treinador treinadorDeletado = new Treinador();
                treinadorDeletado.setTreinadorId(idTreinador);
                log.gravarDeletLogTreinador(treinadorDeletado);
            }

            System.out.println("Treinador removido com sucesso!");
            return true;
            
        } else {
            System.out.println(" Falha ao remover o treinador. Treinador '" + linhaParaRemover + "' não encontrado.");
            return false;
        }
    }

    // ?: example for jUnit test
    public boolean testGereciadorTxt(){
        return true;
    }
}
