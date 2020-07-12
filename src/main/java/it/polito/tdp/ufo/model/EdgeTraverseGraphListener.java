package it.polito.tdp.ufo.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;




public class EdgeTraverseGraphListener implements TraversalListener<String, DefaultEdge> {

	Map<String,String> back;
	Graph<String,DefaultEdge> grafo; //l'iteratore non conosce il grafo su cui sta lavorando
	
	public EdgeTraverseGraphListener(Graph<String,DefaultEdge> grafo, Map<String, String> back) {
		super();
		this.back = back;
		this.grafo=grafo;
	}
	
	
	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
		//ci interessa il cammino, perciò ogni volta che creo un nuovo arco da un nodo in cui mi trovo è necessario salvare qualche informazione
		//voglio tenere traccia per ogni vertice, qual è il vertice precedente, per immagazzinare questa informazione
		//creo una mappa che abbia la chiave la fermata figlia e il valore la fermata padre.
		// necessario che padre sia stato visitato, ma figlio ancora sconosciuto
		
		String sourceVertex=grafo.getEdgeSource(e.getEdge());
		String targetVertex=grafo.getEdgeTarget(e.getEdge());
		//se il grafo e' orientato, allora sono sicuro che la source e' il padre e il target e' il figlo
		// se il grafo non e' orientato, potrebbe essere il contrario, magari ho gia' visto in un verso quell'arco e adesso sto ricapitando nel verso opposto
		// er capire se quell'arco l'ho gia' visitato vado a vedere se il nodo che penso possa essere figlio e' gia' inserito nella mappa
		
		if(!back.containsKey(targetVertex) && back.containsKey(sourceVertex)) {
			//affinchè sia un nuovo arco, la mappa deve contenere la source (il padre) (in teoria questo dovrebbe accadere sempre, perche' altrimenti
			//non potrei vedere proprio l'arco) e NON contenere la destinazione (il figlio)
			back.put(targetVertex, sourceVertex);
		}	
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<String> e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vertexFinished(VertexTraversalEvent<String> e) {
		// TODO Auto-generated method stub

	}

}
