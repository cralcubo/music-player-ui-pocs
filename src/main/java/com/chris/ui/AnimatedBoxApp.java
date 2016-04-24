package com.chris.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AnimatedBoxApp extends Application {
	
	private Stage primaryStage;
	
	private BorderPane rootNode;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		initializeApp();
	}

	private void initializeApp() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AnimatedBoxApp.class.getResource("view/AnimatedBox.fxml"));
		rootNode = loader.load();
		
		Scene scene = new Scene(rootNode);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
