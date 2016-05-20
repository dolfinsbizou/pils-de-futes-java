/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pilsdefutes.pils.de.futes.project.controllers;

import fr.pilsdefutes.pils.de.futes.project.models.Cave;
import fr.pilsdefutes.pils.de.futes.project.models.Manutentionnaire;
import fr.pilsdefutes.pils.de.futes.project.models.Salle;
import fr.pilsdefutes.pils.de.futes.project.views.TcpGrandOrdonnateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Maxime
 */
public class Controller
{

    private String hote = null;
    private int port = -1;
    private TcpGrandOrdonnateur tcpGdOrdo = null;
    private int nbLignes, nbCol;
    private String configCave;
    private int nbManutentionnaires, ordre;
    private ArrayList<Manutentionnaire> joueurs;

    public Controller(String hote, int port)
    {
        this.hote = hote;
        this.port = port;
        this.tcpGdOrdo = new TcpGrandOrdonnateur();
    }

    public void scenario() throws IOException
    {
        tcpGdOrdo.connexion(hote, port);

        tcpGdOrdo.envoiChaine("Pils de futes");
        tcpGdOrdo.envoiChaine("IUT Amiens");

        nbLignes = tcpGdOrdo.receptionEntier();
        nbCol = tcpGdOrdo.receptionEntier();

        //configuration de la cave
        configCave = tcpGdOrdo.receptionChaine();

        //nb de joueurs
        nbManutentionnaires = tcpGdOrdo.receptionEntier();

        //ordre de jeu
        ordre = tcpGdOrdo.receptionEntier();

        //Initialisation modèles TODO
        Cave cave;

        while (true)
        {
            for (int i = 0; i < ordre - 1; i++)
            {
                tcpGdOrdo.receptionChaine(); //TODO envoyer le résultat
            }
            //PHASE TRAITEMENT
            String phraseDeJeu = "";

            //ArrayList<Salle> sallesPotentielles = rechercheSalle(cave);
            tcpGdOrdo.envoiChaine(phraseDeJeu);

            for (int i = 0; i < nbManutentionnaires - ordre; i++)
            {
                tcpGdOrdo.receptionChaine(); //TODO envoyer le résultat
            }
        }

        /*
		// - déconnexion
		try {
			tcpGdOrdo.deconnexion();
		} catch (IOException e) {
			// Qu'importe si le serveur s'est arrêté avant le client.
		} //*/
    }

    public void miseAJourSalles(String phrase, Manutentionnaire m)
    {
        boolean continuer = true;
        Pattern pattern = Pattern.compile("NESOPRI");
        Matcher matcher;

        for (int i = 0; i < phrase.length(); i++)
        {
            char letter = phrase.charAt(i);

            matcher = pattern.matcher(String.valueOf(letter));

            if (matcher.find())
            {
                switch (letter)
                {
                    case 'I':
                        continuer = false;
                        break;
                    case 'N':

                        break;
                    case 'S':
                        break;
                    case 'E':
                        break;
                    case 'O':
                        break;
                    case 'R':
                        break;
                    case 'P':
                        break;
                }
            }

            if (!continuer)
            {
                break;
            }
        }
    }

    private ArrayList<Salle> rechercheSalle(Cave c, Manutentionnaire m)
    {
        ArrayList<Salle> tabSalle = new ArrayList();
        int n = 7;
        int i = 0;
        int j = 0;
        boolean continuer = true;
        while (continuer)
        {
            int xM = m.getX() - n;
            int yM = m.getY() - n;
            for (i = xM; i <= xM + n; i++)
            {
                for (j = yM; i <= yM + n; j++)
                {
                    if (i >= 0 && j >= 0 && i <= nbLignes && j <= nbCol)
                    {
                        if (c.getSallesList().get(nbLignes * i + j).getNbEmplacements() > 0)
                        {
                            tabSalle.add(c.getSallesList().get(nbLignes * i + j));
                        }
                    }
                }
            }
            if (tabSalle.isEmpty() && i != nbCol && j != nbLignes) // a init
            {
                n = n + 1;
            }
            else
            {
                continuer = false;
            }
        }
        return tabSalle;
    }
}
