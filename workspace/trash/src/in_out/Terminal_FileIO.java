package in_out;
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
/**
 * la classe FileIO
 * salva file .txt e .dat e carica oggetti da file .dat
 *
 */
public class Terminal_FileIO
{
	private final String FILE_NON_ESISTE="ERRORE: il file non esiste.";
	private final String NOME_FILE_ERRATO="ERRORE: nome file errato.";
	private final String PERCORSO_FILE=System.getProperty("user.dir")+"\\";
	private final String NULLA="";
	private File file;

	/**
	 * creaNomeFile:
	 * crea il nome di un file.
	 * percorso corrente+percorsoFile+_nome+.txt/.dat
	 * @param _nome
	 * @param percorsoFile
	 * @param txt
	 * @return String
	 */
	protected String creaNomeFile(String _nome,String percorsoFile,boolean txt)
	{
		String nome;
		if(percorsoFile.equalsIgnoreCase(NULLA))
		{
			nome=PERCORSO_FILE+_nome;
		}else{
			nome=percorsoFile+_nome;
		}
		if(txt)
		{
			if(!nome.subSequence(nome.length()-4, nome.length()).equals(".txt")){nome=nome+".txt";}
		}else{
			if(!nome.subSequence(nome.length()-4, nome.length()).equals(".dat")){nome=nome+".dat";}
		}
		return nome;
	}
	/**
	 * salvaString:
	 * salva 'stringa' nel file 'nomeFile' di tipo .txt
	 * @param stringa
	 * @param nomeFile
	 * @return boolean
	 */
	protected boolean salvaString(String stringa, String nomeFile)
	{
		boolean salvato=false;
		if(nomeFile.contains(".txt"))
		{
			file=new File(nomeFile);
			try {
				if(file.exists())
				{
					Terminal.stampaString("ERRORE:il file esiste già sovrascriverlo? [si-no] ",false);
					if(Terminal.siono()){file.delete();}
				}
				if(file.createNewFile())
				{
					PrintWriter pw=new PrintWriter(
							new BufferedWriter(
									new FileWriter(file)));
					pw.println(stringa);
					pw.close();
					salvato=true;
				}
			} catch (IOException e) {
				System.out.println("ERRORE: "+e);
			}
		}else{
			System.out.println(NOME_FILE_ERRATO);
			}
		return salvato;
	}
	/**
	 * caricaString:
	 * carica una stringa da un file .txt
	 * @param nomeFile
	 * @return String
	 */
	protected String caricaString(String nomeFile)
	{
		String oggetto = "";
		if(nomeFile.contains(".txt"))
		{
			file=new File(nomeFile);
			try {
				if(file.exists())
				{
					BufferedReader input = new BufferedReader(new FileReader(file));
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
		}else{
			System.out.println(NOME_FILE_ERRATO);
		}
		return oggetto;
	}
	/**
	 * salvaObject:
	 * salva un 'oggetto' nel file 'nomeFile' di tipo .dat
	 * @param oggetto
	 * @param nomeFile
	 * @return boolean
	 */
	protected boolean salvaObject(Object oggetto, String nomeFile)
	{
		boolean salvato=false;
		if(nomeFile.contains(".dat"))
		{
			file=new File(nomeFile);
			try {
				if(file.exists())
				{
					Terminal.stampaString("ERRORE:il file esiste già sovrascriverlo? [si-no] ",false);
					if(Terminal.siono()){file.delete();}
				}
				if(file.createNewFile())
				{
					ObjectOutputStream sorgente=new ObjectOutputStream(
							new BufferedOutputStream(
									new FileOutputStream(file)));
					sorgente.writeObject(oggetto);
					sorgente.close();
					salvato=true;
				}
			} catch (FileNotFoundException e){
				System.out.println("ERRORE: "+e);
			} catch (IOException e){
				System.out.println("ERRORE: "+e);
			}
		}else{
			System.out.println(NOME_FILE_ERRATO);
			}
		return salvato;
	}/**
	 * cancFile:
	 * elimina il file 'nomeFile' di tipo .dat o .txt
	 * @param nomeFile
	 * @return boolean
	 */
	protected boolean cancFile(String nomeFile)
	{
		boolean canc=false;
		if(nomeFile.contains(".dat")||nomeFile.contains(".txt"))
		{
			file=new File(nomeFile);
			if(file.exists()){file.delete();canc=true;}
		}else{
			System.out.println(NOME_FILE_ERRATO);
			}
		return canc;
	}
	/**
	 * caricaObject
	 * carica un oggetto dal file 'nomeFile' di tipo .dat
	 * @param nomeFile
	 * @return Object
	 */
	protected Object caricaObject(String nomeFile)
	{
		Object oggetto = null;
		if(nomeFile.contains(".dat"))
		{
			file=new File(nomeFile);
			try {
				if(file.exists())
				{
					ObjectInputStream sorgente=new ObjectInputStream(
							new BufferedInputStream(
									new FileInputStream(file)));
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
		}else{
			System.out.println(NOME_FILE_ERRATO);
		}
		return oggetto;
	}
	/**
	 * fileLista:
	 * ritorna la lista dei file in un certo percorso.
	 * se numeroElementi==true torna una String[1] che contiene la lunghezza della lista
	 * @param percorso
	 * @param numeroElementi
	 * @return String[]
	 */
	protected String[] fileLista(String percorso)
	{
		String stringa;
		if(percorso.equalsIgnoreCase(NULLA))
		{
			stringa=PERCORSO_FILE;
		}else{
			stringa=percorso;
		}
		file=new File(stringa);
		return file.list();
	}
}