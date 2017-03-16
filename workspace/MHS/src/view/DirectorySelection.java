package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DirectorySelection extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;
	private int result;
	
	/**
	 * constructor
	 */
	public DirectorySelection(String title)
	{
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		result = fileChooser.showOpenDialog(null);
	}

	/**
	 * 
	 * @return the selected File
	 */
	public File getFile()
	{
		if(result == JFileChooser.APPROVE_OPTION)
			return fileChooser.getSelectedFile();
		else
			return null;
	}
}
