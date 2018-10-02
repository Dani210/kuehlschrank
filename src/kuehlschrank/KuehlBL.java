package kuehlschrank;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.AbstractListModel;

public class KuehlBL extends AbstractListModel<Lebensmittel> {

    private ArrayList<Lebensmittel> entries;
    private int state;
    private DAL dal;

    public KuehlBL() {
        entries = new ArrayList();
        state = 0;
        dal = new DAL();
    }

    public KuehlBL(ArrayList<Lebensmittel> list) {
        entries = list;
        state = 0;
        dal = new DAL();
    }

    public void setState(int state) {
        this.state = state;
    }

    public void add(Lebensmittel l) {
        entries.add(l);
        this.fireContentsChanged(this, 0, entries.size() - 1);
    }

    public void delete(int i) {
        entries.remove(i);
        this.fireContentsChanged(this, 0, entries.size() - 1);
    }

    public void sort() {
        Comparator c = new Sorter();
        entries.sort(c);
    }

    public void sortWithQSandComparable() {
        /*
            TODO:
            QS Implementierung
         */
        boolean changed = true;
        int li, re, pivot, idx;

        Lebensmittel leb1, leb2;

        leb1 = leb2 = null;
        idx = -1;
        li = 0;
        pivot = entries.size() - 1;
        re = pivot - 1;

        while (changed) {
            changed = false;
            
            for (; re >= 0; re--) {
                if (entries.get(re).compareTo(entries.get(pivot)) <= 0) {
                    leb1 = entries.get(re);
                    break;
                }
            }
            for (; li <= entries.size() - 1; li++) {
                if (entries.get(li).compareTo(entries.get(pivot)) >= 0) {
                    leb2 = entries.get(li);
                    break;
                }
            }
            
            if(leb1 != null && leb2 != null){
                if(leb1.compareTo(leb2) <= 0){
                    idx = entries.indexOf(leb1);
                    entries.set(entries.indexOf(leb2), leb1);
                    entries.set(idx, leb2);
                    changed = true;
                }
                else if(leb1.compareTo(leb2) > 0){
                    entries.set(entries.indexOf(leb2), entries.get(pivot));
                    entries.set(pivot, leb2);
                    changed = true;
                }
            }
            
        }
    }

    public void write(String file, ArrayList<Lebensmittel> entries, int[] counters) throws FileNotFoundException, IOException {
        dal.write(file, entries, counters);
    }

    public ArrayList<Lebensmittel>[] read(String file) throws FileNotFoundException, IOException {
        return dal.read(file);
    }

    public ArrayList<Lebensmittel> getEntries() {
        return entries;
    }

    @Override
    public int getSize() {
        if (state == 0) {
            return entries.size();
        }

        if (state == 1) {
            int i = 0;
            Lebensmittel lm = null;

            for (Lebensmittel l : entries) {

                if (l instanceof Apfel) {
                    lm = (Apfel) l;
                    if (lm.getAblaufdatum().isBefore(LocalDate.now())) {
                        i++;
                    }
                } else if (l instanceof Milch) {
                    lm = (Milch) l;
                    if (lm.getAblaufdatum().isBefore(LocalDate.now())) {
                        i++;
                    }
                } else if (l instanceof Fleisch) {
                    lm = (Fleisch) l;
                    if (lm.getAblaufdatum().isBefore(LocalDate.now())) {
                        i++;
                    }
                }
            }
            return i;
        }

        return 0;
    }

    @Override
    public Lebensmittel getElementAt(int index) {
        if (state == 0) {
            return entries.get(index);
        }
        if (state == 1) {
            Lebensmittel lm = null;

            Lebensmittel l = entries.get(index);
            if (l instanceof Apfel) {
                lm = (Apfel) l;
                if (lm.getAblaufdatum().isBefore(LocalDate.now())) {
                    return l;
                }
            } else if (l instanceof Milch) {
                lm = (Milch) l;
                if (lm.getAblaufdatum().isBefore(LocalDate.now())) {
                    return l;
                }
            } else if (l instanceof Fleisch) {
                lm = (Fleisch) l;
                if (lm.getAblaufdatum().isBefore(LocalDate.now())) {
                    return l;
                }
            }
        }

        return null;
    }

}
