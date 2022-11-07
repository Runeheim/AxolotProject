package Function;

public class Commandes {

    public  String emailClients;
    public  String nomProduit;
    public float prixProduit;
    private int quantity;
    public  float prixTotal;

    public Commandes(String emailClients, String nomProduit, float prixProduit, int quantity, float prixTotal){
        this.emailClients = emailClients;
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.setQuantity(quantity);
        this.prixTotal = prixTotal;
    }

    public String getQuantity() {
        return String.valueOf(quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
