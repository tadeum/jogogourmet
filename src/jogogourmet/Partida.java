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
        novoJogo.addOpcao(new Opcao("Bolo de Chocolate", "bolo"));

        // Cria uma nova partida para o novo jogo
        Partida partida = new Partida(novoJogo);
        partida.jogar(partida);
    }

    public void jogar(final Partida partida){
    
        // Mantem o tipos que o usuario diz não ser.
        this.excTipos = new HashSet<String>();

        // Mantem os pratos que o usuario diz não ser.
        this.excPratos = new HashSet<String>();

        // Marca o termino do jogo
        boolean terminado = false;

        // Jogando
        do {
            terminado = questiona();
         } while (!terminado);

    }

    private boolean questiona() {

        // Reinicializa as variaveis de controle
        excPratos.clear();
        excTipos.clear();
        String confTipo = null; 
        String confPrato = null;
        boolean acertou = false;
        boolean cancelou = false;
  
        // Recupera a lista de opções jogo;
        List<Opcao> opcs = new ArrayList<Opcao>(this.jogo.getOpcoes());
  
        // Faz o questionamento inicial (Pense em um prato...)
        int questaoInicial = (int) ConfirmeDialog.show(jogo.getTextoQuestaoInicial(), this.jogo.getNome(), false, -1, new String[] { "Ok, pensei", "Sair" });
        // Encerra o jogo se fechar a janela ou clicar no botao sair
        if (questaoInicial == 1 || questaoInicial == -1) return true;  

        // Percorre as opcoes do questionario
        for (final Opcao opc : opcs) {

            // Ignora todos os pratos com a qualidade que o usuario disse que não e
            if (excTipos.contains(opc.getTipo())) continue;
            // Passa a mostrar apenos pratos do tipo que o usuario disse que é
            if (confTipo != null && !confTipo.equals(opc.getTipo())) continue;

            
            confPrato = opc.getNome(); // Sava o tipo atual

            // Questiona o tipo se ainda não é sabido
            if (confTipo == null){

                 // Monta texto para perguntar o tipo
                 final String msg = this.jogo.getPrefixoDialog() + opc.getTipo()+"?";

                 // Chama o dialogo para perguntar o tipo do prato
                 int res = (int) ConfirmeDialog.show(msg, "Confirme", false, 3, new String[] { "Sim", "Não" });

                 // Se respondeu não, adiciona na lista de excluidos 
                 // para não questionar novamente este tipo
                 if (res == 1) {
                    excTipos.add(opc.getTipo());
                    continue;
                 } else {
                     // Salva o tipo confirmado para apresentar somente este nesta rodada
                     confTipo = opc.getTipo();
                 }
            }

            // Se o usuario disse sim para o tipo, questiona sobre o prato

            // Monta o texto para perguntar o prato
            final String msg =  this.jogo.getPrefixoDialog() + opc.getNome()+"?";

            // Chama o dialogo para perguntar o tipo do prato
            int res = (int) ConfirmeDialog.show(msg, "Confirme", false, 3, new String[] { "Sim", "Não" });
   
            // Se respondeu sim, mostra mensagem de acerto 
            if (res == 0) {
                res = (int) ConfirmeDialog.show("Acertei de novo!", this.jogo.getNome(), false, 1, new String[] { "Ok" });
                acertou = true;
                break;
            }
        }

        // Se não acertou, desiste e pergunta qual foi o prato pensado
        if (!acertou){

            // Apresenta o dialogo questionando qual foi o prato pensado
            Object nprato = ConfirmeDialog.show("Qual prato você pensou?","Desisto", true,1);

            // Se não concelou o dialogo, pergunta qual o tipo do prato pensado
            if (nprato == null) {
                cancelou = true;
            } else {
              // Apresenta o dialogo questionado qual o tipo do prato pensado
              Object  ntipo =  ConfirmeDialog.show(nprato +" é _______ mas "+confPrato+" não","Complete", true, 1);
              // Se não cencelou o dialogo acrescenta o prato na lista de opcoes do jogo
              if (ntipo == null) {
                cancelou = true;
              } else {
                  jogo.addOpcao(new Opcao((String) nprato, (String) ntipo));
              }
            }
        }

        return cancelou;
    }



        
    }
