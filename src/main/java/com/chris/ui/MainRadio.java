package com.chris.ui;

import java.util.Arrays;
import java.util.List;

import bo.roman.radio.player.RadioPlayer;
import bo.roman.radio.player.listener.RadioPlayerEventListener;
import bo.roman.radio.player.listener.Observer;
import bo.roman.radio.player.model.CodecInformation;
import bo.roman.radio.player.model.RadioPlayerEntity;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class MainRadio {

	// FLAC: file:///Users/christian/Desktop/06.Echoes.flac
	// AAC: http://stream-tx3.radioparadise.com/aac-128
	// MP3: http://listen.181fm.com/181-90salt_128k.mp3

	private static final String STATION = "http://amp.cesnet.cz:8000/cro-d-dur-256.ogg";

	private RadioPlayer rp;

	public MainRadio() {
		rp = new RadioPlayer();
		// Observers
		List<Observer<RadioPlayerEntity>> rpeO = Arrays.asList(new PrintRadioPlayerObserver());

		Observer<CodecInformation> codecObserver = new Observer<CodecInformation>() {
			@Override
			public void update(CodecInformation t) {
				System.out.println("****************************");
				System.out.println(t);
				System.out.println("****************************");
			}
		};
		List<Observer<CodecInformation>> cifO = Arrays.asList(codecObserver);
		MediaPlayerEventAdapter eventsAdapter = new RadioPlayerEventListener(rpeO, cifO);

		rp.addEventsListener(eventsAdapter);

	}

	public static void main(String[] args) throws InterruptedException {
		MainRadio mr = new MainRadio();
		RadioPlayer mrp = mr.rp;

		Thread t = new Thread(() -> mrp.play(STATION));
		t.start();
		t.join();
	}

}
