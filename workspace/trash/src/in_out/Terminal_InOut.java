package in_out;

/**
 * 
 * @verion 0.0
 *Console_InOut:
 *classe usata per la gestione dell'input/output
 */
public class Terminal_InOut implements prob.I_InOut{
	private Terminal_FileIO file =new Terminal_FileIO();
	private final String SEPARATOR=System.getProperty("file.separator");
	private final String FOLDER="file";
	private final String PERCORSO=System.getProperty("user.dir");
	/*Standard_Input*/
	public double leggiDouble(double min,double max)
	{
		return Terminal.leggiDoubleLimitatoMinMax(min, max);
	}
	public double leggiDouble(double min, boolean minormax)
	{
		if(minormax){return Terminal.leggiDoubleLimitatoMinMax(min, Double.MAX_VALUE);}
		else{return Terminal.leggiDoubleLimitatoMinMax(Double.MIN_VALUE, min);}
	}
	public double leggiDouble()
	{
		return Terminal.leggiDoubleLimitatoMinMax(Double.MIN_VALUE, Double.MAX_VALUE);
	}
	public int leggiInt(int min,int max)
	{
		return Terminal.leggiIntLimitatoMinMax(min, max);
	}
	public int leggiInt(int min, boolean minormax)
	{
		if(minormax){return Terminal.leggiIntLimitatoMinMax(min, Integer.MAX_VALUE);}
		else{return Terminal.leggiIntLimitatoMinMax(Integer.MIN_VALUE, min);}
	}
	public int leggiInt()
	{
		return Terminal.leggiIntLimitatoMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	public String leggiString()
	{
		
		return Terminal.leggiString();
	}
	public char leggiChar()
	{
		return Terminal.leggiChar();
	}
	public boolean leggisiono()
	{
		return Terminal.siono();
	}
	/*Output*/
	public void stampaString(String stringa, boolean aCapo, boolean isError)
	{
		Terminal.stampaString(stringa, aCapo);
	}
	public void stampaChar(char car, boolean aCapo, boolean isError)
	{
		Terminal.stampaChar(car, aCapo);
	}
	/*Standard_Menu*/
	public int menu(String[] scelte,String titolo,boolean usaNumeri)
	{
		Terminal_Menu m=new Terminal_Menu(scelte, titolo, usaNumeri);
		return m.menu();		
	}	
	public int menuAsterisco(String[] scelte,String titolo,boolean usaNumeri)
	{
		Terminal_MenuAsterisco m=new Terminal_MenuAsterisco(scelte, titolo, usaNumeri);
		return m.stampaMenu();		
	}
	/*Standard_FileIO*/
	public Object loadFile()
	{
		String percorso=PERCORSO+SEPARATOR+FOLDER+SEPARATOR;
		String s[]=file.fileLista(percorso);
		int scelta=menu(s,"Quale file vuoi caricare?",true);
		if(s[scelta].endsWith(".txt")){return null;}
		String nome=percorso+s[scelta];
		return file.caricaObject(nome);
	}
	public boolean saveFile(Object o)
	{
		String percorso=PERCORSO+SEPARATOR+FOLDER+SEPARATOR;
		stampaString("Inserisci il nome del file che vuoi salvare: ",false,false);
		String nome=leggiString();
		nome=file.creaNomeFile(nome,percorso,false);
		return file.salvaObject(o, nome);
	}
	public String loadString()
	{
		String percorso=PERCORSO+SEPARATOR+FOLDER+SEPARATOR;
		String s[]=file.fileLista(percorso);
		int scelta=menu(s,"Quale file vuoi caricare?",true);
		if(s[scelta].endsWith(".dat")){return "";}
		String nome=percorso+s[scelta];
		return file.caricaString(nome);
	}
	public boolean saveString(String stringa)
	{
		String percorso=PERCORSO+SEPARATOR+FOLDER+SEPARATOR;
		stampaString("Inserisci il nome del file che vuoi salvare: ",false,false);
		String nome=leggiString();
		nome=file.creaNomeFile(nome,percorso,true);
		return file.salvaString(stringa, nome);
	}
	public boolean delFileDat()
	{
		String percorso=PERCORSO+SEPARATOR+FOLDER+SEPARATOR;
		String s[]=file.fileLista(percorso);
		int scelta=menu(s,"Quale file vuoi eliminare?",true);
		String nome=percorso+s[scelta];
		return file.cancFile(nome);
	}
	public boolean delFileTxt()
	{
		String percorso=PERCORSO+SEPARATOR+FOLDER+SEPARATOR;
		String s[]=file.fileLista(percorso);
		int scelta=menu(s,"Quale file vuoi eliminare?",true);
		String nome=percorso+s[scelta];
		return file.cancFile(nome);
	}
	@Override
	public void close() {}
}