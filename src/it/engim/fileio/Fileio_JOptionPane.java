package it.engim.fileio;

//import it.engim.AnagrafeAnimali;
import it.engim.CoppiaAnimalePadrone;
import it.engim.interfaces.Animale;
import it.engim.model.Cane;
import it.engim.model.Gatto;
import it.engim.model.Persona;

import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

public class Fileio {

    public static void main(String[] args) throws IOException {

        String nomePadrone;
        String cognomePadrone;
        String codiceFiscale;
        
        Animale animale;
        Character tipoAnimale = '\0';
        String nomeAnimale = "";
        int eta = 0;
        Character sesso = '\0';

        boolean bEnd = false;
        int indice = 0;

        int result = 0;
        String[] possibilities;
        File fi;

        CoppiaAnimalePadrone[] capArray = new CoppiaAnimalePadrone[1000];

        //Creo un FileChooser
        final JFileChooser fc = new JFileChooser();
        //Aggiungo il filtro solo x i file .txt
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Txt files", "txt"));
        //Disabilito la possibilità di scegliere "tutti i tipi di file"
        fc.setAcceptAllFileFilterUsed(false);

        result = fc.showOpenDialog(null);

        fi = fc.getSelectedFile();

        if (result == JFileChooser.APPROVE_OPTION) {
            while (!bEnd) {

                result = JOptionPane.showOptionDialog(null, "Vuoi inserire una nuova coppia animale-padrone?",
                        "Inserimento", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (result == 0) {//Se seleziono Yes
                    nomePadrone = JOptionPane.showInputDialog(null, "Inserisci il nome del padrone:");
                    while (nomePadrone == null || nomePadrone.equals("")) {
                        nomePadrone = JOptionPane.showInputDialog(null, "Inserisci il nome del padrone:\n(*Il nome è obbligatorio!)");
                    }

                    cognomePadrone = JOptionPane.showInputDialog(null, "Inserisci il cognome del padrone:");
                    while (cognomePadrone == null || cognomePadrone.equals("")) {
                        cognomePadrone = JOptionPane.showInputDialog(null, "Inserisci il cognome del padrone:\n(*Il cognome è obbligatorio!)");
                    }

                    codiceFiscale = JOptionPane.showInputDialog(null, "Inserisci il codice fiscale del padrone:");
                    while (codiceFiscale == null || codiceFiscale.equals("")) {
                        codiceFiscale = JOptionPane.showInputDialog(null, "Inserisci il codice fiscale del padrone:\n(*Il codice fiscale è obbligatorio!)");
                    }

                    Persona persona = new Persona(nomePadrone, cognomePadrone, codiceFiscale);

                    possibilities = new String[]{"Cane", "Gatto"};
                    String animaleScelto = (String) JOptionPane.showInputDialog(null,
                            "Seleziona l'animale", "Vuoi inserire un cane o un gatto?",
                            JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);

                    tipoAnimale = animaleScelto.charAt(0);

                    nomeAnimale = JOptionPane.showInputDialog(null,
                            String.format("Come si chiama il %s ?", animaleScelto.toLowerCase()));
                    while (nomeAnimale == null || nomeAnimale.equals("")) {
                        nomeAnimale = JOptionPane.showInputDialog(null,
                                String.format("Come si chiama il %s ?\n(*Il nome è obbligatorio!)", animaleScelto.toLowerCase()));
                    }

                    do {
                        String sEtaAnimale = JOptionPane.showInputDialog(null,
                                String.format("Che età ha il %s ?", animaleScelto.toLowerCase()));

                        try {
                            eta = Integer.parseInt(sEtaAnimale);
                            if (eta < 0) {
                                throw new IllegalArgumentException();
                            }
                            bEnd = true;

                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Devi inserire un numero!",
                                    "Error", JOptionPane.ERROR_MESSAGE);

                        } catch (IllegalArgumentException e) {
                            JOptionPane.showMessageDialog(null, "L'età deve essere un numero positivo!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } while (!bEnd);

                    bEnd = false;

                    possibilities = new String[]{"M", "F"};
                    String sessoAnimale = (String) JOptionPane.showInputDialog(null,
                            "Seleziona il sesso",
                            String.format("Seleziona il sesso del %s:", animaleScelto.toLowerCase()),
                            JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);

                    sesso = sessoAnimale.charAt(0);

                    if (tipoAnimale.toString().equalsIgnoreCase("C")) {
                        animale = new Cane(nomeAnimale, eta, sesso);
                    } else {
                        animale = new Gatto(nomeAnimale, eta, sesso);
                    }

                    CoppiaAnimalePadrone cap = new CoppiaAnimalePadrone(animale, persona);

                    capArray[indice] = cap;
                    indice++;

                } else {//Se seleziono No per l'inserimento di una nuova coppia
                    if (indice == 0) {
                        FileUtils.writeStringToFile(fi, "***** NON CI SONO COPPIE!!!*****");
                    } else {

                        possibilities = new String[]{"Linea", "Multilinea"};
                        String formattazione = (String) JOptionPane.showInputDialog(null,
                                "Seleziona la formattazione", "Linea o multilinea?",
                                JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);

                        String separator = "";

                        if (formattazione.equals("Linea")) {
                            separator = ";";
                        } else {

                            separator = System.getProperty("line.separator");//"\n";
                        }

                        String line;
                        FileUtils.writeStringToFile(fi, "");
                        
                        for (int i = 0; i < indice; i++) {
                            
                            line = capArray[i].getPersona().getNome() + separator + capArray[i].getPersona().getCognome()
                                    + separator + capArray[i].getPersona().getCodFisc() + separator
                                    + capArray[i].getAnimale().getNome() + separator
                                    + capArray[i].getAnimale().getEta() + separator
                                    + capArray[i].getAnimale().getSesso() + separator
                                    + capArray[i].getAnimale().getIdAnimale() + separator;
                            if(separator.equals(";")){
                                line +=System.getProperty("line.separator");
                            }  
                            FileUtils.writeStringToFile(fi, line, true);
                        }

                    }
                    bEnd = true;
                    JOptionPane.showMessageDialog(null, "****Programma terminato.\n"
                            + "(Verifica il file:\n" + fi.getPath() + ")",
                            "Fine", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Non è stato selezionato nessun file!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
