package board;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fileSelector.Reader;

public class InputListener implements KeyListener{
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyChar();
		 if(code == 'd' || code == 'D'){
			 Board.debug = !Board.debug;
		 }else if(code == 'e' || code == 'E'){
			 Reader.pickFile();
			 Maze.start();
		 }else if(code == 'r' || code == 'R'){
			 Maze.start();
		 }else if(code == 'c' || code == 'C'){
			 Board.MSAI.debugMoves = !Board.MSAI.debugMoves;
		 }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
