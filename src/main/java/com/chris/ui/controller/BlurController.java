package com.chris.ui.controller;


import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.effect.Blend;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BlurController {
	
	@FXML
	private ImageView viewer;
	
	@FXML
	private Rectangle rectangle;
	
	private FadeTransition ft;
	
	
	@FXML
	private void initialize() {
		String url = "http://cdn.zumic.com/wp-content/uploads/2013/03/pink-floyd-dark-side-of-the-moon-cover-art-oil-water.jpg";
		viewer.setImage(new Image(url));
		viewer.setEffect(new Blend());
		
		ft = new FadeTransition(Duration.millis(150), rectangle);
	}
	
	@FXML
	private void onHover() {
		GaussianBlur blur = new GaussianBlur();
		blur.setRadius(40);
		viewer.setEffect(blur);
		
		fadeIt(0, 0.5);
	}
	
	@FXML
	private void onExit() {
		viewer.setEffect(new Blend());
		
		fadeIt(0.5, 0);
	}
	
	private void fadeIt(double from, double to) {
		ft.setFromValue(from);
		ft.setToValue(to);
		ft.setDelay(Duration.millis(50));
		
		ft.play();
	}

}
