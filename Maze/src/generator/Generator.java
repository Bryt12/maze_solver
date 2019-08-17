package generator;

import java.awt.Color;

import board.Maze;
import stepper.Stepper;

public class Generator extends Stepper{

	double randomWall;
	double randomPositionColumn;
	double randomPositionRow;
	public Generator(tileTypes types, Color color) {
		super(types, color);
		// TODO Auto-generated constructor stub
	}
	
	public void generateImportantPosition(int i){
		randomWall = Math.random();
		randomPositionColumn = Math.random() * Maze.COLUMNS;
		randomPositionRow = Math.random() * Maze.ROWS;
		if(randomWall >= 0.75){
			Maze.boardInt[0][(int) randomPositionColumn] = i;
		}else if(randomWall >= 0.5){
			Maze.boardInt[Maze.ROWS-1][(int) randomPositionColumn] = i;
		}else if(randomWall >= 0.25){
			Maze.boardInt[(int) randomPositionRow][0] = i;
		}else if(randomWall >= 0){
			Maze.boardInt[(int) randomPositionRow][Maze.COLUMNS-1] = i;
		}else{
		}
		
	}
	
}
