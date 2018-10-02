package kuehlschrank;

import java.time.LocalDate;

public abstract class Lebensmittel implements Comparable<Lebensmittel>{
    protected LocalDate ablaufdatum;
    protected String hersteller;
    protected double menge;

    public Lebensmittel(LocalDate ablaufdatum, String hersteller, double menge) {
        this.ablaufdatum = ablaufdatum;
        this.hersteller = hersteller;
        this.menge = menge;
    }

    public LocalDate getAblaufdatum() {
        return ablaufdatum;
    }
    
    public abstract double berechneKcal();

    

}
