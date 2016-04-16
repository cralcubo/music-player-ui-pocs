package com.chris.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class ImageDisplayerController {
	
	@FXML
	private ImageView viewer;
	
	@FXML
	private void initialize() {
		Rectangle clip = new Rectangle(viewer.getFitWidth(), viewer.getFitHeight());
		clip.setArcWidth(20);
		clip.setArcHeight(20);
		viewer.setClip(clip);
	}
	
	public void setImage(String uri) {
		viewer.setImage(new Image(uri));
	}

}
