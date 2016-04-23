package com.chris.ui.controller;


import javafx.fxml.FXML;
import javafx.scene.effect.Blend;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class BlurController {
	
	@FXML
	private ImageView viewer;
	
	@FXML
	private Rectangle rectangle;
	
	@FXML
	private void initialize() {
		String url = "http://cdn.zumic.com/wp-content/uploads/2013/03/pink-floyd-dark-side-of-the-moon-cover-art-oil-water.jpg";
		viewer.setImage(new Image(url));
		viewer.setEffect(new Blend());
	}
	
	@FXML
	private void onHover() {
		GaussianBlur blur = new GaussianBlur();
		blur.setRadius(40);
		viewer.setEffect(blur);
		rectangle.setOpacity(0.5);
	}
	
	@FXML
	private void onExit() {
		viewer.setEffect(new Blend());
		rectangle.setOpacity(0);
	}

}
