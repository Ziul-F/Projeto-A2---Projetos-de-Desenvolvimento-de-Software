package View;

import Service.GerenciadorPokemon;
import Service.GerenciadorTreinador;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorPokemon gerenciadorPokemon = new GerenciadorPokemon();
        GerenciadorTreinador gerenciadorTreinador = new GerenciadorTreinador();
        Menus menu = new Menus();

        int escolhaMenuPrincipal = 0;
        do
        {
            menu.menuPrincipal();
            escolhaMenuPrincipal = scanner.nextInt();
            scanner.nextLine();

            if ((escolhaMenuPrincipal == 1) || (escolhaMenuPrincipal == 2) || (escolhaMenuPrincipal == 3)){
                switch(escolhaMenuPrincipal){
                    case 1:
                        gerenciadorPokemon.gerenciadorPokemon();
                    break;
                    case 2:
                        gerenciadorTreinador.gerenciadorTreinador();
                    break;
                }
            }
            else{
                System.out.println("Escolha um número válido");
                System.out.println("------------------------------------------");
            }
        }
        while(escolhaMenuPrincipal != 3);
        System.out.println("------------------------------------------");
        System.out.println("Programa Fechando");
        scanner.close();
    }
}