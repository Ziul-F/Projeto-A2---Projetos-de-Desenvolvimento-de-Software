import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorPokemon gerenciadorPokemon = new GerenciadorPokemon();

        int escolhaMenuPrincipal = 0;
        do
        {
            System.out.println("-- BEM VINDO AO GERENCIADOR DE POKEMONS --");
            System.out.println("--------------------------------------");
            System.out.println("Para entrar escolha um número.");
            System.out.println("1 - Gerenciar Pokédex;");
            System.out.println("2 - Gerenciar Treinadores.");
            System.out.println("3 - Sair");
            System.out.println("--------------------------------------");
            System.out.print("Sua escolha:");
            escolhaMenuPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch(escolhaMenuPrincipal){
                case 1:
                    gerenciadorPokemon.menuGerenciadorPokemon();
                break;
                case 2:
                    // gerenciadorTreinador.menuGerenciadorTreinador;
                break;
            }
        }
        while(escolhaMenuPrincipal != 3);
        System.out.println("Programa Fechando");
        scanner.close();
    }
}