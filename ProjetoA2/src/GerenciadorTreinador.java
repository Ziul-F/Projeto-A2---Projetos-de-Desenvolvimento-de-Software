import java.util.Scanner;

public class GerenciadorTreinador implements CatalogoTreinador{
    Menus menu = new Menus();
    Scanner scanner = new Scanner(System.in);
    
    public void gerenciadorTreinador()
    {
        int escolhaMenuTReinador = 0;
        
        escolhaMenuTReinador = scanner.nextInt();

        do{
            switch (escolhaMenuTReinador) {
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
        while(escolhaMenuTReinador != 6);


    };

    @Override
    public void adicionarTreinador(){};
    @Override
    public void deletarIformacaoTreinador(){};
    @Override
    public void buscarTreinador(){};
    @Override
    public void listarTreinador(){};
    @Override
    public void mudarInformacaoTreinador(){};
}
