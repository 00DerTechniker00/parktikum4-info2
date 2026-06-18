package HaseJaeger;

/**
 * Diese Klasse definiert Zaehler fuer die Akteurstypen
 * in einer Simulation.
 * Ein Zaehler wird ueber einen Namen identifiziert und 
 * zaehlt, wieviele Akteure des Typs innerhalb der Simulation
 * jeweils existieren.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003-04-12
 */
public class Zaehler
{
    // Ein Name fuer den Akteurstyp in dieser Simulation
    private String name;
    // Wie viele von diesem Typ existieren in der Simulation.
    private int zaehler;

    /**
     * Initialisiere mit dem Namen des Typs.
     * @param name Ein Name, z.B. "Fuchs".
     */
    public Zaehler(String name)
    {
        this.name = name;
        zaehler = 0;
    }
    
    /**
     * @return den Namen des Typs dieses Zaehlers.
     */
    public String gibName()
    {
        return name;
    }

    /**
     * @return den aktuellen Zaehlerstand dieses Typs.
     */
    public int gibStand()
    {
        return zaehler;
    }

    /**
     * Erhoehe diesen Zaehler um Eins.
     */
    public void erhoehen()
    {
        zaehler++;
    }
    
    /**
     * Setze diesen Zaehler auf Null zurueck.
     */
    public void zuruecksetzen()
    {
        zaehler = 0;
    }
}
