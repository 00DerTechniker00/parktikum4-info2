package HaseJaeger;

import java.util.List;
import java.util.Random;

/**
 * Ein einfaches Modell eines Hasen.
 * Ein Hase altert, bewegt sich, gebaert Nachwuchs und stirbt.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2003-04-16
 */
public class Hase extends Tier
{
    // Eigenschaften aller Hasen (statische Datenfelder).

    // Das Alter, in dem ein Hase gebaerfaehig wird.
    private static final int GEBAER_ALTER = 5;
    // Das Hoechstalter eines Hasen.
    private static final int MAX_ALTER = 50;
    // Die Wahrscheinlichkeit, mit der ein Hase Nachwuchs geboert.
    private static final double GEBAER_WAHRSCHEINLICHKEIT = 0.15;
    // Die maximale Groesse eines Wurfes (Anzahl der Jungen)
    private static final int MAX_WURFGROESSE = 5;
    // Ein Zufallsgenerator, der die Geburten beeinflusst.
    private static final Random rand = new Random();

    // Individuelle Eigenschaften eines Hasen (Instanzfelder).

    /**
     * Erzeuge einen neuen Hasen. Ein Hase kann das Alter 0
     *(neu geboren) oder ein zufaelliges Alter haben.
     * @param zufaelligesAlter soll der Hase ein zufaelliges Alter haben?
     */
    public Hase(boolean zufaelligesAlter)
    {
        super();
        if(zufaelligesAlter) {
            setzeAlter(rand.nextInt(MAX_ALTER));
        }
    }

    /**
     * Das was ein Hase die meiste Zeit tut - er laeuft herum.
     * Manchmal gebaert er Nachwuchs und irgendwann stirbt er
     * an Altersschwaeche.
     */
    public void agiere(Feld feld, Feld naechstesFeld, List neueTiere)
    {
        alterErhoehen();
        if(istLebendig()) {
            int geburten = gebaereNachwuchs();
            for(int b = 0; b < geburten; b++) {
                Hase neuerHase = new Hase(false);
                neueTiere.add(neuerHase);
                neuerHase.setzePosition(naechstesFeld.zufaelligeNachbarposition(gibPosition()));
                naechstesFeld.platziere(neuerHase);
            }
            Position neuePosition = naechstesFeld.freieNachbarposition(gibPosition());
            // nur in das naechste Feld setzen, wenn eine Position frei ist
            if(neuePosition != null) {
                setzePosition(neuePosition);
                naechstesFeld.platziere(this);
            }
            else {
                // weder Bleiben noch Gehen moeglich - Ueberpopulation - kein Platz
                setzeGestorben();
            }
        }
    }

    /**
     * Erhoehe das Alter dieses Hasen.
     * Dies kann zu seinem Tod fuehren.
     */
    private void alterErhoehen()
    {
        setzeAlter(gibAlter()+1);
        if(gibAlter() > MAX_ALTER) {
            setzeGestorben();
        }
    }

    /**
     * Gebaere Nachwuchs, wenn dieser Hase gebaerfaehig ist.
     * @return die Anzahl der Neugeborenen (kann Null sein).
     */
    private int gebaereNachwuchs()
    {
        int geburten = 0;
        if(kannGebaeren() && rand.nextDouble() <= GEBAER_WAHRSCHEINLICHKEIT) {
            geburten = rand.nextInt(MAX_WURFGROESSE) + 1;
        }
        return geburten;
    }

    public String toString()
    {
        return "Hase, Alter " + gibAlter();
    }
    /**
     * Ein Hase kann gebaeren, wenn er das gebaerfhige Alter
     * erreicht hat.
     */
    private boolean kannGebaeren()
    {
        return gibAlter() >= GEBAER_ALTER;
    }
}
