package br.edu.univas.main;

import java.util.Random;
import java.util.Scanner;

import br.edu.univas.view.DominoView;
import br.edu.univas.vo.ListaPecas;
import br.edu.univas.vo.Peca;
import br.edu.univas.vo.PecaIterador;

public class StartApp {

	private static Random random = new Random();
	private static Scanner sc = new Scanner(System.in);
	private static DominoView view = new DominoView();
	
	public static void main(String[] args) {
		
		ListaPecas monte = criaPecas();		

		ListaPecas computador = distribuiPecas(monte);
		
		ListaPecas jogador = distribuiPecas(monte);
		
		ListaPecas mesa = new ListaPecas();
		
		Peca maiorPecaComputador = procuraMaiorPeca(computador);
		Peca maiorPecaJogador = procuraMaiorPeca(jogador);
		 
//		 Variável usada para controlar a vez de cada Jogador. Quando está true, a vez é do computador e quando está false, a vez é do jogador.
		boolean vezComputador = verificaQuemComecaJogo(maiorPecaComputador, maiorPecaJogador);
		
//		PRIMEIRA JOGADA COM BASE EM QUEM INICIA
		vezComputador = realizaPrimeiraJogada(vezComputador, mesa, computador, jogador, maiorPecaComputador, maiorPecaJogador);
		
		boolean jogadorVenceu = false;
		boolean empate = false;
		boolean fimDeJogo = false;
		
		view.printBoasVindas();
		
		while(!fimDeJogo) {
			
			view.printAndamentoJogo("::::Andamento do Jogo::::\n", mesa.getAsString());
						
			if(vezComputador) {
							
				view.printVezDoComputador();
				
				jogadaComputador(mesa, computador);
				
//				Verifica se o computador conseguiu jogar todas as peças.
				fimDeJogo = verificaQuantidadePecasRestantes(computador);
				
				boolean temPeca = verificaPossibilidadeDeContinuar(computador, mesa);
		
				if(!temPeca) {
//					Computador precisou comprar peça.
					Peca p = new Peca(); 
					p = compraPeca(monte);
					if(p != null) {
						computador.insert(p);
						monte.remove(p);
//						Computador tenta jogar novamente após a compra da peça.
						jogadaComputador(mesa, computador); 
					}
				}
				
				view.printComputadorPassandoVez();
				vezComputador = false;
			}
			else {
				view.printVezDoJogador();

				imprimePecasJogador(jogador);
				int option = readInteger();

				option = validaOpcaoInvalida(option, jogador);
				
				if(option == 0) {
					vezComputador = true;
					continue;
				}
				if(option == 99) {
					executaFuncaoComprar(monte, mesa, jogador, vezComputador, fimDeJogo);
				}
				else {
					Peca px = jogador.getElementAt((option - 1));
					
					px = validarPecaASerJogada(mesa, jogador, px);
					
					jogadaJogador(mesa, px, jogador, fimDeJogo);
				}
				vezComputador = true;					
				fimDeJogo = verificaQuantidadePecasRestantes(jogador);
				if(fimDeJogo) {
					jogadorVenceu = true;
				}
			}
						
				boolean computadorPodeContinuar = verificaPossibilidadeDeContinuar(computador, mesa);
				boolean jogadorPodeContinuar = verificaPossibilidadeDeContinuar(jogador, mesa);
					
				if(!computadorPodeContinuar && !jogadorPodeContinuar && monte == null) {
					int somatorioCPU = realizaSomatorioDasPecas(computador);
					int somatorioJogador = realizaSomatorioDasPecas(jogador);
						
					if(somatorioJogador < somatorioCPU) {
						jogadorVenceu = true;
					}
					else if(somatorioJogador == somatorioCPU) {
						empate = true;
					}
					fimDeJogo = true;
				}
		}
		
		if(fimDeJogo) {
			if(jogadorVenceu) {
				view.printJogadorVenceu();
			}
			else if(empate) {
				view.printEmpate();
			}			
			else{
				view.printComputadorVenceu();
			}
			System.out.println();
			view.printAndamentoJogo("::::Resultado Final::::\n", mesa.getAsString());
		}

		sc.close();
	}

	/**
	 * Este método executa um loop para a função de comprar peças por parte do jogador.
	 */
	public static void executaFuncaoComprar(ListaPecas monte, ListaPecas mesa, ListaPecas jogador,
			boolean vezComputador, boolean fimDeJogo) {
		
		int option;
		boolean swap = false;
		
		while(!swap) {
			Peca p = new Peca(); 
			p = compraPeca(monte);
			if(p != null) {
				jogador.insert(p);
				monte.remove(p);	
			
//				Jogador tenta jogar novamente após a compra da peça
				imprimePecasJogador(jogador);
				option = readInteger();
				option = validaOpcaoInvalida(option, jogador);

				if(option == 0) {
					vezComputador = true;
					swap = true;
					break;
				}
				else if(option == 99) {
					continue;
				}
				else {
					Peca px = jogador.getElementAt((option - 1));
					px = validarPecaASerJogada(mesa, jogador, px);
					jogadaJogador(mesa, px, jogador, fimDeJogo);
					vezComputador = true;
					break;
				}
			}
			else {
				view.printMensagemMonteZerado();
				break;
			}
		}
		
	}

	/**
	 * Este método cria as peças a serem usadas no jogo
	 */
	public static ListaPecas criaPecas() {
		
		ListaPecas lista = new ListaPecas();
		
		int count = 0;
		for (int i = 6; i >= 0; i--) {
			for (int j = 0; j <= 6 - count; j++) {
				Peca p = new Peca();
				p.setLado1(i);
				p.setLado2(j);
				
				lista.insert(p);
			}
			count++;
		}
		
		return lista;
	}
	
	/**
	 * Este método realiza a distribuição das peças de maneira aleatória para o computador e o jogador
	 */
	public static ListaPecas distribuiPecas(ListaPecas monte) {
		
		
		ListaPecas lista = new ListaPecas();
		
		for (int i = 0; i < 7; i++) {
			int size = monte.length();
			int aleatorio = random.nextInt(size);
			
			Peca p = new Peca(); 
			p = monte.getElementAt(aleatorio);
			lista.insert(p);
			monte.remove(p);
		}
		
		return lista;
	}
	
	/**
	 * Este método realiza a busca da peça de maior valor, começando a busca por peças iguais e caso não encontre, busca por peças de lados diferentes
	 */
	public static Peca procuraMaiorPeca(ListaPecas lista) {
		
		PecaIterador iterador = lista.getIterador();
		
		Peca aux = new Peca();
		
		boolean pecaIgual = false;
		
		// Procura peça com lados iguais
		while(iterador.existePeca()) {
			Peca pecaAtual = iterador.getCurrent();
			
			if(pecaAtual.getLado1() == pecaAtual.getLado2()) {				
				aux = pecaAtual;
				pecaIgual = true;
				break;
			}
			iterador.irParaProximo();
		}
		
		if(pecaIgual) {			
			// Procura saber qual peça com lados iguais é maior
			
			//Reseta Iterador para o HEAD da Lista
			iterador = lista.getIterador();
			while(iterador.existePeca()) {
				Peca pecaAtual = iterador.getCurrent();
				
				if(pecaAtual.getLado1() == pecaAtual.getLado2()) {
					if(pecaAtual.getLado1() > aux.getLado1()) {
						aux = pecaAtual;
					}
				}
				
				iterador.irParaProximo();
			}
		}
		if(!pecaIgual) {
			int somaAux = 0;
			iterador = lista.getIterador();
			
			while(iterador.existePeca()) {
				Peca pecaAtual = iterador.getCurrent();
				
				int soma = pecaAtual.getLado1() + pecaAtual.getLado2();
				
				if(soma > somaAux) {
					somaAux = soma;
					aux = pecaAtual;
				}
				iterador.irParaProximo();
			}
		}
		return aux;
	}
	
	/**
	 * Este método retorna um valor booleano para saber quem começa a partida.
	 * 
	 * Se retornar true, o computador inicia a partida, caso retorne false, é o jogador quem inicia.
	 */
	public static boolean verificaQuemComecaJogo(Peca maiorPecaComputador, Peca maiorPecaJogador) {
		
		boolean pecasComputadorIguais = false;
		boolean pecasJogadorIguais = false;
		
		if(maiorPecaComputador.getLado1() == maiorPecaComputador.getLado2()) {
			pecasComputadorIguais = true;
		}
		
		if(maiorPecaJogador.getLado1() == maiorPecaJogador.getLado2()) {
			pecasJogadorIguais = true;
		}
		
		//Se a peça do jogador e a peça do computador forem de lados iguais
		if(pecasComputadorIguais && pecasJogadorIguais) {
			if(maiorPecaComputador.getLado1() > maiorPecaJogador.getLado1()) {
				return true;
			}
			else {
				return false;
			}
		}
		else if(!pecasComputadorIguais && !pecasJogadorIguais) {
			int somaCPU = maiorPecaComputador.getLado1() + maiorPecaComputador.getLado2();
			int somaHumano = maiorPecaJogador.getLado1() + maiorPecaJogador.getLado2();
			
			if(somaCPU > somaHumano) {
				return true;
			}
			else {
				return false;
			}
		}
		else if(pecasComputadorIguais && !pecasJogadorIguais) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Este método realiza a primeira jogada com base nos critérios de maior peça com lados iguais ou da maior peça cuja soma de lados é maior.
	 */
	public static boolean realizaPrimeiraJogada(boolean vezComputador, ListaPecas mesa, ListaPecas computador, ListaPecas jogador, Peca maiorPecaComputador, Peca maiorPecaJogador) {
		if(vezComputador) {//Computador incia a partida
			mesa.insert(maiorPecaComputador);
			
			computador.remove(maiorPecaComputador);
		}
		else {// Jogador inicia a partida
			mesa.insert(maiorPecaJogador);
			
			jogador.remove(maiorPecaJogador);
			
			return true;
		}
		return false;
	}
	
	/**
	 * Este método realiza a jogada do computador considerando a peça na mesa.
	 */
	public static void jogadaComputador(ListaPecas mesa, ListaPecas computador) {
		
		Peca inicio = mesa.retornaPrimeiraPecaDaLista();
		Peca fim = mesa.retornaUltimaPecaDaLista();
		
		PecaIterador it = computador.getIterador();
		
		while(it.existePeca()) {
			Peca p = it.getCurrent();								
						
			int lado1 = p.getLado1();
			int lado2 = p.getLado2();
						
						
			if(lado1 == lado2) { // Peça de lados iguais, por exemplo: 2|2 , 3|3 ...
							
				if(fim != null) {
								
						if(lado1 == inicio.getLado1()) {
							mesa.jogada(p, 1);
							computador.remove(p);
							break;

						}
						else if(lado1 == fim.getLado2()) {
							mesa.jogada(p, 2);
							computador.remove(p);
							break;

						}
						else {
							it.irParaProximo();
						}
				}
				else {
								
					if(lado1 == inicio.getLado1()) {
						mesa.jogada(p, 1);
						computador.remove(p);
						break;

					}
					else if(lado1 == inicio.getLado2()) {
						mesa.jogada(p, 2);
						computador.remove(p);
						break;
					}
					else {
						it.irParaProximo();
					}
				}
			}
			else if(lado1 != lado2) { // Peça de lados diferentes, por exemplo: 2|4 , 6|1 ...
						
				if(fim != null) {
							
					if(lado1 == inicio.getLado1() || lado2 == inicio.getLado1()) {
						mesa.jogada(p, 1);
						computador.remove(p);
						break;
					}
					else if(lado1 == fim.getLado2() || lado2 == fim.getLado2()) {
						mesa.jogada(p, 2);
						computador.remove(p);
						break;
					}
					else {
						it.irParaProximo();
					}
				}
				else {
								
					if(lado1 == inicio.getLado1() || lado2 == inicio.getLado1()) {
						mesa.jogada(p, 1);
						computador.remove(p);
						break;
					}
					else if(lado1 == inicio.getLado2() || lado2 == inicio.getLado2()) {
						mesa.jogada(p, 2);
						computador.remove(p);
						break;
					}
					else {
						it.irParaProximo();
					}
				}									
			}
		}
	}
	
	/**
	 * Este método realiza uma compra aleatória do monte e retorna a peça.
	 */
	public static Peca compraPeca(ListaPecas monte) {
		
		Peca p = null;
		int tamanhoMonte = monte.length();
		if(tamanhoMonte <= 0) {
			return p;
		}
		if(monte != null) {
			int aleatorio = random.nextInt(tamanhoMonte);
			p = monte.getElementAt(aleatorio);
		}
	return p;
	}
	
	/**
	 * Este método realiza a verificação de peças restantes na lista passada como parâmetro.
	 */
	public static boolean verificaQuantidadePecasRestantes(ListaPecas lista) {
		
		int qtdPecas = lista.length();
		if(qtdPecas == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método usado para imprimir as peças que o Jogador possui em mãos
	 */
	public static void imprimePecasJogador(ListaPecas jogador) {
		PecaIterador it = jogador.getIterador();
		int count = 0;
		view.printMensagemJogador();
		while(it.existePeca()) {
			Peca p = it.getCurrent();
			view.printPecaComNumeracao(count + 1, p.toString());
			count++;
			it.irParaProximo();
		}
		view.printPassarVez();
		view.printDigitarNumeroCorrespondente();
	}
	
	/**
	 * Este método realiza a validação caso o jogador digite um valor inválido para a escolha de peça a ser jogada.
	 */
	public static int validaOpcaoInvalida(int option, ListaPecas lista) {
		
		int qtdPecas = lista.length();
		
		if(option < 0 ||(option > qtdPecas && option != 99)) {
			while(option < 0 || (option > qtdPecas && option != 99)) {
				view.printOpcaoInvalida();
				imprimePecasJogador(lista);
				option = readInteger();
			}
			return option;
		}
		return option;
	}
	
	/**
	 * Este método realiza a validação da peça escolhida pelo jogador a fim de se avaliar se é compatível com a mesa.
	 * Caso não seja ele realiza um loop até o jogador digitar corretamente.
	 */
	public static Peca validarPecaASerJogada(ListaPecas mesa, ListaPecas jogador, Peca px) {
		
		Peca primeiraPecaLista = mesa.retornaPrimeiraPecaDaLista();
		Peca ultimaPecaLista = mesa.retornaUltimaPecaDaLista();
					
		int lado1Px	= px.getLado1();
		int lado2Px	= px.getLado2();
		
		int option;
		
		boolean swap = false;
		
		while(!swap) {							
			if(ultimaPecaLista == null) {
				if(lado1Px != primeiraPecaLista.getLado1() && lado1Px != primeiraPecaLista.getLado2() && lado2Px != primeiraPecaLista.getLado1() && lado2Px != primeiraPecaLista.getLado2()) {
								
					view.printMensagemPecaNãoCoincideComMesa();
					view.printAndamentoJogo("::::Andamento do Jogo::::\n", mesa.getAsString());
					imprimePecasJogador(jogador);
					option = readInteger();
								
						if(option == 0) {
							swap = true;
						}
						else {
							px = jogador.getElementAt(option - 1);
							lado1Px = px.getLado1();
							lado2Px = px.getLado2();
								
							return px;
						}	
					}
					else {
						swap = true; 
					}
				}
				else {
					if(lado1Px != primeiraPecaLista.getLado1() && lado2Px != primeiraPecaLista.getLado1() && lado1Px != ultimaPecaLista.getLado2() && lado2Px != ultimaPecaLista.getLado2()) {
								
						view.printMensagemPecaNãoCoincideComMesa();
						view.printAndamentoJogo("::::Andamento do Jogo::::\n", mesa.getAsString());
						imprimePecasJogador(jogador);
						option = readInteger();
								
						if(option == 0) {
							swap = true;
						}
						else {
							px = jogador.getElementAt(option - 1);
							lado1Px = px.getLado1();
							lado2Px = px.getLado2();								
						return px;
						}
					}
					else {
						swap = true;
					}
				}
			}
		return px;
	}
	
	/**
	 * Este método realiza a jogada do Jogador na mesa, passando por processos de validação.
	 */
	public static void jogadaJogador(ListaPecas mesa, Peca px, ListaPecas jogador, boolean fimDeJogo) {
		
		Peca primeiraPecaLista = mesa.retornaPrimeiraPecaDaLista();
		Peca ultimaPecaLista = mesa.retornaUltimaPecaDaLista();
					
		int lado1Px	= px.getLado1();
		int lado2Px	= px.getLado2();
		
		int number;
		
		if(ultimaPecaLista == null) {
			view.printJogarNasExtremidades("Esquerda", "Direita");
			number = readInteger();
					
			if(number == 1 && (lado1Px == primeiraPecaLista.getLado1() || lado2Px == primeiraPecaLista.getLado1())) {
				mesa.jogada(px, number);
			}
			else if(number == 2 && (lado1Px == primeiraPecaLista.getLado2() || lado2Px == primeiraPecaLista.getLado2())) {
				mesa.jogada(px, number);
			}
			else {
				validaJogadaNasPontasDaMesa(mesa, px, primeiraPecaLista, ultimaPecaLista);
			}		
		}
		else {
			view.printJogarNasExtremidades(primeiraPecaLista.toString(), ultimaPecaLista.toString());
			number = readInteger();
					
			if(number == 1 && (lado1Px == primeiraPecaLista.getLado1() || lado2Px == primeiraPecaLista.getLado1())) {
				mesa.jogada(px, number);
			}
			else if(number == 2 && (lado1Px == ultimaPecaLista.getLado2() || lado2Px == ultimaPecaLista.getLado2())) {
				mesa.jogada(px, number);
			}
			else {
				validaJogadaNasPontasDaMesa(mesa, px, primeiraPecaLista, ultimaPecaLista);				
			}
		}
		jogador.remove(px);

//		Verifica se o jogador conseguiu jogar todas as peças			
		fimDeJogo = verificaQuantidadePecasRestantes(jogador);
	}

	/**
	 * Este método realiza a validação de uma jogada pelas pontas da mesa.
	 */
	public static void validaJogadaNasPontasDaMesa(ListaPecas mesa, Peca px, Peca primeiraPecaLista,
			Peca ultimaPecaLista) {
		
		int lado1Px = px.getLado1();
		int lado2Px = px.getLado2();
		
		int number;
		
		boolean swap = false;
		
		if(ultimaPecaLista == null) {
			while(!swap) {
				view.printMensagemErroJogada();
				view.printJogarNasExtremidades("Esquerda", "Direita");
				number = readInteger();
				if(number == 1 && (lado1Px == primeiraPecaLista.getLado1() || lado2Px == primeiraPecaLista.getLado1())) {
					mesa.jogada(px, number);
					swap = true;
				}
				else if(number == 2 && (lado1Px == primeiraPecaLista.getLado2() || lado2Px == primeiraPecaLista.getLado2())) {
					mesa.jogada(px, number);
					swap = true;
				}
			}			
		}
		else {
			while(!swap) {
				view.printMensagemErroJogada();
				view.printJogarNasExtremidades(primeiraPecaLista.toString(), ultimaPecaLista.toString());
				number = readInteger();
				if(number == 1 && (lado1Px == primeiraPecaLista.getLado1() || lado2Px == primeiraPecaLista.getLado1())) {
					mesa.jogada(px, number);
					swap = true;
				}
				else if(number == 2 && (lado1Px == ultimaPecaLista.getLado2() || lado2Px == ultimaPecaLista.getLado2())) {
					mesa.jogada(px, number);
					swap = true;
				}
			}
		}		
	}
	
	/**
	 * Este método realiza a verificação das peças da lista passada como parâmetro a fim de determinar se o jogador ou o computador poderá continuar a partida
	 */
	public static boolean verificaPossibilidadeDeContinuar(ListaPecas lista, ListaPecas mesa) {
		
		PecaIterador it = lista.getIterador();
		
		int lado1;
		int lado2;
		
		Peca primeiraPeca = mesa.retornaPrimeiraPecaDaLista();
		Peca ultimaPeca = mesa.retornaUltimaPecaDaLista();
		
		while(it.existePeca()) {
			Peca p = it.getCurrent();
			lado1 = p.getLado1();
			lado2 = p.getLado2();
			
			if(ultimaPeca != null) { // Jogo possui mais de uma peça na mesa ==> Não é a primeira jogada
				if(lado1 == primeiraPeca.getLado1() || lado2 == primeiraPeca.getLado1()) {
					return true;
				}
				else if(lado1 == ultimaPeca.getLado2() || lado2 == ultimaPeca.getLado2()) {
					return true;
				}
			}
			else {
				if(lado1 == primeiraPeca.getLado1() || lado1 == primeiraPeca.getLado2()) {
					return true;
				}
				else if(lado2 == primeiraPeca.getLado1() || lado2 == primeiraPeca.getLado2()) {
					return true;
				}
			}
			it.irParaProximo();
		}
		return false;
	}
	
	/**
	 * Este método realiza a soma dos lados de cada peça na lista passada como parâmetro e retorna o somatório total das peças na lista
	 */
	public static int realizaSomatorioDasPecas(ListaPecas lista) {
		
		PecaIterador it = lista.getIterador();
		
		int somatorio = -1;
		int lado1;
		int lado2;
		
		while(it.existePeca()) {
			Peca p = it.getCurrent();
			lado1 = p.getLado1();
			lado2 = p.getLado2();
			somatorio += lado1 + lado2;
			it.irParaProximo();
		}
		
		return somatorio;
	}
	
	/**
	 * Método usado para ler um valor inteiro do teclado e consumir o enter "preso" na leitura
	 */
	public static int readInteger() {
		int number = sc.nextInt();
		sc.nextLine();
		
		return number;
	}
	
}
