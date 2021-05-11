package br.edu.univas.view;

public class DominoView {
	
	public void printBoasVindas() {
		System.out.println("::::SEJA BEM-VINDO AO DOMIN�::::\n");
	}
	
	public void printAndamentoJogo(String a, String b) {
		System.out.println(a + b);
	}
	
	public void printVezDoComputador() {
		System.out.println("\n� a vez do Computador...");
	}
	
	public void printComputadorPassandoVez() {
		System.out.println("Computador realizou a jogada...");
		System.out.println("Computador passando a vez...\n");
	}
	
	public void printVezDoJogador() {
		System.out.println("\nJogador � a sua vez...\n");
	}
	
	public void printMensagemJogador() {
		System.out.println("Jogador, essas s�o as suas pe�as:");
	}
	
	public void printPassarVez() {
		System.out.println("0 - Passar a vez?");
		System.out.println("99 - Comprar Pe�a");
	}
	
	public void printDigitarNumeroCorrespondente() {
		System.out.println("Digite o n�mero correspondente a pe�a que deseja jogar:");
	}
	
	public void printPecaComNumeracao(int number, String s) {
		System.out.println(number + " - " + s);
	}
	
	public void printMensagemMonteZerado() {
		System.out.println("O monte est� zerado. N�o � poss�vel comprar pe�as!");
	}
	
	public void printMensagemPecaN�oCoincideComMesa() {
		System.out.println("Pe�a escolhida n�o coincide com a mesa!");
		System.out.println("Por favor, escolha outra pe�a ou passe a vez!");
	}
	
	public void printMensagemErroJogada() {
		System.out.println("ERRO: Jogada n�o permitida, pois os lados n�o coincidem!");
		System.out.println("Por favor, tente novamente!\n");
	}
	
	public void printJogarNasExtremidades(String a, String b) {
		System.out.println("Voc� deseja joga em qual extremidade?");
		System.out.println("1 - " + a);
		System.out.println("2 - " + b);
	}
	
	public void printOpcaoInvalida() {
		System.out.println("Op��o informada foi inv�lida! \nPor favor, digite um valor v�lido!!!");
	}
	
	public void printJogadorVenceu() {
		System.out.println("O jogador ganhou o jogo!");
	}
	
	public void printEmpate() {
		System.out.println("O jogo resultou em um empate!");
	}
	
	public void printComputadorVenceu() {
		System.out.println("O computador venceu!");
	}
}
