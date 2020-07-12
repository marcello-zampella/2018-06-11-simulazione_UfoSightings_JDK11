package it.polito.tdp.ufo.model;

public class AnnoNumero {
	int anno;
	int numero;
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public AnnoNumero(int anno, int numero) {
		super();
		this.anno = anno;
		this.numero = numero;
	}
	@Override
	public String toString() {
		return anno+" -> "+numero;
	}
	
	
	

}
