package in_out;

import java.util.*;
/**
 * 
 *Input:
 *classe che gestisce qualsiasi input da tastiera
 */
public class Terminal {
	
	private final static String AVVISO_MIN="inserire un valore maggiore o uguale a ";
	private final static String AVVISO_MAX="inserire un valore minore o uguale a ";
	private static Scanner sc = new Scanner(System.in);

	/**
	 * leggiDoubleMinMax:
	 * metodo che legge un double compreso tra 'min' e 'max', estremi inclusi 
	 * @param min
	 * @param max
	 * @return double
	 */
	protected static double leggiDoubleLimitatoMinMax(double min,double max)
	{
		boolean controllo;
		double n=0; 
		do{
			try{
					n=Double.parseDouble(leggiString());
					//n=sc.nextDouble();
					controllo=true;
			}catch(Exception e)
			{
				stampaString("inserisci un numero.",true);
				controllo=false;
			}
			if(n<min)
			{
				controllo=false;
				stampaString(AVVISO_MIN+min,true);
			}
			if(n>max)
			{
				controllo=false;
				stampaString(AVVISO_MAX+max,true);
			}
		}while(controllo!=true);
		return n;
	}
	/**
	 * leggiintMinMax:
	 * metodo che legge un int compreso tra 'min' e 'max', estremi inclusi 
	 * @param min
	 * @param max
	 * @return int
	 */
	protected static int leggiIntLimitatoMinMax(int min,int max)
	{
		int n=0;
		boolean controllo;
		do{
			try{
					n=Integer.parseInt(leggiString());
					//n=sc.nextInt();
					controllo=true;		
			}catch(Exception e)
			{
				stampaString("inserisci un numero intero.",true);
				controllo=false;
			}
			if(n<min)
			{
				controllo=false;
				stampaString(AVVISO_MIN+min,true);
			}
			if(n>max)
			{
				controllo=false;
				stampaString(AVVISO_MAX+max,true);
			}
		}while(controllo!=true);
		return n;
	}
	/**
	 * leggiString:
	 * legge una stringa.
	 * se il valore aCapo==true allora viene usato come separatore il tasto invio
	 * @param aCapo
	 * @return string
	 */
	protected static String leggiString()
	{
		String invio = System.getProperty("line.separator");
		sc.useDelimiter(invio);
		boolean controllo;
		String stringa=null;
		do{
			try{
					stringa=sc.next();
					controllo=true;
			}catch(Exception e)
			{
				stampaString("inserisci una stringa di caratteri.",true);
				controllo=false;
			}
		}while(controllo!=true);
		return stringa;
	}
	/**
	 * leggiChar:
	 * legge un solo carattere
	 * @return char
	 */
	protected static char leggiChar()
	{
		char car = 0;
		boolean controllo;
		String stringaLetta=null;
		do{
			stringaLetta=leggiString();
			car=stringaLetta.charAt(0);
			controllo=true;
			if(stringaLetta.length()>1)
			{
				controllo=false;
				stampaString("inserisci un solo carattere.",true);
				
			}
		}while(controllo!=true);
		return car;
	}
	/**
	 * siono:
	 * legge una risposta che può essere si o no
	 * (si, sì, SI, Sì, no, NO, etc...) e restituisce un boolean
	 * @return boolean
	 */
	protected static boolean siono()
	{
		boolean risposta = false,controllo;
		String stringa=null;
		do{
			stringa=leggiString();
			controllo=true;
			if(stringa.equalsIgnoreCase("SI")||stringa.equalsIgnoreCase("sì"))
			{
				risposta=true;
			}else{
				if(stringa.equalsIgnoreCase("NO"))
				{
					risposta=false;
				}else{
					stampaString("la risposta può essere si o no.",true);
					controllo=false;
				}
			}
		}while(controllo!=true);
		return risposta;
	}
	/**
	 * stampaChar:
	 * stampa un carattere
	 * @param car
	 * @param aCapo
	 */
	protected static void stampaChar(char car,boolean aCapo)
	{
		System.out.print(car);
		if(aCapo){System.out.println();}
	}
	/**
	 * stampaString:
	 * stampa una stringa
	 * @param stringa
	 * @param aCapo
	 */
	protected static void stampaString(String stringa, boolean aCapo)
	{
		System.out.print(stringa);
		if(aCapo){System.out.println();}
	}
}