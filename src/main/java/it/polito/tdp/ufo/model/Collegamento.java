package it.polito.tdp.ufo.model;

public class Collegamento {

	String s1;
	String s2;
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public Collegamento(String s1, String s2) {
		super();
		this.s1 = s1;
		this.s2 = s2;
	}
	@Override
	public String toString() {
		return "Collegamento [s1=" + s1 + ", s2=" + s2 + "]";
	}
	
}
