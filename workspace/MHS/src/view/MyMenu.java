package view;
import java.util.*;
/**
 * 
 *Menu:
 *classe che gestisce i menu e le relative scelte.
 */
public class MyMenu
{
	private int numberChoices,selection,maxLength,frameLength;
	private String[] choices;
	private String[] padding;
	private String title;
	private boolean numbers;
	private String frame="*";
	private static final String MIN_WARNING="Insert a value >= than ";
	private static final String MAX_WARNING="Insert a value <= than  ";
	
	
	public	MyMenu(String[] _choices,String _title,boolean useNumbers)
	{
		this.numbers = useNumbers;
		this.title = _title;
		this.maxLength = 0;
		this.numberChoices = _choices.length;
		this.choices = new String[this.numberChoices];
		this.padding = new String[this.numberChoices];
		
		for(int i = 0; i < this.numberChoices; i++)
		{
			this.choices[i] = _choices[i];
			if(this.choices[i].length() > this.maxLength)
			{
				this.maxLength = this.choices[i].length();
			}
		}
		
		for(int i = 0; i < this.numberChoices; i++)
		{
			this.padding[i] = " ";
			for(int j = this.maxLength - this.choices[i].length(); j > 0; j--)
			{
				this.padding[i]=this.padding[i]+" ";
			}
		}
		if(this.numbers)
		{
			this.frameLength = this.maxLength + 11;
		}else{
			this.frameLength = this.maxLength + 7;
		}
		for(int i = 1; i < this.frameLength; i++)
		{
			this.frame = this.frame + "*";
		}
		
	}
	
	public int printMenu()
	{
		boolean maiusc = false;
		String name;
		this.selection = -1;
		if(this.numbers == false)
		{
			do{
				printString(this.frame, true);
				printString(this.title, true);
				printString(this.frame, true);
				
				for(int i = 0; i < this.numberChoices; i++)
				{
					printString("* " + this.choices[i] + this.padding[i] + "*", true);				
				}
				printString(this.frame, true);
				name = readString();
				for(int i = 0; i < this.numberChoices; i++)
				{
					if((name.equals(this.choices[i])) == true)
					{
						this.selection=i;
						break;
					}
					if((name.equalsIgnoreCase(this.choices[i])) == true)
					{
						maiusc=true;
					}
				}
				if(this.selection < 0)
				{
					printString("Inexistent selected option",true);
					if( maiusc == true)
					{
						printString("Caps lock inserted, retry",true);
						maiusc = false;
					}
				}
			}while(this.selection < 0);
		}else{
			printString(this.frame, true);
			printString(this.title, true);
			printString(this.frame, true);
			for(int i = 0; i < this.numberChoices; i++)
			{
				if(i < 10)
				{
					printString("*   " + i +" -> " + this.choices[i] + this.padding[i] + "*", true);
				}else{
					if( i < 100)
					{
						printString("*  " + i + " -> " + this.choices[i] + this.padding[i] + "*", true);
					}else{
						printString("* " + i + " -> " + this.choices[i] + this.padding[i] + "*", true);
					}
				}
			}
			printString(this.frame, true);
			this.selection=readLimitedInt(0, (this.numberChoices-1));
			printString(this.frame, true);
		}
		return this.selection;
	}
	
	private static void printString(String string, boolean newLine)
	{
		System.out.print(string);
		if(newLine){System.out.println();}
	}
	
	
	public static int readLimitedInt(int min,int max)
	{
		int n = 0;
		boolean check;
		do{
			try{
					n = Integer.parseInt(readString());
					check = true;	
					
			}catch(Exception e)
			{
				printString("Insert an integer ",true);
				check = false;
			}
			if(n < min)
			{
				check = false;
				printString(MIN_WARNING + min, true);
			}
			if(n > max && max > 0)
			{
				check = false;
				printString(MAX_WARNING + max, true);
			}
		}while(check != true);
		return n;
	}
	
	public static String readString()
	{
		Scanner sc = new Scanner(System.in);
		String enter = System.getProperty("line.separator");
		sc.useDelimiter(enter);
		boolean check;
		String string = null;
		do{
			try{
					string = sc.next();
					check = true;
			}catch(Exception e)
			{
				printString("Insert a string of characters ",true);
				check = false;
			}
		}while( check != true);
		sc.close();
		return string;
	}
}