package HaseJaeger;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Ein einfacher Jaeger-Beute-Simulator, basierend auf einem
 * Feld mit Fuechsen und Hasen.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003-04-13
 */
public class Simulator
{
    // Die privaten static final Variablen definieren
    // Konfigurationsinformationen fuer die Simulation.
    // Die Standardbreite fuer ein Feld.
    private static final int STANDARD_BREITE = 50;
    // Die Standardtiefe fuer ein Feld.
    private static final int STANDARD_TIEFE = 50;
    // Die Wahrscheinlichkeit fuer die Geburt eines Fuchses ant
    // einer beliebigen Position im Feld.
    private static final double FUCHSGEBURT_WAHRSCHEINLICH = 0.02;
    // Die Wahrscheinlichkeit fuer die Geburt eines Fuchses an
    // einer beliebigen Position im Feld.
    private static final double HASENGEBURT_WAHRSCHEINLICH = 0.08;


    //todo: von mir hinzugefügt

    // Die Wahrscheinlichkeit fuer das Erscheinen eines Jaegers an
    // einer beliebigen Position im Feld.
    private static final double JAEGER_WAHRSCHEINLICH = 0.01;
    // Die Anzahl der Schuesse, die ein Jaeger pro Schritt abgibt.
    private static final int JAEGER_SCHUSS_ANZAHL = 2;


    // Die Liste der Tiere im Feld
    private List tiere;
    // Die Liste der gerade geborenen Tiere
    private List neueTiere;
    // Der aktuelle Zustand des Feldes
    private Feld feld;
    // Zweites Feld, mit dem der naechste Schritt erzeugt wird
    private Feld naechstesFeld;
    // Der aktuelle Schritt der Simulation
    private int schritt;
    // Eine grafische Ansicht der Simulation
    private Simulationsansicht ansicht;
    
    public static void main(String[] args)
    {
        Simulator sim = new Simulator();
        sim.starteLangeSimulation();
    }
    
    /**
     * Erzeuge ein Simulationsfeld mit einer Standardgroesse.
     */
    public Simulator()
    {
        this(STANDARD_TIEFE, STANDARD_BREITE);
    }
    
    /**
     * Erzeuge ein Simulationsfeld mit der gegebenen Groesse.
     * @param tiefe die Tiefe des Feldes (muss groesser als Null sein).
     * @param breite die Breite des Feldes (muss groesser als Null sein).
     */
    public Simulator(int tiefe, int breite)
    {
        if(breite <= 0 || tiefe <= 0) {
            System.out.println("Abmessungen muessen groesser als Null sein.");
            System.out.println("Benutze Standardwerte.");
            tiefe = STANDARD_TIEFE;
            breite = STANDARD_BREITE;
        }
        tiere = new ArrayList();
        neueTiere = new ArrayList();
        feld = new Feld(tiefe, breite);
        naechstesFeld = new Feld(tiefe, breite);

        // Eine Ansicht der Zustaende aller Positionen im Feld erzeugen.
        ansicht = new Simulationsansicht(tiefe, breite);
        ansicht.setzeFarbe(Fuchs.class, Color.blue);
        ansicht.setzeFarbe(Hase.class, Color.orange);
        ansicht.setzeFarbe(Jaeger.class, Color.black);
        
        // Einen gueltigen Startzustand einnehmen.
        zuruecksetzen();
    }
    
    /**
     * Starte die Simulation vom aktuellen Zustand aus fuer einen laengeren
     * Zeitraum, etwa 500 Schritte.
     */
    public void starteLangeSimulation()
    {
        simuliere(500);
    }
    
    /**
     * Fuehre vom aktuellen Zustand aus die angegebene Anzahl an
     * Simulationsschritten durch.
     * Brich vorzeitig ab, wenn die Simulation nicht mehr aktiv ist.
     */
    public void simuliere(int schritte)
    {
        for(int schritt = 1; schritt <= schritte && ansicht.istAktiv(feld); schritt++) {
            simuliereEinenSchritt();
        }
    }
    
    /**
     * Fuehre einen einzelnen Simulationsschritt aus:
     * Durchlaufe alle Feldpositionen und aktualisiere den 
     * Zustand jedes Fuchses und Hasen.
     */
    public void simuliereEinenSchritt()
    {
        schritt++;
        neueTiere.clear();
        
        // alle Tiere agieren lassen
        for(Iterator iter = tiere.iterator(); iter.hasNext(); ) {
            Tier tier = (Tier)iter.next();
            if(tier.istLebendig()) {
                tier.agiere(feld, naechstesFeld, neueTiere);
            }
            else {
                iter.remove();   // totes Tier entfernen
            }
        }
        // neu geborene Tiere in die Liste der Tiere einfuegen.
        tiere.addAll(neueTiere);
        
        // feld und naechstesFeld am Ende des Schritts austauschen.
        Feld temp = feld;
        feld = naechstesFeld;
        naechstesFeld = temp;
        naechstesFeld.raeumen();

        // das neue Feld in der Ansicht anzeigen.
        ansicht.zeigeStatus(schritt, feld);
    }
        
    /**
     * Setze die Simulation an den Anfang zurueck.
     */
    public void zuruecksetzen()
    {
        schritt = 0;
        tiere.clear();
        feld.raeumen();
        naechstesFeld.raeumen();
        bevoelkere(feld);
        
        // Zeige den Startzustand in der Ansicht.
        ansicht.zeigeStatus(schritt, feld);
    }
    
    /**
     * Bevoelkere das Feld mit Fuechsen und Hasen.
     */
    private void bevoelkere(Feld feld)
    {
        Random rand = new Random();
        feld.raeumen();
        for(int zeile = 0; zeile < feld.gibTiefe(); zeile++) {
            for(int spalte = 0; spalte < feld.gibBreite(); spalte++) {
                if(rand.nextDouble() <= FUCHSGEBURT_WAHRSCHEINLICH) {
                    Fuchs fuchs = new Fuchs(true);
                    fuchs.setzePosition(zeile, spalte);
                    tiere.add(fuchs);
                    feld.platziere(fuchs);
                }
                else if(rand.nextDouble() <= HASENGEBURT_WAHRSCHEINLICH) {
                    Hase hase = new Hase(true);
                    hase.setzePosition(zeile, spalte);
                    tiere.add(hase);
                    feld.platziere(hase);
                }

                //todo: von mir hinzugefügt
                else if(rand.nextDouble() <= JAEGER_WAHRSCHEINLICH) {
                    Jaeger jaeger = new Jaeger(JAEGER_SCHUSS_ANZAHL);
                    jaeger.setzePosition(zeile, spalte);
                    tiere.add(jaeger);
                    feld.platziere(jaeger);
                }
                // ansonsten die Position leer lassen
            }
        }
        Collections.shuffle(tiere);
    }
}
