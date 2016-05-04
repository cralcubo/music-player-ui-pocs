package com.chris.ui;

import java.net.URI;
import java.util.Optional;

import com.chris.ui.controller.ImageDisplayerController;
import com.chris.ui.controller.UpdateImageEvent;

import bo.roman.radio.cover.model.Album;
import bo.roman.radio.cover.model.CoverArt;
import bo.roman.radio.cover.model.Radio;
import bo.roman.radio.player.RadioPlayer;
import bo.roman.radio.player.listener.MediaMetaNotifier;
import bo.roman.radio.player.listener.MediaMetaObserver;
import bo.roman.radio.player.listener.MediaMetaSubject;
import bo.roman.radio.player.listener.PrintRadioPlayerObserver;
import bo.roman.radio.player.listener.RadioPlayerEventListener;
import bo.roman.radio.player.model.RadioPlayerEntity;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	// http://50.7.74.82:8193
	// http://7609.live.streamtheworld.com:80/977_ALTERN_SC
	// http://stream-tx3.radioparadise.com/aac-320
	// http://icecast.omroep.nl:80/3fm-bb-mp3
	// http://server1.radiodanz.com:8020/
	// http://streaming.radionomy.com/Classic-Rap
	// http://184.164.135.70:8074
	// http://icecast.omroep.nl:80/radio4-bb-mp3
	// http://195.154.182.222:27147/973
	// http://listen.181fm.com/181-90salt_128k.mp3
	// http://listen.181fm.com/181-hairband_128k.mp3
	// http://streaming64.radionomy.com/SleepTime?lang=en-us&br=64
	// http://streaming.radionomy.com/MUSICFORHEALING-RELAXATION?lang=en-us
	// http://radiostreams.radioup.com:2226
	// 1.FM
	// http://192.99.35.93:6578 Rock
	// http://185.33.21.112:11134 High voltage
	// http://185.33.21.112:11077 classic
	// http://185.33.21.247:8068 90s
	// http://185.33.21.245:8078 top 40
	// http://193.34.51.67:80/
	// http://89.16.185.174:8004/stream
	// http://108.61.73.117:8128/ top 40
	// http://87.98.180.164:8300/ ita
	// http://184.95.52.178:9150
	
	private static final String STATION = "http://streaming.radionomy.com/Classic-Rap";
	private Stage primaryStage;
	private static AnchorPane rootLayout;
	private static RadioPlayer rp;
	
	private double xOffset = 0;
    private double yOffset = 0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setResizable(false);
		primaryStage.setAlwaysOnTop(true);
		this.primaryStage = primaryStage;
		initRootLayout();
	}

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ImageDisplayer.fxml"));
			rootLayout = loader.load();
			
			rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
			rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                primaryStage.setX(event.getScreenX() - xOffset);
	                primaryStage.setY(event.getScreenY() - yOffset);
	            }
	        });
			
			Scene scene = new Scene(rootLayout);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
			
			ImageDisplayerController idc = loader.getController(); 
			rootLayout.addEventHandler(UpdateImageEvent.UPDATE_IMAGE, event -> idc.setImage(event.getImageUrl()));
			
			MediaMetaSubject mms = new MediaMetaNotifier();
			mms.registerObserver(new CoverUpdatedNotifier(rootLayout));
			mms.registerObserver(new PrintRadioPlayerObserver());
			rp.addEventsListener(new RadioPlayerEventListener(rp, mms));
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		rp = new RadioPlayer();
//		rp.setVolume(80);
		
		Thread t1 = new Thread(() ->launch(args));
		t1.start();
		
		Thread t2 = new Thread(() -> rp.play(STATION));
		t2.start();
		t2.join();
		
	}
	
	private class CoverUpdatedNotifier implements MediaMetaObserver {
		private Node node;
		
		public CoverUpdatedNotifier(Node node) {
			this.node = node;
		}
		
		@Override
		public void update(RadioPlayerEntity rpe) {
			UpdateImageEvent event = new UpdateImageEvent(UpdateImageEvent.UPDATE_IMAGE);
			Optional<URI> ca = rpe.getAlbum()
					.flatMap(Album::getCoverArt)
					.flatMap(CoverArt::getMediumUri);
			
			String cover = ca.orElse(rpe.getRadio().flatMap(Radio::getLogoUri).get()).toString();
			event.setImageUrl(cover);
			node.fireEvent(event);
		}
		
	}

}
