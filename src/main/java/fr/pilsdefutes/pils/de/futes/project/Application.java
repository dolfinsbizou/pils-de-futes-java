/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pilsdefutes.pils.de.futes.project;

import fr.pilsdefutes.pils.de.futes.project.models.Manutentionnaire;

/**
 *
 * @author Maxime
 */
public class Application {
    public static void main(String[] argv)
    {
        System.out.println("Hello World !");
        Manutentionnaire manutentionnaire = new Manutentionnaire(0,0, 10, "Michel", 2);
        
        manutentionnaire.ajouterPhraseHistorique("EEOPPNE");
        manutentionnaire.ajouterPhraseHistorique("OPPELXX");
        System.out.println(manutentionnaire);
        
    }
}
