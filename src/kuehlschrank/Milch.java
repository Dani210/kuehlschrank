package kuehlschrank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Milch extends Lebensmittel {

    private final double KCAL = 64.; //pro 100g

    public Milch(LocalDate ablaufdatum, String hersteller, double menge) {
        super(ablaufdatum, hersteller, menge);
    }

    @Override
    public double berechneKcal() {
        return this.menge * 10. * KCAL;
    }

    @Override
    public String toString() {
        return "Milch von " + this.hersteller + " " + this.menge + " kg - "
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
