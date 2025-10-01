package Dados;

import Service.Pokemon;
import Service.Treinador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logs {
    private static final String logPath = "log.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private String getTimestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }

    private void escreverLog(String mensagem) {
        try (FileWriter fileWriter = new FileWriter(logPath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            bufferedWriter.write(mensagem);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }


    public void gravarCadastroLogTreinador(Treinador treinador){
       int idTreinador = treinador.getTreinadorId();
        String mensagem = String.format("[%s] usuário admin cadastrou um treinador de id %d para o banco de dados.", getTimestamp(), idTreinador);
        escreverLog(mensagem);
    }
    public void gravarDeletLogTreinador(Treinador treinador){
        int idTreinador = treinador.getTreinadorId();
        String mensagem = String.format("[%s] usuário admin deletou um treinador de id %d para o banco de dados.", getTimestamp(), idTreinador);
        escreverLog(mensagem);
    }
    public void gravarAtualizacaoLogTreinador(Treinador treinador){
        int idTreinador = treinador.getTreinadorId();
        String mensagem = String.format("[%s] usuário admin atualizou uma informação de um treinador de id %d para o banco de dados.", getTimestamp(), idTreinador);
        escreverLog(mensagem);
    }
    public void gravarCadastroLogPokemon(Pokemon p){
        int idPokemon = p.getIdPokemon();
        String mensagem = String.format("[%s] usuário admin cadastrou um pokemon de id %d para o banco de dados.", getTimestamp(), idPokemon);
        escreverLog(mensagem);
    }
    public void gravarAtualizacaoLogPokemon(Pokemon p){
        int idPokemon = p.getIdPokemon();
        String mensagem = String.format ("[%s] usuário admin atualizou uma informação de um pokemon de id %d para o banco de dados.", getTimestamp(), idPokemon);
        escreverLog(mensagem);
    }
    public void gravarDeletLogPokemon(Pokemon p){
        int idPokemon = p.getIdPokemon();
        String mensagem = String.format ("[%s] usuário admin deletou um pokemon de id %d para o banco de dados.", getTimestamp(), idPokemon);
        escreverLog(mensagem);
    }
}
