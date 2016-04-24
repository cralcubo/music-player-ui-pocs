package com.chris.ui.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class AnimatedBoxController {
	
	@FXML
	private HBox box;
	
	@FXML
	private void mouseEnter() {
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(box.prefHeightProperty(), 100);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue );
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
	@FXML
	private void mouseExit() {
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(box.prefHeightProperty(), 0);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue );
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
}
