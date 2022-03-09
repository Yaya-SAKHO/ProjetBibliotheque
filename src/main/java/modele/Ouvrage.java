package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class Ouvrage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String numISBN;
    private String nomAuteur;
    private String prenomAuteur;
    private String nomEditeur;
    private LocalDate dateParution;
    //private PublicVise publicOuvrage;
    private String titre;
    //private LocalDate dernierNumExemplaire ;


    public Ouvrage(String numISBN, String nomAuteur, String prenomAuteur,
                   String nomEditeur, LocalDate dateParution, String titre)
    {
        this.numISBN = numISBN;
        this.nomAuteur = nomAuteur;
        this.prenomAuteur = prenomAuteur;
        this.nomEditeur = nomEditeur;
        this.dateParution = dateParution;
       // this.publicOuvrage = publicOuvrage;
        this.titre = titre;
       // this.dernierNumExemplaire = dernierNumExemplaire;
    }

    public String getNumISBN() {
        return numISBN;
    }

    public String getNomAuteur() {
        return nomAuteur;
    }

    public String getPrenomAuteur() {
        return prenomAuteur;
    }

    public String getNomEditeur() {
        return nomEditeur;
    }

    public LocalDate getDateParution() {
        return dateParution;
    }

    /*public PublicVise getPublicOuvrage() {
        return publicOuvrage;
    }*/

    public String getTitre() {
        return titre;
    }

   /* public LocalDate getDernierNumExemplaire() {
        return dernierNumExemplaire;
    }*/
}
