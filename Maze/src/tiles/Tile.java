package tiles;

import java.awt.Color;

public class Tile {

	public enum tileTypes{
		WALL,HALL,START,END,STEPPER,GENERATOR
	}
	
	protected int rowCord;
	protected int columnCord;
	protected int tileNumber;
	tileTypes tileType;
	protected Color tileColor;
	boolean endTile;
	boolean hallTile;
	boolean startTile;
	boolean wallTile;
	boolean stepperTile;
	
	public Tile(tileTypes types){
		tileType = types;
	}
	
	public Tile(int row, int column, tileTypes types, Color color, int number){
		rowCord = row;
		columnCord = column;
		tileType = types;
		tileColor = color;
		tileNumber = number;
	}

	public int getTileNumber(){
		return tileNumber;
	}
	
	public int getRowCord() {
		return rowCord;
	}

	public void setRowCord(int rowCord) {
		this.rowCord = rowCord;
	}

	public int getColumnCord() {
		return columnCord;
	}

	public void setColumnCord(int columnCord) {
		this.columnCord = columnCord;
	}

	public tileTypes getTileType() {
		return tileType;
	}

	public void setTileType(tileTypes tileType) {
		this.tileType = tileType;
	}

	public Color getTileColor() {
		return tileColor;
	}

	public void setTileColor(Color Color) {
		tileColor = Color;
	}

}
