import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("-- BEM VINDO A POKÉDEX --");

        Scanner scanner = new Scanner(System.in);
        GerenciadorPokemon gerenciadorPokemon = new GerenciadorPokemon();

        int escolha = 0;
        while (escolha != 4) {

        System.out.println("Escolha um número:");
        
        System.out.println("1 - Adicionar Pokemon;");

        System.out.println("2 - Deletar Pokemon;");

        // System.out.println("3 - Buscar Pokemon;");
        // gerenciadorPokemon.buscarPokemon(null);

        System.out.println("4 - Listar  Pokemons;");
        
        System.out.println("5 - Sair.");

        escolha = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Sua escolha:" + escolha);
        
        
            if (escolha == 1) {
                gerenciadorPokemon.adicionarPokemon(null);
            }
            if (escolha == 2) {
                
            } 
            if (escolha == 3) {
                // gerenciadorPokemon.buscarPokemon(null);
            }
            if (escolha == 4) {
                gerenciadorPokemon.listarPokemon();
            } else {
                
            }

        }
        System.out.println("Programa Fechando");
    }
}