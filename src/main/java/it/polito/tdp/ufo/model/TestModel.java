package it.polito.tdp.ufo.model;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

public class TestModel {

	public static void main(String[] args) {
		
		SimpleDirectedGraph grafo=new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		grafo.addVertex("A");
		grafo.addVertex("B");
		grafo.addVertex("C");
		grafo.addVertex("D");
		grafo.addEdge("A", "B");
		grafo.addEdge("B", "A");
		grafo.addEdge("B", "C");
		grafo.addEdge("C", "D");
		System.out.println(grafo);
		System.out.println(Graphs.successorListOf(grafo, "A"));

	}

}
