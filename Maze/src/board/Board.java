package board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import stepper.Stepper;
import tiles.Tile;
import tiles.Tile.tileTypes;

public class Board extends JPanel implements ActionListener{
	
	int actualWidth;
	int actualHeight;
	boolean direction = true;
	public static boolean random;
	public static boolean dance = false;
	public static boolean debug = false;
	public static boolean first = true;
	public static int timing;
	public static int count = 0;
	public static int countR = 0;
	long time;
	static int tileNumber;
	Rectangle2D stepper;
	Rectangle2D stepperF;
	Rectangle2D tile;
	Timer t;
	static Stepper MSAI = new Stepper(tileTypes.STEPPER, Color.BLUE);
	
	public Board(){
		random = true;
		tileNumber = 0;
		try{
			timing = Integer.parseInt(JOptionPane.showInputDialog("Please enter speed for solver (0 is the fastest)"));
		}catch(NumberFormatException e){
			timing = 50;
		}
	}
	
	public static void initBoard(){
		Maze.boardTile = new Tile[Maze.boardInt.length][Maze.boardInt[1].length];
		for(int row = 0; row < Maze.boardInt.length; row++){
			for(int column = 0; column < Maze.boardInt[row].length; column++){
				switch(Maze.boardInt[row][column]){
				case 0:
					Maze.boardTile[row][column] = new Tile(row,column,tileTypes.HALL,Color.WHITE,tileNumber);
					break;
				case 1:
					Maze.boardTile[row][column] = new Tile(row,column,tileTypes.WALL,Color.BLACK,tileNumber);
					break;
				case 2:
					Maze.boardTile[row][column] = new Tile(row,column,tileTypes.START,Color.GREEN,tileNumber);
					break;
				case 3:
					Maze.boardTile[row][column] = new Tile(row,column,tileTypes.END,Color.RED,tileNumber);
					break;
				default:
					break;
				}
				tileNumber++;
			}
		}
		MSAI.setStepperCord();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//Draw tiles
		for(int row = 0; row < Maze.boardInt.length; row++){
			for(int column = 0; column < Maze.boardInt[row].length; column++){
				g2.setColor(Maze.boardTile[row][column].getTileColor());
				tile = new Rectangle2D.Double(column*(Maze.WIDTH/Maze.boardTile[row].length),row*(Maze.HEIGHT/Maze.boardTile.length), Maze.WIDTH/Maze.boardTile[row].length,Maze.HEIGHT/Maze.boardTile.length);
				g2.fill(tile);
				//Draw debug
				if(debug){
					g2.setColor(Color.PINK);
					if(MSAI.possibleMove(row,column)){
						tile = new Rectangle2D.Double(column*(Maze.WIDTH/Maze.boardTile[row].length),row*(Maze.HEIGHT/Maze.boardTile.length), Maze.WIDTH/Maze.boardTile[row].length,Maze.HEIGHT/Maze.boardTile.length);
						g2.fill(tile);
					}
				}
			}
		}

		//Draw stepper
		if(!random){
			g2.setColor(MSAI.getTileColor());
			stepperF = new Rectangle2D.Double(MSAI.getColumnCord()*(Maze.WIDTH/Maze.boardTile[1].length), MSAI.getRowCord()*(Maze.HEIGHT/Maze.boardTile.length), Maze.WIDTH/Maze.boardTile[1].length,Maze.HEIGHT/Maze.boardTile.length);
			g2.fill(stepperF);
		}else if(MSAI.debugMoves){
			g2.setColor(Color.CYAN);
			stepper = new Rectangle2D.Double(MSAI.getColumnCord()*(Maze.WIDTH/Maze.boardTile[1].length), MSAI.getRowCord()*(Maze.HEIGHT/Maze.boardTile.length), Maze.WIDTH/Maze.boardTile[1].length,Maze.HEIGHT/Maze.boardTile.length);
			g2.fill(stepper);
		}else if(random){
			g2.setColor(MSAI.getTileColor());
			stepper = new Rectangle2D.Double(MSAI.getColumnCord()*(Maze.WIDTH/Maze.boardTile[1].length), MSAI.getRowCord()*(Maze.HEIGHT/Maze.boardTile.length), Maze.WIDTH/Maze.boardTile[1].length,Maze.HEIGHT/Maze.boardTile.length);
			g2.fill(stepper);
		}
		if(first){
			try{
				t.stop();
			}catch(NullPointerException e){
				
			}
			t = new Timer(timing, this);
			t.start();
			first = false;
			Maze.startTime = new Date();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(MSAI.getWin()){
			random = false;
			MSAI.setStepperCord();
			MSAI.setTileColor(Color.YELLOW);
			MSAI.setWin(false);
			Maze.finishTime = new Date();
			time = (Maze.finishTime.getTime() - Maze.startTime.getTime());
			JOptionPane.showMessageDialog(this, time/1000.000 + " Seconds");			
			t.stop();
			timing = 150;
			t = new Timer(timing, this);
			t.start();
		}
		if(MSAI.getVictory()){
			MSAI.setVictory(false);
			timing = 200;
			t.stop();
			t = new Timer(timing, this);
			t.start();
			dance = true;
		}
		repaint();
		//Main code that runs
		if(random){
			MSAI.calculateMoves(MSAI.getRowCord(), MSAI.getColumnCord());
			MSAI.selectMove(MSAI.getRowCord(), MSAI.getColumnCord());
			MSAI.storeToMemory(MSAI.getRowCord(), MSAI.getColumnCord());
			MSAI.reshapeMap(MSAI.getRowCord(), MSAI.getColumnCord());
			//MSAI.printMap();
			MSAI.makeMove();
			MSAI.closeDeadEnd();
		}else if(dance){
			MSAI.victoryDance(countR);
			if(direction){
				countR++;
				if(countR >= MSAI.getRainbowLength() - 1){
					direction = false;
				}
			}else{
				countR--;
				if(countR <= 0){
					direction = true;
				}
			}
		}else{
			MSAI.selectMoveFromMemory(MSAI.getRowCord(), MSAI.getColumnCord());
			MSAI.makeMove();	
			MSAI.clearMoves();
		}
		repaint();
	}
}
