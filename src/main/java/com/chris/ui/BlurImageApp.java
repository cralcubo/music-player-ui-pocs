package com.chris.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BlurImageApp extends Application {
	
	private Stage primaryStage;
	
	private StackPane rootPane;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		initializeRoot();
	}

	private void initializeRoot() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BlurImageApp.class.getResource("view/BlurEffects.fxml"));
		
		rootPane = loader.load();
		
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
