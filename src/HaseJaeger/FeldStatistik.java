package HaseJaeger;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Diese Klasse sammelt und liefert statistische Daten ueber den
 * Zustand eines Feldes. Auf sehr flexible Weise: Es wird ein
 * Zaehler angelegt und gepflegt fuer jede Objektklasse, die im
 * Feld gefunden wird.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003-04-12
 */
public class FeldStatistik
{
    // Die Zaehler fuer die jeweiligen Akteurstypen (Fuchs, Hase, etc.)
    // in der Simulation.
    private HashMap zaehler;
    // Sind die Zaehlerstaende momentan aktuell?
    private boolean zaehlerAktuell;

    /**
     * Erzeuge ein Objekt fuer die Feldstatistik.
     */
    public FeldStatistik()
    {
        // Wir legen eine Sammlung fuer die Zaehler an, die wir fuer
        // die gefundenen Tierarten erzeugen.
        zaehler = new HashMap();
        zaehlerAktuell = true;
    }

    /**
     * @return Eine Beschreibung, welche Tiere das
     *          Feld bevoelkern.
     */
    public String gibBewohnerInfo(Feld feld)
    {
        StringBuffer buffer = new StringBuffer();
        if(!zaehlerAktuell) {
            gibZaehlerstaende(feld);
        }
        Iterator schluessel = zaehler.keySet().iterator();
        while(schluessel.hasNext()) {
            Zaehler info = (Zaehler) zaehler.get(schluessel.next());
            buffer.append(info.gibName());
            buffer.append(": ");
            buffer.append(info.gibStand());
            buffer.append(' ');
        }
        return buffer.toString();
    }

    /**
     * Verwerfe alle bisher gesammelten Daten; setze alle Zaehler
     * auf Null zurueck.
     */
    public void zuruecksetzen()
    {
        zaehlerAktuell = false;
        Iterator schluessel = zaehler.keySet().iterator();
        while(schluessel.hasNext()) {
            Zaehler z = (Zaehler) zaehler.get(schluessel.next());
            z.zuruecksetzen();
        }
    }

    /**
     * Erhoehe den Zaehler fuer eine Tierklasse.
     */
    public void erhoeheZaehler(Class tierklasse)
    {
        Zaehler z = (Zaehler) zaehler.get(tierklasse);
        if(z == null) {
            // Wir haben noch keinen Zaehler fuer
            // diese Spezies - also neu anlegen
            z = new Zaehler(tierklasse.getName());
            zaehler.put(tierklasse, z);
        }
        z.erhoehen();
    }

    /**
     * Signalisiere, dass eine Tierzaehlung beendet ist.
     */
    public void zaehlungBeendet()
    {
        zaehlerAktuell = true;
    }

    /**
     * Stelle fest, ob die Simulation noch aktiv ist, also
     * ob sie weiterhin laufen sollte.
     * @return true wenn noch mehr als eine Spezies lebt.
     */
    public boolean istAktiv(Feld feld)
    {
        // Wieviele Zaehler sind nicht Null.
        int nichtNull = 0;
        if(!zaehlerAktuell) {
            gibZaehlerstaende(feld);
        }
        Iterator schluessel = zaehler.keySet().iterator();
        while(schluessel.hasNext()) {
            Zaehler info = (Zaehler) zaehler.get(schluessel.next());
            if(info.gibStand() > 0) {
                nichtNull++;
            }
        }
        return nichtNull > 1;
    }
    
    /**
     * Erzeuge Zaehler fuer die Anzahl der Fuechse und Hasen.
     * Diese werden nicht staendig aktuell gehalten, waehrend
     * Fuechse und Hasen in das Feld gesetzt werden, sondern
     * jeweils bei der Abfrage der Zaehlerstaende berechnet.
     */
    private void gibZaehlerstaende(Feld feld)
    {
        zuruecksetzen();
        for(int zeile = 0; zeile < feld.gibTiefe(); zeile++) {
            for(int spalte = 0; spalte < feld.gibBreite(); spalte++) {
                Object tier = feld.gibObjektAn(zeile, spalte);
                if(tier != null) {
                    erhoeheZaehler(tier.getClass());
                }
            }
        }
        zaehlerAktuell = true;
    }
}
