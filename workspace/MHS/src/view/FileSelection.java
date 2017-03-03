package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelection extends JPanel
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
	public FileSelection(String title,String ... extensions)
	{
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("", extensions);
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle(title);
		fileChooser.setCurrentDirectory(new File("."));
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
