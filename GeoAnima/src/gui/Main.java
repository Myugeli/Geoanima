package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	private void start() {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DrawPanel drawPanel = new DrawPanel();

		frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(300, 300);
		frame.setLocation(375, 55);
	}
	
	public static void main(String args[]) {
		
	}
}
