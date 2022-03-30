package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Ouvrage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String numISBN;
    private String nomAuteur;
    private String nomEditeur;
    private LocalDate dateParution;
    //private  String publicOuvrage;
    private PublicVise publicOuvrage;
    private String titre;
    private HashMap<String,Exemplaire> exemplaires;
    private  int dernierNumExemplaire = 1;



    public Ouvrage(String numISBN, String nomAuteur,
                   String nomEditeur, LocalDate dateParution, String titre,PublicVise publicOuvrage )
    {
        this.numISBN = numISBN;
        this.nomAuteur = nomAuteur;
        this.nomEditeur = nomEditeur;
        this.dateParution = dateParution;
        this.titre = titre;
        this.publicOuvrage = publicOuvrage;
        this.exemplaires = new HashMap<>();
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

    public PublicVise getPublicOuvrage() {
        return publicOuvrage;
    }

    public String getTitre() {
        return titre;
    }

    public int incrementerNumero() {
        return dernierNumExemplaire ++;
    }


    public void addExemplaires(Exemplaire e) {
        String key = e.getNumExemplaire() + e.getOuvrage().getNumISBN();
        exemplaires.put(key,e);
    }

    public void setExemplaire(Exemplaire e){
        this.addExemplaires(e);
    }


    public  Exemplaire getExemplaire(String key) {
        return exemplaires.get(key);
    }

    public HashSet<Exemplaire> getHashSetExemplaires() {
        HashSet<Exemplaire> result = new HashSet<>();
        for (Exemplaire e : exemplaires.values()) {
            result.add(e);
        }
        return result;
    }

    public HashMap<String, Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(HashMap<String, Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }


    public void ajoutExmplaire(Ouvrage o, LocalDate dateReception, Boolean empruntable){
        int numExemplaire = o.incrementerNumero();
        Exemplaire e = new Exemplaire(o, numExemplaire,dateReception, empruntable);
        setExemplaire(e);


    }
}
