package fileSelector;

import java.io.File;

import javax.swing.JFileChooser;

public class FileChooser {

	final static JFileChooser fc = new JFileChooser();
	
	public static File getFile(){
		
		fc.setCurrentDirectory(new java.io.File("C:/Development/Java/Maze/Maze code/"));
		fc.setDialogTitle("Select maze file");
		fc.showOpenDialog(null);
		return fc.getSelectedFile();
	}
	
}
