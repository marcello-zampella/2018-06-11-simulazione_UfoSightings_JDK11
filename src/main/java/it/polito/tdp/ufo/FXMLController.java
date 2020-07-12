package it.polito.tdp.ufo;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoNumero;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<AnnoNumero> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;
    
    @FXML
    private Button btnAnalizza;

    @FXML
    private Button btnSequenza;


    @FXML
    void handleAnalizza(ActionEvent event) {
    	String stato=this.boxStato.getValue();
    	if(stato==null) {
    		this.txtResult.appendText("SCEGLI ANNO");
    		return;
    	}
    	List<String> raggiungibili=this.model.genera(stato);
    	this.txtResult.appendText("\n *** PREDECESSORI DI "+stato+" ***\n");
    	for(String s:this.model.getPredecessori()) {
    		this.txtResult.appendText(s+"\n");
    	}
    	this.txtResult.appendText("\n *** SUCCESSORI DI "+stato+" ***\n");
    	for(String s:this.model.getSuccessori()) {
    		this.txtResult.appendText(s+"\n");
    	}
    	this.txtResult.appendText("\n *** ARRIVABILI DA "+stato+" ***\n");
    	for(String s:raggiungibili) {
    		this.txtResult.appendText(s+"\n");
    	}
    	this.btnSequenza.setDisable(false);

    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	if(this.boxAnno.getValue()==null) {
    		this.txtResult.setText("SELEZIONA UN ANNO");
    		return;
    	}
    	model.creaGrafo(this.boxAnno.getValue());
    	this.btnAnalizza.setDisable(false);
    	this.boxStato.getItems().clear();
    	this.boxStato.getItems().addAll(model.getGrafo().vertexSet());
    	this.txtResult.setText("GRAFO CREATO CON "+model.getGrafo().vertexSet().size()+" nodi e "+model.getGrafo().edgeSet().size()+" archi \n");
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	String stato=this.boxStato.getValue();
    	ArrayList<String> percorso=model.cercaSequenza(stato);
    	this.txtResult.appendText("\n *** PERCORSO MIGLIORE PARTENDO DA DA "+stato+" ***\n");
    	for(String s:percorso) {
    		this.txtResult.appendText(s+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(model.getAnnoNumero());
		this.btnAnalizza.setDisable(true);
		this.btnSequenza.setDisable(true);
	}
}
