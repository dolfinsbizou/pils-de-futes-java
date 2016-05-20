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
public class Controller {

    private String hote = null;
    private Cave cave;
    private int port = -1;
    private TcpGrandOrdonnateur tcpGdOrdo = null;
    private int nbLignes, nbCol;
    private String configCave;
    private int nbManutentionnaires, ordre;
    private ArrayList<Manutentionnaire> joueurs;

    public Controller(String hote, int port) {
        this.hote = hote;
        this.port = port;
        this.tcpGdOrdo = new TcpGrandOrdonnateur();
    }

    public void scenario() throws IOException {
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

        cave = new Cave(nbLignes, nbCol, configCave);
        for (int i = 0; i < ordre - 1; i++) {
            joueurs.add(new Manutentionnaire(cave.getEscalier().getX(), cave.getEscalier().getY(), 10, "machin", i+1, 7));
        }
        
        joueurs.add(new Manutentionnaire(cave.getEscalier().getX(), cave.getEscalier().getY(), 10, "machin", ordre, 7));
        
        for (int i = 0; i < nbManutentionnaires - ordre; i++) {
                joueurs.add(new Manutentionnaire(cave.getEscalier().getX(), cave.getEscalier().getY(), 10, "machin", ordre+1+i, 7));
            }

        //Initialisation modèles TODO
        while (true) {
            for (int i = 0; i < ordre - 1; i++) {

                String p = tcpGdOrdo.receptionChaine(); //TODO envoyer le résultat
                miseAJourSalles(p, joueurs.get(i));
            }
            //PHASE TRAITEMENT
            String phraseDeJeu = "";

            for (char action : phraseDeJeu.toCharArray()) {
                tcpGdOrdo.envoiCaractere(action);
            }
            miseAJourSalles(phraseDeJeu, joueurs.get(ordre));

            for (int i = 0; i < nbManutentionnaires - ordre; i++) {
                String p = tcpGdOrdo.receptionChaine(); //TODO envoyer le résultat
                miseAJourSalles(p, joueurs.get(i + ordre));
            }
            
            for(int i = 0 ; i < nbManutentionnaires ; i++)
                joueurs.get(i).setNbRestants(7);
        }

        /*
		// - déconnexion
		try {
			tcpGdOrdo.deconnexion();
		} catch (IOException e) {
			// Qu'importe si le serveur s'est arrêté avant le client.
		} //*/
    }

    public void miseAJourSalles(String phrase, Manutentionnaire m) {
        boolean continuer = true;
        Pattern pattern = Pattern.compile("NESOPRI");
        Matcher matcher;

        for (int i = 0; i < phrase.length(); i++) {
            char letter = phrase.charAt(i);

            matcher = pattern.matcher(String.valueOf(letter));

            if (matcher.find()) {
                switch (letter) {
                    case 'I':
                        continuer = false;
                        break;
                    case 'N':
                        // S'il peut aller vers le nord, il y va
                        if (cave.isSalleAtXY(m.getX(), m.getY() - 1)) {
                            m.setY(m.getY() - 1);
                        }

                        break;

                    case 'S':
                        // S'il peut aller vers le sud, il y va
                        if (cave.isSalleAtXY(m.getX(), m.getY() + 1)) {
                            m.setY(m.getY() + 1);
                        }

                        break;

                    case 'E':
                        // S'il peut aller vers l'est, il y va
                        if (cave.isSalleAtXY(m.getX() + 1, m.getY())) {
                            m.setX(m.getX() + 1);
                        }

                        break;

                    case 'O':
                        // S'il peut aller vers l'ouest, il y va
                        if (cave.isSalleAtXY(m.getX() - 1, m.getY())) {
                            m.setX(m.getX() - 1);
                        }

                        break;

                    case 'R':
                        // S'il est à l'escalier et qu'il a de la place, il prend une bouteille
                        if (cave.isEscalier(m.getX(), m.getY()) && m.getNbBouteillesDansSac() < 10) {
                            m.setNbBouteillesDansSac(m.getNbBouteillesDansSac() + 1);
                        }

                        break;

                    case 'P':
                        // S'il n'est pas à l'escalier, s'il a assez de bouteilles et si la salle a des emplacements dispos, alors il peut poser une bouteille
                        if (m.getNbBouteillesDansSac() > 0 && !cave.isEscalier(m.getX(), m.getY()) && cave.getSalleAtXY(m.getX(), m.getY()).getNbEmplacements() > 0) {
                            m.setNbBouteillesDansSac(m.getNbBouteillesDansSac() - 1);

                            Salle s = cave.getSalleAtXY(m.getX(), m.getY());
                            s.setNbEmplacements(s.getNbEmplacements() - 1);
                        }

                        break;
                }
            }

            if (!continuer) {
                break;
            }
        }
    }

    private ArrayList<Salle> rechercheSalle(Cave c, Manutentionnaire m) {
        ArrayList<Salle> tabSalle = new ArrayList();
        int n = 7;
        int i = 0;
        int j = 0;
        boolean continuer = true;

        while (continuer) {
            int xM = m.getX() - n;
            int yM = m.getY() - n;
            for (i = xM; i <= xM + n; i++) {
                for (j = yM; i <= yM + n; j++) {
                    if (i >= 0 && j >= 0 && i <= nbLignes && j <= nbCol) {
                        if (c.getSallesList().get(nbLignes * i + j).getNbEmplacements() > 0) {
                            tabSalle.add(c.getSallesList().get(nbLignes * i + j));
                        }
                    }
                }
            }
            if (tabSalle.isEmpty() && i != nbCol && j != nbLignes) // a init
            {
                n = n + 1;
            } else {
                continuer = false;
            }
        }
        return tabSalle;
    }

    public ArrayList <String> calculDeplacement(Manutentionnaire manu, Salle salleCible) {
        int i, depH, depV, depT;
        String DH, DV;
        ArrayList <String> tabDepl = new ArrayList() ;
        depH = salleCible.getX() - manu.getX();
        depV = salleCible.getY() - manu.getY();
        depT = Math.abs(depH) + Math.abs(depV);

        DH = String.valueOf('O');
        DV = String.valueOf('S');

        depT = Math.max(depT, manu.getNbRestants());
        if (depT >= 7) {
            tabDepl.add(String.valueOf(7));
        } else {
            tabDepl.add(String.valueOf(depT));
        }
        if (depT != 0) {
            if (depH > 0) {
                DH = String.valueOf('E');
            }
            if (depV > 0) {
                DV = String.valueOf('N');
            }

            if (Math.abs(depH) >= depT) {
                for (i = 1; i <= depT; i++) {
                    tabDepl.add(DH);
                }
            } else {
                for (i = 1; i <= Math.abs(depH); i++) {
                    tabDepl.add(DH);
                }
                if (Math.abs(depV) >= manu.getNbRestants() - Math.abs(depH)) {
                    for (i = Math.abs(depH) + 1; i <= manu.getNbRestants(); i++) {
                        tabDepl.add(DV);
                    }
                } else {
                    for (i = Math.abs(depH) + 1; i <= Math.abs(depH) + Math.abs(depV); i++) {
                        tabDepl.add(DV);
                    }
                }

            }
        }

        return tabDepl;
    }
    
    public ArrayList<CoupTheorique> coupPossible (Manutentionnaire man, ArrayList<Salle> tabSalle)
    {
        int valeurActuelle=0, actionActuelle=0;
        int valeurMax=0, actionMax = 0;
        int perteMax=0, perteActuelle=0;
        
        ArrayList<CoupTheorique> listeCoupTh = new ArrayList();
        
        for (Salle tmp : tabSalle)
        {
            valeurActuelle = 4*(tmp.getDistanceEscalier()+1) * man.getNbBouteillesDansSac();
            valeurMax = 4*(tmp.getDistanceEscalier()+1) * 10;
            
            if (tmp.getDistanceManutentionnaire() > 0)
            {
                actionMax = tmp.getDistanceManutentionnaire();
                actionActuelle = actionMax;
            }
            
            actionMax += maximum(tmp.getNbEmplacements(), man.getNbBouteillesDansSac());
            actionActuelle += minimum(tmp.getNbEmplacements(), man.getNbBouteillesDansSac());
            
            perteMax = actionMax + (actionMax/7) + 1;
            perteActuelle = actionActuelle + (actionActuelle/7) + 1;
            
            valeurMax -= perteMax;
            valeurActuelle -= perteActuelle;
            
            if (valeurMax > 0 || valeurActuelle > 0)
            {
                ArrayList<String> phraseTmp = calculDeplacement(man, tmp);
                String phrase = "";
                
                int nbMouvements = Integer.parseInt(phraseTmp.get(0));
                
                phraseTmp.remove(0);
                for (String tmp2 : phraseTmp)
                    phrase += tmp2;
                
                int nbPose = minimumTernaire(nbMouvements, tmp.getNbEmplacements(), man.getNbBouteillesDansSac());
                
                for (int i=0; i< nbPose; i++)
                    phrase += "P";
                
                listeCoupTh.add(new CoupTheorique(valeurMax, valeurMax, phrase));
            }
        }
        return listeCoupTh;
    }
        
    int maximum(int a, int b)
    {
        if (a<b)
            return b;
        else
            return a;
    }
    
    int minimum(int a, int b)
    {
        if (a>b)
            return b;
        else
            return a;
    }
    
    int minimumTernaire(int a, int b, int c)
    {
        if (a < minimum(b,c))
            return a;
        else
            return (minimum(b,c));
    }
}
