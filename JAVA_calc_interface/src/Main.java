
import Function.Clients;
import Function.Promos;
import Function.Commandes;
import Function.FunctionsList;
import Function.FunctionsList.*;
import Function.Produits;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {


        //crée les différentes frames
        JFrame f = new JFrame("Artishop");
        f.setLayout(new GridLayout(2, 1));

        JFrame fClient = new JFrame("Gestions Clients");
        fClient.setLayout(new GridLayout(1, 1));

        //crée et paramétrer les Jpanel avec des données J
        JPanel panelBoutique = new JPanel(); // parti du haut

        JPanel panelbas = new JPanel(); // parti du bas qui contient client + commande
        panelbas.setLayout(new GridLayout(1, 1)); // nb de ligne et de colonne

        JPanel panelClient = new JPanel(); // colonne gauche en bas
        panelClient.setLayout(new GridLayout(14, 1));

        JPanel panelCommande = new JPanel(); // colonne droite en bas
        panelCommande.setLayout(new GridLayout(5, 1));

        JPanel panelButton = new JPanel();

        List<Clients> listClient = new ArrayList<>(); //Liste des clients
        List<Commandes> commandesList = new ArrayList<Commandes>(); // List produit commander afficher dans commande
        List<Commandes> commandesListTotal = new ArrayList<Commandes>(); // liste toutes les commandes

        List<Produits> listProduit = new ArrayList<>(); // Liste des produit (CSV) à afficher dans boutique
        List<Promos> listPromo = new ArrayList<>(); // Liste des promos (CSV) à afficher à coter de la commande
        List<Promos> listPromoProduit = new ArrayList<>(); // Liste des promos à appliquer sur le produit
        List<Promos> listPromoCommande = new ArrayList<>(); // Liste des promos à appliquer sur la commande

        int compteurCommande = 1; // Compteur de commande réinitialiser à chaque lancement

        //region Gestion des CSVs , Lecture et écriture
//        //Lire un CSV

        // récupérer les données dans les CSV
        new FunctionsList().LectureCSV("Articles.csv", listProduit);
        new FunctionsList().LectureCSV("Clients.csv", listClient);
        new FunctionsList().LectureCSV("Promos.csv", listPromo);
        new FunctionsList().LectureCSV("Commandes.csv", commandesListTotal);


//endregion

        //region Affichage/modif Client

// Ajout Titre
        JLabel presClient = new JLabel("   Ajouts de données clients");
        presClient.setFont(new Font("Arial", Font.PLAIN, 20));
        panelClient.add(presClient);

// Ajout nom
        JLabel nom = new JLabel("   Nom");
        nom.setFont(new Font("Arial", Font.PLAIN, 12));
        panelClient.add(nom);

        JTextField nomClient = new JTextField();
        nomClient.setFont(new Font("Arial", Font.PLAIN, 12));
        panelClient.add(nomClient);
// Ajout prenom
        JLabel prenom = new JLabel("   Prenom");
        prenom.setFont(new Font("Arial", Font.PLAIN, 12));
        panelClient.add(prenom);

        JTextField prenomClient = new JTextField();
        prenomClient.setFont(new Font("Arial", Font.PLAIN, 12));
        panelClient.add(prenomClient);
// Ajout mobile
        JLabel mobile = new JLabel("   Téléphone");
        mobile.setFont(new Font("Arial", Font.PLAIN, 12));
        panelClient.add(mobile);

        JTextField mobileClient = new JTextField();
        mobileClient.setFont(new Font("Arial", Font.PLAIN, 12));
        panelClient.add(mobileClient);
//Ajout mail
        JLabel email = new JLabel("   Email");
        email.setFont(new Font("Arial", Font.PLAIN, 12));
        panelClient.add(email);

        JTextField mailClient = new JTextField();
        mailClient.setText("");
        mailClient.setFont(new Font("Arial", Font.PLAIN, 12));
        panelClient.add(mailClient);
// Ajout Bouton Add Client

        panelClient.add(new JLabel()); // créer un espace

        JButton buttonNextClient = new JButton(("Enregistrer le client"));
        buttonNextClient.setVerticalTextPosition(SwingConstants.BOTTOM);
        buttonNextClient.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonNextClient.setBounds(40, 80, 250, 250);
        buttonNextClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FunctionsList().AddClient(commandesListTotal, listClient, nomClient, prenomClient, mobileClient, mailClient);
                fClient.setVisible(false);
                f.setVisible(true);
            }
        });
        panelClient.add(buttonNextClient);

        panelClient.add(new JLabel()); // créer un espace

        JButton buttonNoCLient = new JButton(("Passer"));
        buttonNoCLient.setVerticalTextPosition(SwingConstants.BOTTOM);
        buttonNoCLient.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonNoCLient.setBounds(40, 80, 250, 250);
        buttonNoCLient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fClient.setVisible(false);
                f.setVisible(true);
            }
        });

        panelClient.add(buttonNoCLient);


        //endregion

        //region promotion sur article

        // Ajout des promotions
        JPanel panelPromotion = new JPanel();
        panelPromotion.setLayout(new GridLayout(2, 3));


        JComboBox choicePromoCommande = new JComboBox<>();
        JComboBox choicePromoProduit = new JComboBox<>();




//------------------------------------------

        choicePromoCommande.addItem("pas de promotion");
        choicePromoProduit.addItem("pas de promotion");



        for (Promos p : listPromo) {
            if (p.getApplication().equals("Commande")) {
                listPromoCommande.add(p);
                choicePromoCommande.addItem(p);
            }

            if (p.getApplication().equals("Produit")) {
                listPromoProduit.add(p);
                choicePromoProduit.addItem(p);
            }
        }
        panelPromotion.add(new JLabel("Promotion sur l'ensemble de la commande:"));
        panelPromotion.add(choicePromoCommande);
        panelPromotion.add(new JLabel(""));


        panelPromotion.add(new JLabel("Promotion sur les prochains produits :"));
        panelPromotion.add(choicePromoProduit);
        panelPromotion.add(new JLabel(""));

        panelCommande.add(panelPromotion);



//        System.out.print(promoProduitSelectedItem+"\n");
//        System.out.print(promoCommandeSelectedItem);

//----------------------------------------- début gestion des check BOX



//        for (Promos p : listPromo) {
//            JCheckBox checkBoxPromo = new JCheckBox("-" + p.getValues() + " " + p.getUnit() + " " + p.getApplication());
//            checkBoxPromo.setBounds(40, 80, 1050, 1050);
//            checkBoxPromo.addItemListener(new ItemListener() {
//                @Override
//                public void itemStateChanged(ItemEvent e) {
//
//                    if (p.getApplication() == "Commande") {
//                        if (e.getStateChange() == 1) {
//                            listPromoCommande.add(p);
//                        } else {
//                            listPromoCommande.remove(p);
//                        }
//                    }
//                    if (p.getApplication() == "Produit") {
//                        if (e.getStateChange() == 1) {
//                            listPromoProduit.add(p);
//                        } else {
//                            listPromoProduit.remove(p);
//                        }
//
//                    }
//
////                (e.getStateChange()==1?"checked":"unchecked"));
//
//
//                }
//            });
//            panelPromotion.add(checkBoxPromo);
//
//
//        }
//        panelCommande.add(panelPromotion);

//----------------------------------------- fin gestion des check BOX
        //endregion

        //region Affichage Commande

//Ajout du titre
        JLabel recapCommande = new JLabel("   Récapitulatif de la commande");
        recapCommande.setFont(new Font("Arial", Font.PLAIN, 20));

        panelCommande.add(recapCommande);
// Ajout Label Récap
        JLabel totalCommande = new JLabel("   Nombre de produit : 0 " + "Prix total : 0 €");
        totalCommande.setFont(new Font("Arial", Font.BOLD, 20));


//Ajout de la zone de texte des commandes
        JTextArea aeraCommande = new JTextArea(6, 4);
        aeraCommande.setEditable(false); //texte non modifiable
        aeraCommande.setFont(new Font("Arial", Font.PLAIN, 18));


// Ajout bouton annulé et valider commandes
        JPanel buttonCommande = new JPanel();
        buttonCommande.setLayout(new GridLayout(1, 3));

        JButton buttonAnnuler = new JButton(("Recommencer la commande"));
        buttonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //fonction pour clear la commande et tout les champ associer
                new FunctionsList().ClearCommande(aeraCommande, commandesList, totalCommande);
            }
        });
        buttonCommande.add(buttonAnnuler);

        buttonCommande.add(new JLabel());

        JButton buttonValiderCommande = new JButton(("Valider la commande"));
        buttonValiderCommande.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int nbArticle = 0;
                float prixTotal = 0;

                new  FunctionsList().AddCommande(compteurCommande,commandesList, commandesListTotal, nbArticle, prixTotal);
                new FunctionsList().WriteCSV("Commandes.csv", commandesListTotal);
                //rajoute client dans la liste et dans le CSV
                f.setVisible(false);
                fClient.setVisible(true);
                new FunctionsList().ClearCommande(aeraCommande, commandesList, totalCommande);

            }

        });
        buttonCommande.add(buttonValiderCommande);


//  Ajout d'un event dès le changement d'une promotion sur la commande
        choicePromoCommande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FunctionsList().DisplayCommande(commandesList, aeraCommande, totalCommande, choicePromoCommande.getSelectedItem().toString());
            }
        });
        //endregion

        //region Affichage produit boutique
        //Parcours la liste pour afficher photo (100x100) nom et prix



        for (Produits p : listProduit) {

            ImageIcon ii = (new ImageIcon(p.getImage()));

            JButton buttonProduit = new JButton(("<html>" + p.getNom() + "<br>" + p.getPrix() + "</hmtl>"), ii);

            buttonProduit.setVerticalTextPosition(SwingConstants.BOTTOM);
            buttonProduit.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonProduit.setBounds(40, 80, 1050, 1050);

            //Ajout de la fonction pour rajouter un produit à la liste et mettre à jour  la liste
            buttonProduit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new FunctionsList().AddProduct(commandesList, p, String.valueOf(compteurCommande), choicePromoProduit.getSelectedItem().toString());
                    new FunctionsList().DisplayCommande(commandesList, aeraCommande, totalCommande, choicePromoCommande.getSelectedItem().toString());

                }
            });

            panelBoutique.add(buttonProduit);


        }

//endregion

        //region Propriété de l'application
// Ajout boutique
        JScrollPane scroll = new JScrollPane(panelBoutique); // rajout de la possibilité de scroll pour téléphone
        panelBoutique.setPreferredSize(new Dimension(350, 1050)); // Bloque la taille de la boutique pour faire du responsive
        f.getContentPane().add(scroll);


// Ajout Commande panel
        JScrollPane scrollAeraCommance = new JScrollPane(aeraCommande); // rajout de la possibilité de scroll pour téléphone
        scrollAeraCommance.setPreferredSize(new Dimension(350, 750)); // Bloque la taille de la boutique pour faire du responsive
        panelCommande.add(scrollAeraCommance);

        panelCommande.add(totalCommande);
        panelCommande.add(buttonCommande);
        panelbas.add(panelCommande);


        f.add(panelbas);


        f.setSize(1650, 1080); //Donne la taille maximal souhaiter
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); //Permet de prendre tout l'écran
//        f.setUndecorated(true); // Permey d'enlever la barre de raccourci


        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quel action quand on ferme
        f.setVisible(true); // rend l'appli visible


//Frame Client
        fClient.add(panelClient);

        fClient.setSize(1650, 1080); //Donne la taille maximal souhaiter
        fClient.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fClient.setVisible(false); // parti client invisible pour commencer

//endregion

        System.out.print("OK ça marche"); // Test pour confirmer qu'on arrive bien à la fin du code

    }
}