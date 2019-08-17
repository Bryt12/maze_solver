package board;

import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Window extends JFrame{

	public static void windowInit(){
		JFrame frame = new JFrame();
		Board board = new Board();
		InputListener kl = new InputListener();
		board.addKeyListener(kl);
		board.setFocusable(true);
		board.requestFocusInWindow();
		frame.setSize(Maze.WIDTH+6,Maze.HEIGHT+29);
		frame.add(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Bryson's maze solver");
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
