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


        ES.afficherTitre("== Saisie d'un lecteur ==");
        nom = ES.lireChaine("Saisir le nom du lecteur :");
        prenom = ES.lireChaine("Saisir le prénom du lecteur :");
        adresse = ES.lireChaine("Saisir l'adresse du lecteur :");
        dateNaiss = ES.lireDate("Saisir la date de naissance du lecteur :");
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

            ES.afficherLibelle("Veuiller rensseigner un numero de lecteur existant");
            numLecteur = ES.lireEntier();
            a = verifNumLecteur(numLecteur);
        }

        return numLecteur;
    }

    //-----  Classes conteneurs et éléments de dialogue pour l'ouvrage -------------------------------------------------

    public static class InfosOuvrage {

        public final String numISBN;
        public final String nomAuteur;
        public final String prenomAuteur;
        public final String nomEditeur;
        public final LocalDate dateParution;
        public final String titre;


        public InfosOuvrage(final String numISBN, final String nomAuteur, final String prenomAuteur, final String nomEditeur, final LocalDate dateParution, final String titre) {
            this.numISBN = numISBN;
            this.nomAuteur = nomAuteur;
            this.prenomAuteur = prenomAuteur;
            this.nomEditeur = nomEditeur;
            this.dateParution = dateParution;
            this.titre = titre;

        }
    }

    public InfosOuvrage saisirOuvrage() {

        String numISBN;
        String titre;
        String nomAuteur;
        String prenomAuteur;
        String nomEditeur;
        LocalDate dateParution;
        /*publicVise publicOuvrage;
        int dernNumExemplaire;
        public Exemplaire ouvrage;*/

        ES.afficherTitre("== Saisie d'un ouvrage ==");
        numISBN = ES.lireChaine("Saisir le numero ISBN :");
        titre = ES.lireChaine("Saisir le titre de l'ouvrage :");
        nomAuteur = ES.lireChaine(" Saisir le nom de l'auteur :");
        prenomAuteur = ES.lireChaine("Saisir le prenom de l'auteur :");
        nomEditeur = ES.lireChaine("Saisir le nom de l'editeur :");
        dateParution = ES.lireDate("Saisir la date de parution");

        return new InfosOuvrage(numISBN, nomAuteur, prenomAuteur, nomEditeur, dateParution, titre);

    }

    public void afficherOuvrage(Ouvrage ouvrage) {
        ES.afficherTitre("== affichage du l'ouvrage == " + ouvrage.getNumISBN());
        ES.afficherLibelle("Titre : " + ouvrage.getTitre());
        ES.afficherLibelle("Nom Auteur  :" + ouvrage.getNomAuteur());
        ES.afficherLibelle("Prenom Editeur : " + ouvrage.getNomEditeur());
        ES.afficherLibelle("Date de parution : " + ouvrage.getDateParution());
    }


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


    //-----  Primitives d'affichage  -----------------------------------------------
    public void informerUtilisateur(final String msg, final boolean succes) {
        ES.afficherLibelle((succes ? "[OK]" : "[KO]") + " " + msg);
    }

    public void informerUtilisateur(final String msg) {
        ES.afficherLibelle(msg);
    }

}





