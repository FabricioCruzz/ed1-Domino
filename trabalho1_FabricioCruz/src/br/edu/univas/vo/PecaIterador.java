package br.edu.univas.vo;

public class PecaIterador {
	
	private Node current;
	
	public PecaIterador(Node firstNode) {
		this.current = firstNode;
	}
	
	public boolean existePeca() {
		return current != null;
	}
	
	public void irParaProximo() {
		if(current != null) {
			current = current.next;
		}
	}
	
	public Peca getCurrent() {
		return current.info;
	}
}
