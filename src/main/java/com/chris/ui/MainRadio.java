package com.chris.ui;

import bo.roman.radio.player.RadioPlayer;
import bo.roman.radio.player.listener.CodecInformationNotifier;
import bo.roman.radio.player.listener.CodecInformationSubject;
import bo.roman.radio.player.listener.MediaMetaNotifier;
import bo.roman.radio.player.listener.MediaMetaSubject;
import bo.roman.radio.player.listener.PrintRadioPlayerObserver;
import bo.roman.radio.player.listener.RadioPlayerEventListener;

public class MainRadio {
	
	// FLAC: file:///Users/christian/Desktop/06.Echoes.flac
	// AAC:  http://stream-tx3.radioparadise.com/aac-128
	// MP3:  http://listen.181fm.com/181-90salt_128k.mp3
	
	private static final String STATION = "http://amp.cesnet.cz:8000/cro-d-dur-256.ogg";
	
	private RadioPlayer rp;
	
	public MainRadio() {
		rp = new RadioPlayer();
		MediaMetaSubject mms = new MediaMetaNotifier();
		mms.registerObserver(new PrintRadioPlayerObserver());
		
		CodecInformationSubject cis = new CodecInformationNotifier();
		cis.registerObservers(ci -> {
			System.out.println("****************************");
			System.out.println(ci);
			System.out.println("****************************");
		});
		rp.addEventsListener(new RadioPlayerEventListener(rp, mms, cis));
	}
	
	public static void main(String[] args) throws InterruptedException {
		MainRadio mr = new MainRadio();
		RadioPlayer mrp = mr.rp;
		
		Thread t = new Thread(() ->mrp.play(STATION));
		t.start();
		t.join();
	}

}
