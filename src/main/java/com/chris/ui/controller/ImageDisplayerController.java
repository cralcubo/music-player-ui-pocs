package com.chris.ui.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ImageDisplayerController {
	
	@FXML
	private ImageView viewer;
	
	@FXML
	private Rectangle rectangle;
	
	private Reflection reflection;
	
	@FXML
	private void initialize() {
		reflection = new Reflection();
		
		Rectangle clip = new Rectangle(viewer.getFitWidth(), viewer.getFitHeight());
		clip.setArcWidth(20);
		clip.setArcHeight(20);
		viewer.setClip(clip);
		
		viewer.setEffect(reflection);
	}
	
	@FXML
	private void onHover() {
		GaussianBlur blur = new GaussianBlur();
		blur.setRadius(40);
		blur.setInput(reflection);
		viewer.setEffect(blur);
		
		fadeRectangle(0, 0.5);
	}
	
	@FXML
	private void onExit() {
		viewer.setEffect(reflection);
		
		fadeRectangle(0.5, 0);
	}
	
	public void setImage(String uri) {
		viewer.setImage(new Image(uri));
	}
	
	private void fadeRectangle(double from, double to) {
		FadeTransition fadeRectangle = new FadeTransition(Duration.millis(200), rectangle);
		fadeRectangle.setFromValue(from);
		fadeRectangle.setToValue(to);
		fadeRectangle.play();
	}

}
