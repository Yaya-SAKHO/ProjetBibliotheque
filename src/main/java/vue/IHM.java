package vue;

import modele.*;
import util.*;

import java.util.Set;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

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

        public InfosLecteur( final String nom, final String prenom, final String adresse, final LocalDate dateNaiss, final String email) {
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
        boolean date ;


        ES.afficherTitre("== Saisie d'un lecteur ==");
        nom = ES.lireChaine("Saisir le nom du lecteur :");
        prenom = ES.lireChaine("Saisir le prénom du lecteur :");
        adresse = ES.lireChaine("Saisir l'adresse du lecteur :");
        dateNaiss = ES.lireDate("Saisir la date de naissance du lecteur :");
        date = veriDateNaissance(dateNaiss);
        while (!date){
            dateNaiss = ES.lireDate("la date de naissance doit être antérieure à la date du jour");
            date = veriDateNaissance(dateNaiss);
        }
        email = ES.lireEmail("Saisir l'email du lecteur :");

        return new InfosLecteur( nom, prenom, adresse, dateNaiss, email);
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
        ES.afficherLibelle("Saisir le numero du lecteur");
        int numLecteur = ES.lireEntier();
        boolean a = verifNumLecteur(numLecteur);

        while (!a) {

            ES.afficherLibelle("Veuiller renseigner un numero de lecteur existant");
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
        public final String prenomAuteur;
        public final String nomEditeur;
        public final LocalDate dateParution;
        public final String titre;
        public final String publicVise ;


        public InfosOuvrage(final String numISBN, final String nomAuteur, final String prenomAuteur, final String nomEditeur, final LocalDate dateParution, final String titre,final String publicVise) {
            this.numISBN = numISBN;
            this.nomAuteur = nomAuteur;
            this.prenomAuteur = prenomAuteur;
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
        String prenomAuteur;
        String nomEditeur;
        LocalDate dateParution;
        String publicOuvrage;
        PublicVise publicVise;
        boolean a ;
        boolean b;

        /*int dernNumExemplaire;
        public Exemplaire ouvrage;*/

        ES.afficherTitre("== Saisie d'un ouvrage ==");
        numISBN = ES.lireChaine("Saisir le numero ISBN :");
        a = verifNumISBN(numISBN);
        while (a){

            numISBN = ES.lireChaine("Numero ISBN déjà existant. resaisir le numero ISBN :");
            a = verifNumISBN(numISBN);
        }
        titre = ES.lireChaine("Saisir le titre de l'ouvrage :");
        nomAuteur = ES.lireChaine(" Saisir le nom de l'auteur :");
        prenomAuteur = ES.lireChaine("Saisir le prenom de l'auteur :");
        nomEditeur = ES.lireChaine("Saisir le nom de l'editeur :");
        dateParution = ES.lireDate("Saisir la date de parution");
        b = veriDateParution(dateParution);
        while (!b){
            dateParution = ES.lireDate("date de parution doit etre anterieur a la date du jour. Saisir de nouveau");
            b = veriDateParution(dateParution);
        }

        publicOuvrage = ES.lireChaine("Saisir le public visé : 1 pour Enfant , 2 pour Ado , 3 pour Adulte");
         switch(publicOuvrage){
            case "1" :
                publicOuvrage = PublicVise.ENFANT.getLibelle();
                break;
             case "2" :
                 publicOuvrage = PublicVise.ADO.getLibelle();
                 break; 
             case "3":
                 publicOuvrage = PublicVise.ADULTE.getLibelle();
                 break;
        }



        return new InfosOuvrage(numISBN, nomAuteur, prenomAuteur, nomEditeur, dateParution, titre, publicOuvrage );

    }

    public void afficherOuvrage(Ouvrage ouvrage) {
        ES.afficherTitre("== affichage du l'ouvrage == " + ouvrage.getNumISBN());
        ES.afficherLibelle("Titre : " + ouvrage.getTitre());
        ES.afficherLibelle("Nom Auteur  :" + ouvrage.getNomAuteur());
        ES.afficherLibelle("Prenom Editeur : " + ouvrage.getNomEditeur());
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


    public String saisirISBN(Set<String> lesISBN) {
        this.lesISBN = lesISBN;
        ES.afficherLibelle("Saisir le numero ISBN");
        String numISBN = ES.lireChaine();
        boolean a = verifNumISBN(numISBN);

        while (!a) {
            ES.afficherLibelle("Veuillez renseigner un numero ISBN existant");
            numISBN = ES.lireChaine();
            a = verifNumISBN(numISBN);
        }

        return numISBN;
    }




    //-----  Primitives d'affichage  -----------------------------------------------
    public void informerUtilisateur(final String msg, final boolean succes) {
        ES.afficherLibelle((succes ? "[OK]" : "[KO]") + " " + msg);
    }

    public void informerUtilisateur(final String msg) {
        ES.afficherLibelle(msg);
    }

}





