package visualization.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import visualization.controllers.GraphController;

public class CustomPane extends BorderPane {
	private GraphController controller = new GraphController();

	private Group nodes = new Group();
	private Group edges = new Group();
	
	private ObservableList<String> edgeOptions = FXCollections.observableArrayList();
	private ComboBox<String> edgeStartOptions = new ComboBox<>(edgeOptions), edgeEndOptions = new ComboBox<>(edgeOptions);
	
	private Button addNodeBtn = new Button("Add node"), addEdgeBtn = new Button("Connect nodes");
	private TextField tfNewNode = new TextField(), tfEdgeWeight = new TextField();
			
	private Pane graphPane = new Pane(nodes, edges);
	private ScrollPane temp = new ScrollPane();
	
	
	//TODO make style sheet file
	private static final String TF_ERROR_STYLE = "-fx-text-box-border: #FF0000", 
								CB_ERROR_STYLE = "-fx-background-color: #FF0000";
	
	public CustomPane() {
		temp.setPrefSize(1000, 500);
		temp.setContent(graphPane);
		
		edgeStartOptions.setValue("Start of edge");
		edgeEndOptions.setValue("End of edge");
		
		setBtnListeners();
		
		setCenter(temp);
		setBottom(new HBox(new HBox(tfNewNode, addNodeBtn),
					new HBox(edgeStartOptions, edgeEndOptions, tfEdgeWeight), addEdgeBtn));
	}
	
	private void setBtnListeners() {
		addNodeBtn.setOnAction(e -> {
			try {
				tfNewNode.setStyle(null);
				controller.addNode(tfNewNode.getText(), nodes, edgeOptions);
			} catch(IllegalArgumentException ex) {
				tfNewNode.setStyle(TF_ERROR_STYLE);
			}
		});
		
		addEdgeBtn.setOnAction(e -> {
			try {
				edgeStartOptions.setStyle(null);
				edgeEndOptions.setStyle(null);
				tfEdgeWeight.setStyle(null);
				
				controller.addEdge(edgeStartOptions.getSelectionModel().getSelectedIndex(), 
									edgeEndOptions.getSelectionModel().getSelectedIndex(),
									tfEdgeWeight.getText(), edges);
			} catch(IllegalArgumentException ex) {
				if(edgeStartOptions.getSelectionModel().isEmpty())
					edgeStartOptions.setStyle(CB_ERROR_STYLE);
				
				if(edgeEndOptions.getSelectionModel().isEmpty())
					edgeEndOptions.setStyle(CB_ERROR_STYLE);
				
//				if(tfEdgeWeight.getText().isBlank() || x)
//					tfEdgeWeight.setStyle(TF_ERROR_STYLE);
			}
			
//			if(!tfEdgeStart.getText().isBlank() && !tfEdgeEnd.getText().isBlank() && !tfEdgeWeight.getText().isBlank()) {
//				tfEdgeStart.setStyle(null);
//				tfEdgeEnd.setStyle(null);
//				tfEdgeWeight.setStyle(null);
//				
//				controller.addEdge(tfEdgeStart.getText(), tfEdgeEnd.getText(), tfEdgeWeight.getText(), edges);
//				
//				return;
//			}			
//			
//			if(tfEdgeStart.getText().isBlank())
//				tfEdgeStart.setStyle(TF_ERROR_STYLE);
//			
//			if(tfEdgeEnd.getText().isBlank())
//				tfEdgeEnd.setStyle(TF_ERROR_STYLE);
//			
//			if(tfEdgeWeight.getText().isBlank())
//				tfEdgeWeight.setStyle(TF_ERROR_STYLE);
		});
	}
	
}