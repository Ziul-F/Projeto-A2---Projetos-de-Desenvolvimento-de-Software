
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.*;

public class GerenciadorPokemon implements Catalogo {


    Scanner scanner = new Scanner(System.in);

    private Map<String, Pokemon> catalogo = new HashMap<>();
    private static final String arquivoTxt = "Pokedex.txt";
    Menus menu = new Menus();


    public void gerenciadorPokemon(){
        int escolha = 0;
        do {

            menu.menuPokemon();
            escolha = scanner.nextInt();
            scanner.nextLine();

        
            switch (escolha) {
                case 1 :
                    adicionarPokemon(null);
                break;
                case 2:
                    deletarIformacao();
                break; 
                case 3:
                    try {
                        buscarPokemon();
                    } catch (PokemonNaoEncontradoException e) { //uma geração automatica do VS por causa que o PokemonNaoEncontradoException não estava funcionando
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                break;
                case 4:
                    listarPokemon();
                break;

                case 5:
                    mudarInformacao();
                break;
            }

        }
        while (escolha != 6);
    }
    
    @Override
    public void adicionarPokemon(Pokemon pokemon) {

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
    }

    @Override
    public void buscarPokemon() throws PokemonNaoEncontradoException{
        
        System.out.println("Qual o nome do pokemon você quer buscar? ");
        String escolha = scanner.nextLine(); 
        String firstLetter = escolha.substring(0, 1).toUpperCase();
        String restOfStr = escolha.substring(1);
        escolha = firstLetter + restOfStr;
        boolean verificadorBuscar = false;

        try{
            List<String> linhas = Files.readAllLines(Paths.get(arquivoTxt));

            

            for(String linha : linhas){
                if(linha.contains(" Nome: " + escolha)){
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
            throw new PokemonNaoEncontradoException("O Pokémon com o nome " + escolha + " nao foi encontrado.");
        }
    }

    @Override
    public void listarPokemon() {
        GerenciadorTxt txt = new GerenciadorTxt();
        System.out.println("--- Catálogo de Pokémon ---");
        System.out.println("");
        txt.ListarPokemon();
        System.out.println("");
        System.out.println("Final do catálogo.");
    }

    public void salvarCadastro() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTxt))) {
            for (Pokemon p : catalogo.values()) {
                writer.write("Id: " + p.getIdPokemon() + "; Nome: " + p.getNome() + "; tipo: " + p.getTipo() + "; Nível: " + p.getNivel()+ "; Habilidades: " + p.getHabilidadeList());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o catálogo: " + e.getMessage());
        }
    }

    @Override
    public void mudarInformacao() {
        System.out.println("--- Mudar Informação de um Pokémon ---");
        System.out.print("Digite o nome do Pokémon que você quer editar: ");
        String nomeParaEditar = scanner.nextLine();
        String primeiraLetra = nomeParaEditar.substring(0, 1).toUpperCase();
        String resto = nomeParaEditar.substring(1);
        nomeParaEditar = primeiraLetra + resto;

        try {
            List<String> linhas = Files.readAllLines(Paths.get(arquivoTxt));
            boolean pokemonEncontrado = false;

            // Cria uma nova lista para armazenar as linhas modificadas
            List<String> novasLinhas = new ArrayList<>();

            for (String linha : linhas) {
                // Se a linha contém o nome do Pokémon, vamos editá-la
                if (linha.contains("Nome: " + nomeParaEditar)) {
                    pokemonEncontrado = true;
                    String linhaAtualizada = processarModificacao(linha);
                    novasLinhas.add(linhaAtualizada);
                } else {
                    // Se não for o Pokémon, adiciona a linha como está
                    novasLinhas.add(linha);
                }
            }

            if (pokemonEncontrado) {
                // Reescreve o arquivo com as novas linhas
                Files.write(Paths.get(arquivoTxt), novasLinhas);
                System.out.println("Informações do Pokémon '" + nomeParaEditar + "' foram atualizadas com sucesso!");
            } else {
                System.out.println("Erro: O Pokémon com o nome '" + nomeParaEditar + "' não foi encontrado.");
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler/escrever o arquivo: " + e.getMessage());
        }
    }

    private String processarModificacao(String linha) {
        // --- Passo 1: Analisar a linha e extrair os dados ---
        Map<String, String> dados = new HashMap<>();
        try {
            // Usa substrings para encontrar cada campo de forma segura
            String[] campos = {"Id", "Nome", "Tipo", "Nível", "Habilidades"};
            int inicio = 0;
            for (int i = 0; i < campos.length; i++) {
                String chave = campos[i];
                String chaveCompleta = chave + ":";
                int fim;
                
                // Encontra o fim da chave-valor
                if (i < campos.length - 1) {
                    fim = linha.indexOf(campos[i + 1] + ":");
                } else {
                    fim = linha.length();
                }

                // Extrai o valor
                String valorBruto = linha.substring(linha.indexOf(chaveCompleta, inicio) + chaveCompleta.length(), fim).trim();
                
                // Remove o ';' se for o caso
                if (valorBruto.endsWith(";")) {
                    valorBruto = valorBruto.substring(0, valorBruto.length() - 1);
                }
                
                // Normaliza a chave e adiciona ao mapa
                dados.put(normalizeString(chave), valorBruto.trim());

                inicio = fim;
            }
        } catch (Exception e) {
            System.err.println("Erro ao analisar a linha do Pokémon: " + e.getMessage());
            return linha; // Retorna a linha original para evitar corromper o arquivo
        }
        
        // --- Passo 2: Exibir dados e pedir a alteração ---
        System.out.println("\n--- Dados atuais do Pokémon ---");
        dados.forEach((chave, valor) -> System.out.println(chave + ": " + valor));
        System.out.println("---------------------------------");
        
        System.out.print("\nQual campo você deseja mudar? (Id, Nome, Tipo, Nível, Habilidades): ");
        String campoParaMudar = scanner.nextLine().trim();

        // --- Passo 3: Obter o novo valor e validar ---
        switch (campoParaMudar.toLowerCase()) {
            case "id":
            case "nivel":
                System.out.print("Digite o novo valor para '" + campoParaMudar + "': ");
                String novoValorNumerico = scanner.nextLine();
                if (novoValorNumerico.matches("\\d+")) {
                    dados.put(campoParaMudar, novoValorNumerico);
                    System.out.println("Campo '" + campoParaMudar + "' atualizado para: " + novoValorNumerico);
                } else {
                    System.out.println("Aviso: Valor inválido. Apenas números são permitidos para este campo.");
                }
                break;
            case "nome":
            case "tipo":
            case "habilidades":
                System.out.print("Digite o novo valor para '" + campoParaMudar + "': ");
                String novoValorTexto = scanner.nextLine();
                dados.put(campoParaMudar, novoValorTexto);
                System.out.println("Campo '" + campoParaMudar + "' atualizado para: " + novoValorTexto);
                break;
            default:
                System.out.println("Erro: Campo '" + campoParaMudar + "' não encontrado. Nenhuma alteração foi feita.");
                break;
        }

        // --- Passo 4: Reconstruir a linha com os dados atualizados ---
        StringBuilder linhaReconstruida = new StringBuilder();
        linhaReconstruida.append("Id: ").append(dados.get("id")).append("; ");
        linhaReconstruida.append("Nome: ").append(dados.get("nome")).append("; ");
        linhaReconstruida.append("Tipo: ").append(dados.get("tipo")).append("; ");
        linhaReconstruida.append("Nível: ").append(dados.get("nivel")).append("; ");
        linhaReconstruida.append("Habilidades: ").append(dados.get("habilidades"));

        return linhaReconstruida.toString();
    }

    private String normalizeString(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }



    @Override
    public void deletarIformacao(){};

}
