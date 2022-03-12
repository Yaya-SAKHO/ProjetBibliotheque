package modele;

public enum PublicVise {

    ENFANT("Enfant",3), ADO("Adolescent",12), ADULTE("Adulte",18);



    private int ageMin ;
    private String libelle;

    PublicVise(String libelle , int age){
        this.ageMin = age;
        this.libelle = libelle;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public String getLibelle() {
        return libelle;
    }

}
