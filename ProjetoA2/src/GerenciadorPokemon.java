
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.*;

public class GerenciadorPokemon implements Catalogo {
    private String tipo;


    Scanner scanner = new Scanner(System.in);
    GerenciadorTxt gerenciarOutPut = new GerenciadorTxt();
    Logs log = new Logs();
    

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
                    adicionarPokemon();
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
    public void adicionarPokemon() {

        
        System.out.println("------------------------------------------");
        System.out.println("--------- Adicionar novo Pokémon ---------");
        System.out.println("------------------------------------------");

                    System.out.print("Digite o ID do Pokémon: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
            
                    System.out.print("Digite o nome do Pokémon: ");
                    boolean temMaiuscula = false;
                    String nome = scanner.nextLine();

                    for (char c : nome.toCharArray()) {
                        if (Character.isUpperCase(c)) {
                            temMaiuscula = true;
                        }
                    }

                    if (temMaiuscula == false) {
                        String firstLetter = nome.substring(0, 1).toUpperCase();
                        String restOfStr = nome.substring(1);
                        nome = firstLetter + restOfStr;
                    }
                    
            

                    escolhaTipos();
                    
            
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

                    salvarPokemonNoArquivo(pokemons);
                    if (salvarPokemonNoArquivo(pokemons)) {
                        this.log.gravarCadastroLogPokemon(pokemons);
                        System.out.println("Pokémon adicionado!");
                    }
                    else{
                        System.out.println("Falha no cadastro.");
                    }
                    
                    
                    System.out.println("------------------------------------------");
    }

    @Override
    public void buscarPokemon() throws PokemonNaoEncontradoException{

        System.out.println("------------------------------------------");

        System.out.println("Qual o nome do pokemon você quer buscar? ");
        String escolha = scanner.nextLine(); 
        boolean verificadorBuscar = false;
        boolean temMaiuscula = false;

        for (char c : escolha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                temMaiuscula = true;
            }
        }
        if (temMaiuscula == false) {
            String firstLetter = escolha.substring(0, 1).toUpperCase();
            String restOfStr = escolha.substring(1);
            escolha = firstLetter + restOfStr;
        }
        String termoDeBuscaExato = "Nome: " + escolha + ";";


        try{
            List<String> linhas = Files.readAllLines(Paths.get(arquivoTxt));

            

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
            throw new PokemonNaoEncontradoException("O Pokémon com o nome " + escolha + " nao foi encontrado.");
        }
        System.out.println("------------------------------------------");
    }

    @Override
    public void listarPokemon() {
        GerenciadorTxt txt = new GerenciadorTxt();
        System.out.println("------------------------------------------");
        System.out.println("---------- Catálogo de Pokémon -----------");
        System.out.println("------------------------------------------");
        System.out.println("");
        txt.ListarPokemon();
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("----------- Final do catálogo ------------");
        System.out.println("------------------------------------------");
    }

    private boolean salvarPokemonNoArquivo(Pokemon pokemon) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTxt, true))) { 
            writer.write("Id: " + pokemon.getIdPokemon() + "; Nome: " + pokemon.getNome() + "; Tipo: " + pokemon.getTipo() + "; Nível: " + pokemon.getNivel() + "; Habilidades: " + pokemon.getHabilidadeList());
            writer.newLine();
            System.out.println("Pokémon " + pokemon.getNome() + " salvo no arquivo.");
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar o Pokémon: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void mudarInformacao() {
        System.out.println("------------------------------------------");
        System.out.println("----- Mudar Informação de um Pokémon -----");
        System.out.println("------------------------------------------");
        System.out.print("Digite o nome do Pokémon que você quer editar: ");
        String nomeParaEditar = scanner.nextLine();
        String primeiraLetra = nomeParaEditar.substring(0, 1).toUpperCase();
        String resto = nomeParaEditar.substring(1);
        nomeParaEditar = primeiraLetra + resto;

        try {
            List<String> linhas = Files.readAllLines(Paths.get(arquivoTxt));
            boolean pokemonEncontrado = false;
            int idPokemonAtualizado = -1;

            List<String> novasLinhas = new ArrayList<>();

            for (String linha : linhas) {
                if (linha.contains("Nome: " + nomeParaEditar)) {
                    pokemonEncontrado = true;

                     try {
                        int start = linha.indexOf("Id: ") + 4;
                        int end = linha.indexOf(";", start);
                        String idStr = linha.substring(start, end).trim();
                         idPokemonAtualizado = Integer.parseInt(idStr);
                    } catch (Exception e) {
                        System.err.println("Aviso: Falha ao extrair ID para log de atualização.");
                    }

                    String linhaAtualizada = processarModificacao(linha);
                    novasLinhas.add(linhaAtualizada);
                } else {
                    novasLinhas.add(linha);
                }
            }

            if (pokemonEncontrado) {
                Files.write(Paths.get(arquivoTxt), novasLinhas);
                System.out.println("Informações do Pokémon '" + nomeParaEditar + "' foram atualizadas com sucesso!");

                if (idPokemonAtualizado != -1) {
                    Pokemon pokemonAtualizado = new Pokemon(idPokemonAtualizado); 
                    this.log.gravarAtualizacaoLogPokemon(pokemonAtualizado); 
                }

            } else {
                System.out.println("Erro: O Pokémon com o nome '" + nomeParaEditar + "' não foi encontrado.");
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler/escrever o arquivo: " + e.getMessage());
        }
        System.out.println("------------------------------------------");
    }

    private String processarModificacao(String linha) {
        Map<String, String> dados = new HashMap<>();
        try {
            String[] campos = {"Id", "Nome", "Tipo", "Nível", "Habilidades"};
            int inicio = 0;
            for (int i = 0; i < campos.length; i++) {
                String chave = campos[i];
                String chaveCompleta = chave + ":";
                int fim;
                
                if (i < campos.length - 1) {
                    fim = linha.indexOf(campos[i + 1] + ":");
                } else {
                    fim = linha.length();
                }

                String valorBruto = linha.substring(linha.indexOf(chaveCompleta, inicio) + chaveCompleta.length(), fim).trim();
                
                if (valorBruto.endsWith(";")) {
                    valorBruto = valorBruto.substring(0, valorBruto.length() - 1);
                }
                
                dados.put(normalizeString(chave), valorBruto.trim());

                inicio = fim;
            }
        } catch (Exception e) {
            System.err.println("Erro ao analisar a linha do Pokémon: " + e.getMessage());
            return linha; 
        }
        
        System.out.println("\n--- Dados atuais do Pokémon ---");
        dados.forEach((chave, valor) -> System.out.println(chave + ": " + valor));
        System.out.println("---------------------------------");
        
        System.out.print("\nQual campo você deseja mudar? (Id, Nome, Tipo, Nível, Habilidades): ");
        String campoParaMudar = scanner.nextLine().trim();

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
    public void deletarIformacao(){
        
        System.out.println("------------------------------------------");
        String linhaParaRemover;
        System.out.println("Qual o nome do pokemon que você quer apagar? ");
        linhaParaRemover = scanner.nextLine(); 

        if (linhaParaRemover != null && !linhaParaRemover.isEmpty()) {
            linhaParaRemover = linhaParaRemover.substring(0, 1).toUpperCase() + linhaParaRemover.substring(1).toLowerCase();
        } else {
            System.out.println("Nome do Pokémon não pode ser vazio.");
            return;
        }
        
        List<String> linhas = new ArrayList<>();
        String linhaDeletada = null; 
        boolean pokemonEncontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoTxt))) {
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
            System.err.println("Erro ao ler o arquivo de Pokémons: " + e.getMessage());
            return; 
        }

        if (!pokemonEncontrado) {
            System.out.println("Pokémon '" + linhaParaRemover + "' não encontrado.");
            return;
        }
        
        int idPokemon = -1;
        if (linhaDeletada != null) {
            try {
                int start = linhaDeletada.indexOf("Id: ") + 4;
                int end = linhaDeletada.indexOf(";", start);
                String idStr = linhaDeletada.substring(start, end).trim();
                idPokemon = Integer.parseInt(idStr);
            } catch (Exception e) {
                System.err.println("Aviso: ID do Pokémon não pôde ser extraído. O log será incompleto.");
            }
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTxt))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
            

            if (idPokemon != -1) {
                Pokemon pokemonDeletado = new Pokemon(idPokemon); 
                this.log.gravarDeletLogPokemon(pokemonDeletado); 
            }

            System.out.println("Pokémon '" + linhaParaRemover + "' removido com sucesso!");

        } catch(IOException e){
            System.err.println("Erro ao reescrever o arquivo após a deleção: " + e.getMessage());
            return; 
        }


        System.out.println("------------------------------------------");
    };


    public void escolhaTipos(){

        boolean verificador = false;
        verificador = true;

        do{
            System.out.println("------------------------------------------");
            menu.tiposPokemon();
            int escolhatipos = scanner.nextInt();
            scanner.nextLine();
            if ((escolhatipos > 0) && (escolhatipos < 15)) {
                switch(escolhatipos){
                case 1:
                    tipo = "Normal";
                    verificador = true;
                break;
                case 2:
                    tipo = "Fogo";
                    verificador = true;
                break;
                case 3:
                    tipo = "Água";
                    verificador = true;
                break;
                case 4:
                    tipo = "Planta";
                    verificador = true;
                break;
                case 5:
                    tipo = "Elétrico";
                    verificador = true;
                break;
                case 6:
                    tipo = "Gelo";
                    verificador = true;
                break;
                case 7:
                    tipo = "Lutador";
                    verificador = true;
                break;
                case 8:
                    tipo = "Veneno";
                    verificador = true;
                break;
                case 9:
                    tipo = "Terra";
                    verificador = true;
                break;
                case 10:
                    tipo = "Vaoador";
                    verificador = true;
                break;
                case 11:
                    tipo = "Psíquico";
                    verificador = true;
                break;
                case 12:
                    tipo = "Inseto";
                    verificador = true;
                break;
                case 13:
                    tipo = "Pedra";
                    verificador = true;
                break;
                case 14:
                    tipo = "Fantasma";
                    verificador = true;
                break;
                case 15:
                    tipo = "Dragão";
                    verificador = true;
                break;
                }
            }
            else{
                System.out.println("Escolha um número de 1 a 15.");
            }
            System.out.println("------------------------------------------");
        }
        while(!verificador);
    }

    // ?: example for jUnit test
    public boolean TestUnitarioGerenciador(){
        return true;
    }
}
