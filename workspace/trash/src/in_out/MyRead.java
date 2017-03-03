package MyClasses;

import java.util.*;
/**
 * classe che contiene svariati metodi di lettura.
 * @author Gianmaria Micheli
 *
 */
public class MyRead
{
	
	private static final String ERRORE_FORMATO = "errore nel formato di inserimento, riprovare\n";
	
	private static Scanner lettore= new Scanner(System.in);
		private static Scanner lettoreEnter= new Scanner(System.in);
	
	/**
	 * metodo che fa in modo che il parametro lettore non consideri l'invio come carattere.
	 */
	static {
		lettore.useDelimiter(System.getProperty("line.separator"));
	}
	
	/**
	 * metodo che permette di inserire una stringa da tastiera.
	 * @param messaggio il messaggio da visualizzare a schermo.
	 * @return la stringa inserita.
	 */
	public static String leggiStringa(String messaggio)
	{
		System.out.println(messaggio);
		return lettore.next();
	}
	
	/**
	 * metodo che permette di inserire una stringa non vuota da tastiera.
	 * @param messaggio il messaggio da visualizzare a schermo.
	 * @return la stringa non vuota inserita.
	 */
	public static String leggiStringaValida(String messaggio)
	{
		boolean invalido=true;
		String stringa= null;
		while(invalido)
		{	
			System.out.println(messaggio);
			stringa=lettoreEnter.nextLine();
			if(stringa.trim().length()==0)
			{
				System.out.println("errore nell'inserimento");
			}
			
			else
			{
				invalido=false;
			}
		}
		return stringa;
	}
	
	/**
	 * metodo che permette di inserire un carattere da tastiera.
	 * @param messaggio il messaggio da visualizzare a schermo.
	 * @return il carattere inserito.
	 */
	public static char leggiChar(String messaggio)
	{
		String stringa;
		System.out.println(messaggio);
		stringa=lettore.next();
		return stringa.charAt(0);
	}
	
	/**
	 * metodo che permette di inserire un carattere sempre maiuscolo da tastiera.
	 * @param messaggio il messaggio da visualizzare a schermo.
	 * @return il carattere inserito in maiuscolo.
	 */
	public static char leggiCharCaps(String messaggio)
	{
		String stringa;
		System.out.println(messaggio);
		stringa=lettore.next();
		stringa=stringa.toUpperCase();
		return stringa.charAt(0);

	}
	
	/**
	 * metodo che permette di inserire un numero intero da tastiera.
	 * @param messaggio il messaggio da visualizzare a schermo.
	 * @return il numero intero inserito.
	 */
	public static int leggiInt(String messaggio)
	{
		int risultato = 0;
		boolean trovato = false;
		do
		{
			System.out.println(messaggio);
			if(lettore.hasNextInt())
			{
				risultato = lettore.nextInt();
				trovato = true;
			}
			else
			{
				System.out.println(ERRORE_FORMATO);
				@SuppressWarnings("unused")
				String daButtare = lettore.next();
			}
		}
		while(!trovato);
		return risultato;
	}
	
	/**
	 * metodo che legge un intero senza stampare un messaggio a schermo.
	 * @return il numero intero inserito.
	 */
	public static int leggiInt(String INTESTAZIONE, MyMenu menu)
	{
		int risultato = 0;
		boolean trovato = false;
		do
		{
			System.out.println(INTESTAZIONE);
			menu.stampaMenu();
			if(lettore.hasNextInt())
			{
				risultato = lettore.nextInt();
				trovato = true;
			}
			else
			{
				System.out.println(ERRORE_FORMATO);
				@SuppressWarnings("unused")
				String daButtare = lettore.next();
			}
		}
		while(!trovato);
		return risultato;
	}
	/**
	 * metodo che permette di inserire un numero intero valido per un'età da maggiorenne.
	 * @return un numero intero compreso tra i valori indicati.
	 */
	public static int leggiIntIntervallo(String messaggio,String messaggioErrore, int valoreMinimo, 
											int valoreMassimo)
	{
		int valore=0;
		boolean invalido=true;
		while(invalido)
		{
			System.out.println(messaggio);
			if(lettore.hasNextInt())
			{
				valore = lettore.nextInt();
				if((valore < valoreMinimo) || (valore > valoreMassimo))
					System.out.println(messaggioErrore);
				else
				{
					invalido=false;
				}
			}
			else
			{
				System.out.println(ERRORE_FORMATO);
				@SuppressWarnings("unused")
				String daButtare = lettore.next();
			}
		}
		return valore;	
	}
	
	/**
	 * metodo che legge un valore compreso in un intervallo stampandone a schermo il menu.
	 * @param messaggioScelta messaggio di partenza.
	 * @param menu menu di scelta.
	 * @param messaggioErrore messaggio di errore di inserimento
	 * @param valoreMinimo
	 * @param valoreMassimo
	 * @return l'int inserito.
	 */
	public static int leggiIntIntervallo(String messaggioScelta, MyMenu menu,String messaggioErrore, int valoreMinimo, 
			int valoreMassimo)
	{
		int valore = 0;
		boolean invalido = true;
		while(invalido)
		{
			System.out.println(messaggioScelta);
			menu.stampaMenu();
			if(lettore.hasNextInt())
			{
				valore = lettore.nextInt();
				if((valore < valoreMinimo) || (valore > valoreMassimo))
					System.out.println(messaggioErrore);
				else
				{
					invalido=false;
				}
			}
			else
			{
				System.out.println(ERRORE_FORMATO);
				@SuppressWarnings("unused")
				String daButtare = lettore.next();
			}
		}
		return valore;
	}
	
	
	/**
	 * metodo che permette di inserire un numero double da tastiera.
	 * @param messaggio il messaggio da visualizzare a schermo.
	 * @return il numero double inserito.
	 */
	public static double leggiDouble(String messaggio)
	{
		double risultato = 0;
		boolean trovato = false;
		do
		{
			System.out.println(messaggio);
			if(lettore.hasNextDouble())
			{
				risultato = lettore.nextDouble();
				trovato = true;
			}
			else
			{
				System.out.println(ERRORE_FORMATO);
				@SuppressWarnings("unused")
				String daButtare = lettore.nextLine();
			}
		}
		while(!trovato);
		return risultato;
	}
	
	/**
     * metodo che permette l'inserimento di un array di interi da tastiera.
     * @param lunghezza lunghezza dell'array da inserire.
     * @return un array di interi.
     */
	public static int [] leggiArrayIntero(int lunghezza, String messaggio)
	{
		int i=0;
		int array[] = new int [lunghezza];
		int valore = 0;
		
		for(i = 0; i < lunghezza ; i++)
		{
			valore = i +1;
			String stringa = (messaggio + " " + valore);
			array[i] = MyRead.leggiInt(stringa);
			valore = 0;
		}
		return array;
	}
	
	/**
	 * metodo che permette la lettura di una matrice quadrata intera.
	 * @param dimensioneLato dimensione della matrice quadrata.
	 * @param messaggio1 messaggio per la lettura della riga.
	 * @param messaggio2 messaggio per la lettura del singolo valore della riga.
	 * @return una matrice quadrata di valori interi.
	 */
	public static int [][] leggiMatriceQuadrataIntera(int dimensioneLato, String messaggio1, String messaggio2)
	{
		int j = 0;
		int matrice[][]= new int [dimensioneLato][dimensioneLato];
		int valore = 0;
		
		for(j = 0; j < dimensioneLato; j++)
		{
			valore = j + 1;
			System.out.println(messaggio1 + " " + valore);
			matrice[j] = leggiArrayIntero(dimensioneLato, messaggio2);
		}
		
		return matrice;
	}
}
