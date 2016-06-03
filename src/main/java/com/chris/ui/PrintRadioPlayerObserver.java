package com.chris.ui;

import bo.roman.radio.player.listener.Observer;
import bo.roman.radio.player.model.RadioPlayerEntity;

public class PrintRadioPlayerObserver implements Observer<RadioPlayerEntity> {

	@Override
	public void update(RadioPlayerEntity rpe) {
		System.out.println("****************************");
		System.out.println("****************************");
		rpe.getRadio().ifPresent(r -> System.out.println(r));
		rpe.getSong().ifPresent(s -> System.out.println(s));
		rpe.getAlbum().ifPresent(a -> System.out.println(a));
		System.out.println("****************************");
		System.out.println("****************************");
	}
	

}
