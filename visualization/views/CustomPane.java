package visualization.views;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import visualization.controllers.GraphController;
import visualization.models.EdgeDisplay;
import visualization.models.NodeDisplay;

public class CustomPane extends BorderPane {
	private GraphController controller = new GraphController();
	
	private Group nodes = new Group(), edges = new Group();
	private ObservableList<String> edgeOptions = FXCollections.observableArrayList();
	
	private ComboBox<String> edgeStartOptions = new ComboBox<>(edgeOptions), edgeEndOptions = new ComboBox<>(edgeOptions);
	private Button addNodeBtn = new Button("Add node"), addEdgeBtn = new Button("Connect nodes");
	private TextField tfNewNode = new TextField(), tfEdgeWeight = new TextField();
	
	private Pane graphPane = new Pane(edges, nodes);
	private ScrollPane temp = new ScrollPane();
	
	
	//TODO make style sheet file
	private static final String TF_ERROR_STYLE = "-fx-text-box-border: red", 
								CB_ERROR_STYLE = "-fx-border-color: red";
	
	public CustomPane() {
		temp.setPrefSize(1000, 500);
		temp.setContent(graphPane);
		
		nodes.getChildren().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> c) {
				while(c.next()) {
					for(Node node: c.getRemoved()) {
						String value = ((NodeDisplay) node).value();
						
						controller.removeNode(value);
						edgeOptions.remove(value);
					}
				}
			}
			
		});
		
		//Configure combo-boxes
		edgeStartOptions.setValue("Start of edge");
		edgeEndOptions.setValue("End of edge");
		
		setBtnListeners();
		
		setCenter(temp);
		setBottom(new HBox(new HBox(tfNewNode, addNodeBtn), new HBox(edgeStartOptions, edgeEndOptions, tfEdgeWeight), addEdgeBtn));
	}
	
	//TODO for when changing to directed (make arrow sign for line, if graph turned to directed, add it to edge display view, otherwise remove it)
	private void repaint() {
		
	}
	
	private void setBtnListeners() {
		addNodeBtn.setOnAction(e -> {
			try {
				tfNewNode.setStyle(null);
				String newValue = controller.addNode(tfNewNode.getText(), nodes.getChildren());
				
				if(newValue != null)
					edgeOptions.add(newValue);
			} catch(IllegalArgumentException ex) {
				tfNewNode.setStyle(TF_ERROR_STYLE);
			}
		});
		
		addEdgeBtn.setOnAction(e -> {
			try {
				edgeStartOptions.setStyle(null);
				edgeEndOptions.setStyle(null);
				tfEdgeWeight.setStyle(null);

				int startIndex = edgeStartOptions.getSelectionModel().getSelectedIndex(), endIndex = edgeEndOptions.getSelectionModel().getSelectedIndex();
				
				controller.addEdge((NodeDisplay) nodes.getChildren().get(startIndex),
									(NodeDisplay) nodes.getChildren().get(endIndex),
									tfEdgeWeight.getText(), edges.getChildren());
			} catch(IndexOutOfBoundsException ex) {
				if(edgeStartOptions.getSelectionModel().isEmpty())
					edgeStartOptions.setStyle(CB_ERROR_STYLE);
				
				if(edgeEndOptions.getSelectionModel().isEmpty())
					edgeEndOptions.setStyle(CB_ERROR_STYLE);
			} catch(NumberFormatException ex) {
				tfEdgeWeight.setStyle(TF_ERROR_STYLE);
			} catch(IllegalArgumentException ex) {
				if(tfEdgeWeight.getText().isBlank())
					tfEdgeWeight.setStyle(TF_ERROR_STYLE);
				
				if(edgeStartOptions.getSelectionModel().getSelectedIndex() == edgeEndOptions.getSelectionModel().getSelectedIndex()) {
					edgeStartOptions.setStyle(CB_ERROR_STYLE);
					edgeEndOptions.setStyle(CB_ERROR_STYLE);
				}
			}
		});
	}
	
}