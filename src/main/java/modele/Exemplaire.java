
package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class Exemplaire implements Serializable {

    private static final long serialVersionUID = 1L;
    private int numExemplaire;
    private LocalDate dateReception;
    private Boolean empruntable;
    private Ouvrage ouvrage;


    public Exemplaire (Ouvrage o, int numExemplaire, LocalDate dateReception, Boolean empruntable) {
        this.numExemplaire = numExemplaire;
        this.dateReception = dateReception;
        this.empruntable = empruntable;
        o.setExemplaire(this);
        setOuvrage(o);
        this.ouvrage = o;
    }

    public void setOuvrage(Ouvrage o) {
        o = this.ouvrage;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public int getNumExemplaire() {
        return this.numExemplaire;
    }

    public boolean isEmpruntable() {
        return empruntable ;
    }

    public LocalDate getDateReception() {
        return this.dateReception;
    }

    public Ouvrage getExemplaire(){
        return ouvrage;
    }

}