package Function;

import java.lang.ref.Cleaner;

public class Clients {

    public String email;
    public String nom;
    public String prenom;
    public String tel;

    public Clients(String email, String nom, String prenom, String tel){
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public  void setEmail(String Email){
        this.email = Email;
    }
    public String getEmail() {
        return email;
    }

    public  void setNom(String Nom){
        this.nom = Nom;
    }
    public String getNom() {
        return nom;
    }

    public  void setPrenom(String Prenom){
        this.prenom = Prenom;
    }
    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
}
