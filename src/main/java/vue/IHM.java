package vue;

import modele.*;
import util.*;

import java.util.Set;
import java.time.LocalDate;

/**
 * La classe IHM est responsable des interactions avec l'utilisateur/trice en
 * mode texte. C'est une classe qui n'est associée à aucun état : elle ne contient aucun
 * attribut d'instance. Aucune méthode de cette classe n'est pas censée modifier ses paramètres,
 * c'est pourquoi les paramètres des méthodes sont tous marqués comme `final`.
 */

public class IHM {

    private final Bibliotheque bibliotheque;

    public IHM(Bibliotheque bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

//-----  affichage menu et saisie des commandes par l'utilisateur  -------------------------------------------------

    /**
     * afficherInterface permet l'affichage du menu et le choix d'une commande
     * par l'utilisateur (dialogueSaisirCommande) puis l'invocation de la méthode
     * de la classe Bibliotheque réalisant l'action  (gererDialogue)
     */

    public void afficherInterface() {
        Commande cmd;
        do {
            cmd = this.dialogueSaisirCommande();
            this.gererDialogue(cmd);
        } while (cmd != Commande.QUITTER);
    }

    private Commande dialogueSaisirCommande() {
        ES.afficherTitre("===== Bibliotheque =====");
        ES.afficherLibelle(Commande.synopsisCommandes());
        ES.afficherLibelle("===============================================");
        ES.afficherLibelle("Saisir l'identifiant de l'action choisie :");
        return Commande.lireCommande();
    }

    private void gererDialogue(Commande cmd) {
        switch (cmd) {
            case QUITTER:
                break;
            case CREER_LECTEUR:
                bibliotheque.nouveauLecteur(this);
                break;
            case CONSULTER_LECTEURS:
                bibliotheque.consulterLecteur(this);
                break;
            case CREER_OUVRAGE:
                bibliotheque.nouvelOuvrage(this);
                break;
            case CONSULTER_OUVRAGE:
                bibliotheque.consulterOuvrage(this);
                break;
            case CREER_EXEMPLAIRE:
                bibliotheque.nouvelExemplaire(this);
                break;
            case CONSULTER_EXEMPLAIRE:
                bibliotheque.consulterExemplaireOuvrage(this);
                break;
            case EMPRUNTER_EXEMPLAIRE:
                bibliotheque.EmprunterExemplaire(this);
                break;
            case CONSULTER_EMPRUNT_LECTEUR:
                bibliotheque.consulterEmpruntLecteur(this);
            case RENDRE_EXEMPLAIRE:
                bibliotheque.rendreExemplaire(this);
            default:
                assert false : "Commande inconnue.";
        }
    }

//-----  Classes conteneurs et éléments de dialogue pour le lecteur -------------------------------------------------

    /**
     * Classe conteneur pour les informations saisies pour la création d'un
     * lecteur. Tous les attributs sont `public` par commodité d'accès.
     * Tous les attributs sont `final` pour ne pas être modifiables.
     */

    public static class InfosLecteur {
        //public final Integer num;
        public final String nom;
        public final String prenom;
        public final String adresse;
        public final LocalDate dateNaiss;
        public final String email;

        public InfosLecteur(final String nom, final String prenom, final String adresse, final LocalDate dateNaiss, final String email) {
            this.nom = nom;
            this.prenom = prenom;
            this.adresse = adresse;
            this.dateNaiss = dateNaiss;
            this.email = email;
        }
    }

    public InfosLecteur saisirLecteur() {
        String nom, prenom, adresse, email;
        LocalDate dateNaiss;
        boolean date;


        ES.afficherTitre("== Saisie d'un lecteur ==");
        nom = ES.lireChaine("Saisir le nom du lecteur :");
        prenom = ES.lireChaine("Saisir le prénom du lecteur :");
        adresse = ES.lireChaine("Saisir l'adresse du lecteur :");
        dateNaiss = ES.lireDate("Saisir la date de naissance du lecteur :");
        date = veriDateNaissance(dateNaiss);
        while (!date) {
            dateNaiss = ES.lireDate("La date de naissance doit être antérieure à la date du jour");
            date = veriDateNaissance(dateNaiss);
        }
        email = ES.lireEmail("Saisir l'email du lecteur :");

        return new InfosLecteur(nom, prenom, adresse, dateNaiss, email);
    }

    public void afficherLecteur(Lecteur lecteur) {
        ES.afficherTitre("== affichage du lecteur== " + lecteur.getNum());
        ES.afficherLibelle("Nom : " + lecteur.getNom());
        ES.afficherLibelle("Prénom : " + lecteur.getPrenom());
        ES.afficherLibelle("Mail  :" + lecteur.getEmail());
        ES.afficherLibelle("Date de naissance : " + lecteur.getDateNaiss());
        ES.afficherLibelle("Age du lecteur : " + lecteur.getAge());
    }

    public Set<Integer> lesNumLecteur;

    public boolean verifNumLecteur(int numLecteur) {

        if (lesNumLecteur.contains(numLecteur)) {
            return true;
        } else {
            return false;
        }

    }


    public int saisirNumLecteur(Set<Integer> lesNumLecteur) {
        this.lesNumLecteur = lesNumLecteur;
        ES.afficherLibelle("Saisir le numéro du lecteur");
        int numLecteur = ES.lireEntier();
        boolean a = verifNumLecteur(numLecteur);

        while (!a) {

            ES.afficherLibelle("Veuillez renseigner un numéro de lecteur existant");
            numLecteur = ES.lireEntier();
            a = verifNumLecteur(numLecteur);
        }

        return numLecteur;
    }

    public boolean veriDateNaissance(LocalDate dateNaiss) {

        if (LocalDate.now().isAfter(dateNaiss)) {
            return true;
        } else {
            return false;
        }
    }

    //-----  Classes conteneurs et éléments de dialogue pour l'ouvrage -------------------------------------------------

    public static class InfosOuvrage {

        public final String numISBN;
        public final String nomAuteur;
        public final String nomEditeur;
        public final LocalDate dateParution;
        public final String titre;
        public final PublicVise publicVise;


        public InfosOuvrage(final String numISBN, final String nomAuteur, final String nomEditeur, final LocalDate dateParution, final String titre, final PublicVise publicVise) {
            this.numISBN = numISBN;
            this.nomAuteur = nomAuteur;
            this.nomEditeur = nomEditeur;
            this.dateParution = dateParution;
            this.titre = titre;
            this.publicVise = publicVise;

        }
    }

    public InfosOuvrage saisirOuvrage() {

        String numISBN;
        String titre;
        String nomAuteur;
        String nomEditeur;
        LocalDate dateParution;
        PublicVise publicOuvrage = null;
        int publicVise;
        boolean a;
        boolean b;


        ES.afficherTitre("== Saisie d'un ouvrage ==");
        numISBN = ES.lireChaine("Saisir le numéro ISBN :");
        a = verifNumISBN(numISBN);
        while (a) {

            numISBN = ES.lireChaine("Numero ISBN déjà existant. Saisir de nouveau le numéro ISBN :");
            a = verifNumISBN(numISBN);
        }
        titre = ES.lireChaine("Saisir le titre de l'ouvrage :");
        nomAuteur = ES.lireChaine(" Saisir le nom de l'auteur :");
        nomEditeur = ES.lireChaine("Saisir le nom de l'éditeur :");
        dateParution = ES.lireDate("Saisir la date de parution");
        b = veriDateParution(dateParution);
        while (!b) {
            dateParution = ES.lireDate("La date de parution doit être antérieure a la date du jour. Saisir de nouveau :");
            b = veriDateParution(dateParution);
        }

        do {
            publicVise = ES.lireEntier("Saisir le public visé : 1 pour Enfant , 2 pour Ado , 3 pour Adulte");
            if (publicVise == 1){
                publicOuvrage = PublicVise.ENFANT;
            }
            else if(publicVise == 2){
                publicOuvrage = PublicVise.ADO;
            }
            else if (publicVise == 3){
                publicOuvrage = PublicVise.ADULTE;
            }

        }while (publicVise!=1 && publicVise!=2 && publicVise!=3 );


        return new InfosOuvrage(numISBN, nomAuteur, nomEditeur, dateParution, titre, publicOuvrage);

    }

    public void afficherOuvrage(Ouvrage ouvrage) {
        ES.afficherTitre("== affichage de l'ouvrage == " + ouvrage.getNumISBN());
        ES.afficherLibelle("Titre : " + ouvrage.getTitre());
        ES.afficherLibelle("Nom de l'auteur  :" + ouvrage.getNomAuteur());
        ES.afficherLibelle("Prénom de l'editeur : " + ouvrage.getNomEditeur());
        ES.afficherLibelle("Date de parution : " + ouvrage.getDateParution());
        ES.afficherLibelle("Public Ouvrage : " + ouvrage.getPublicOuvrage());
    }


    public Set<String> lesISBN;

    public boolean verifNumISBN(String numISBN) {

        if (bibliotheque.listerISBN().contains(numISBN)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean veriDateParution(LocalDate dateParution) {

        if (LocalDate.now().isAfter(dateParution)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean veriDateEmprunt(LocalDate dateEmprunt) {

        if (LocalDate.now().isBefore(dateEmprunt)) {
            return true;
        } else {
            return false;
        }
    }


    public String saisirISBN(Set<String> lesISBN) {
        this.lesISBN = lesISBN;
        ES.afficherLibelle("Saisir le numéro ISBN");
        String numISBN = ES.lireChaine();
        boolean a = verifNumISBN(numISBN);

        while (!a) {
            ES.afficherLibelle("Veuillez renseigner un numéro ISBN existant");
            numISBN = ES.lireChaine();
            a = verifNumISBN(numISBN);
        }

        return numISBN;
    }

    //-----  Classes conteneurs et éléments de dialogue pour l'exemplaire-------------------------------------------------

    public static class InfosExemplaire {

        public final LocalDate dateReception;
        public final Boolean empruntable;

        public InfosExemplaire(final LocalDate dateReception, final Boolean empruntable) {
            this.dateReception = dateReception;
            this.empruntable = empruntable ;

        }
    }

    public InfosExemplaire saisirExemplaire() {

        LocalDate dateReception ;
        String estempruntable ;
        Boolean empruntable = null;

        boolean dateRecept;



        ES.afficherTitre("== Saisie d'un exemplaire ==");
        dateReception = ES.lireDate("Saisir la date de réception:");
        dateRecept = verifDateReception(dateReception ,bibliotheque.getDateP());
        while (!dateRecept) {
            dateReception = ES.lireDate("La date de parution doit être antérieure à la date de réception. Saisir de nouveau :");
            dateRecept = verifDateReception(dateReception ,bibliotheque.getDateP());
        }

        estempruntable= ES.lireChaine("L'exemplaire est-il empruntable ? ('o' ou 'n')");
        while(empruntable == null){
            if(estempruntable.equals("o")){
                empruntable = true ;
            }
            else if(estempruntable.equals("n")){
                empruntable = false ;
            }
            else{
                estempruntable= ES.lireChaine("Saisie non conforme, l'exemplaire est-il empruntable ? ('o' ou 'n')");
            }
        }

        return new InfosExemplaire(dateReception,empruntable);

    }

    public boolean verifDateReception(LocalDate dateReception , LocalDate dateParution) {

        if (dateReception.isAfter(dateParution)) {
            return true;
        } else {
            return false;
        }
    }

    public void afficherExemplaire(Exemplaire exemplaire) {
        ES.afficherTitre("== affichage de l'exemplaire == " + exemplaire.getNumExemplaire());
        ES.afficherLibelle("Numéro ISBN : " + exemplaire.getOuvrage().getNumISBN());
        ES.afficherLibelle("Titre : "  + exemplaire.getOuvrage().getTitre());
        ES.afficherLibelle("Date de Réception  :" + exemplaire.getDateReception());
        ES.afficherLibelle("Empruntable  : " + exemplaire.isEmpruntable());
    }


    /*public void informerInfoUtulisateur(String ISBN, int numExemplaire, String titre, boolean empruntable, LocalDate date){
        afficherExemplaire(ISBN,numExemplaire,titre,empruntable,date);
    }*/

//-----  Classes conteneurs et éléments de dialogue pour EmpruntExemplaire-------------------------------------------------

public static class InfosEmpruntExemplaire {

    public final String numISBN;
    public final int numExmplaire;
    public final int numLecteur;
    public final LocalDate dateEmprunt;



    public InfosEmpruntExemplaire(final String numISBN, final int numExmplaire,final int numLecteur,final LocalDate dateEmprunt) {
        this.numISBN = numISBN;
        this.numExmplaire = numExmplaire ;
        this.numLecteur = numLecteur ;
        this.dateEmprunt = dateEmprunt;

    }
}


    public InfosEmpruntExemplaire saisirEmpruntExemplaire(Set<String> lesISBN, Set<Integer> lesNumLecteur ) {

        LocalDate dateEmprunt;
        this.lesISBN = lesISBN;
        this.lesNumLecteur = lesNumLecteur;
        int numExemplaire ;



        ES.afficherTitre("== Saisie d'un Emprunt pour un exemplaire ==");

        ES.afficherLibelle("Saisir le numéro ISBN :");
        String numISBN = ES.lireChaine();
        boolean a = verifNumISBN(numISBN);
        while (!a) {
            ES.afficherLibelle("Veuillez renseigner un numéro ISBN existant");
            numISBN = ES.lireChaine();
            a = verifNumISBN(numISBN);
        }

        ES.afficherLibelle("Saisir le numéro de l'exemplaire :");
        numExemplaire= ES.lireEntier();

        ES.afficherLibelle("Saisir le numéro du lecteur :");
        int numLecteur = ES.lireEntier();
        boolean b = verifNumLecteur(numLecteur);

        while (!b) {

            ES.afficherLibelle("Veuillez renseigner un numéro de lecteur existant :");
            numLecteur = ES.lireEntier();
            b = verifNumLecteur(numLecteur);
        }

        dateEmprunt = ES.lireDate("Saisir la date d'emprunt :");
        boolean c = veriDateEmprunt(dateEmprunt);
        while (!c) {
            dateEmprunt = ES.lireDate("La date d'emprunt doit être antérieure à la date du jour. Saisir de nouveau :");
            c = veriDateEmprunt(dateEmprunt);
        }

        return new InfosEmpruntExemplaire(numISBN,numLecteur,numExemplaire, dateEmprunt);

    }

    public void afficheLecteur(Lecteur lecteur) {
        ES.afficherTitre("affichage des emprunts du lecteur numéro " + lecteur.getNum());
        ES.afficherLibelle("Nom : " + lecteur.getNom());
        ES.afficherLibelle("Prénom : " + lecteur.getPrenom());

    }

    public void afficherEmpruntLecteur(Emprunt emprunt) {
        ES.afficherTitre("== Numéro d'emprunt ==  " + emprunt.getLecteurEmprunt().getNum());
        ES.afficherLibelle("Numéro d'exemplaire : " + emprunt.getExemplaireEmprunt().getNumExemplaire());
        ES.afficherLibelle("Numéro ISBN : " + emprunt.getExemplaireEmprunt().getOuvrage().getNumISBN());
        ES.afficherLibelle("Date d'emprunt : " + emprunt.getDateEm());
        ES.afficherLibelle("Date de retour : " + emprunt.getDateRetour());


    }

    //-----  Classes conteneurs et éléments de dialogue pour EmpruntExemplaire-------------------------------------------------

    public static class InfosExemplaireRendu {

        public final String numISBN;
        public final int numExemplaire ;
        public final int numLecteur;


        public InfosExemplaireRendu(final String numISBN, final int numExemplaire,final int numLecteur) {
            this.numISBN = numISBN;
            this.numExemplaire = numExemplaire ;
            this.numLecteur = numLecteur ;

        }
    }

    public InfosExemplaireRendu saisirExemplaireRendu(Set<String> lesISBN, Set<Integer> lesNumLecteur ) {

        this.lesISBN = lesISBN;
        this.lesNumLecteur = lesNumLecteur;
        int numExemplaire ;

        ES.afficherLibelle("Saisir le numéro ISBN :");
        String numISBN = ES.lireChaine();
        boolean a = verifNumISBN(numISBN);
        while (!a) {
            ES.afficherLibelle("Veuillez renseigner un numéro ISBN existant");
            numISBN = ES.lireChaine();
            a = verifNumISBN(numISBN);
        }

        ES.afficherLibelle("Saisir le numéro de l'exemplaire :");
        numExemplaire= ES.lireEntier();

        ES.afficherLibelle("Saisir le numéro du lecteur :");
        int numLecteur = ES.lireEntier();
        boolean b = verifNumLecteur(numLecteur);

        while (!b) {

            ES.afficherLibelle("Veuillez renseigner un numéro de lecteur existant :");
            numLecteur = ES.lireEntier();
            b = verifNumLecteur(numLecteur);
        }

        return new InfosExemplaireRendu(numISBN,numExemplaire,numLecteur);

    }









    //-----  Primitives d'affichage  -----------------------------------------------
    public void informerUtilisateur(final String msg, final boolean succes) {
        ES.afficherLibelle((succes ? "[OK]" : "[KO]") + " " + msg);
    }

    public void informerUtilisateur(final String msg) {
        ES.afficherLibelle(msg);
    }

}





