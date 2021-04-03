
package jogogourmet;

public class Opcao implements Comparable<Opcao>{

    private String nome;
    private String tipo;

    public Opcao(String nome, String tipo){
        this.nome = nome;
        this.tipo = tipo;
    }
     public String getNome(){
         return this.nome;
     }
     public String getTipo() {
         return this.tipo;
     }
     public void setTipo(String tipo) {
    	 this.tipo = tipo;
     }
     
     public int compareTo(Opcao opcao) {
         return  opcao.tipo.compareTo(tipo);
     }

}