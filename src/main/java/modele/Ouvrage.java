package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class Ouvrage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String numISBN;
    private String nomAuteur;
    private String nomEditeur;
    private LocalDate dateParution;
    private  String publicOuvrage;
    private String titre;
    private HashSet<Exemplaire> exemplaires;
    private  int dernierNumExemplaire = 1;



    public Ouvrage(String numISBN, String nomAuteur,
                   String nomEditeur, LocalDate dateParution, String titre,String publicOuvrage )
    {
        this.numISBN = numISBN;
        this.nomAuteur = nomAuteur;
        this.nomEditeur = nomEditeur;
        this.dateParution = dateParution;
        this.titre = titre;
        this.publicOuvrage = publicOuvrage;
        this.exemplaires = new HashSet<>();
    }

    public String getNumISBN() {
        return numISBN;
    }

    public String getNomAuteur() {
        return nomAuteur;
    }


    public String getNomEditeur() {
        return nomEditeur;
    }

    public LocalDate getDateParution() {
        return dateParution;
    }

    public String getPublicOuvrage() {
        return publicOuvrage;
    }

    public String getTitre() {
        return titre;
    }

    public int incrementerNumero() {
        return dernierNumExemplaire ++;
    }


    public void setExemplaires(Exemplaire e) {
        exemplaires.add(e);
    }

    public void setExemplaire(Exemplaire e){
        this.setExemplaires(e);
    }



    public HashSet<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    /*private void setExemplaire(Exemplaire e, int num) {
        this.exemplaires.put(num, e);
    }*/

    public void ajoutExmplaire(Ouvrage o, LocalDate dateReception, Boolean empruntable){
        int numExemplaire = o.incrementerNumero();
        Exemplaire e = new Exemplaire(o, numExemplaire,dateReception, empruntable);
        setExemplaire(e);


    }
}
