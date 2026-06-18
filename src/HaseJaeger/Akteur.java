package HaseJaeger;

/**
 * Abstrakte Superklasse fuer Akteure in der Simulation.
 * Enthält Position und Lebenszustand, die von Tieren und Jaegern genutzt werden.
 */
public abstract class Akteur
{
    private Position position;
    private boolean lebendig;

    public Akteur()
    {
        lebendig = true;
    }

    public Position gibPosition()
    {
        return position;
    }

    public void setzePosition(int zeile, int spalte)
    {
        this.position = new Position(zeile, spalte);
    }

    public void setzePosition(Position position)
    {
        this.position = position;
    }

    public boolean istLebendig()
    {
        return lebendig;
    }

    public void setzeGestorben()
    {
        lebendig = false;
    }

    /**
     * Jeder Akteur kann handeln (agieren). Implementierungen
     * fuehren die jeweilige Aktionslogik aus.
     */
    abstract public void agiere(Feld aktuellesFeld, Feld naechstesFeld, java.util.List neueTiere);
}
