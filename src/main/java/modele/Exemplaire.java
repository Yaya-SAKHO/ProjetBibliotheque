
package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Exemplaire implements Serializable {

    private static final long serialVersionUID = 1L;
    private int numExemplaire;
    private LocalDate dateReception;
    private Boolean empruntable;
    private Ouvrage ouvrage;
    private Boolean disponible;



    public Exemplaire (Ouvrage o, int numExemplaire, LocalDate dateReception, Boolean empruntable) {
        this.numExemplaire = numExemplaire;
        this.dateReception = dateReception;
        this.empruntable = empruntable;
        this.disponible = true;
        this.ouvrage = o;
        o.setExemplaire(this);
        //ouvrage.setExemplaire(this);

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

    public void setEmprunts(Emprunt em){
        this.addEmprunt(em);
    }

    public void addEmprunt(Emprunt em) {
        //emprunts
    }



    public boolean isDisponible() {
        if(this.disponible == true && this.empruntable == true){
            return disponible ;
        }
        else{
            return false ;
        }

    }

    public boolean isNotDisponible() {
        if(this.disponible == false){
            if(this.empruntable == false){
                return true ;
            }
            else{
                return false;
            }
        }
        else{
            return false ;
        }
    }

    public void setDisponible(boolean a){
        if(this.disponible == false){
            this.disponible = a ;
        }
        else if(this.empruntable == false){
            this.empruntable = a ;
        }
    }


}