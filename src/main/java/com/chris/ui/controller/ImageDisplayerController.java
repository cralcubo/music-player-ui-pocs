package com.chris.ui.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ImageDisplayerController {
	
	@FXML
	private ImageView viewer;
	
	@FXML
	private Rectangle rectangle;
	
	@FXML
	private HBox box;
	
	private Reflection reflection;
	private static final Duration duration = Duration.millis(200);
	
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
		blur.setRadius(10);
		blur.setInput(reflection);
		viewer.setEffect(blur);
		
		fadeRectangle(0, 0.5);
		animateBox(50);
	}
	
	@FXML
	private void onExit() {
		viewer.setEffect(reflection);
		
		fadeRectangle(0.5, 0);
		animateBox(0);
	}
	
	public void setImage(String uri) {
		viewer.setImage(new Image(uri));
	}
	
	private void fadeRectangle(double from, double to) {
		FadeTransition fadeRectangle = new FadeTransition(duration, rectangle);
		fadeRectangle.setFromValue(from);
		fadeRectangle.setToValue(to);
		fadeRectangle.play();
	}
	
	private void animateBox(int height) {
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(box.prefHeightProperty(), height);
		KeyFrame keyFrame = new KeyFrame(duration, keyValue);
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

}
