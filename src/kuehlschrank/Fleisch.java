package kuehlschrank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fleisch extends Lebensmittel {

    private final double KCAL = 130f; //pro 100g

    public Fleisch(LocalDate ablaufdatum, String hersteller, double menge) {
        super(ablaufdatum, hersteller, menge);
    }

    @Override
    public double berechneKcal() {
        return this.menge * 10. * KCAL;
    }

    @Override
    public String toString() {
        return "Fleisch von " + this.hersteller + " " + this.menge + " kg - "
                + berechneKcal() + " kcal ("
                + this.ablaufdatum.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"))
                + ")";
    }

    @Override
    public int compareTo(Lebensmittel l) {
        if (this.menge > l.menge) {
            return 1;
        } else if (this.menge < l.menge) {
            return -1;
        }

        return 0;
    }

}
