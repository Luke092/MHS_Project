package in_out;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
/**
 * 
 * @verion 0.0
 *Console_FileIO:
 *classe usata per la gestione grafica dei file
 */
public class Console_FileIO
{
	private JFileChooser sceltafile= new JFileChooser();
	private final String FILE_NON_ESISTE="ERRORE: il file non esiste.";
	private final String PERCORSO_FILE=System.getProperty("user.dir");
	private Component c=new Component() {private static final long serialVersionUID = 1L;};
	/**
	 * loadFile
	 * carica un object da un file .dat
	 * @return Object
	 */
	protected Object loadFile()
	{
		sceltafile.setCurrentDirectory(new File(PERCORSO_FILE));
		sceltafile.setFileFilter(new FiltroDat());
		sceltafile.setDialogTitle("scegli un file");		
		int i=sceltafile.showOpenDialog(c);
		if(i == JFileChooser.APPROVE_OPTION) {
			File filescelto = sceltafile.getSelectedFile();
			return caricaObject(filescelto);
		}
		return null;
	}
	/**
	 * saveFile:
	 * salva un object in un file .dat
	 * @param o Object
	 * @return boolean
	 */
	protected boolean saveFile(Object o)
	{
		sceltafile.setCurrentDirectory(new File(PERCORSO_FILE));
		sceltafile.setFileFilter(new FiltroDat());
		sceltafile.setDialogTitle("scegli un file");		
		int i=sceltafile.showOpenDialog(c);
		if(i == JFileChooser.APPROVE_OPTION) {
			File filescelto = sceltafile.getSelectedFile();
			return salvaObject(o,filescelto);
		}
		return false;
	}
	/**
	 * loadString:
	 * carica una stringa da un file .txt
	 * @return String
	 */
	protected String loadString()
	{
		String stringa="";
		sceltafile.setCurrentDirectory(new File(PERCORSO_FILE));
		sceltafile.setFileFilter(new FiltroTxt());
		sceltafile.setDialogTitle("scegli un file");		
		int i=sceltafile.showOpenDialog(c);
		if(i == JFileChooser.APPROVE_OPTION) {
			File filescelto = sceltafile.getSelectedFile();
			stringa=caricaString(filescelto);
			return stringa;
		}
		return stringa;
	}
	/**
	 * saveString:
	 * salva una stringa in un file .txt
	 * @param stringa String
	 * @return boolean
	 */
	protected boolean saveString(String stringa)
	{
		sceltafile.setCurrentDirectory(new File(PERCORSO_FILE));
		sceltafile.setFileFilter(new FiltroTxt());
		sceltafile.setDialogTitle("scegli un file");		
		int i=sceltafile.showOpenDialog(c);
		if(i == JFileChooser.APPROVE_OPTION) {
			File filescelto = sceltafile.getSelectedFile();
			return salvaString(stringa,filescelto);
		}
		return false;
	}
	/**
	 * delFileDat:
	 * elimina un file .dat
	 * @return boolean
	 */
	protected boolean delFileDat()
	{
		sceltafile.setCurrentDirectory(new File(PERCORSO_FILE));
		sceltafile.setFileFilter(new FiltroDat());
		sceltafile.setDialogTitle("scegli un file");		
		int i=sceltafile.showOpenDialog(c);
		if(i == JFileChooser.APPROVE_OPTION) {
			File filescelto = sceltafile.getSelectedFile();
			if(filescelto.exists()){filescelto.delete();return true;}
		}
		return false;
	}
	/**
	 * delFileTxt:
	 * elimina un file .txt
	 * @return boolean
	 */
	protected boolean delFileTxt()
	{
		sceltafile.setCurrentDirectory(new File(PERCORSO_FILE));
		sceltafile.setFileFilter(new FiltroTxt());
		sceltafile.setDialogTitle("scegli un file");		
		int i=sceltafile.showOpenDialog(c);
		if(i == JFileChooser.APPROVE_OPTION) {
			File filescelto = sceltafile.getSelectedFile();
			if(filescelto.exists()){filescelto.delete();return true;}
		}
		return false;
	}
	private boolean salvaString(String stringa, File f)
	{
		boolean salvato=false;
		try {
			f.createNewFile();
			f.exists();
			PrintWriter pw=new PrintWriter(
					new BufferedWriter(
							new FileWriter(f)));
			pw.println(stringa);
			pw.close();
			salvato=true;
		} catch (IOException e) {
			System.out.println("ERRORE: "+e);
		}
		return salvato;
	}
	private String caricaString(File f)
	{
		String oggetto = "";
		try {
			if(f.exists())
			{
				BufferedReader input = new BufferedReader(new FileReader(f));
				StringBuffer buffer = new StringBuffer();
				String text;
				while ((text = input.readLine()) != null){buffer.append(text + "\n");}
				oggetto=buffer.toString();
				input.close();
			}else{
				System.out.println(FILE_NON_ESISTE);
			}
		} catch (FileNotFoundException e){
			System.out.println("ERRORE: "+e);
		} catch (IOException e){
			System.out.println("ERRORE: "+e);
		}
		return oggetto;
	}
	private boolean salvaObject(Object oggetto,File f)
	{
		boolean salvato=false;
			try {
				f.createNewFile();
				f.exists();
				ObjectOutputStream sorgente=new ObjectOutputStream(
						new BufferedOutputStream(
								new FileOutputStream(f)));
				sorgente.writeObject(oggetto);
				sorgente.close();
				salvato=true;
			} catch (FileNotFoundException e){
				System.out.println("ERRORE: "+e);
			} catch (IOException e){
				System.out.println("ERRORE: "+e);
			}
		return salvato;
	}
	private Object caricaObject(File f)
	{
		Object oggetto = null;
			try {
				if(f.exists())
				{
					ObjectInputStream sorgente=new ObjectInputStream(
							new BufferedInputStream(
									new FileInputStream(f)));
					try {
						oggetto=sorgente.readObject();
					} catch (ClassNotFoundException e){
						System.out.println("ERRORE: "+e);
					}
					sorgente.close();
				}else{
					System.out.println(FILE_NON_ESISTE);
				}
			} catch (FileNotFoundException e){
				System.out.println("ERRORE: "+e);
			} catch (IOException e){
				System.out.println("ERRORE: "+e);
			}
		return oggetto;
	}
public class FiltroDat extends FileFilter {
	@Override
	public boolean accept(File file)
	{
	    if (file.isDirectory()) return true;
	    String fname = file.getName().toLowerCase();
	    return fname.endsWith(".dat");
	}
	@Override
	public String getDescription(){return "file '.dat'";}
}
public class FiltroTxt extends FileFilter {
	@Override
	public boolean accept(File file)
	{
	    if (file.isDirectory()) return true;
	    String fname = file.getName().toLowerCase();
	    return fname.endsWith(".txt");
	}
	@Override
	public String getDescription(){return "file '.txt'";}
}
}
