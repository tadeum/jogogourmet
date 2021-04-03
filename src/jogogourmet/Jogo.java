package jogogourmet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    // Altera o tipo para '' para ir para o final da lista
    public void mudaTipo(Opcao opcao) {
    	int idx = this.getOpcoes().indexOf(opcao);
    	this.getOpcoes().get(idx).setTipo("");
    	reordenaOpcoes();
    }

    // Reordena para deixar as indefinidas no final da lista
    public void reordenaOpcoes() {
    	 Collections.sort(this.getOpcoes());
    }
    
    public List<Opcao> getOpcoes(){
        return this.opcoes;
    }
}