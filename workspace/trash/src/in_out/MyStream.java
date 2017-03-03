package MyClasses;
import java.io.*;
 
/**
 * Classe che permette il salvataggio e il caricamento dati.
 * @author Gianmaria Micheli
 */
public class MyStream implements Serializable
{
	private static final long serialVersionUID = 1L;
   
	private static final String MSG_OK_FILE = "File caricato correttamente.\n";
    private static final String MSG_SALVA = "File salvato correttamente\n";
    private static final String DOMANDA_SOVRASCRIVERE = "File già presente. Sovrascrivere?\n1-sì\n2-no ";
    private static final String ERRORE_SCELTA = "errore, scegliere una delle opzioni indicate.\n";
    private static final String NO_CLOSE = "Attenzione: riscontrato problema con la chiusura del file ";
    private static final String NO_FILE = "Attenzione: file non trovato ";
    private static final String NO_READ = "Attenzione: riscontrato problema con la lettura del file ";
    private static final String NO_CAST = "Attenzione il file con cast non corrisponde: lettura del file fallita ";
    private static final String NO_WRITE = "Attenzione: riscontrato problema con la scrittura del file ";
 

    /**
     * Metodo che permette di caricare un oggetto da file
     * @param f file da cui caricare.
     * @return l'oggetto letto dal file.
     */
    public static Object caricaSingoloOggetto (File f)
    {
    	Object letto = null;
        ObjectInputStream ingresso = null;
                               
        try
        {
        	ingresso = new ObjectInputStream( new BufferedInputStream( new FileInputStream(f)));
            letto = ingresso.readObject();
        }
        catch (FileNotFoundException excNotFound)
        {
        	System.out.println(NO_FILE + f.getName() + "\n");
        }
        catch (IOException excLettura)
        {
        	System.out.println(NO_READ + f.getName() + "\n");
        }
        catch (ClassNotFoundException excCast)
        {
        	System.out.println(NO_CAST + f.getName() + "\n");
        }
                               
        finally
        {
        	if (ingresso != null)
            {
        		try
                {
        			ingresso.close();
                }
                catch (IOException excChiusura)
                {
                	System.out.println(NO_CLOSE + f.getName() + "\n");
                }
            }
        }                              
        return letto;
    }
    
    /**
    * Metodo per caricare un file
    * @param f file da cui effettuare il caricamento.
    * @param oggetto oggetto da caricare.
    * @return  l'oggetto caricato.
    */
    public static Object caricaFile (File f, Object oggetto)
    {
    	if (f.exists())
        {
    		try
            {
    			oggetto = (Object) caricaSingoloOggetto(f);
            }
            catch (ClassCastException excCast)
            {
            	System.out.println(NO_CAST);
            }
            finally
            {
            	if (oggetto != null)
                {
            		System.out.println(MSG_OK_FILE);
            		return oggetto;
                }
            }
        }
        else
        {
        	System.out.println(NO_FILE);
        }
        return null;    
    }
    
    
    /**
     * Metodo che consente di salvare un oggetto sul file in questione.
     * @param f file su cui avviene il salvataggio.
     * @param Object daSalvare l'oggetto da salvare.
     */
    public static void salvaSingoloOggetto (File f, Object daSalvare)
    {
    	ObjectOutputStream uscita = null;
                
    	try
    	{
    		uscita = new ObjectOutputStream( new BufferedOutputStream( new FileOutputStream(f)));
    		uscita.writeObject(daSalvare);
    		System.out.println(MSG_SALVA);
    	}
    	catch (IOException excScrittura)
    	{
    		System.out.println(NO_WRITE + f.getName() + "\n");
    	}
    	finally
    	{
    		if (uscita != null)
    		{
    			try
    			{
    				uscita.close();                                    
    			}	
    			catch (IOException excChiusura)
    			{
    				System.out.println(NO_CLOSE + f.getName() + "\n");
    			}
    		}
    	}
    }
    
    /**
    * Metodo per salvare un file
    * @param f file su cui salvare
    * @param oggetto oggetto da salvare.
    * @param controlloSovrascrittura se true avviene in controllo della sovrascrittura del file, se false non avviene.
    */
    public static void salvaFile(File f, Object oggetto, Boolean controlloSovrascrittura)
    {
    	if (f.exists())
        {
    		if (controlloSovrascrittura)
            {
    			if (MyRead.leggiIntIntervallo(DOMANDA_SOVRASCRIVERE, ERRORE_SCELTA, 1, 2) == 1)
    				salvaSingoloOggetto(f, oggetto);
            }
            else
            {
            	salvaSingoloOggetto(f, oggetto);                                       
            }
        }                      
        else
        {
        	System.out.println(NO_FILE);
        }
    }
    
}