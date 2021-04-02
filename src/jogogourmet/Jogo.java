package jogogourmet;

import java.util.ArrayList;
import java.util.List;

public class Jogo {

    private  String nome;
    private String questaoInicial; 
    private List<Opcao> opcoes = new ArrayList<Opcao>();

    public Jogo(String nome, String questaoInicial){
        this.nome = nome;
        this.questaoInicial = questaoInicial;
    }

    public String getNome(){
        return this.nome;
    }

    public String getTextoQuestaoInicial(){
        return "Pense em um " +  this.questaoInicial + " que você gosta";
    }
    public String getPrefixoDialog(){
        return "O "+this.questaoInicial+" que voce pensou é ";
    }
    
    public void addOpcao(Opcao opcao){
        opcoes.add(opcao);
    }

    public List<Opcao> getOpcoes(){
        return this.opcoes;
    }
}