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
				
				name = MyRead.readString();
				
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
			
			this.selection = MyRead.readLimitedInt(0, (this.numberChoices-1));
			printString(this.frame, true);
		}
		return this.selection;
	}
	
	private static void printString(String string, boolean newLine)
	{
		System.out.print(string);
		if(newLine){System.out.println();}
	}
	
}