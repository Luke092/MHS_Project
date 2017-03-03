package in_out;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Terminal_v1 {
	
	private static BufferedReader br;
	private static String _String;
	private static int _int;
	private static char _char;
	private static double _double;
	
	private final static String AVVISO_MIN="inserire un valore maggiore o uguale a ";
	private final static String AVVISO_MAX="inserire un valore minore o uguale a ";

	/**
	 * leggiDoubleMinMax:
	 * metodo che legge un double compreso tra 'min' e 'max', estremi inclusi 
	 * @param min
	 * @param max
	 * @return double
	 */
	protected static double leggiDoubleLimitatoMinMax(double min,double max)
	{
		boolean controllo=false;
		do{
			br = new BufferedReader(new InputStreamReader(System.in));
		    try
		    {
		         _String = br.readLine();
		         _double = Double.parseDouble(_String);
		         controllo=true;
			}catch(Exception e)
			{
				stampaString("inserisci un numero.",true);
				controllo=false;
			}
			if(_double<min)
			{
				controllo=false;
				stampaString(AVVISO_MIN+min,true);
			}
			if(_double>max)
			{
				controllo=false;
				stampaString(AVVISO_MAX+max,true);
			}
		}while(controllo!=true);
		return _double;
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
		boolean controllo;
		do{
			br = new BufferedReader(new InputStreamReader(System.in));
		    try
		    {
		         _String = br.readLine();
		         _int = Integer.parseInt(_String);
		    	controllo=true;		
			}catch(Exception e)
			{
				stampaString("inserisci un numero intero.",true);
				controllo=false;
			}
			if(_int<min)
			{
				controllo=false;
				stampaString(AVVISO_MIN+min,true);
			}
			if(_int>max)
			{
				controllo=false;
				stampaString(AVVISO_MAX+max,true);
			}
		}while(controllo!=true);
		return _int;
	}
	/**
	 * leggiString:
	 * legge una stringa.
	 * @param aCapo
	 * @return string
	 */
	protected static String leggiString()
	{
		boolean controllo;
		do{ 
			br = new BufferedReader(new InputStreamReader(System.in));
		    try
		    {
		    	_String = br.readLine();
				controllo=true;
			}catch(Exception e)
			{
				stampaString("inserisci una stringa di caratteri.",true);
				controllo=false;
			}
		}while(controllo!=true);
		return _String;
	}
	/**
	 * leggiChar:
	 * legge un solo carattere
	 * @return char
	 */
	protected static char leggiChar()
	{
		boolean controllo;
		do{
			br = new BufferedReader(new InputStreamReader(System.in));
			try
		    {
		       _String = br.readLine();
		       _char = _String.charAt(0);
		       controllo=true;
			}catch(Exception e)
			{
				stampaString("inserisci un carattere.",true);
				controllo=false;
			}
			if(_String.length()>1)
			{
				controllo=false;
				stampaString("inserisci un solo carattere.",true);
			}
		}while(controllo!=true);
		return _char;
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
		Scanner sc = new Scanner(System.in);
		do{
			_String=leggiString();
			controllo=true;
			if(_String.equalsIgnoreCase("SI")||_String.equalsIgnoreCase("sì"))
			{
				risposta=true;
			}else{
				if(_String.equalsIgnoreCase("NO"))
				{
					risposta=false;
				}else{
					stampaString("la risposta può essere si o no.",true);
					controllo=false;
				}
			}
		}while(controllo!=true);
		sc.close();
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
