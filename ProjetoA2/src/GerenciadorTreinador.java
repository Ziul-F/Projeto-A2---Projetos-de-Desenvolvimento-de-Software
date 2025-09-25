import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorTreinador implements CatalogoTreinador{

    Menus menu = new Menus();
    Scanner scanner = new Scanner(System.in);
    String treinadorPath = "Treinadores.txt";
    private List<String> timeDoTreinador = new ArrayList<>();
    Treinador treinador = new Treinador();
    
    
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
        System.out.println("-------- Adicionar novo Treinador --------");
        System.out.println("------------------------------------------");
        boolean verificadorId = false;
        do{

            System.out.print("Digite o ID do Treinador: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (id > 0 && id < 999999) {
                treinador.setTreinadorId(id);
                verificadorId = true;
            }
            else{
                System.out.println("Coloque um número válido.");
            }
            
           
            
        }while(!verificadorId);
        
            
        System.out.print("Digite o nome do Treinador: ");
        String nome = scanner.nextLine();
        
        
        if (nome != null && !nome.isEmpty()) {
            nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
        };
        treinador.setNome(nome);
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
        salvarTime();
        System.out.println("------------------------------------------");
    }

    @Override
    public void deletarIformacaoTreinador(){
        try {
            GerenciadorTxt gerenciadorTxt = new GerenciadorTxt();
            gerenciadorTxt.deletarLinhaTreinador();
            System.out.println("------------------------------------------");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void buscarTreinador(){
        System.out.println("------------------------------------------");
        System.out.println("Qual o nome do treinador que você quer buscar? ");
        String escolha = scanner.nextLine(); 
        
        
        if (escolha != null && !escolha.isEmpty()) {
            escolha = escolha.substring(0, 1).toUpperCase() + escolha.substring(1).toLowerCase();
        }
        
        boolean verificadorBuscar = false;

        try{
            List<String> linhas = Files.readAllLines(Paths.get(treinadorPath));
            
            for(String linha : linhas){
                if(linha.contains("Nome: " + escolha)){
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
            System.out.println("O Pokémon com o nome " + escolha + " nao foi encontrado.");
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
    public void mudarInformacaoTreinador(){}

    private String buscarPokemonNoArquivo(String nomeDesejado) {
        try (BufferedReader br = new BufferedReader(new FileReader("Pokedex.txt"))) {
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
            System.err.println("Erro ao ler o arquivo de Pokémons: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de formato ao extrair o ID.");
        }
        return null; 
    }

    private void salvarTime() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(treinadorPath, true))) {
            
            bw.write("Id: " + treinador.getTreinadorId() + "; Nome: " + treinador.getNome() + ";[");
            
            for (String p : timeDoTreinador) {
                bw.write(" " + p + " "); 
            }
            bw.write(" ]"); 

            System.out.println("------------------------------------------");
            bw.newLine(); 

            System.out.println("\nSeu time foi salvo com sucesso em '" + treinadorPath + "'.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o time: " + e.getMessage());
        }
    }
}