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

public class Fileio {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		
tfgfu
		File fi = new File("C:\\Users\\Andrea\\Desktop\\Corso Android\\Java\\file.txt");
		//File fiw = new File("C:\\Utenti\\effetto\\file.txt");
		
		// Da Java7 in poi c'è la classe Paths. Il metodo statico
		// get(...) riceve un elenco di String; ogni stringa è un pezzo
		// del percorso del file: ci penserà lui a mettere il separatore
		// corretto in base al sistema operativo.
		// Path p = Paths.get("pezzo1", "pezzo2");
		// File file = p.toFile();
		
		// -- Metodi di utilità sul file...
		fi.exists();
		
		fi.canRead();
		fi.canWrite();
		
		fi.isFile();
		fi.isDirectory();
		
		fi.createNewFile();
		// --
		
		
		CoppiaAnimalePadrone[]
				capArray = new CoppiaAnimalePadrone[1000];
		
		int indice = 0;
		Scanner s = new Scanner(fi);
		while(s.hasNextLine()) {
			
			String line = s.nextLine().trim();
			
			if( line.startsWith("#") 
					|| line.length() == 0 
					) {
				continue;
			}
			
			String[] r = line.split(";");
			
			Persona p = new Persona(r[0], r[1], r[2]);
			
			int eta = Integer.valueOf(r[5]);
			char sesso = r[6].charAt(0);
			
			Animale a;
			if( r[3].equals("C") ) {
				a = new Cane(r[4], eta, sesso);
			} else {
				a = new Gatto(r[4], eta, sesso);
			}
			a.getEta();	
			
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
		
		
		
		
		
		//FileWriter fw = new FileWriter(fi, true);
		//fw.append("\nAltra Linea");
		//fw.flush();
		//fw.close();
		
		
		//fw.append("Ancora altra linea");
	}

}
