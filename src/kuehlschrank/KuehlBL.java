package kuehlschrank;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public static ArrayList<Lebensmittel> sortWithQSandComparable(int li,
            int re, /*int pivot,*/ ArrayList<Lebensmittel> list) {
        /*
            TODO:
            QS Implementierung
         */
        
        /*
        int idx;
        boolean change = false;
        Lebensmittel leb1, leb2;

        leb1 = leb2 = null;
        idx = -1;

            
            for (; re >= 0; re--) {
                if (list.get(re).compareTo(list.get(pivot)) <= 0) {
                    leb1 = list.get(re);
                    break;
                }
            }
            for (; li <= list.size() - 1; li++) {
                if (list.get(li).compareTo(list.get(pivot)) >= 0) {
                    leb2 = list.get(li);
                    break;
                }
            }
            
            if(leb1 != null && leb2 != null){
                if(leb1.compareTo(leb2) <= 0){
                    idx = list.indexOf(leb1);
                    list.set(list.indexOf(leb2), leb1);
                    list.set(idx, leb2);
                    change = true;
                }
                else if(leb1.compareTo(leb2) > 0){
                    list.set(list.indexOf(leb2), list.get(pivot));
                    list.set(pivot, leb2);
                    
                    change = true;
                    
                    if(pivot > 1) pivot--;
                    sortWithQSandComparable(re, li, pivot, list);
                }
            }
            
            if(!change){
                if(pivot < list.size()-2){
                    li = pivot + 1;
                    re = list.size()-1;
                }
                
                sortWithQSandComparable(li, re, pivot, list);
            }
            
        return list; */
        
        if (list == null || list.isEmpty())
			return list;
 
		if (li >= re)
			return list;
 
		// pick the pivot
		int middle = li + (re - li) / 2;
		Lebensmittel pivot = list.get(middle);
 
		// make left < pivot and right > pivot
		int i = li, j = re;
		while (i <= j) {
			while (list.get(i).compareTo(pivot) < 0) {
				i++;
			}
 
			while (list.get(j).compareTo(pivot) > 0) {
				j--;
			}
 
			if (i <= j) {
				Lebensmittel temp = list.get(i);
				list.set(i, list.get(j));
                                list.set(j, temp);
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (li < j)
                        sortWithQSandComparable(li, j, list);
 
		if (re > i)
                        sortWithQSandComparable(i, re, list);
                
                return list;
    }

    public int searchViaBinary(int menge){
        ArrayList<Lebensmittel> sorted = 
                KuehlBL.sortWithQSandComparable(0, this.entries.size()-1, entries);
        int idx;
        
        for(idx = 0; idx < sorted.size(); idx++){
            if(sorted.get(idx).menge == menge){
                return idx;
            }
        }
        idx = -1;
        return idx;
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

    public void setList(ArrayList<Lebensmittel> entriesSorted) {
        entries = entriesSorted;
    }

}
