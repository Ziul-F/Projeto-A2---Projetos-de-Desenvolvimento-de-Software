package Service;

import Dados.GerenciadorTxt;
import Dados.Logs;
import View.Menus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorTreinador implements CatalogoTreinador {

    Menus menu = new Menus();
    Scanner scanner = new Scanner(System.in);
    String treinadorPath = "Treinadores.txt";
    private List<String> timeDoTreinador = new ArrayList<>();
    Logs log = new Logs();
    
    public void gerenciadorTreinador()
    {
        int escolhaMenuTreinador = 0;
        
        Menus menu = new Menus();
        
        do{
            menu.menuTreinador();
            escolhaMenuTreinador = scanner.nextInt();
            scanner.nextLine();
            switch (escolhaMenuTreinador) {
                case 1: 
                    adicionarTreinador();
                    break;
                case 2:
                    deletarIformacaoTreinador();
                    break;
                case 3: 
                    buscarTreinador();
                    break;
                case 4: 
                    listarTreinador();
                    break;
                case 5: 
                    mudarInformacaoTreinador();
                    break;
            }
        }
        while(escolhaMenuTreinador != 6);
    }

    @Override
    public void adicionarTreinador(){
        System.out.println("------------------------------------------");
        System.out.println("-------- Adicionar novo Service.Treinador --------");
        System.out.println("------------------------------------------");
        boolean verificadorId = false;
        Treinador novoTreinador = new Treinador();
        do{

            System.out.print("Digite o ID do Service.Treinador: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (id > 0 && id < 999999) {
                novoTreinador.setTreinadorId(id);
                verificadorId = true;
            }
            else{
                System.out.println("Coloque um número válido.");
            }
            
           
            
        }while(!verificadorId);
        
            
        System.out.print("Digite o nome do Service.Treinador: ");
        String nome = scanner.nextLine();
        nome = formatarNome(nome);
        novoTreinador.setNome(nome);
        
        System.out.println("------------------------------------------");
        
        
        boolean verificador = false;
        do{
            System.out.println("Coloque o numero de pokemons em que você deseja adicionar ao seu time (Máximo de 6 pokemons por treinador)");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha > 0 && escolha <= 6) { 
                verificador = true;
                for (int i = 0; i < escolha; i++){ 
                    System.out.println("Digite o nome do pokemon.");
                    String nomeDesejado = scanner.nextLine();
                    
                    String pokemonEncontrado = buscarPokemonNoArquivo(nomeDesejado);

                    if (pokemonEncontrado != null) {
                        timeDoTreinador.add(pokemonEncontrado);
                        System.out.println(nomeDesejado + " foi adicionado ao seu time!");
                    } 
                    else {
                        System.out.println("Pokémon '" + nomeDesejado + "' não encontrado. Tente novamente.");
                        i--; 
                    }
                }
            }
            else{
                System.out.println("coloque um número entre 1 e 6.");
            }
        } while (!verificador);
        if (salvarTime(novoTreinador)){ log.gravarCadastroLogTreinador(novoTreinador);}
        else{System.err.println("Erro ao salvar o cadastro no Log;");}
        System.out.println("------------------------------------------");
    }

    @Override
    public void deletarIformacaoTreinador(){
        System.out.println("------------------------------------------");
        String linhaParaRemover;
        System.out.println("Qual o nome do treinador que você quer apagar? ");
        linhaParaRemover = scanner.nextLine(); 
        linhaParaRemover = formatarNome(linhaParaRemover);
        
        
        List<String> linhas = new ArrayList<>();
        String linhaDeletada = null; 
        boolean pokemonEncontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(treinadorPath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains("Nome: " + linhaParaRemover + ";")) {
                    linhaDeletada = linha;
                    pokemonEncontrado = true;
                } else {
                    linhas.add(linha);
                }
            }
        } catch(IOException e){
            System.err.println("Erro ao ler o arquivo de Treinadores: " + e.getMessage());
            return; 
        }

        if (!pokemonEncontrado) {
            System.out.println("Service.Treinador '" + linhaParaRemover + "' não encontrado.");
            return;
        }
        
        int idTreinador = -1;
        if (linhaDeletada != null) {
            try {
                int start = linhaDeletada.indexOf("Id: ") + 4;
                int end = linhaDeletada.indexOf(";", start);
                String idStr = linhaDeletada.substring(start, end).trim();
                idTreinador = Integer.parseInt(idStr);
            } catch (Exception e) {
                System.err.println("Aviso: ID do Pokémon não pôde ser extraído. O log será incompleto.");
            }
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(treinadorPath))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
            

            if (idTreinador != -1) {
                Treinador treinadorDeletado = new Treinador(); 
                treinadorDeletado.setTreinadorId(idTreinador);
                this.log.gravarDeletLogTreinador(treinadorDeletado); 
            }

            System.out.println("Service.Treinador '" + linhaParaRemover + "' removido com sucesso!");

        } catch(IOException e){
            System.err.println("Erro ao reescrever o arquivo após a deleção: " + e.getMessage());
            return; 
        }


        System.out.println("------------------------------------------");
    }

    @Override
    public void buscarTreinador(){
        System.out.println("------------------------------------------");
        System.out.println("Qual o nome do treinador que você quer buscar? ");
        String escolha = scanner.nextLine(); 
        escolha = formatarNome(escolha);
        
        String termoDeBuscaExato = "Nome: " + escolha + ";";
        
        boolean verificadorBuscar = false;

        try{
            List<String> linhas = Files.readAllLines(Paths.get(treinadorPath));
            
            for(String linha : linhas){
                if(linha.contains(termoDeBuscaExato)){
                    System.out.println(linha);
                    verificadorBuscar = true;
                    break;
                }
            }
        }
        catch(IOException e){
            System.out.println("Ocorreu um erro ao ler o arquivo.");
        }
        if(verificadorBuscar == false){
            System.out.println("O Service.Treinador com o nome " + escolha + " nao foi encontrado.");
        }
        System.out.println("------------------------------------------");
    }

    @Override
    public void listarTreinador(){
        GerenciadorTxt txt = new GerenciadorTxt();
        System.out.println("------------------------------------------");
        System.out.println("-------- Catálogo de Treinadores ---------");
        System.out.println("------------------------------------------");
        System.out.println("");
        txt.ListarTreinadores();
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("----------- Final do catálogo ------------");
        System.out.println("------------------------------------------");
    }
    
    @Override
    public void mudarInformacaoTreinador() {
        System.out.println("------------------------------------------");


        try {
            List<String> linhas = Files.readAllLines(Paths.get(treinadorPath));
            boolean treinadorEncontrado = false;
            int idTreinadorAtualizado = -1; 
            List<String> novasLinhas = new ArrayList<>();

            System.out.println(" Qual o nome do treinador que voce deseja mudar? ");
            String nome = scanner.nextLine();
            nome = formatarNome(nome);

            String termoDeBuscaExato = "Nome: " + nome + ";";
            
            for (String linha : linhas) {
                
                if (linha.contains(termoDeBuscaExato)) {
                    treinadorEncontrado = true;
                    
                    if (linha != null) {
                        try {
                            int start = linha.indexOf("Id: ") + 4;
                            int end = linha.indexOf(";", start);
                            String idStr = linha.substring(start, end).trim();
                            idTreinadorAtualizado = Integer.parseInt(idStr);
                        } catch (Exception e) {
                            System.err.println("Aviso: ID do Pokémon não pôde ser extraído. O log será incompleto.");
                        }
                    }
                    

                    String linhaAtualizada = processarModificacaoTreinador(linha);
                    novasLinhas.add(linhaAtualizada);
                } else {
                    novasLinhas.add(linha);
                }
            }

            if (treinadorEncontrado) {
                Files.write(Paths.get(treinadorPath), novasLinhas);

                if (idTreinadorAtualizado != -1) {
                    Treinador t = new Treinador();
                    t.setTreinadorId(idTreinadorAtualizado);
                    this.log.gravarAtualizacaoLogTreinador(t); 
                }
                System.out.println("Informações do Service.Treinador atualizadas!");
            } else {
                System.out.println("Service.Treinador não encontrado.");
            }

        } catch (IOException e) {
            System.err.println("Erro ao processar arquivo: " + e.getMessage());
        }
        System.out.println("------------------------------------------");
    }

    private String buscarPokemonNoArquivo(String nomeDesejado) {
        try (BufferedReader br = new BufferedReader(new FileReader(treinadorPath))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.toLowerCase().contains("nome: " + nomeDesejado.toLowerCase())) {
                    String[] partes = linha.split(";");

                    String idStr = partes[0].trim().split(": ")[1];
                    int idPokemon = Integer.parseInt(idStr);

                    String nomePokemon = partes[1].trim().split(": ")[1];
                    
                    return "Id: " + idPokemon + "; Nome: " + nomePokemon + ";"; 
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de treinadores: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de formato ao extrair o ID.");
        }
        return null; 
    }

    private boolean salvarTime(Treinador treinadorParaSalvar) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(treinadorPath, true))) {
            
            bw.write("Id: " + treinadorParaSalvar.getTreinadorId() + "; Nome: " + treinadorParaSalvar.getNome() + ";[");
            
            for (String p : timeDoTreinador) {
                bw.write(" " + p + " "); 
            }
            bw.write(" ]"); 

            System.out.println("------------------------------------------");
            bw.newLine(); 

            System.out.println("\nSeu time foi salvo com sucesso em '" + treinadorPath + "'.");
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar o time: " + e.getMessage());
            return false;
        }
    }


    private String processarModificacaoTreinador(String linhaOriginal) {

        Map<String, String> dados = new HashMap<>();
        

        try {
            String[] campos = {"Id", "Nome", "Time"}; 
            int inicio = 0;
            
            for (int i = 0; i < campos.length; i++) {
                String chave = campos[i];
                String chaveCompleta = chave + ":";
                int fim;
                

                if (i < campos.length - 1) {
                    fim = linhaOriginal.indexOf(campos[i + 1] + ":");
                } else {
                    fim = linhaOriginal.length(); 
                }

                String valorBruto = linhaOriginal.substring(linhaOriginal.indexOf(chaveCompleta, inicio) + chaveCompleta.length(), fim).trim();
                
                if (valorBruto.endsWith(";") || valorBruto.endsWith("]")) {
                    valorBruto = valorBruto.substring(0, valorBruto.length() - 1).trim();
                }
                
                dados.put(chave.toLowerCase(), valorBruto);

                inicio = fim;
            }
        } catch (Exception e) {
            System.err.println("Erro ao analisar a linha do Service.Treinador para edição: " + e.getMessage());
            return linhaOriginal;
        }

        
        System.out.println("\n--- Dados atuais do Service.Treinador ---");
        dados.forEach((chave, valor) -> System.out.println(chave + ": " + valor));
        System.out.println("---------------------------------");
        
        System.out.print("\nQual campo você deseja mudar? (Nome, ID, Time): ");
        String campoParaMudar = scanner.nextLine().trim().toLowerCase();

        if (campoParaMudar.equals("nome") || campoParaMudar.equals("id")) {
            System.out.print("Digite o novo valor para '" + campoParaMudar + "': ");
            String novoValor = scanner.nextLine();
            
            if (campoParaMudar.equals("nome") && novoValor != null && !novoValor.isEmpty()) {
                novoValor = novoValor.substring(0, 1).toUpperCase() + novoValor.substring(1).toLowerCase();
            }
            
            dados.put(campoParaMudar, novoValor);
            System.out.println("Campo '" + campoParaMudar + "' atualizado para: " + novoValor);
            
        } else if (campoParaMudar.equals("time")) {
            System.out.println("Aviso: Alterar o time é um processo complexo e requer lógica adicional.");
        }
        else {
            System.out.println("Erro: Campo '" + campoParaMudar + "' não encontrado ou não editável.");
        }



        StringBuilder linhaReconstruida = new StringBuilder();
        linhaReconstruida.append("Id: ").append(dados.get("id")).append(";"); 
        linhaReconstruida.append(" Nome: ").append(dados.get("nome")).append(";"); 
        linhaReconstruida.append(" [").append(dados.get("time")).append(" ]");

        return linhaReconstruida.toString();
    }

    private String formatarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "";
        }
        return nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
    }

    // ?: example for jUnit test
    public boolean testGereciadorTreinador(){
        return true;
    }
}
