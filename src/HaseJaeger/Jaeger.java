package HaseJaeger;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Ein Modell eines Jaegers.
 * 
 * @author Piet
 * @version 2026-06-15
 */
public class Jaeger extends Akteur
{
    // Die Anzahl der Tiere, auf die in jedem Schritt geschossen wird.
    private int schussAnzahl;

    /**
     * Erzeuge einen Jaeger.
     * @param schussAnzahl Die Anzahl der Schuesse pro Schritt.
     */
    public Jaeger(int schussAnzahl)
    {
        super();
        this.schussAnzahl = schussAnzahl;
    }

    /**
     * Agiere: Schiesse auf Tiere in der Nachbarschaft und bewege dich.
     */
    public void agiere(Feld aktuellesFeld, Feld naechstesFeld, List neueTiere)
    {
        schiessen(aktuellesFeld);
            
        // Zufallige Bewegung
        Position neuePosition = naechstesFeld.freieNachbarposition(gibPosition());
        if(neuePosition != null) {
            setzePosition(neuePosition);
            naechstesFeld.platziere(this);
        }
        else {
            if(naechstesFeld.gibObjektAn(gibPosition()) == null) {
                naechstesFeld.platziere(this);
            }
            else {
                setzeGestorben();
            }
        }
    }

    /**
     * Schiesse auf Tieren in der Nachbarschaft.
     */
    private void schiessen(Feld feld)
    {
        Iterator nachbarPositionen = feld.nachbarpositionen(gibPosition());
        int treffer = 0;
        while(nachbarPositionen.hasNext() && treffer < schussAnzahl)
        {
            Position pos = (Position) nachbarPositionen.next();
            Akteur ziel = feld.gibObjektAn(pos);
            if(ziel != null && !(ziel instanceof Jaeger))
            {
                ziel.setzeGestorben();
                treffer++;
            }
        }
    }
    
    public String toString()
    {
        return "Jaeger";
    }
}
