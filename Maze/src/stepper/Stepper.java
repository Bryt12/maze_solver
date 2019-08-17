package stepper;

import java.awt.Color;
import java.util.HashMap;

import board.Board;
import board.Maze;
import tiles.Tile;

public class Stepper extends Tile{

	public enum direction{
		UP,RIGHT,DOWN,LEFT;
	}
	public enum typeOfTile{
		HALL,INTERSECTION,DEADEND;
	}
	
	int up = 2;
	int right = 2;
	int down = 2;
	int left = 2;
	int upShort;
	int rightShort;
	int downShort;
	int leftShort;
	int totalMoves;
	int move;
	int key;
	static int lastMove = 0;
	boolean upIdea;
	boolean rightIdea;
	boolean downIdea;
	boolean leftIdea;
	boolean upMove;
	boolean rightMove;
	boolean downMove;
	boolean leftMove;
	boolean skip;
	boolean deadEnd;
	boolean win = false;
	boolean victory = false;
	public static boolean debugMoves = true;
	static boolean picking;
	static double random;
	Color[] rainbow = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.CYAN, Color.BLUE, Color.MAGENTA};
	HashMap<Integer, Integer[]> shortTermMemory = new HashMap<Integer, Integer[]>();
	HashMap<Integer, Integer[]> longTermMemory = new HashMap<Integer, Integer[]>();

	public Stepper(tileTypes types, Color color) {
		super(types);
		tileColor = color;
	}
	
	public void restart(){
		shortTermMemory.clear();
		longTermMemory.clear();
		win = false;
		victory = false;
		Board.dance = false;
		Board.random = true;
		Board.timing = 0;
		Board.count = 0;
		Board.countR = 0;
		Board.first = true;
		tileColor = Color.BLUE;
	}
	
	public int getUp(){
		upMove = false;
		return up;
	}
	public int getRight(){
		rightMove = false;
		return right;
	}
	public int getDown(){
		downMove = false;
		return down;
	}
	public int getLeft(){
		leftMove = false;
		return left;
	}
	public boolean getWin(){
		return win;
	}
	public boolean possibleMove(int row, int column){
		key = getCTN(row,column);
		try{
			if(this.shortTermMemory.get(key)[5] < 2 && Maze.boardTile[row][column].getTileType() != tileTypes.START){
				return true;
			}else{
				return false;
			}
		}catch(NullPointerException e){
			return false;
		}catch(ArrayIndexOutOfBoundsException e1){
			return false;
		}
	}
	public void setWin(boolean Win){
		win = Win;
	}
	public void setVictory(boolean Victory){
		victory = Victory;
	}
	public Integer getCTN(int row, int column){
		//Get current tile number - CTN
		return Maze.boardTile[row][column].getTileNumber();
	}
	
	//Finds starting tile and sets steppers cord to start
	public void setStepperCord(){
		for(int row = 0; row < Maze.boardTile.length; row++){
			for(int column = 0; column < Maze.boardTile[row].length; column++){
				if(Maze.boardTile[row][column].getTileType() == tileTypes.START){
					rowCord = row;
					columnCord	= column;
				}
			}
		}
	}

	public int checkForOne(int in){
		if(in == 1){
			return 1;
		}else{
			return 0;
		}
	}

	//Sets moves in array (up, right, down, left) to 1 (hall) or 0 (wall)
	public void calculateMoves(int row, int column){
		key = getCTN(row,column);
		totalMoves = 0;
		skip = false;
		if(!win){
			try{
				if(this.shortTermMemory.get(key) == null || this.shortTermMemory.get(key)[0] == 2 || Maze.boardTile[row-1][column].getRowCord() < -20){
					if(Maze.boardTile[row-1][column].getTileType() == tileTypes.HALL || Maze.boardTile[row-1][column].getTileType() == tileTypes.START){
						up = 1;	
					}else{
						up = 0;
					}
					win = Maze.boardTile[row-1][column].getTileType() == tileTypes.END;
					if(win){upMove = true;}
				}else{
					up = this.shortTermMemory.get(key)[0];
				}
			}catch(ArrayIndexOutOfBoundsException e1){
				up = 0;
			}
		}
		if(!win){
			try{
				if(this.shortTermMemory.get(key) == null || this.shortTermMemory.get(key)[1] == 2 || Maze.boardTile[row][column+1].getRowCord() < -20){
					if(Maze.boardTile[row][column+1].getTileType() == tileTypes.HALL || Maze.boardTile[row][column+1].getTileType() == tileTypes.START){
						right = 1;
					}else{
						right = 0;
					}
					win = Maze.boardTile[row][column+1].getTileType() == tileTypes.END;
					if(win){rightMove = true;}
				}else{
					right = this.shortTermMemory.get(key)[1];
				}
			}catch(ArrayIndexOutOfBoundsException e1){
				right = 0;
			}
		}
		if(!win){
			try{
				if(this.shortTermMemory.get(key) == null || this.shortTermMemory.get(key)[2] == 2 || Maze.boardTile[row+1][column].getRowCord() < -20){
					if(Maze.boardTile[row+1][column].getTileType() == tileTypes.HALL || Maze.boardTile[row+1][column].getTileType() == tileTypes.START){
						down = 1;
					}else{
						down = 0;
					}
					win = Maze.boardTile[row+1][column].getTileType() == tileTypes.END;
					if(win){downMove = true;}
				}else{
					down = this.shortTermMemory.get(key)[2];
				}
			}catch(ArrayIndexOutOfBoundsException e1){
				down = 0;
			}
		}
		if(!win){
			try{
				if(this.shortTermMemory.get(key) == null || this.shortTermMemory.get(key)[3] == 2 || Maze.boardTile[row][column-1].getRowCord() < -20){
					if(Maze.boardTile[row][column-1].getTileType() == tileTypes.HALL || Maze.boardTile[row][column-1].getTileType() == tileTypes.START){
						left = 1;
					}else{
						left = 0;
					}
					win = (Maze.boardTile[row][column-1].getTileType() == tileTypes.END);
					if(win){leftMove = true;}
				}else{
					left = this.shortTermMemory.get(key)[3];
				}
			}catch(ArrayIndexOutOfBoundsException e1){
				left = 0;
			}
		}
			totalMoves = checkForOne(up) + checkForOne(right) + checkForOne(down) + checkForOne(left);
			shortTermMemory.put(this.getCTN(row, column), new Integer[] {up,right,down,left,1,totalMoves});
	}

	//Checks if at deadend
	public void reshapeMap(int row, int column){
		key = getCTN(row,column);
		if(this.shortTermMemory.get(key)[5] == 1){
			deadEnd = true;
		}
		if(deadEnd && this.shortTermMemory.get(key)[5] > 1){
			deadEnd = false;
		}
	}
	
	//Randomly selects moves from list
	public void selectMove(int row, int column){
		if(debugMoves && (this.shortTermMemory.get(key)[0] + this.shortTermMemory.get(key)[1] + this.shortTermMemory.get(key)[2] + this.shortTermMemory.get(key)[3])== 1){
			lastMove = 0;
		}
		picking = true;
		key = getCTN(row,column);
		while(picking && !win){
			random = Math.random();
			if(random >= 0.75 && this.shortTermMemory.get(key)[0] == 1){
				if(lastMove == 3){
					if(random >= 0.99 && !debugMoves){
						upMove = true;
						picking = false;
					}
				}else{
					upMove = true;
					picking = false;
				}

			}else if(random >= 0.5 && this.shortTermMemory.get(key)[1] == 1){
				if(lastMove == 4){
					if(random >= 0.74 && !debugMoves){
						rightMove = true;
						picking = false;	
					}
				}else{
					rightMove = true;
					picking = false;
				}
			}else if(random >= 0.25 && this.shortTermMemory.get(key)[2] == 1){
				if(lastMove == 1){
					if(random >= 0.49 && !debugMoves){
						downMove = true;
						picking = false;
					}
				}else{
					downMove = true;
					picking = false;
				}
			}else if(random >= 0 && this.shortTermMemory.get(key)[3] == 1){
				if(lastMove == 2){
					if(random >= 0.24 && !debugMoves){
						leftMove = true;
						picking = false;
					}
				}else{
					leftMove = true;
					picking = false;
				}
			}else{
			}
		}
	}
	
	public int getCurrentMove(){
		if(upMove){
			move = 1;
		}else if(rightMove){
			move = 2;
		}else if(downMove){
			move = 3;
		}else if(leftMove){
			move = 4;
		}
		return move;
	}
	
	public int getCurrentType(){
		return totalMoves;
	}
	
	public boolean getVictory(){
		return this.victory;
	}
	
	public void storeToMemory(int row, int column){
		key = this.getCTN(row, column);
		lastMove = this.getCurrentMove();
		longTermMemory.put(key, new Integer[] {this.getCurrentMove(),this.getCurrentType()});
	}	

	public void printMap(){
		for(int i = 0; i < Maze.boardTile.length * Maze.boardTile[1].length; i++){
			Integer j = i;
			if(i%Maze.boardTile.length == 0){
				System.out.println();
			}
			try{
			System.out.print(longTermMemory.get(j)[0]);
			}catch(NullPointerException e){
				System.out.print(0);
			}
		}
		System.out.println();
	}
	
	public void selectMoveFromMemory(int row, int column){
		key = getCTN(row,column);
		try{
			switch(longTermMemory.get(key)[0]){
			case 1:
				upMove = true;
				victory = Maze.boardTile[row-1][column].getTileType() == tileTypes.END;
				break;
			case 2:
				rightMove = true;
				victory = Maze.boardTile[row][column+1].getTileType() == tileTypes.END;
				break;
			case 3:
				downMove = true;
				victory = Maze.boardTile[row+1][column].getTileType() == tileTypes.END;
				break;
			case 4:
				leftMove = true;
				victory = Maze.boardTile[row][column-1].getTileType() == tileTypes.END;
				break;
			default:
				break;
			}
		}catch(NullPointerException e){
			
		}
	}
	
	public void makeMove(){
		if(upMove){
			this.up();
		}else if(rightMove){
			this.right();
		}else if(downMove){
			this.down();
		}else if(leftMove){
			this.left();
		}
	}
	
	public void clearMoves(){
		upMove = false;
		rightMove = false;
		downMove = false;
		leftMove = false;
	}
	
	//If deadend "mode" is active then close wall behind stepper as it steps
	public void closeDeadEnd(){
		key = this.getCTN(this.rowCord, this.columnCord);
		if(upMove){
			try{
				if(deadEnd){
					upShort = this.shortTermMemory.get(key)[0];
					rightShort = this.shortTermMemory.get(key)[1];
					downShort = 0;
					leftShort = this.shortTermMemory.get(key)[3];
					shortTermMemory.replace(key, new Integer[] {upShort, rightShort, downShort, leftShort, 0});
				}
			}catch(NullPointerException e){
				if(deadEnd){
					upShort = 2;
					rightShort = 2;
					downShort = 0;
					leftShort = 2;
					shortTermMemory.put(key, new Integer[] {upShort, rightShort, downShort, leftShort, 0});
				}
			}
		}else if(rightMove){
			try{
				if(deadEnd){
					upShort = this.shortTermMemory.get(key)[0];
					rightShort = this.shortTermMemory.get(key)[1];
					downShort = this.shortTermMemory.get(key)[2];
					leftShort = 0;
					shortTermMemory.replace(key, new Integer[] {upShort, rightShort, downShort, leftShort, 0});
				}	
			}catch(NullPointerException e){
				if(deadEnd){
					upShort = 2;
					rightShort = 2;
					downShort = 2;
					leftShort = 0;
					shortTermMemory.put(key, new Integer[] {upShort, rightShort, downShort, leftShort, 0});
				}
			}
		}else if(downMove){
			try{
				if(deadEnd){
					upShort = 0;
					rightShort = this.shortTermMemory.get(key)[1];
					downShort = this.shortTermMemory.get(key)[2];
					leftShort = this.shortTermMemory.get(key)[3];
					shortTermMemory.replace(key, new Integer[] {upShort, rightShort, downShort, leftShort, 0});
				}
			}catch(NullPointerException e){
				if(deadEnd){
					upShort = 0;
					rightShort = 2;
					downShort = 2;
					leftShort = 2;
					shortTermMemory.put(key, new Integer[] {upShort, rightShort, downShort, leftShort, 0});
				}
			}
		}else if(leftMove){
			try{
				if(deadEnd){
					upShort = this.shortTermMemory.get(key)[0];
					rightShort = 0;
					downShort = this.shortTermMemory.get(key)[2];
					leftShort = this.shortTermMemory.get(key)[3];
					shortTermMemory.replace(key, new Integer[] {upShort, rightShort, downShort, leftShort, 0});
					}	
			}catch(NullPointerException e){
				if(deadEnd){
					upShort = 2;
					rightShort = 0;
					downShort = 2;
					leftShort = 2;
					shortTermMemory.put(key, new Integer[] {upShort, rightShort, downShort, leftShort, 0});
				}
			}
		}
		upMove = false;
		rightMove = false;
		downMove = false;
		leftMove = false;
	}
	
	public void printMoves(int row, int column){
		for(int i = 0; i<2; i++){
			System.out.println("");
		}
		System.out.println("    " + shortTermMemory.get(this.getCTN(row, column))[0]);
		System.out.println(shortTermMemory.get(this.getCTN(row, column))[3] + "   " + shortTermMemory.get(this.getCTN(row, column))[1]);
		System.out.println("    " + shortTermMemory.get(this.getCTN(row, column))[2]);
		System.out.println(this.getCTN(row, column));
	}
	
	public void victoryDance(int i){
		double r =  Math.random() * getRainbowLength();
		this.tileColor = rainbow[(int)r];
	}
	
	public int getRainbowLength(){
		return rainbow.length;
	}
	public void up(){
		this.rowCord--;
	}
	public void right(){
		this.columnCord++;
	}
	public void down(){
		this.rowCord++;
	}
	public void left(){
		this.columnCord--;
	}
}
