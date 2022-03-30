package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import vue.*;

public class Bibliotheque implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private Map<Integer, Lecteur> lecteurs;  // association qualifiée par le num
    private Map<String, Ouvrage> ouvrages;
    private int dernierNumLecteur = 1;
    private LocalDate dateP;



    public Bibliotheque() {
        this.lecteurs = new HashMap<>();
        this.ouvrages = new HashMap<>();
    }

    //----------------------------------------Lecteur---------------------------------------------------------------------
    public void nouveauLecteur(IHM ihm) {
        int numLecteur = incrementerNumLecteur();
        IHM.InfosLecteur infosLecteur = ihm.saisirLecteur();

        Lecteur l = lecteurs.get(numLecteur);
        if (l == null) {
            l = new Lecteur(numLecteur, infosLecteur.nom, infosLecteur.prenom, infosLecteur.dateNaiss, infosLecteur.email);
            setLecteur(l, numLecteur);
            ihm.informerUtilisateur("Création du lecteur de numéro : " + numLecteur, true);

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

    public Set<Integer> listerNumLect() {
        return lecteurs.keySet();
    }

    public Map<Integer, Lecteur> getLecteurs() {
        return this.lecteurs;
    }

    private void setLecteur(Lecteur l, Integer num) {
        this.lecteurs.put(num, l);
    }

    public int incrementerNumLecteur() {
        return dernierNumLecteur++;
    }
    //---------------------------------------------Ouvrage-----------------------------------------------------------------

    public void nouvelOuvrage(IHM ihm) {
        IHM.InfosOuvrage infosOuvrage = ihm.saisirOuvrage();

        Ouvrage o;

        o = new Ouvrage(infosOuvrage.numISBN, infosOuvrage.nomAuteur, infosOuvrage.nomEditeur, infosOuvrage.dateParution, infosOuvrage.titre, infosOuvrage.publicVise);
        setOuvrage(o, infosOuvrage.numISBN);
        ihm.informerUtilisateur("Création de l'ouvrage de numéro ISBN : " + infosOuvrage.numISBN, true);

    }

    private void setOuvrage(Ouvrage o, String num) {
        this.ouvrages.put(num, o);
    }


    public Set<String> listerISBN() {
        return ouvrages.keySet();
    }

    public void consulterOuvrage(IHM ihm) {
        String numISBN = ihm.saisirISBN(listerISBN());
        Ouvrage o = ouvrages.get(numISBN);
        ihm.afficherOuvrage(o);
    }

    //---------------------------------------------Exemplaire-----------------------------------------------------------------
    public void nouvelExemplaire(IHM ihm) {
        String ISBN = ihm.saisirISBN(listerISBN());
        Ouvrage o = ouvrages.get(ISBN);
        dateP = o.getDateParution();
        IHM.InfosExemplaire infosExemplaire = ihm.saisirExemplaire();
        o.ajoutExmplaire(o, infosExemplaire.dateReception, infosExemplaire.empruntable);
        ihm.informerUtilisateur("Création de l'exemplaire : " , true);
    }

    public LocalDate getDateP() {
        return dateP;
    }


    public void consulterExemplaireOuvrage(IHM ihm) {

        String numISBNE= ihm.saisirISBN(listerISBN());
        Ouvrage o = ouvrages.get(numISBNE);
        for (Exemplaire ex : o.getHashSetExemplaires()) {
            ihm.afficherExemplaire(ex);
        }
    }

    /*---------------------------------------------EmprunterExemplaire-----------------------------------------------------------------*/

    public void EmprunterExemplaire(IHM ihm) {
        IHM.InfosEmpruntExemplaire infosEmpruntExemplaire = ihm.saisirEmpruntExemplaire(listerISBN(),listerNumLect());
        Ouvrage o = ouvrages.get(infosEmpruntExemplaire.numISBN);
        Exemplaire e = recupExemplaire(o, infosEmpruntExemplaire.numExmplaire);
        Lecteur l = lecteurs.get(infosEmpruntExemplaire.numLecteur);
        //boolean f = verifSatureEmprunt(empunt)
        int age = l.getAge();
        PublicVise publicVise = o.getPublicOuvrage();
        boolean g = verifPublicLecteur(age,publicVise);
        if (g  /*&& /*e.isDisponible()*/){
            Emprunt em = new Emprunt(infosEmpruntExemplaire.dateEmprunt,e,l);
            o.getExemplaires().get(e.getNumExemplaire()+o.getNumISBN()).setDisponible(false);
            //l.getEmprunts().add(em);
            l.setEmprunts(em);
            //l.addEmprunt(em);
            ihm.informerUtilisateur("Emprunt réussi de numéro d'exemplaire : " +infosEmpruntExemplaire.numExmplaire , true);
        }
        else {
            ihm.informerUtilisateur("L'emprunt Public ne correspond pas à l'ouvrage ou l'ouvrage n'est pas disponible: " +infosEmpruntExemplaire.numExmplaire , false);
        }
    }

    public Exemplaire recupExemplaire(Ouvrage o , int numExemplaire){
        String key = numExemplaire + o.getNumISBN();
        return o.getExemplaire(key);
    }

    public boolean verifPublicLecteur(int age , PublicVise publicVise)
    {
        return age > publicVise.getAgeMin();
    }

    public void consulterEmpruntLecteur(IHM ihm) {
        int numLecteur = ihm.saisirNumLecteur(listerNumLect());
        Lecteur l = lecteurs.get(numLecteur);
        ihm.afficheLecteur(l);
        for (Emprunt em : l.getEmprunts()){
            ihm.afficherEmpruntLecteur(em);
        }
    }

    //----------------------------------------Rendre Exemplaire---------------------------------------------------------------------

    public void rendreExemplaire(IHM ihm) {
        IHM.InfosExemplaireRendu infosExemplaireRendu = ihm.saisirExemplaireRendu(listerISBN(),listerNumLect());
        Ouvrage o = ouvrages.get(infosExemplaireRendu.numISBN);
        Exemplaire e = recupExemplaire(o, infosExemplaireRendu.numExemplaire);
        boolean c = e.isNotDisponible();
        if (c){
            e.setDisponible(true);
            Lecteur l = lecteurs.get(infosExemplaireRendu.numLecteur);
            //l.suprimerEmprunt();
            ihm.informerUtilisateur("Rendu réussi : " , true);
        }

    }


}
