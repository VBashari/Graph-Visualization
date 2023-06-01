package visualization;

import java.util.List;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GraphDisplay extends BorderPane {
	private GraphController controller = new GraphController();
	
	private MenuBar menuBar = new MenuBar();
	private Menu menuOptions = new Menu("Options"), menuHelp;
	private CheckMenuItem isDirected = new CheckMenuItem("Show as directed graph");
	
	private TextField tfNewNode = new TextField(), tfEdgeWeight = new TextField();
	private ComboBox<String> nodeStartOptions = new ComboBox<>(), nodeEndOptions = new ComboBox<>(),
			singleNodeOptions = new ComboBox<>();
	private Button btnAddNode = new Button("Add node"), btnAddEdge = new Button("Add edge"),
			btnBFS = new Button("Run BFS"), btnDFS = new Button("Run DFS"),
			btnShortestPath = new Button("Find shortest path");
	
	private static final String TF_ERROR_STYLE = "-fx-text-box-border: red", CB_ERROR_STYLE = "-fx-border-color: red";

	public GraphDisplay() {
		tfNewNode.setPromptText("Add node value");
		tfEdgeWeight.setPromptText("Add edge weight");
		
		nodeStartOptions.setPromptText("Starting node");
		nodeEndOptions.setPromptText("Ending node");
		singleNodeOptions.setPromptText("Select node");
		
		setNodeListOptions(controller.getNodes());
		
		setAddNodeListener();
		setAddEdgeListener();
		
		setBFSlistener();
		setDFSlistener();
		setShortestPathListener();
		
		setMenuHelpListener();
		
		createLayout();
	}
	
	private void createLayout() {
		menuBar.getMenus().addAll(menuHelp, menuOptions);
		menuOptions.getItems().add(isDirected);
		
		HBox newNodeBar = new HBox(tfNewNode, btnAddNode),
			newEdgeBar = new HBox(tfEdgeWeight, btnAddEdge),
			dualNodeBar = new HBox(nodeStartOptions, nodeEndOptions);
			
		newNodeBar.setSpacing(5);
		newEdgeBar.setSpacing(5);
		dualNodeBar.setSpacing(5);
		
		VBox singleNodeBar = new VBox(new Label("Single node operations"), singleNodeOptions, btnBFS, btnDFS);
		VBox edgeBar = new VBox(new Label("Dual node operations"), dualNodeBar, newEdgeBar, btnShortestPath);
			
		singleNodeBar.setSpacing(3);
		edgeBar.setSpacing(3);
		
		VBox sidebar = new VBox(newNodeBar, singleNodeBar, edgeBar);
		sidebar.setPadding(new Insets(20));
		sidebar.setSpacing(20);
		
		setTop(menuBar);
		setCenter(controller.getPane());
		setLeft(sidebar);
	}
	
	// Do colour transition and reverse it
	private void animateColorTrans(List<NodeDisplay> list, Color fromColor, Color toColor) {
		SequentialTransition toColorSequence = new SequentialTransition();
		ParallelTransition fromColorSequence = new ParallelTransition();
		
		for(NodeDisplay node: list) {
			toColorSequence.getChildren().add(new FillTransition(Duration.millis(500), node.getShape(), fromColor, toColor));
			fromColorSequence.getChildren().add(new FillTransition(Duration.millis(1000), node.getShape(), toColor, fromColor));
		}
		
		toColorSequence.play();
		toColorSequence.setOnFinished(e -> fromColorSequence.play());
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
				nodeStartOptions.setStyle(null);
				nodeEndOptions.setStyle(null);
				tfEdgeWeight.setStyle(null);
				
				if(nodeStartOptions.getSelectionModel().isEmpty() || nodeEndOptions.getSelectionModel().isEmpty())
					throw new IndexOutOfBoundsException("Select a starting and ending node");
					
				controller.addEdge(
					tfEdgeWeight.getText(),
					nodeStartOptions.getSelectionModel().getSelectedIndex(),
					nodeEndOptions.getSelectionModel().getSelectedIndex()
				);
			} catch(IllegalArgumentException ex) {
				tfEdgeWeight.setStyle(TF_ERROR_STYLE);
			} catch(IndexOutOfBoundsException ex) {
				nodeStartOptions.setStyle(CB_ERROR_STYLE);
				nodeEndOptions.setStyle(CB_ERROR_STYLE);
			}
		});
	}
	
	private void setBFSlistener() {
		btnBFS.setOnAction(e -> {
			try {
				singleNodeOptions.setStyle(null);
				
				if(singleNodeOptions.getSelectionModel().isEmpty())
					throw new IndexOutOfBoundsException("Select a node");
				
				List<NodeDisplay> bfsList = controller.bfs(singleNodeOptions.getSelectionModel().getSelectedIndex());
				
				// If no results, do nothing
				if(bfsList == null)
					return;
				
				animateColorTrans(bfsList, Color.BLACK, Color.RED);
			} catch(IndexOutOfBoundsException ex) {
				singleNodeOptions.setStyle(CB_ERROR_STYLE);
			}
		});
	}
	
	private void setDFSlistener() {
		btnDFS.setOnAction(e -> {
			try {
				singleNodeOptions.setStyle(null);
				
				if(singleNodeOptions.getSelectionModel().isEmpty())
					throw new IndexOutOfBoundsException("Select a node");
				
				List<NodeDisplay> dfsList = controller.dfs(singleNodeOptions.getSelectionModel().getSelectedIndex());
				
				// If no results, do nothing
				if(dfsList == null)
					return;
				
				animateColorTrans(dfsList, Color.BLACK, Color.BLUE);
			} catch(IndexOutOfBoundsException ex) {
				singleNodeOptions.setStyle(CB_ERROR_STYLE);
			}
		});
	}
	
	private void setShortestPathListener() {
		btnShortestPath.setOnAction(e -> {
			try {
				nodeStartOptions.setStyle(null);
				nodeEndOptions.setStyle(null);
				
				if(nodeStartOptions.getSelectionModel().isEmpty() || nodeEndOptions.getSelectionModel().isEmpty())
					throw new IndexOutOfBoundsException("Select a starting and ending node");
					
				List<NodeDisplay> shortestPath = controller.shortestPath(nodeStartOptions.getSelectionModel().getSelectedIndex(),
																		nodeEndOptions.getSelectionModel().getSelectedIndex());
				
				animateColorTrans(shortestPath, Color.BLACK, Color.GREEN);
			} catch(IndexOutOfBoundsException ex) {
				nodeStartOptions.setStyle(CB_ERROR_STYLE);
				nodeEndOptions.setStyle(CB_ERROR_STYLE);
			} catch(Exception ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setContentText(ex.getMessage());
				
				alert.showAndWait();
			}
		});
	}
	
	private void setMenuHelpListener() {
		Label lbHelp = new Label("Help");
		menuHelp = new Menu("", lbHelp);
		
		lbHelp.setOnMouseClicked(e -> {			
			Label title = new Label("Graph visualiation");
			title.setWrapText(true);
			title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			
			Label info = new Label("Adding a node: Insert the name of the node and press the \"Add node\" button.\n" + 
					"Deleting a node: Right click a node to remove it\n\n" +
					"Adding an edge: Select two nodes from the lists and press the \"Add edge\" button. " + 
					"You can add a numeric weight value to the connection (not adding a weight defaults to 0)\n" +
					"Deleting an edge: Right click an edge to remove it\n\n" +
					"BFS (Breadth-First Search): Returns the level-by-level graph traversal starting from a specified node\n" +
					"DFS (Depth-First Search): Returns the subtree-by-subtree graph traversal startin from a specified node\n" +
					"Shortest path: Returns the shortest path between specified nodes using the Bellman-Ford algorithm.");
			info.setWrapText(true);
			info.setFont(Font.font(15));
			
			VBox pane = new VBox(title, info);
			pane.setSpacing(10);
			pane.setPadding(new Insets(25));
			
			Stage stageInfo = new Stage();
			stageInfo.initModality(Modality.APPLICATION_MODAL);
			stageInfo.setScene(new Scene(pane));
			
			stageInfo.setTitle("Information");
			stageInfo.setWidth(500);
			
			stageInfo.show();
		});
	}
	
	private void setNodeListOptions(ObservableList<Node> nodes) {
		/* Set listener to add/remove node values as added/removed
		 * from both node option boxes
		 */
		nodes.addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> node) {
				while(node.next()) {
					if(node.wasAdded()) {
						for(Node x: node.getAddedSubList()) {
							nodeStartOptions.getItems().add(((NodeDisplay) x).getValue());
							nodeEndOptions.getItems().add(((NodeDisplay) x).getValue());
							
							singleNodeOptions.getItems().add(((NodeDisplay) x).getValue());
						}
					} else if(node.wasRemoved()) {
						for(Node x: node.getRemoved()) {
							nodeStartOptions.getItems().remove(((NodeDisplay) x).getValue());
							nodeEndOptions.getItems().remove(((NodeDisplay) x).getValue());
							
							singleNodeOptions.getItems().remove(((NodeDisplay) x).getValue());
						}
					}
				}
			}
			
		});
	}
}
