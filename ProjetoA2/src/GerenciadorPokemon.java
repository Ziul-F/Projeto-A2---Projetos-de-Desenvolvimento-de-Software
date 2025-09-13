import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;
import java.util.*;

public class GerenciadorPokemon implements Catalogo {

    Scanner scanner = new Scanner(System.in);

    private Map<String, Pokemon> catalogo = new HashMap<>();
    private static final String arquivoTxt = "Pokedex.txt";

    @Override
    public void adicionarPokemon(Pokemon pokemon) {
        // if (pokemon != null) {
        //     catalogo.put(pokemon.getNome(), pokemon);
        //     salvarCatalogo();
        //     System.out.println("Pokémon " + pokemon.getNome() + " adicionado e salvo.");
        // }


        System.out.println("--- Adicionar novo Pokémon ---");
            
                    System.out.print("Digite o nome do Pokémon: ");
                    String nome = scanner.nextLine();
            
                    System.out.print("Digite o tipo do Pokémon: ");
                    String tipo = scanner.nextLine();
            
                    System.out.print("Digite o ID do Pokémon: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
            
                    System.out.print("Digite o nível do Pokémon: ");
                    int nivel = scanner.nextInt();
                    scanner.nextLine();

                    List<Habilidade> habilidades = new ArrayList<>();
                    String respostaHabilidade;

                    do {
                        System.out.println("\nAdicionar nova habilidade (s/n)?");
                        respostaHabilidade = scanner.nextLine();
                        if (respostaHabilidade.equalsIgnoreCase("s")) {
                            System.out.print("Digite o nome da habilidade: ");
                            String nomeHabilidade = scanner.nextLine();

                            System.out.print("Digite a descrição da habilidade: ");
                            String descricaoHabilidade = scanner.nextLine();
                                
                            Habilidade novaHabilidade = new Habilidade(nomeHabilidade, descricaoHabilidade);
                            habilidades.add(novaHabilidade);
                        }
                    }
                    while (respostaHabilidade.equalsIgnoreCase("s"));
            

                    Pokemon pokemons = new Pokemon(id, nome, tipo, nivel, habilidades);

                    adicionarPokemon(pokemons);
                    
                    
                    System.out.println("\nPokémon adicionado! Catálogo atual:");

                    // listarPokemon();
    }

    @Override
    public Pokemon buscarPokemon(String nome) throws PokemonNaoEncontradoException {
        Pokemon pokemon = catalogo.get(nome);
        if(pokemon == null){
            throw new PokemonNaoEncontradoException("O Pokémon com o nome '" + nome + "' não foi encontrado.");
        }

        return pokemon;
    }

    @Override
    public void listarPokemon() {
        System.out.println("--- Catálogo de Pokémon ---");
        System.out.println("");
        // if (catalogo.isEmpty()) {
        //     System.out.println("Nenhum Pokémon encontrado no catálogo.");
        //     return;
        // }

        // for (Pokemon p : catalogo.values()) {
        //     System.out.print("Id: " + p.getIdPokemon() + " , ");
        //     System.out.print("Nome: " + p.getNome()+ " , ");
        //     System.out.print("Tipo: " + p.getTipo()+ " , ");
        //     System.out.print("Nível: " + p.getNivel()+ " , ");
        //     System.out.println("Habilidades: " + p.getHabilidadeList()); 
        //     System.out.println("\n---");
        // }


        lerArquivo();
        System.out.println("");
        System.out.println("Final do catálogo.");
    }


    public void salvarCatalogo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTxt))) {
            for (Pokemon p : catalogo.values()) {
                writer.write("Id: " + p.getIdPokemon() + "; Nome: " + p.getNome() + "; tipo: " + p.getTipo() + "; Nível: " + p.getNivel()+ "; Habilidades: " + p.getHabilidadeList());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o catálogo: " + e.getMessage());
        }
    }


    public void lerArquivo(){
        String filePath = "Pokedex.txt";


        try{
            List<String> linhas = Files.readAllLines(Paths.get(filePath));

            for(String linha: linhas){
                System.out.println(linha);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("arquivo nao encontrado");
        }
        catch(IOException e){
            System.out.println("alguma coisa nao esta certa");
        }
    }

    public void buscarPokemon(){
        String filePath = "Pokedex.txt";
         
        System.out.println("Qual id do pokemon você quer buscar? ");
        String escolha = scanner.nextLine();

        try{
            List<String> linhas = Files.readAllLines(Paths.get(filePath));
            for(String linha : linhas){

                if(linha.contains("Id: " + escolha)){
                    System.out.println(linha);
                }
            }
        }
        catch(IOException e){
            System.out.println("alguma coisa nao esta certa");
        }
        
    }


}
