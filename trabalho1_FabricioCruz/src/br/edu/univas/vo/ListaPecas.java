package br.edu.univas.vo;

public class ListaPecas {
	
	private Node head;
	private Node last;
	
	public PecaIterador getIterador() {
		PecaIterador it = new PecaIterador(head);
		return it;
	}
	
	public void insert(Peca peca) {
		
		Node novoNode = new Node();
		
		if(head == null) {
			head = novoNode;
			last = novoNode;
		}
		else {
			novoNode.previous = last;
			last.next = novoNode;
		}
		
		last = novoNode;
		
		novoNode.info = peca;
	}
	
	public void jogada(Peca peca, int posicao) { // O int posicao sendo 1 ou 2, quer dizer se irá posicionar a peça no head ou no last da lista com as peças do jogo
		
			int lado1;
			int lado2;
			int aux;
			
			Node novoNode = new Node();
			
			if(posicao == 1) {
				lado1 = head.info.getLado1();
				lado2 = head.info.getLado2();
				if(peca.getLado1() == lado1) { //Trabalha com HEAD
					 
					//Inverte o lado da peça pra encaixar com a do tabuleiro na posição head
					aux = peca.getLado2();
					peca.setLado2(peca.getLado1());
					peca.setLado1(aux);
					head.previous = novoNode;
					novoNode.info = peca;
					novoNode.next = head;
					head = novoNode;
				}
				else { //peca.getLado2() == lado1
					head.previous = novoNode;
					novoNode.info = peca;
					novoNode.next = head;
					head = novoNode;										
				}
			}
			else if(posicao == 2) { //Trabalha com LAST
				lado1 = last.info.getLado1();
				lado2 = last.info.getLado2();
				if(peca.getLado1() == lado2) {
					last.next = novoNode;
					novoNode.info = peca;
					novoNode.previous = last;
					last = novoNode;					
				}
				else { //peca.getLado2() == lado2
					
					// Inverte o lado da peça pra encaixar com a do tabuleiro na posição last
					aux = peca.getLado1();
					peca.setLado1(peca.getLado2());
					peca.setLado2(aux);
					
					last.next = novoNode;
					novoNode.info = peca;
					novoNode.previous = last;
					last = novoNode;						
				}
			}		
	}
	
	public Peca remove(Peca peca) {
		
		if(head == null) {
			return null;
		}
		
		Node current = head;
		Node previous = null;
		
		while(current != null) {
			if(current.info.getLado1() == peca.getLado1() && current.info.getLado2() == peca.getLado2()) {
				break;
			}
			previous = current;
			current = current.next;
		}
		
		if(current == null) {
			return null;
		}
		
		Peca pecaRemovida = current.info;
		
		if(this.head == this.last) {
			this.head = null;
			this.last = null;
			return pecaRemovida;
		}
		
		if(previous == null) {
			head = current.next;
		}
		else {
			previous.next = current.next;
			
			if(current.next == null) {
				this.last = previous;
			}
		}
		
		return pecaRemovida;
	}
	
	private Node getNodeAt(int index) {
		if(index < 0) {
			return null;			
		}
		int count = 0;
		
		Node current = head;
		
		while(current != null) {
			if(index == count) {
				return current;
			}
			count++;
			current = current.next;
		}
		return null;
	}
	
	public Peca getElementAt(int index) {
		
		Node node = getNodeAt(index);
		if(node != null) {
			return node.info;
		}
		return null;
	}
	
	public String getAsString() {
        if(head == null) {
            return "A lista está vazia!";
        }
        
        StringBuilder builder = new StringBuilder();
    
        Node current = head;
        while(current != null) {
            builder.append(" (" + current.info.getLado1() + " | " + current.info.getLado2() + ") ");

            current = current.next;
        }
        return builder.toString();
    }
	
	public int length() {
		int count = 0;
		
		PecaIterador it = this.getIterador();
		while(it.existePeca()) {
			count++;
			it.irParaProximo();
		}
		
		return count;
	}
	
	public Peca retornaPrimeiraPecaDaLista() {
		Peca primeiraPeca = head.info;
		
		return primeiraPeca;
	}
	
	public Peca retornaUltimaPecaDaLista() {
		
		if(this.head == this.last) {
			return null;
		}
		
		Peca ultimaPeca = last.info;
		
		return ultimaPeca;
	}	
}
