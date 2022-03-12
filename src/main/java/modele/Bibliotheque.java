package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import util.ES;
import vue.*;
import vue.IHM.InfosLecteur;

public class Bibliotheque implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private Map<Integer, Lecteur> lecteurs;  // association qualifiée par le num
    private Map<String, Ouvrage> ouvrages;
    private int dernierNumLecteur = 0;


    public Bibliotheque() {
        this.lecteurs = new HashMap<>();
        this.ouvrages = new HashMap<>();
    }
//----------------------------------------Lecteur---------------------------------------------------------------------
    public void nouveauLecteur(IHM ihm) {
        int dernierNum = getDernierNumLecteur();
        IHM.InfosLecteur infosLecteur = ihm.saisirLecteur();

        Lecteur l = lecteurs.get(dernierNum);
        if (l==null){
            l = new Lecteur(dernierNum,infosLecteur.nom, infosLecteur.prenom, infosLecteur.dateNaiss, infosLecteur.email);
            setLecteur(l, dernierNum);
            ihm.informerUtilisateur("création du lecteur de numéro : " + dernierNum, true);

        } else {
            ihm.informerUtilisateur("numéro de lecteur existant", false);
        }
    }

    private Collection<Lecteur> lesLecteurs() {
        return lecteurs.values();
    }

     public void consulterLecteur(IHM ihm) {
        int numLecteur = ihm.saisirNumLecteur(listerNumLect());
        Lecteur l = lecteurs.get(numLecteur);
        ihm.afficherLecteur(l);
    }

    public Set<Integer> listerNumLect(){
        return lecteurs.keySet();
    }

    public Map<Integer, Lecteur> getLecteurs() {
        return this.lecteurs;
    }

    private void setLecteur(Lecteur l, Integer num) {
        this.lecteurs.put(num, l);
    }

    public int getDernierNumLecteur(){
        return dernierNumLecteur++;
    }
 //---------------------------------------------Ouvrage-----------------------------------------------------------------

    public void nouvelOuvrage(IHM ihm) {
        IHM.InfosOuvrage infosOuvrage = ihm.saisirOuvrage();

        Ouvrage o ;

                o = new Ouvrage(infosOuvrage.numISBN, infosOuvrage.nomAuteur, infosOuvrage.prenomAuteur, infosOuvrage.nomEditeur, infosOuvrage.dateParution, infosOuvrage.titre,infosOuvrage.publicVise);
                setOuvrage(o, infosOuvrage.numISBN);
                ihm.informerUtilisateur("création de l'ouvrage de numéro ISBN : " + infosOuvrage.numISBN, true);




    }

    private void setOuvrage(Ouvrage o, String num) {
        this.ouvrages.put(num, o);
    }



    public Set<String> listerISBN(){
        return ouvrages.keySet();
    }

    public void consulterOuvrage(IHM ihm) {
        String numISBN = ihm.saisirISBN(listerISBN());
        Ouvrage o = ouvrages.get(numISBN);
        ihm.afficherOuvrage(o);
    }


}
