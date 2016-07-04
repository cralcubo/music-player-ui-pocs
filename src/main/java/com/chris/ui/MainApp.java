	package com.chris.ui;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.chris.ui.controller.ImageDisplayerController;
import com.chris.ui.controller.UpdateImageEvent;

import bo.roman.radio.cover.model.Album;
import bo.roman.radio.cover.model.CoverArt;
import bo.roman.radio.cover.model.Radio;
import bo.roman.radio.player.RadioPlayer;
import bo.roman.radio.player.listener.RadioPlayerEventListener;
import bo.roman.radio.player.listener.Observer;
import bo.roman.radio.player.model.CodecInformation;
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
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class MainApp extends Application {
	// http://50.7.74.82:8193
	// http://7609.live.streamtheworld.com:80/977_ALTERN_SC
	// http://stream-tx3.radioparadise.com/aac-320
	// http://icecast.omroep.nl:80/3fm-bb-mp3
	// http://server1.radiodanz.com:8020/
	// http://us1.internet-radio.com:8122/live
	// http://streaming.radionomy.com/Classic-Rap
	// http://208.77.21.31:14930
	// http://184.164.135.70:8074
	// http://icecast.omroep.nl:80/radio4-bb-mp3
	// http://195.154.182.222:27147/973
	// http://listen.181fm.com/181-90salt_128k.mp3
	// http://listen.181fm.com/181-hairband_128k.mp3
	// http://streaming64.radionomy.com/SleepTime?lang=en-us&br=64
	// http://streaming.radionomy.com/MUSICFORHEALING-RELAXATION?lang=en-us
	// http://streaming212.radionomy.com:80/MusicoftheGodsRadio
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
	// http://193.34.51.67:80/
	// http://195.154.72.66:443
	// http://99.198.110.162:7042
	// http://uk2.internet-radio.com:8008/
	// http://amp.cesnet.cz:8000/cro-d-dur.flac
	// http://91.121.38.100:8030
	// http://51.255.127.128:8026/
	
	private static final String STATION = "http://185.33.21.112:11134";
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
			
			// Observers
			List<Observer<RadioPlayerEntity>> rpeO = Arrays.asList(new CoverUpdatedNotifier(rootLayout), new PrintRadioPlayerObserver());
			
			Observer<CodecInformation> codecObserver = new Observer<CodecInformation>() {
				@Override
				public void update(CodecInformation t) {
					System.out.println("****************************");
					System.out.println(t);
					System.out.println("****************************");
				}
			};
			List<Observer<CodecInformation>> cifO = Arrays.asList(codecObserver);
			MediaPlayerEventAdapter eventsAdapter = new RadioPlayerEventListener(rpeO, cifO, Collections.emptyList());
			
			rp.addEventsListener(eventsAdapter);
			
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		rp = new RadioPlayer();
		rp.setVolume(80);
		
		Thread t1 = new Thread(() ->launch(args));
		t1.start();
		
		Thread t2 = new Thread(() -> rp.play(STATION));
		t2.start();
		t2.join();
		
	}
	
	private class CoverUpdatedNotifier implements Observer<RadioPlayerEntity> {
		private static final String DEFAULTLOGO_PATH = "src/main/resources/pimped-radio-glossy.jpeg";
		private Node node;
		private UpdateImageEvent event;
		
		public CoverUpdatedNotifier(Node node) {
			this.node = node;
			event = new UpdateImageEvent(UpdateImageEvent.UPDATE_IMAGE);
		}
		
		@Override
		public void update(RadioPlayerEntity rpe) {
			Optional<URI> ca = rpe.getAlbum()
					.flatMap(Album::getCoverArt)
					.flatMap(CoverArt::getLargeUri);
			
			Optional<URI> cr = rpe.getRadio().flatMap(Radio::getLogoUri);
			
			String cover = ca.orElse(cr.orElse(Paths.get(DEFAULTLOGO_PATH).toUri())).toString();
			event.setImageUrl(cover);
			node.fireEvent(event);
		}
		
	}

}
