package HaseJaeger;

import java.util.List;
import java.util.Iterator;

/**
 * Ein Modell eines Jaegers.
 * 
 * @author Piet
 * @version 2026-06-15
 */
public class Jaeger extends Tier
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
        if(istLebendig()) {
            schiessen(aktuellesFeld);
            
            // Zufallige Bewegung
            Position neuePosition = naechstesFeld.freieNachbarposition(gibPosition());
            if(neuePosition != null) {
                setzePosition(neuePosition);
                naechstesFeld.platziere(this);
            }
            else {
                // Falls kein Platz in der Nachbarschaft ist, bleibe stehen (falls moeglich)
                if(naechstesFeld.gibObjektAn(gibPosition()) == null) {
                    naechstesFeld.platziere(this);
                }
                else {
                    // Falls auch der eigene Platz belegt ist (sollte nicht passieren bei Jaegern)
                    setzeGestorben();
                }
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
        while(nachbarPositionen.hasNext() && treffer < schussAnzahl) {
            Position pos = (Position) nachbarPositionen.next();
            Tier tier = feld.gibObjektAn(pos);
            if(tier != null && !(tier instanceof Jaeger)) {
                tier.setzeGestorben();
                treffer++;
            }
        }
    }
    
    public String toString()
    {
        return "Jaeger";
    }
}
