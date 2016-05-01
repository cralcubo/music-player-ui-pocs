package com.chris.ui.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class AnimatedBoxController {
	
	@FXML
	private HBox box;
	
	@FXML
	private Button button;
	
	@FXML
	private ToggleButton toggle;
	
	@FXML
	private Slider slider;
	
	
	private double OFF = 0;
	private double ON = 100;
	
	
	@FXML
	private void initialize() {
		button.setText("Hola");
		
		toggle.setMinSize(148, 148); 
		toggle.setMaxSize(148, 148);
		
		slider.setValue(100);
	}
	
	@FXML
	private void toggleAction() {
		if(toggle.isSelected()) {
			System.out.println(".:. Toggle selected, play music!");
		}
		else {
			System.out.println(".:. Toggle de-selected, stop music!");
		}
	}
	
	@FXML
	private void sliderAction() {
		System.out.println(".:. Slider value is=" + slider.getValue());
	}
	
	@FXML
	private void mouseEnter() {
		// ON
		
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(box.prefHeightProperty(), ON);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue );
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
	@FXML
	private void mouseExit() {
		// OFF
		
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(box.prefHeightProperty(), OFF);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue );
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
}
