package modele;

import java.io.Serializable;
import java.time.LocalDate;

public class Emprunt implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private Exemplaire exemplaireEmprunt;
    private Lecteur lecteurEmprunt;

    public Emprunt (LocalDate dateEmprunt, Exemplaire e , Lecteur l ) {
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = this.dateEmprunt.plusDays(15);
        this.exemplaireEmprunt = e;
        this.lecteurEmprunt = l;
        l.setEmprunts(this);
        e.setEmprunts(this);
        setLecteur(l);
        setExemplaire(e);
    }

    public void setLecteur(Lecteur l ){
        l = this.lecteurEmprunt;
    }

    public void setExemplaire(Exemplaire e){
        e = this.exemplaireEmprunt;
    }

    public LocalDate getDateEm() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public Exemplaire getExemplaireEmprunt() {
        return exemplaireEmprunt;
    }



    public Lecteur getLecteurEmprunt() {
        return lecteurEmprunt;
    }

    public void suprimerEmprunt(){

    }
}
