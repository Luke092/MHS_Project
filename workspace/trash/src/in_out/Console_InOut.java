package in_out;

/**
 * 
 * @verion 0.0
 *Console_InOut:
 *classe usata per la gestione grafica dell'input/output
 */
public class Console_InOut implements prob.I_InOut{
	private Console console;
	private Console_FileIO file=new Console_FileIO();
	
	public Console_InOut(int width,int height, int offset, String titolo)
	{
		console = new Console(width, height, offset, titolo);
	}
	public void close()
	{
		console.closeConsole();
	}
	/*Input*/
	public double leggiDouble(double min,double max)
	{
		return console.leggiDoubleLimitatoMinMax(min, max);
	}
	public double leggiDouble(double min, boolean minormax)
	{
		if(minormax){return console.leggiDoubleLimitatoMinMax(min, Double.MAX_VALUE);}
		else{return console.leggiDoubleLimitatoMinMax(Double.MIN_VALUE, min);}
	}
	public double leggiDouble()
	{
		return console.leggiDoubleLimitatoMinMax(Double.MIN_VALUE, Double.MAX_VALUE);
	}
	public int leggiInt(int min,int max)
	{
		return console.leggiIntLimitatoMinMax(min, max);
	}
	public int leggiInt(int min, boolean minormax)
	{
		if(minormax){return console.leggiIntLimitatoMinMax(min, Integer.MAX_VALUE);}
		else{return console.leggiIntLimitatoMinMax(Integer.MIN_VALUE, min);}
	}
	public int leggiInt()
	{
		return console.leggiIntLimitatoMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	public char leggiChar()
	{
		return console.leggiChar();
	}
	public boolean leggisiono()
	{
		return console.siono();
	}
	public String leggiString() {
		return console.leggiString();
	}
	/*Output*/
	public void stampaString(String stringa, boolean aCapo, boolean isError)
	{
		console.stampaString(stringa, aCapo, isError);
	}
	public void stampaChar(char car, boolean aCapo, boolean isError)
	{
		console.stampaChar(car, aCapo, isError);
	}
	/*Menu*/
	public int menu(String[] scelte,String titolo,boolean usaNumeri)
	{
		return console.menu(scelte, titolo, usaNumeri);		
	}
	/*File*/
	public Object loadFile()
	{
		return file.loadFile();
	}
	public boolean saveFile(Object o)
	{
		return file.saveFile(o);
	}
	public String loadString()
	{
		return file.loadString();
	}
	public boolean saveString(String stringa)
	{
		return file.saveString(stringa);
	}
	public boolean delFileDat()
	{
		return file.delFileDat();
	}
	public boolean delFileTxt()
	{
		return file.delFileTxt();
	}
	
}