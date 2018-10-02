package kuehlschrank;

import java.time.LocalDate;
import java.util.Comparator;

public class Sorter implements Comparator<Lebensmittel> {
    
    
    @Override
    public int compare(Lebensmittel l1, Lebensmittel l2) {
        
        LocalDate d1, d2;
        
        d1 = d2 = null;
        
        //l1 ->
        if(l1 instanceof Apfel){
            l1 = (Apfel) l1;
            d1 = l1.getAblaufdatum();
        }
        else if( l1 instanceof Fleisch){
            l1 = (Fleisch) l1;
            d1 = l1.getAblaufdatum();
        }
        else if( l1 instanceof Milch){
            l1 = (Milch) l1;
            d1 = l1.getAblaufdatum();
        }
        //<- l1
        
        //l2 ->
        if(l2 instanceof Apfel){
            l2 = (Apfel) l2;
            return d1.compareTo(l2.getAblaufdatum());
        }
        else if( l2 instanceof Fleisch){
            l2 = (Fleisch) l2;
            return d1.compareTo(l2.getAblaufdatum());
        }
        else if( l2 instanceof Milch){
            l2 = (Milch) l2;
            return d1.compareTo(l2.getAblaufdatum());
        }
        //<- l2
        
        
        
        
        return 0;
    }
    
}