/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pilsdefutes.pils.de.futes.project.models;

import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class Cave {
    private int nbLines;
    private int nbCols;
    private String configCave; //Chaine de caractères reçue du Server avec les données d'init
    private ArrayList<Salle> sallesList = new ArrayList(); 
    private Salle escalier;

    public Salle getEscalier()
    {
        return escalier;
    }

    public Cave(int nbLines, int nbCols, String configCave) 
    {
        this.nbLines = nbLines;
        this.nbCols = nbCols;
        this.configCave = configCave;
        
        int xEscalier;
        int yEscalier;
        
        //Récupération de la Salle de l'Escalier
        int index = configCave.indexOf('@');

        xEscalier = index/nbCols;
        yEscalier = index - nbCols;
        escalier = new Salle(xEscalier, yEscalier, true, 0);

        int j=0;

        //Boucle de parcours de la chaine récupérée et initialisation du tableau de salle
        for(int i=0; i<configCave.length(); i++)
        {
            if (i == nbLines)
            {
                i=0;
                j++;
            }

            if (configCave.charAt(i) == '@')
            {
                sallesList.add(escalier);
                this.escalier = sallesList.get(index);
            }
            else
            {
                int valInt = Character.getNumericValue(configCave.charAt(i));
                sallesList.add(new Salle(i,j,false, valInt, xEscalier, yEscalier));
            }
        }
    }

    public ArrayList<Salle> getSallesList() {
        return sallesList;
    }
    
    //Test pour savoir si une Salle existe
    public boolean isSalleAtXY(int x, int y)
    {
        if (x > nbCols || x < 0 || y < 0 || y > nbLines)
            return false;
        else
            return true;
    }
    
    //Test pour savoir si une Salle est l'escalier
    public boolean isEscalier(int x, int y)
    {
        if (x == escalier.getX() && y == escalier.getY())
            return true;
        else
            return false;
    }
    
    //Methode qui renvoie la salle aux coordonnées données
    public Salle getSalleAtXY(int x, int y)
    {
        return sallesList.get(x*nbLines + y);
    }
}
