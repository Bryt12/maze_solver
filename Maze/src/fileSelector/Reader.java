package fileSelector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import board.Maze;

public class Reader {

	static boolean open = false;
	static boolean inputMode = false;
	static int insideCounter = 0;
	static int outsideCounter = 0;
	
	public static void readFile(){
		File mazeFile;
		BufferedReader br = null;
		FileReader fr = null;
		
		try{
			mazeFile = new File("C:/Development/Java/Maze/Maze code/mazeCode.maze");
			fr = new FileReader(mazeFile);
			br = new BufferedReader(fr);
			try {
				stringToArray(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(FileNotFoundException fnfe){
			stringToArray("{110011100011}{000100001000}{010110101110}{310000111000}{111111000110}{100000010000}{101111011101}{000000110000}{010110001110}{110011100010}{000110011010}{010000111210}");
		}
	}
	
	@SuppressWarnings("resource")
	public static int readSpeed(){
		File mazeFile;
		BufferedReader br = null;
		FileReader fr = null;
		
		try{
			mazeFile = new File("C:/Development/Java/Maze/Maze Code/Setup.txt");
			fr = new FileReader(mazeFile);
			br = new BufferedReader(fr);
			try {
				return Integer.parseInt(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
		}catch(FileNotFoundException fnfe){
			return 0;
		}
	}
	
	public static void pickFile(){
		File mazeFile;
		BufferedReader br = null;
		FileReader fr = null;
		try{
			try{
				mazeFile = FileChooser.getFile();
				fr = new FileReader(mazeFile);
				br = new BufferedReader(fr);
				try {
					stringToArray(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}catch(FileNotFoundException fnfe){
				stringToArray("{110011100011}{000100001000}{010110101110}{310000111000}{111111000110}{100000010000}{101111011101}{000000110000}{010110001110}{110011100010}{000110011010}{010000111210}");
			}
		}catch(NullPointerException e1){
			stringToArray("{110011100011}{000100001000}{010110101110}{310000111000}{111111000110}{100000010000}{101111011101}{000000110000}{010110001110}{110011100010}{000110011010}{010000111210}");
		}
	}
	
	public static void stringToArray(String code){
		int counter = 0;
		for(int i = 0; i < code.length();i++){
			if(code.charAt(i) == '{'){
				counter++;
			}
		}
		Maze.boardInt = new int[counter][counter];
		open = false;
		insideCounter = 0;
		outsideCounter = 0;
		for(int i = 0; i < code.length();i++){
			if(code.charAt(i) == '{'){
				open = true;
			}else if(code.charAt(i) == '}'){
				open = false;
				outsideCounter++;
			}
			if(open && code.charAt(i)!='{'){
				Maze.boardInt[outsideCounter][insideCounter] = Character.getNumericValue(code.charAt(i));
				insideCounter++;
			}else if(!open && code.charAt(i)!='{'){
				insideCounter = 0;
			}
		}
	}
}