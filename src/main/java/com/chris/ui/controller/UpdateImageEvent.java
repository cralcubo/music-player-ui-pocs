package com.chris.ui.controller;

import javafx.event.Event;
import javafx.event.EventType;

public class UpdateImageEvent extends Event {
	private static final long serialVersionUID = 1L;

	public static final EventType<UpdateImageEvent> UPDATE_IMAGE = new EventType<>("UPDATE_IMAGE");
	
	private String imageUrl;

	public UpdateImageEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
