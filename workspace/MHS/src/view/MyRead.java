package view;

import java.util.Scanner;

public  class MyRead
{
	private static final String MIN_WARNING="Insert a value >= than ";
	private static final String MAX_WARNING="Insert a value <= than  ";
	
	private static Scanner sc = new Scanner(System.in);
	
	public static String readString()
	{
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
				System.out.println("Insert a string of characters ");
				check = false;
			}
		}while( check != true);
		return string;
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
				System.out.println("Insert an integer ");
				check = false;
			}
			if(n < min)
			{
				check = false;
				System.out.println(MIN_WARNING + min);
			}
			if(n > max && max > 0)
			{
				check = false;
				System.out.println(MAX_WARNING + max);
			}
		}while(check != true);
		return n;
	}
	
	public static int readTimeLimit()
	{
		System.out.println("Insert time limit: ");
		return readLimitedInt(1, -1);
	}
}
