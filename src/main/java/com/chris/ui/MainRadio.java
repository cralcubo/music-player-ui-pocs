package com.chris.ui;

import java.util.List;

import bo.roman.radio.player.RadioPlayer;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_stats_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.TrackInfo;
import uk.co.caprica.vlcj.player.TrackType;
import uk.co.caprica.vlcj.player.media.Media;

public class MainRadio {
	
	// FLAC: file:///Users/christian/Desktop/06.Echoes.flac
	// AAC:  http://stream-tx3.radioparadise.com/aac-128
	// MP3:  http://listen.181fm.com/181-90salt_128k.mp3
	
	private static final String STATION = "http://stream-tx3.radioparadise.com/aac-64";
	
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
			long startTime = System.currentTimeMillis();
			
			for(int i = 0 ; i < 10; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				printInfo(mediaPlayer, startTime);
			}
		}
		
//		@Override
//		public void mediaMetaChanged(MediaPlayer mediaPlayer, int metaType) {
//			System.out.println(".:. Meta Changed!");
//			mediaPlayer.parseMedia();
//			printInfo(mediaPlayer);
//		}
		
		
		private void printInfo(MediaPlayer mediaPlayer, long startTime) {
			mediaPlayer.parseMedia();
			
			List<TrackInfo> tis = mediaPlayer.getTrackInfo(TrackType.AUDIO);
			
			tis.forEach(System.out::println);
			libvlc_media_stats_t s = mediaPlayer.getMediaStatistics();
			long timePassed = (System.currentTimeMillis() - startTime);
			System.out.printf(" BITRATE CALC [%d ms]%n", timePassed);
			System.out.println(s);
//			System.out.println(".:. f_input_bitrate: " + (s.f_input_bitrate * 8000));
			System.out.println(".:. f_demux_bitrate: " + (int)(s.f_demux_bitrate * 8000));
			
//			System.out.println(".:. CALCULATED: " + (s.i_demux_read_bytes * 8/timePassed));
			
//			System.out.println(mediaPlayer.getMediaDetails());
//			System.out.println(mediaPlayer.getMediaMeta());
		}
	}

}
