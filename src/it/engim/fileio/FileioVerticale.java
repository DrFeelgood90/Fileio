package it.engim.fileio;

import it.engim.AnagrafeAnimali;
import it.engim.CoppiaAnimalePadrone;
import it.engim.interfaces.Animale;
import it.engim.model.Cane;
import it.engim.model.Gatto;
import it.engim.model.Persona;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileioVerticale {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		// Sistemare il percorso del file...
		File fi = new File("C:\\Users\\Andrea\\Desktop\\Corso Android\\Java\\fileVerticale.txt");
		
		CoppiaAnimalePadrone[]
				capArray = new CoppiaAnimalePadrone[1000];
		
		int indice = 0;
		Scanner s = new Scanner(fi);
		
		for (int i = 0; i < capArray.length; i++) {
			CoppiaAnimalePadrone coppiaAnimalePadrone = capArray[i];
			
		}
		
		String[] datiPadrone = new String[3];
		String[] datiAnimale = new String[5];
		while(s.hasNextLine()) {
			
			String line = s.nextLine();
			
			for (int i = 0; i < 8; i++) {
				
				if(i < 3) {
					datiPadrone[i] = line;
					
				} else {
					datiAnimale[i-3] = line;
				}
				
				if( i != 7 ) {
					line = s.nextLine();
				}
			}
			
			//String[] r = line.split(";");
			Persona p = new Persona(datiPadrone[0], datiPadrone[1], datiPadrone[2]);
			
			int eta = Integer.valueOf(datiAnimale[2]);
			char sesso = datiAnimale[3].charAt(0);
			
			Animale a;
			if( datiAnimale[0].equals("C") ) {
				a = new Cane(datiPadrone[0], eta, sesso);
			} else {
				a = new Gatto(datiAnimale[0], eta, sesso);
			}
			
			CoppiaAnimalePadrone cap =
					new CoppiaAnimalePadrone(a, p);
			
			capArray[indice] = cap;
			
			indice = indice + 1;
		}
		
		CoppiaAnimalePadrone[] 
			arrayCorto = new CoppiaAnimalePadrone[indice];
		
		for(int i = 0; i < arrayCorto.length; i++) {
			arrayCorto[i] = capArray[i];
		}
		
		
		
		AnagrafeAnimali anagrafeAnimali =
				new AnagrafeAnimali("clinica", arrayCorto);
		anagrafeAnimali.stampaNomeAnimalePadrone();
	}

}


