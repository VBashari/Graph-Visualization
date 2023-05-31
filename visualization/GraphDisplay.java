package visualizationREDO;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GraphDisplay extends BorderPane {
	private GraphController controller = new GraphController();
	
	private Button btnAddNode = new Button("Add node"), btnAddEdge = new Button("Add edge");
	private TextField tfNewNode = new TextField(), tfEdgeWeight = new TextField();
	private ComboBox<String> edgeStartOptions = new ComboBox<>(), edgeEndOptions = new ComboBox<>();
	
	private static final String TF_ERROR_STYLE = "-fx-text-box-border: red", CB_ERROR_STYLE = "-fx-border-color: red";
	
	public GraphDisplay() {
		setCenter(controller.getPane());
		setEdgeOptions(controller.getNodes());
		
		setAddNodeListener();
		setAddEdgeListener();
		
		setBottom(new HBox(
				new HBox(btnAddNode, tfNewNode),
				new HBox(btnAddEdge, edgeStartOptions, edgeEndOptions, tfEdgeWeight)));
	}
	
	private void setAddNodeListener() {
		btnAddNode.setOnAction(e -> {
			try {
				tfNewNode.setStyle(null);
				controller.addNode(tfNewNode.getText());
			} catch(IllegalArgumentException ex) {
				tfNewNode.setStyle(TF_ERROR_STYLE);
			}
		});
	}
	
	private void setAddEdgeListener() {
		btnAddEdge.setOnAction(e -> {
			try {
				edgeStartOptions.setStyle(null);
				edgeEndOptions.setStyle(null);
				tfEdgeWeight.setStyle(null);
				
				if(edgeStartOptions.getSelectionModel().isEmpty() || edgeEndOptions.getSelectionModel().isEmpty())
					throw new IndexOutOfBoundsException("Select a starting and ending node");
					
				controller.addEdge(
					tfEdgeWeight.getText(),
					edgeStartOptions.getSelectionModel().getSelectedIndex(),
					edgeEndOptions.getSelectionModel().getSelectedIndex()
				);
			} catch(IllegalArgumentException ex) {
				tfEdgeWeight.setStyle(TF_ERROR_STYLE);
			} catch(IndexOutOfBoundsException ex) {
				edgeStartOptions.setStyle(CB_ERROR_STYLE);
				edgeEndOptions.setStyle(CB_ERROR_STYLE);
			}
		});
	}
	
	private void setEdgeOptions(ObservableList<Node> nodes) {
		// Set listener to add/remove node values as added/removed
		// from both node option boxes
		nodes.addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> node) {
				while(node.next()) {
					if(node.wasAdded()) {
						for(Node x: node.getAddedSubList()) {
							edgeStartOptions.getItems().add(((NodeDisplay) x).getVa());
							edgeEndOptions.getItems().add(x.toString());
						}
					} else if(node.wasRemoved()) {
						for(Node x: node.getRemoved()) {
							edgeStartOptions.getItems().remove(x.toString());
							edgeEndOptions.getItems().remove(x.toString());
						}
					}
				}
			}
			
		});
	}
}
