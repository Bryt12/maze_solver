package board;

import java.util.Date;

import javax.swing.UIManager;

import fileSelector.Reader;
import tiles.Tile;

public class Maze{
	
	public final static int COLUMNS = 50;
	public final static int ROWS = 50;
	public final static int WIDTH = 600;
	public final static int HEIGHT = 600;
	//public static int[][] boardInt = {{0,0,0,0,0,0},{1,0,1,1,1,0},{2,0,1,0,0,0},{1,1,1,0,1,1},{1,0,1,0,1,0},{3,0,0,0,0,0}};
	//public static int[][] boardInt = {{1,0,1,0,0,1},{3,0,0,1,0,0},{1,1,0,1,0,1},{0,0,0,0,0,1},{0,1,1,1,0,1},{0,0,0,1,2,1}};
	//public static int[][] boardInt = {{1,1,1,1,1,1,1,1,3,0,1,1},{1,1,1,1,1,1,1,1,1,0,1,1},{1,1,1,1,1,1,1,1,1,0,1,1},{1,1,1,1,1,1,1,1,1,0,1,1},{1,1,1,1,1,1,1,1,1,0,1,1},{1,1,1,0,0,0,0,0,1,0,1,1},{1,1,1,0,1,1,1,0,1,0,1,1},{1,1,1,0,0,0,0,0,1,0,1,1},{1,1,1,1,1,1,1,0,1,0,1,1},{1,1,1,1,1,1,1,0,1,0,1,1},{1,1,1,1,1,1,1,2,0,0,1,1},{1,1,1,1,1,1,1,1,1,1,1,1}};
	public static int[][] boardInt; 
	public static Tile[][] boardTile;
	public static Date startTime;
	public static Date finishTime;

	
	
	public static void main(String[] args) {
				
		//Generator MG = new Generator(Tile.tileTypes.GENERATOR, null);
		//MG.generateImportantPosition(3);
		//MG.generateImportantPosition(2);
		Reader.pickFile();
		//Reader.readFile();
		start();
		Window.windowInit();
	}
	
	public static void start(){
		Board.MSAI.restart();
		Board.initBoard();
	}
	
}
