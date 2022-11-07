package Function;


import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Optional;
import java.util.Scanner;
import java.util.List;


public class FunctionsList {

 public static int cpt;
    //region Gestion des fonctions des boutons de l'interface

    /**
     * Permet de mettre à jour l'affichage de la commande lors de l'ajout de nouveau produit ou de la finalisation de
     * la commande
     *
     * @param commandesList Liste des commandes à afficher
     * @param aeraCommande  zone de texte à remplir avec les commandes
     * @param totalCommande label à remplir avec le nb de produit et le prix total
     */
    //Display COmmande Liste on TextArea
    public void DisplayCommande(List<Commandes> commandesList, JTextArea aeraCommande,
                                JLabel totalCommande, String promoCommandeSelectedItem) {
        float prixTotal = 0;
        int nombreProduits = 0;
        String txtCommande = "";
// Lecture de la liste des commande pour compter les article et le prix
        for (Commandes com : commandesList) {
            txtCommande += com.nomProduit;
            txtCommande += ", prix " + com.prixProduit;
            txtCommande += ", quantitée : " + com.getQuantity();
            txtCommande += ", Total : " + com.prixTotal + " €\n";
            nombreProduits = nombreProduits + Integer.parseInt(com.getQuantity());
            prixTotal = prixTotal + com.prixTotal;
            prixTotal = Math.round(prixTotal * 100) / 100.0f;
            aeraCommande.setText(txtCommande);
        }

// Gestion de la promotion sur toutes la commande
        String txtPromoCommande = "";
        if (!promoCommandeSelectedItem.equals("pas de promotion")) {

            if (promoCommandeSelectedItem.contains("%")) {
                int index100 = promoCommandeSelectedItem.indexOf("%");
                float valuePromoCommande = Float.parseFloat(promoCommandeSelectedItem.substring(1, (index100 - 1)));
                txtPromoCommande += " soit : " + Math.round((prixTotal*(1-valuePromoCommande/100))*100)/100f + "€ avec " + promoCommandeSelectedItem;
            }
            if (promoCommandeSelectedItem.contains("E")) {
                int indexE = promoCommandeSelectedItem.indexOf("E");
                float valuePromoCommande = Float.parseFloat(promoCommandeSelectedItem.substring(1, (indexE - 1)));
                txtPromoCommande += " soit : " +Math.round((prixTotal-valuePromoCommande)*100)/100f+ "€ avec un rédution de " +valuePromoCommande + " €";
            }
        }
//Mise à jour du texte récapitulatif de la commande
        totalCommande.setText("   Nombre de produit : " + nombreProduits + ", Prix total : " + prixTotal + " €" +txtPromoCommande);
    }

    /**
     * Permet d'ajouter un produit dans la liste de commande
     *
     * @param commandesList Liste de commande à compléter
     * @param pdt           le produit à rajouter dans la commande
     * @param EmailClient   le mail client à rajouter dans la commande
     */
    //Ajout Produit dans la liste de commandes
    public void AddProduct(List<Commandes> commandesList, Produits pdt
            , String EmailClient, String promoProduitSelectedItem) {
//Gestion des Promotions sur les produits
        Produits p = new Produits(pdt.nom, pdt.prix, pdt.image);
        String txtPromoProduit = promoProduitSelectedItem;
        if (!txtPromoProduit.equals("pas de promotion")) {

            if (txtPromoProduit.contains("%")) {
                int index100 = txtPromoProduit.indexOf("%");
                float valuePromoProduit = Float.parseFloat(txtPromoProduit.substring(1, (index100 - 1)));

                p.setPrix(p.prix * (1 - (valuePromoProduit / 100)));
                p.setNom(p.getNom()+" -" + valuePromoProduit + " %");
            }
            if (txtPromoProduit.contains("E")) {
                int indexE = txtPromoProduit.indexOf("E");
                float valuePromoProduit = Float.parseFloat(txtPromoProduit.substring(1, (indexE - 1)));

                p.prix = p.prix -valuePromoProduit;
                p.nom += " -" + valuePromoProduit + " Euros";
            }


        }

// Gestion du cumul de plusieur sfois le même article
        boolean newproduct = true;
        for (Commandes com : commandesList) {
            if (com.nomProduit.equals(p.nom)) {
                int qte = Integer.parseInt(com.getQuantity());
                com.setQuantity(qte + 1);
                //Opération pour éliminer les approximation
                com.prixTotal = Float.parseFloat(String.valueOf(Integer.parseInt(com.getQuantity()) * Double.parseDouble(String.valueOf(com.prixProduit))));
                newproduct = false;
            }
        }

        if (newproduct) {
            Commandes cmd = new Commandes(String.valueOf(cpt), p.nom, p.prix, Integer.parseInt("1"), p.prix);
            commandesList.add(cmd);
        }


    }

    public void AddCommande(int compteurCommande, List<Commandes> commandesList, List<Commandes> commandesListTotal, int nbArticle, float prixTotal){

        for (Commandes item : commandesList) {

            commandesListTotal.add(item);
            nbArticle += Float.parseFloat(item.getQuantity());
            prixTotal += item.prixTotal;

        }

        commandesListTotal.add(new Commandes("Article : " + nbArticle, "Prix Total : " + prixTotal, 0, 0, 0));

        cpt += 1;

    }
    /**
     * Vide la liste de commande, le récap commande et la zone de texte
     *
     * @param aeraCommande  zone de texte à reset
     * @param commandeList  list à reset
     * @param totalCommande ligne récapitulative de la commande  à reset
     */
// Delete all produit's Commandes
    public void ClearCommande(JTextArea aeraCommande, List<Commandes> commandeList, JLabel totalCommande) {
        aeraCommande.setText("");
        totalCommande.setText("   Nombre de produit : 0 " + "Prix total : 0 €");
        commandeList.clear();

    }

    // Fonction pour ajouter un nouveau client
    public void AddClient(List<Commandes> commandesListTotal, List<Clients> listClient, JTextField nomClient, JTextField prenomClient,
                          JTextField mobileClient, JTextField mailClient) {
        Clients c = new Clients(mailClient.getText(), nomClient.getText(), prenomClient.getText(), mobileClient.getText());

        if (!c.getEmail().equals("") || !c.getTel().equals("")) {
            listClient.add(c);

            WriteCSV("Clients.csv", listClient);
            nomClient.setText("");
            prenomClient.setText("");
            mobileClient.setText("");
            mailClient.setText("");
        }

        commandesListTotal.add(new Commandes(c.nom+" "+c.prenom+" "+c.tel, c.email,0,0,0));
        new FunctionsList().WriteCSV("Commandes.csv", commandesListTotal);


    }

    //endregion

    //region Gestion des CSV (Lecture./écriture)
    /**
     * Permet de lire des CSV dans src/CSV/ et de les ajouter dans une liste
     *
     * @param path nom du fichier à ouvrir (Articles.csv/Promos.csv/Clients.csv/Commandes.csv)
     * @param list nom de la list à remplir avec les données du CSV
     */

    public void LectureCSV(String path, List list) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/CSV/" + path));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter("\n");

        if (path == "Articles.csv") {
            while (scanner.hasNext()) { //boucle pour instancier chaque produit dans une liste
                Produits p = new Produits();
                String produit = scanner.nextLine();
                String splitProduit[] = produit.split(";");
                p.setNom(splitProduit[0]);
                p.setPrix(Float.parseFloat(splitProduit[1]));
                p.setImage(splitProduit[2]);

                list.add(p);
            }
        }

        if (path == "Clients.csv") {
            while (scanner.hasNext()) { //boucle pour instancier chaque client dans une liste
                Clients c = new Clients("", "", "", "");
                String client = scanner.nextLine();
                String splitClient[] = client.split(";");
                c.setPrenom(splitClient[0]);
                c.setNom(splitClient[1]);
                c.setEmail(splitClient[2]);
                c.setTel(splitClient[3]);

                list.add(c);
            }
        }
        if (path == "Commandes.csv") {
            while (scanner.hasNext()) { //boucle pour instancier chaque client dans une liste
                Commandes c = new Commandes("", "", 0, 0, 0);
                String commande = scanner.nextLine();
                String splitClient[] = commande.split(";");
                c.emailClients = splitClient[0];
                c.nomProduit = splitClient[1];
                c.prixProduit = Float.parseFloat(splitClient[2]);
                c.setQuantity(Integer.parseInt(splitClient[3]));
                c.prixTotal = c.prixProduit * Integer.parseInt(c.getQuantity());

                list.add(c);
            }
        }

        if (path == "Promos.csv") {
            while (scanner.hasNext()) { //boucle pour instancier chaque client dans une liste
                Promos p = new Promos(0, "", "");
                String promos = scanner.nextLine();
                String splitClient[] = promos.split(";");
                p.setValues(Integer.parseInt(splitClient[0]));
                p.setUnit(splitClient[1]);
                p.setApplication(splitClient[2]);

                list.add(p);
            }
        }
        scanner.close();
    }

    /**
     * Permet d'écrire des données dans un CSV
     *
     * @param path nom du Fichier dans src/CSV/ (Articles.csv/Clients.csv/Commandes.csv/Promos.csv)
     * @param list Liste qui contiens les donnés à écrire dans le fichier
     */
    public void WriteCSV(String path, List list) {
        FileWriter file;
        //Délimiteurs qui doivent être dans le fichier CSV
        final String DELIMITER = ";";
        final String SEPARATOR = "\n";

        try {
            file = new FileWriter("src/CSV/" + path);

            if (path == "Commandes.csv") {
                for (Object item : list) {
                    Commandes com = (Commandes) item;

                    file.append(com.emailClients);
                    file.append(DELIMITER);
                    file.append(com.nomProduit);
                    file.append(DELIMITER);
                    file.append(String.valueOf(com.prixProduit));
                    file.append(DELIMITER);
                    file.append(com.getQuantity());
                    file.append(DELIMITER);
                    file.append(String.valueOf(com.prixTotal));
                    file.append(SEPARATOR);
                }
            }

            if (path == "Clients.csv") {

                for (Object item : list) {
                    Clients c = (Clients) item;

                    if (c.getPrenom() != null && c.getPrenom() != "") {
                        file.append(c.getPrenom());
                        file.append(DELIMITER);
                        file.append(c.getNom());
                        file.append(DELIMITER);
                        file.append(c.getEmail());
                        file.append(DELIMITER);
                        file.append(c.getTel());
                        file.append(SEPARATOR);
                    }


                }
            }

            if (path == "Promos.csv") {
                for (Object item : list) {
                    Promos p = (Promos) item;

                    file.append(String.valueOf(p.getValues()));
                    file.append(DELIMITER);
                    file.append(p.getUnit());
                    file.append(DELIMITER);
                    file.append(p.getApplication());
                    file.append(SEPARATOR);

                }
            }

            if (path == "Articles.csv") {
                for (Object item : list) {
                    Produits p = (Produits) item;

                    file.append(String.valueOf(p.getNom()));
                    file.append(DELIMITER);
                    file.append(String.valueOf(p.getPrix()));
                    file.append(DELIMITER);
                    file.append(p.getImage());
                    file.append(SEPARATOR);

                }
            }
            file.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//
//        //Délimiteurs qui doivent être dans le fichier CSV
//        final String DELIMITER = ";";
//        final String SEPARATOR = "\n";
//
//        //En-tête de fichier
//        final String HEADER = "Client;Article;Quantity;Prix Cumulés";
//        try {
//            file = new FileWriter("src/CSV/Commandes.csv");
//            file.append(HEADER); // ajout entête
//            file.append(SEPARATOR); // saut à la ligne
//            // function qui parcours la commandeslist
//
//            for (Commandes com : commandesList) {
//                file.append(com.emailClients);
//                file.append(DELIMITER);
//                file.append(com.nomProduit);
//                file.append(DELIMITER);
//                file.append(com.getQuantity());
//                file.append(DELIMITER);
//                file.append(String.valueOf(com.prixTotal));
//                file.append(SEPARATOR);
//            }
//            file.close();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    //endregion
}
