package io.gu;

import java.awt.EventQueue;

import io.gu.Gui;

public class MainGu {

	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Gui();
			}
		}) ;
		
	}

}
