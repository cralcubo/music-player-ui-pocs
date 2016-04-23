package com.chris.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class ImageDisplayerController {
	
	@FXML
	private ImageView viewer;
	
	@FXML
	private Rectangle rectangle;
	
	private Reflection reflection = new Reflection();
	
	@FXML
	private void initialize() {
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
		rectangle.setOpacity(0.5);
	}
	
	@FXML
	private void onExit() {
		viewer.setEffect(reflection);
		rectangle.setOpacity(0);
	}
	
	public void setImage(String uri) {
		viewer.setImage(new Image(uri));
	}

}
