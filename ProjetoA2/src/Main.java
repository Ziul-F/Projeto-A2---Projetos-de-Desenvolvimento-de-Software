import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("-- BEM VINDO A POKÉDEX --");

        Scanner scanner = new Scanner(System.in);
        GerenciadorPokemon gerenciadorPokemon = new GerenciadorPokemon();

        int escolha = 0;
        do {

            System.out.println("Escolha um número:");
            
            System.out.println("1 - Adicionar Pokemon;");

            System.out.println("2 - Deletar Pokemon;");

            System.out.println("3 - Buscar Pokemon;");

            System.out.println("4 - Listar  Pokemons;");
            
            System.out.println("5 - Sair.");

            escolha = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Sua escolha:" + escolha);

        
            switch (escolha) {
                case 1 :
                    gerenciadorPokemon.adicionarPokemon(null);
                break;
                case 2:
                    // gerenciadorPokemon.deletarPokemon();
                break; 
                case 3:
                    try {
                        gerenciadorPokemon.buscarPokemon();
                    } catch (PokemonNaoEncontradoException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                break;
                case 4:
                    gerenciadorPokemon.listarPokemon();
                break;
            }

        }
        while (escolha != 5);
        System.out.println("Programa Fechando");
        scanner.close();
    }
}