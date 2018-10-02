package kuehlschrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class DAL {

    private Scanner scan;
    private FileOutputStream out;

    public void write(String file, ArrayList<Lebensmittel> entries, int[] countPerList) throws FileNotFoundException, IOException {
        out = new FileOutputStream(file);
        // Zeilen mit # startend => Kommentare
        String head = countPerList[0] + "\n" + "#gefrier" + "\n"
                + countPerList[1] + "\n" + "#kuehl" + "\n"
                + countPerList[2] + "\n" + "#obst" + "\n";

        out.write(head.getBytes());
        for (Lebensmittel l : entries) {
            out.write(l.toString().getBytes());
            out.write("\n".getBytes());
            out.flush();
        }
        out.close();
    }

    public ArrayList<Lebensmittel>[] read(String file) throws FileNotFoundException, IOException {
        ArrayList<Lebensmittel> gefrier, kuehl, obst;
        gefrier = new ArrayList();
        kuehl = new ArrayList();
        obst = new ArrayList();
        scan = new Scanner(new File(file));
        int[] counts = {-1, -1, -1};
        String row, hersteller, part;
        String[] tokens;
        Lebensmittel lm = null;
        double menge;
        LocalDate date;
        char c;
        int i;
        
        while (scan.hasNext()) {
            row = scan.nextLine().trim();
            if (!row.startsWith("#")) {
                try {
                    
                    tokens = row.split(" von ");
                    i = 0;
                    part = tokens[1];
                    c = part.charAt(i);
                    while(c != ' '){
                        i++;
                        c = part.charAt(i);
                    }
                    hersteller = part.subSequence(0, i).toString();
                    
                    
                    tokens = row.split(" kg ");
                    part = tokens[0];
                    i = part.length();
                    c = part.charAt(i-1);
                    while(c != ' '){
                        i--;
                        c = part.charAt(i);
                    }
                    menge = Double.parseDouble(part.subSequence(i+1, part.length()).toString());
                    
                    
                    tokens = row.split("\\(");
                    i = 0;
                    part = tokens[1];
                    c = part.charAt(i);
                    while(c != ')'){
                        i++;
                        c = part.charAt(i);
                    }
                    date = LocalDate.parse(part.subSequence(0, i), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

                    if (row.startsWith("Milch")) {
                        lm = new Milch(date, hersteller, menge);
                    } else if (row.startsWith("Fleisch")) {
                        lm = new Fleisch(date, hersteller, menge);
                    } else if (row.startsWith("Apfel")) {
                        lm = new Apfel(date, hersteller, menge);
                    }

                    if (counts[0] > 0) {
                        gefrier.add(lm);
                        counts[0]--;
                    }
                    else if (counts[1] > 0) {
                        gefrier.add(lm);
                        counts[1]--;
                    }
                    else if (counts[2] > 0) {
                        gefrier.add(lm);
                        counts[2]--;
                    }
                } catch(ArrayIndexOutOfBoundsException aioobe){
                    int n = Integer.parseInt(row.trim());
                    if (counts[0] == -1) {
                        counts[0] = n;
                    } else if (counts[1] == -1) {
                        counts[1] = n;
                    } else if (counts[2] == -1) {
                        counts[2] = n;
                    }
                } 
            }
        }
        scan.close();
        return new ArrayList[]{gefrier, kuehl, obst};
    }
}
