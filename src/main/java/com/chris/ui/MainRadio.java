package com.chris.ui;

import java.util.List;

import bo.roman.radio.player.RadioPlayer;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.TrackInfo;
import uk.co.caprica.vlcj.player.TrackType;

public class MainRadio {
	
	// FLAC: file:///Users/christian/Desktop/06.Echoes.flac
	// AAC:  http://stream-tx3.radioparadise.com/aac-128
	// MP3:  http://listen.181fm.com/181-90salt_128k.mp3
	
	private static final String STATION = "http://stream-tx3.radioparadise.com/aac-320";
	
	private RadioPlayer rp;
	
	public MainRadio() {
		rp = new RadioPlayer();
		rp.addEventsListener(new MyEventHandler());
	}
	
	public static void main(String[] args) throws InterruptedException {
		MainRadio mr = new MainRadio();
		RadioPlayer mrp = mr.rp;
		
		Thread t = new Thread(() ->mrp.play(STATION));
		t.start();
		t.join();
	}
	
	private class MyEventHandler extends MediaPlayerEventAdapter {
		
		@Override
		public void playing(MediaPlayer mediaPlayer) {
			System.out.println(".:. Playing!");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			printInfo(mediaPlayer);
		}
		
		private void printInfo(MediaPlayer mediaPlayer) {
			mediaPlayer.parseMedia();
			
			List<TrackInfo> tis = mediaPlayer.getTrackInfo(TrackType.AUDIO, TrackType.TEXT, TrackType.UNKNOWN, TrackType.VIDEO);
			tis.forEach(System.out::println);
			System.out.println(mediaPlayer.getMediaStatistics());
			System.out.println(mediaPlayer.getMediaDetails());
			System.out.println(mediaPlayer.getMediaMeta());
		}
	}

}
