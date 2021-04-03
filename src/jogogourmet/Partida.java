package jogogourmet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import jogogourmet.utils.ConfirmeDialog;

public class Partida {
    private  Jogo jogo;
    private  Set<String> excTipos = new HashSet<String>();
    private  Set<String> excPratos = new HashSet<String>();
   
    public Partida(Jogo jogo) {
        this.jogo = jogo;
    }
     
    public static void main(final String[] args) {
        // Configuração inicial da partida
        final Jogo novoJogo = new Jogo("Jogo Gourmet", "prato");
        novoJogo.addOpcao(new Opcao("Lasanha", "massa"));
        novoJogo.addOpcao(new Opcao("Bolo de Chocolate", ""));
        // Cria uma nova partida para o novo jogo
        Partida partida = new Partida(novoJogo);
        partida.jogar(partida);
    }
    
    public void jogar(final Partida partida){
        
    	boolean terminado = false;
        do {
            terminado = executa();
         } while (!terminado);
    }
    
    private boolean executa() {
        // Reinicializa as variáveis de controle da rodada
        excPratos.clear();        // Mantém a lista de pratos que o usuario diz que não é 
        excTipos.clear();         // Mantém a lista de tipos de prato que o usuario diz que não é
        String confTipo = null;   // Mantém o tipo do prato que o usuario diz que é
        String confPrato = null;  // Mantem o nome do prato que o usuario diz que é
        Opcao nopcao = null;
        boolean acertou = false;  // Controla o acerto
        boolean abortou = false;  // Abotar volta ao inicio do jogo
        boolean cancelou = false; // Cancelar encerra o jogo
 
        // Recupera a lista de opções jogo;
        List<Opcao> opcs = new ArrayList<Opcao>(this.jogo.getOpcoes());
        // Faz o questionamento inicial (Pense em um ...)
        int questaoInicial = (int) ConfirmeDialog.show(jogo.getTextoQuestaoInicial(), this.jogo.getNome(), false, -1, new String[] { "Ok, pensei", "Sair" });
        // Encerra o jogo se fechar a janela ou clicar no botao sair
        if (questaoInicial == 1 || questaoInicial == -1) return true;  
        // Percorre as opcoes do questionario
        for (final Opcao opc : opcs) {
            // Ignora todos os pratos do a tipo que o usuário já disse não ser
            if (excTipos.contains(opc.getTipo())) continue;
            // Ignora todos os pratos nomes de prato que o usuário já disse não ser
            if (excPratos.contains(opc.getNome())) continue;
            // Mostra apenas o tipo de prato que o usuário diz ser
            if (confTipo != null && !confTipo.equals(opc.getTipo())) continue;

            confPrato = opc.getNome(); // Sava o tipo atual
            nopcao = opc;
            // Questiona o tipo se ainda não é sabido
            if (confTipo == null && !opc.getTipo().equals("")){

                 // Monta texto para tipo do prato
                 final String msg = this.jogo.getPrefixoDialog() + opc.getTipo()+"?";
                 // Chama o dialogo para perguntar o tipo do prato
                 int res = (int) ConfirmeDialog.show(msg, "Confirme", false, 3, new String[] { "Sim", "Não" });
                 // Trata cancelamento
                 if (res == -1) { 
                 	abortou = true; 
                 	break;
                 }

                 // Se respondeu não, adiciona na lista de excluidos para verificações futuras
                 if (res == 1) {
                    excTipos.add(opc.getTipo());
                    continue;
                 } else {
                     // Salva o tipo confirmado para apresentar somente este nesta rodada
                     confTipo = opc.getTipo();
                 }

            }

            // Se o usuario disse sim para o tipo, questiona sobre o nome prato
            // Monta o texto para perguntar o nome prato
            final String msg =  this.jogo.getPrefixoDialog() + opc.getNome()+"?";

            // Chama o dialogo para perguntar o tipo do prato
            int res = (int) ConfirmeDialog.show(msg, "Confirme", false, 3, new String[] { "Sim", "Não" });
            // Trata cancelamento
            if (res == -1) { 
            	abortou = true;
            	break;
            }
            // Se respondeu sim, mostra mensagem de acerto 
            if (res == 0) {
                res = (int) ConfirmeDialog.show("Acertei de novo!", this.jogo.getNome(), false, 1, new String[] { "Ok" });
                acertou = true;
                break;
            } else {
            	excPratos.add(opc.getNome());
            }
        }

        // Se não acertou, desiste e pergunta qual foi o prato pensado
        if (!acertou && !abortou ){
            // Apresenta o dialogo questionando qual foi o prato pensado
            Object nprato = ConfirmeDialog.show("Qual prato você pensou?","Desisto", true,1);
            // Se não concelou o dialogo, pergunta qual o tipo do prato pensado
            if (nprato != null) {
            	// Considera trapaça se o usuário informa o nome de um prato que já havia dito que não éra anteriormente
               	if (excPratos.contains(nprato)) {
            		ConfirmeDialog.show("Mas você disse que não éra "+nprato + ".\nNão vou considerar isso", "Trapaça", false, 1,new String[] { "Ok" });
               	} else {
	                // Apresenta o dialogo questionado qual o tipo do prato pensado
	                Object  ntipo =  ConfirmeDialog.show(nprato +" é _______ mas "+confPrato+" não","Complete", true, 1);
	                // Se não cancelou o dialogo adiciona o prato na lista de opcoes;
	                if (ntipo != null) {
	                	// Considera trapaça se informar um tipo prato que já havia dito anteriormente que não era
	                	if (excTipos.contains(ntipo)) {
	                		ConfirmeDialog.show("Mas você disse que não éra "+ ntipo + ".\nNão vou considerar isso", "Trapaça", false, 1,new String[] { "Ok" });
	                	// Consider trapaça se informar um tipo de prato diferente do tipo que afirmou que era
	                	} else if(confTipo != null && !ntipo.equals(confTipo)) {
	                		ConfirmeDialog.show("Mas você disse que era "+confTipo + ".\nNão vou considerar isso", "Trapaça", false, 1,new String[] { "Ok" });
	                	} else {
	                		String verificaprato = this.verificaPrato((String)ntipo, (String)nprato);
	                		if (verificaprato != null){
		                		ConfirmeDialog.show("Penso que "+ nprato +" é "+ verificaprato +",\nmas se voce diz que é "+ntipo+", tudo bem" + ".\nVou considerar como um novo tipo de "+nprato, "Divergencia", false, 1,new String[] { "Ok" });
	                		}
	                		// Adiciona a opcao na lista de opcoes
		                	jogo.addOpcao(new Opcao((String) nprato, (String) ntipo));
		                	// Muda para não sabido o tipo do em questão  
		                	jogo.mudaTipo(nopcao);
		              
		            	}
	                } else cancelou = true; // Clicou no botão cancelar ao informar nome do prato
	            }
             } else cancelou = true; // Clicou no botão cancelar ao informar o tipo do prato
        }
        return cancelou;
    }
    
    private String verificaPrato(String tipo, String nome){
    	for (Opcao opc : this.jogo.getOpcoes()) {
    		if (opc.getNome().equals(nome)) {
    			if (!opc.getTipo().equals(tipo)){
    				return opc.getTipo();
    			}
    		}
    	}
    	return null;
    }
}
