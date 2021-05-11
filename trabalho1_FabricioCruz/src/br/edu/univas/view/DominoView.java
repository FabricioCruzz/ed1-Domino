package br.edu.univas.view;

public class DominoView {
	
	public void printBoasVindas() {
		System.out.println("::::SEJA BEM-VINDO AO DOMINÓ::::\n");
	}
	
	public void printAndamentoJogo(String a, String b) {
		System.out.println(a + b);
	}
	
	public void printVezDoComputador() {
		System.out.println("\nÉ a vez do Computador...");
	}
	
	public void printComputadorPassandoVez() {
		System.out.println("Computador realizou a jogada...");
		System.out.println("Computador passando a vez...\n");
	}
	
	public void printVezDoJogador() {
		System.out.println("\nJogador é a sua vez...\n");
	}
	
	public void printMensagemJogador() {
		System.out.println("Jogador, essas são as suas peças:");
	}
	
	public void printPassarVez() {
		System.out.println("0 - Passar a vez?");
		System.out.println("99 - Comprar Peça");
	}
	
	public void printDigitarNumeroCorrespondente() {
		System.out.println("Digite o número correspondente a peça que deseja jogar:");
	}
	
	public void printPecaComNumeracao(int number, String s) {
		System.out.println(number + " - " + s);
	}
	
	public void printMensagemMonteZerado() {
		System.out.println("O monte está zerado. Não é possível comprar peças!");
	}
	
	public void printMensagemPecaNãoCoincideComMesa() {
		System.out.println("Peça escolhida não coincide com a mesa!");
		System.out.println("Por favor, escolha outra peça ou passe a vez!");
	}
	
	public void printMensagemErroJogada() {
		System.out.println("ERRO: Jogada não permitida, pois os lados não coincidem!");
		System.out.println("Por favor, tente novamente!\n");
	}
	
	public void printJogarNasExtremidades(String a, String b) {
		System.out.println("Você deseja joga em qual extremidade?");
		System.out.println("1 - " + a);
		System.out.println("2 - " + b);
	}
	
	public void printOpcaoInvalida() {
		System.out.println("Opção informada foi inválida! \nPor favor, digite um valor válido!!!");
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
