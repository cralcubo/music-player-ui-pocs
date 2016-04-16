package com.chris.ui;

import bo.roman.radio.player.RadioPlayer;
import bo.roman.radio.player.listener.MediaMetaNotifier;
import bo.roman.radio.player.listener.MediaMetaSubject;
import bo.roman.radio.player.listener.PrintRadioPlayerObserver;
import bo.roman.radio.player.listener.RadioPlayerEventListener;

public class MainRadio {
	
	private static final String STATION = "http://stream-tx3.radioparadise.com/aac-128";

	public static void main(String[] args) throws InterruptedException {
		RadioPlayer rp = new RadioPlayer();
		MediaMetaSubject notifier = new MediaMetaNotifier();
		notifier.registerObserver(new PrintRadioPlayerObserver());
		rp.addEventsListener(new RadioPlayerEventListener(rp, notifier ));
		
		Thread t = new Thread(() ->rp.play(STATION));
		t.start();
		t.join();
	}

}
