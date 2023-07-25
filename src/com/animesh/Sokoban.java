package com.animesh;

import java.awt.EventQueue;
import javax.swing.JFrame;
public class Sokoban extends JFrame {
	
	public final int Offset=30;
	public Sokoban() {
		initUI();
	}
	private void initUI() {
		Board board=new Board();
		add(board);
		setTitle("Sokoban");
		setSize(board.getBoardWidth()+Offset, board.getBoardHeight()+2*Offset);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			Sokoban game=new Sokoban();
			game.setVisible(true);
		});
	}

}