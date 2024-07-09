//package game.gui;
//
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//
//public class SampleController extends Application implements EventHandler<ActionEvent> {
//	Stage stage;
//	Scene startMenu;
//	Scene Mode;
//	
//	
//	@Override
//	public void start(Stage st) throws Exception {
//		stage = st;
//		AnchorPane root = new AnchorPane();
//        root.setPrefHeight(673.0);
//        root.setPrefWidth(358.0);
//        ImageView startImg = new ImageView();
//        startImg.setImage(new Image(getClass().getResourceAsStream("../StartBackground.jpeg")));
//        root.getChildren().addAll(startImg);
//		stage.setFullScreen(true);
//		stage.setTitle("Attack on Titan");
//		startMenu = new Scene(root,650,360);
//		stage.setScene(startMenu);
//		stage.show();
//		
//		
//		
//		
//	}
//
//	@Override
//	public void handle(ActionEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//}
