package in_out;
import java.util.*;
/**
 * 
 *Menu:
 *classe che gestisce i menu e le relative scelte.
 */
public class Terminal_MenuAsterisco
{
	private int numeroScelte,scelta,lunghezzaMax,lunghezzaCornice;
	private String[] scelte;
	private String[] riempimento;
	private String titolo;
	private boolean numeri;
	private String cornice="*";
	private final String AVVISO_MIN="inserire un valore maggiore o uguale a ";
	private final String AVVISO_MAX="inserire un valore minore o uguale a ";
	/**
	 * costruttore:
	 * riceve un'array di stringhe che rappresentano le scelte,
	 * una stringa che rappresenta il titolo del men�, 
	 * e un boolean che indica se vengono usati i numeri o meno.
	 * @param _scelte
	 * @param _titolo
	 * @param usaNumeri
	 */
	public Terminal_MenuAsterisco(String[] _scelte,String _titolo,boolean usaNumeri)
	{
		int i,j;
		numeri=usaNumeri;
		titolo=_titolo;
		lunghezzaMax=0;
		numeroScelte=_scelte.length;
		scelte=new String[numeroScelte];
		riempimento=new String[numeroScelte];
		for(i=0;i<numeroScelte;i++)
		{
			scelte[i]=_scelte[i];
			if(scelte[i].length()>lunghezzaMax)
			{
				lunghezzaMax=scelte[i].length();
			}
		}
		for(i=0;i<numeroScelte;i++)
		{
			riempimento[i]=" ";
			for(j=lunghezzaMax-scelte[i].length();j>0;j--)
			{
				riempimento[i]=riempimento[i]+" ";
			}
		}
		if(numeri)
		{
			lunghezzaCornice=lunghezzaMax+11;
		}else{
			lunghezzaCornice=lunghezzaMax+7;
		}
		for(i=1;i<lunghezzaCornice;i++)
		{
			cornice=cornice+"*";
		}
		
	}
	/**
	 * stampaMenu:
	 * stampa a video il menu e restituisce un'intero che rappresenta
	 * la scelta selezionata dall'utente
	 * @return scelta
	 */
	protected int stampaMenu()
	{
		int i;
		boolean maiusc=false;
		String nome;
		scelta=-1;
		if(numeri==false)
		{
			do{
				Terminal.stampaString(cornice,true);
				Terminal.stampaString(titolo,true);
				Terminal.stampaString(cornice,true);
				for(i=0;i<numeroScelte;i++)
				{
					Terminal.stampaString("* "+scelte[i]+riempimento[i]+"*",true);				
				}
				Terminal.stampaString(cornice,true);
				nome=Terminal.leggiString();
				for(i=0;i<numeroScelte;i++)
				{
					if((nome.equals(scelte[i]))==true)
					{
						scelta=i;
						break;
					}
					if((nome.equalsIgnoreCase(scelte[i]))==true)
					{
						maiusc=true;
					}
				}
				if(scelta<0)
				{
					Terminal.stampaString("ERRORE: la stringa che è stata inserita non compare nelle scelte possibili.",true);
					if(maiusc==true)
					{
						Terminal.stampaString("controllare il tasto MAIUSC.",true);
						maiusc=false;
					}
				}
			}while(scelta<0);
		}else{
			Terminal.stampaString(cornice,true);
			Terminal.stampaString(titolo,true);
			Terminal.stampaString(cornice,true);
			for(i=0;i<numeroScelte;i++)
			{
				if(i<10)
				{
					Terminal.stampaString("*   "+i+" -> "+scelte[i]+riempimento[i]+"*",true);
				}else{
					if(i<100)
					{
						Terminal.stampaString("*  "+i+" -> "+scelte[i]+riempimento[i]+"*",true);
					}else{
						Terminal.stampaString("* "+i+" -> "+scelte[i]+riempimento[i]+"*",true);
					}
				}
			}
			Terminal.stampaString(cornice,true);
			scelta=Terminal.leggiIntLimitatoMinMax(0, (numeroScelte-1));
			Terminal.stampaString(cornice,true);
		}
		return scelta;
	}
}