import java.util.Objects;

public abstract class Colis implements Comparable<Colis> {
    private final String code;
    private final int poids;
    private final boolean horsDakar;

    protected Colis(String code, int poids, boolean horsDakar) {
        if (poids <= 0) {
            throw new IllegalArgumentException("Le poids doit etre > 0");
        }
        this.code = code;
        this.poids = poids;
        this.horsDakar = horsDakar;
    }

    public String getCode() { return code; }
    public int getPoids() { return poids; }
    public boolean isHorsDakar() { return horsDakar; }

    public long tarif() {
        long t = (long) poids * Parametres.TARIF_KG;
        if (horsDakar) {
            t += Parametres.SURCHARGE_HORS_DAKAR;
        }
        return t;
    }

    public abstract String sigle();
    public abstract int priorite();

    @Override
    public String toString() {
        return "[" + sigle() + "] " + code + " " + poids + "kg "
                + (horsDakar ? "hors-Dakar" : "Dakar") + " " + tarif() + " FCFA";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return code.equals(((Colis) o).code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(Colis autre) {
        int c = Integer.compare(autre.priorite(), this.priorite());
        if (c != 0) return c;
        c = Long.compare(autre.tarif(), this.tarif());
        if (c != 0) return c;
        return this.code.compareTo(autre.code);
    }
}
