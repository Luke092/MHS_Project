package in_out;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
/**
 * 
 * @verion 0.0
 *Console:
 *classe usata per la gestione grafica dell'input/output
 */
public class Console
{
	private JFrame console=new JFrame();
	private JTextArea outData=new JTextArea();
	private JLabel errorData=new JLabel();
	private JTextField inData=new JTextField();
	private JComboBox menu=new JComboBox();
	private String newLine="\n";
	private final static String AVVISO_MIN="inserire un valore maggiore o uguale a ";
	private final static String AVVISO_MAX="inserire un valore minore o uguale a ";
	private boolean block=false;
	private KeyListener leggi_tastiera=new KeyListener(){
		public void keyPressed(KeyEvent event){if(event.getKeyCode() == KeyEvent.VK_ENTER){block=false;}}
		public void keyReleased(KeyEvent event){}
		public void keyTyped(KeyEvent event){}
	};
	private ActionListener selezione=new ActionListener() {
		public void actionPerformed(ActionEvent e) {block=false;}
	};
	/**
	 * Console:
	 * costruttore della console
	 * @param width larghezza
	 * @param hout altezza textarea
	 * @param hin altezza textfield
	 * @param herror altezza label
	 * @param titolo
	 * @param visible
	 */
	public Console(int width,int height, int offset, String titolo)
	{
		console.setLayout(null);
		console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		console.setTitle(titolo);
		outData.setBounds(offset, offset, width, height);
		inData.setBounds(offset, 2*offset+height, width, height);
		errorData.setBounds(offset, 3*offset+2*height, width, height);
		menu.setBounds(offset, 2*offset+height, width, height);
		inData.addKeyListener(leggi_tastiera);
		menu.addActionListener(selezione);
		inData.setBackground(Color.white);
		errorData.setForeground(Color.red);
		outData.setForeground(Color.blue);
		outData.setEditable(false);
		console.add(outData);
		console.add(inData);
		console.add(errorData);
		console.add(menu);
		menu.setVisible(false);
		inData.setEditable(false);
		console.setBounds(offset, offset, width+2*offset, 3*height+4*offset+35);
		console.setVisible(true);
	}
	/**
	 * closeConsole
	 * chiude la console grafica
	 */
	public void closeConsole()
	{
		console.dispose();
	}
	/**
	 * leggiDoubleLimitatoMinMax:
	 * legge un double dal textfield compreso tra min e max, estremi inclusi
	 * @param min
	 * @param max
	 * @return double
	 */
	protected double leggiDoubleLimitatoMinMax(double min,double max)
	{
		inData.setEditable(true);
		boolean controllo=false;
		double n=0;
		do{
			try{
				n=Double.parseDouble(leggiString());
				controllo=true;
			}catch(Exception e)
			{
				stampaString("inserisci un numero.",true,true);
				controllo=false;
			}
			if(n<min)
			{
				controllo=false;
				stampaString(AVVISO_MIN+min,true,true);
			}
			if(n>max)
			{
				controllo=false;
				stampaString(AVVISO_MAX+max,true,true);
			}
			delDataIn();
		}while(controllo!=true);
		inData.setEditable(false);
		delDataIn();
		delDataOut(true);
		return n;
	}
	/**
	 * leggiIntLimitatoMinMax:
	 * legge un int dal textfield compreso tra min e max, estremi inclusi
	 * @param min
	 * @param max
	 * @return int
	 */
	protected int leggiIntLimitatoMinMax(int min,int max)
	{
		inData.setEditable(true);
		boolean controllo=false;
		int n=0;
		do{
			try{
				n=Integer.parseInt(leggiString());
				controllo=true;
			}catch(Exception e)
			{
				stampaString("inserisci un numero.",true,true);
				controllo=false;
			}
			if(n<min)
			{
				controllo=false;
				stampaString(AVVISO_MIN+min,true,true);
			}
			if(n>max)
			{
				controllo=false;
				stampaString(AVVISO_MAX+max,true,true);
			}
		}while(controllo!=true);
		inData.setEditable(false);
		delDataIn();
		delDataOut(true);
		return n;
	}
	/**
	 * leggiString:
	 * legge una stringa dal textfield
	 * @return String
	 */
	protected String leggiString()
	{
		String stringa="";
		
		do{
			inData.setEditable(true);
			block=true;
			while(block){stringa=inData.getText();}
			stringa=new String(inData.getText());
			block=false;
			inData.setEditable(false);
			delDataIn();
			delDataOut(true);
			if(stringa==null||stringa.length()==0){stampaString("inserisci una stringa.",true,true);}
		}while(stringa==null);
		return stringa;
	}
	/**
	 * leggiChar:
	 * legge un char dal textfield
	 * @return char
	 */
	protected char leggiChar()
	{
		inData.setEditable(true);
		boolean controllo=false;
		char car = 0;
		String s="";
		do{
				s=leggiString();
				car=s.charAt(0);
				controllo=true;
			if(s.length()>1)
			{
				controllo=false;
				stampaString("inserisci un solo carattere.",true,true);	
			}
		}while(controllo!=true);
		inData.setEditable(false);
		delDataIn();
		delDataOut(true);
		return car;
	}
	/**
	 * siono:
	 * legge si o no dal textfield
	 * @return boolean
	 */
	protected boolean siono()
	{
		inData.setEditable(true);
		String s="";
		boolean controllo=false,risposta = false;
		do{
			s=leggiString();
			controllo=true;
			if(s.equalsIgnoreCase("SI")||s.equalsIgnoreCase("sì"))
			{
				risposta=true;
			}else{
				if(s.equalsIgnoreCase("NO"))
				{
					risposta=false;
				}else{
					stampaString("la risposta può essere si o no.",true,true);
					controllo=false;
				}
			}
		}while(controllo!=true);
		inData.setEditable(false);
		delDataIn();
		delDataOut(true);
		return risposta;
	}
	/**
	 * stampaString:
	 * stampa una stringa nel text area o nel label
	 * @param stringa
	 * @param acapo se serve un new line
	 * @param error se devo stampare nel label
	 */
	protected void stampaString(String stringa, boolean acapo, boolean error)
	{
		if(error)
		{
			delDataOut(true);
			String s=stringa;
			if(acapo){s=s+newLine;}
			errorData.setText(s);
		}else{
			delDataOut(false);
			String s=stringa;
			if(acapo){s=stringa+newLine;}
			outData.setText(s);
		}
		console.repaint();
	}
	/**
	 * stampaChar:
	 * stampa un carattere nel text area o nel label
	 * @param car
	 * @param acapo se serve un new line
	 * @param error se devo stampare nel label
	 */
	protected void stampaChar(char car, boolean acapo, boolean error)
	{
		if(error)
		{
			String stringa=errorData.getText()+car;
			if(acapo){stringa=stringa+newLine;}
			errorData.setText(stringa);
		}else{
			String stringa=outData.getText()+car;
			if(acapo){stringa=stringa+newLine;}
			outData.setText(stringa);
		}
		console.repaint();
	}
	/**
	 * delDataOut:
	 * pulisce il label o la text area
	 * @param error se devo pulire il label
	 */
	private void delDataOut(boolean error)
	{
		if(error){errorData.setText("");}
		else{outData.setText("");}
	}
	/**
	 * delDataIn:
	 * pulisce il textfield
	 */
	private void delDataIn()
	{
		inData.setText("");
	}
	
	/**
	 * menu:
	 * mostro un menu di scelte e fa scegliere all'utente una possibilit�
	 * @param scelte
	 * @param titolo
	 * @param usaNumeri
	 * @return int relativo alla scelta
	 */
	protected int menu(String[] scelte,String titolo,boolean usaNumeri)
	{
		int index=-1;
		inData.setVisible(false);
		menu.setVisible(true);
		outData.setText(titolo);
		menu.removeAllItems();
		menu.addItem("");
		for(int i=0;i<scelte.length;i++)
		{
			menu.addItem(scelte[i]);
		}
		//menu= new JComboBox(scelte);
		menu.repaint();
		block=true;
		while(block){index=menu.getSelectedIndex();}
		block=false;
		index=menu.getSelectedIndex()-1;
		menu.setVisible(false);
		inData.setVisible(true);
		errorData.setVisible(true);
		delDataIn();
		delDataOut(false);
		return index;
	}

}

