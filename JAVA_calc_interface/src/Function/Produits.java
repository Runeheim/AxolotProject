package Function;

public class Produits{
    public String nom;
    public float prix;
    public String image;

    public  Produits()
    {}
    public  Produits(String nom, float prix, String image)
    {
        this.nom=nom;
        this.prix = prix;
        this.image = image;

    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }
    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }





    @Override //surcharge de Tostring
    public String toString() {
        return "Produits [nom="+nom+", prix="+prix+", fichier="+image+"]";
    }
}
