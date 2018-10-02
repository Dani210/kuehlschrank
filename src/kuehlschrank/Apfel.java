package kuehlschrank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Apfel extends Lebensmittel {

    private final double KCAL = 52f;

    public Apfel(LocalDate ablaufdatum, String hersteller, double menge) {
        super(ablaufdatum, hersteller, menge);
    }

    @Override
    public double berechneKcal() {
        return this.menge / 10f * KCAL;
    }

    @Override
    public String toString() {
        return "Apfel von " + this.hersteller + " " + this.menge + " kg - "
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
