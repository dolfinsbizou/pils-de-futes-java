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

    public Cave(int nbLines, int nbCols, String configCave) 
    {
        this.nbLines = nbLines;
        this.nbCols = nbCols;
        this.configCave = configCave;
        
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
                sallesList.add(new Salle(i, j, true, 0));
                escalier = sallesList.get(nbLines*i + j);
            }
            else
            {
                int valInt = Character.getNumericValue(configCave.charAt(i));
                sallesList.add(new Salle(i,j,false, valInt));
            }
        }
        
        //Récupération de la Salle de l'Escalier
        
    }

    
    
    

}
