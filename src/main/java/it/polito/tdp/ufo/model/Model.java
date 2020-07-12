package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;


import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	public Model() {
		dao= new SightingsDAO();
	}
	

	public ArrayList<AnnoNumero> getAnnoNumero() {
		return dao.getAnnoNumero();
	}
	
	
	private SightingsDAO dao;
	private SimpleDirectedGraph<String, DefaultEdge> grafo;
	private ArrayList<String> stati;
	private List<String> successori;
	private List<String> predecessori;
	private HashMap<String, String> backVisit;

	public void creaGrafo(AnnoNumero value) {
		grafo=new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		stati=dao.getStatesByAnno(value.getAnno());
		Graphs.addAllVertices(grafo, stati);
		ArrayList<Collegamento> collegamenti=dao.getAllCollegamenti(value.getAnno());
		for(Collegamento c:collegamenti) {
				grafo.addEdge(c.getS1(), c.getS2());
		}
		
		
	}
	
	


	public SimpleDirectedGraph<String, DefaultEdge> getGrafo() {
		return grafo;
	}

	

	public List<String> getSuccessori() {
		return successori;
	}


	public List<String> getPredecessori() {
		return predecessori;
	}
	
	private ArrayList<String> predec(String stato){
		 ArrayList<String> temp=new ArrayList<String>();
		for(DefaultEdge e: grafo.outgoingEdgesOf(stato)) {
			temp.add(grafo.getEdgeTarget(e));
		}
		return temp;
		
	}
	
	private ArrayList<String> succ(String stato){
		 ArrayList<String> temp=new ArrayList<String>();
		for(DefaultEdge e: grafo.incomingEdgesOf(stato)) {
			temp.add(grafo.getEdgeSource(e));
		}	
		return temp;
		
	}


	public List<String> genera(String stato) {
		successori=this.succ(stato);
				
		
		predecessori=this.predec(stato);
				

		
		List<String> result = new ArrayList<String>();
		backVisit= new HashMap<>();
		GraphIterator<String, DefaultEdge> it=new BreadthFirstIterator<>(this.grafo, stato); // crea un nuovo iteratore e lo associa a questo grafo
	// per scegliere quale sia il nodo in cui deve iniziare glielo devo specificare nel secondo parametro
		
		//GraphIterator<Fermata,DefaultEdge> it=new DepthFirstIterator<>(this.grafo, source); //modo diverso di visitare il grafico
		
		it.addTraversalListener(new EdgeTraverseGraphListener(grafo, backVisit));
				
		backVisit.put(stato, null); // devo dargli un nodo da cui partire
		while(it.hasNext()) {
			result.add(it.next());
		}
		return result;
	
		
	}
	
	int max;
	ArrayList<String> migliore;


	public ArrayList<String> cercaSequenza(String stato) {
		max=0;
		int livello=0;
		ArrayList<String> parziale=new ArrayList<String>();
		parziale.add(stato);
		espandi(livello+1,parziale,stato);
		return migliore;
	}


	private void espandi(int livello, ArrayList<String> parziale, String stato) {
		List<String> possibili=this.succ(stato);
		possibili.removeAll(parziale);
		if(possibili.size()==0) {
			//condizione di terminazione
			int temp=parziale.size();
			if(temp>max) {
				max=temp;
				migliore=new ArrayList<String>(parziale);
			}
			return;
		}
		
		for(String s: possibili) {
			parziale.add(s);
			espandi(livello+1,parziale,s);
			parziale.remove(s);
		}
		
	}

}
