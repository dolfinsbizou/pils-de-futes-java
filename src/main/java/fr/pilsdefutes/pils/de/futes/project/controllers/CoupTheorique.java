/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pilsdefutes.pils.de.futes.project.controllers;

/**
 *
 * @author doug-rattmann
 */
public class CoupTheorique
{
    public int valeurMax, valeurAct;
    public String phrase;
    
    public CoupTheorique(int valeurMax, int valeurAct, String phrase)
    {
        this.valeurMax = valeurMax;
        this.valeurAct = valeurAct;
        this.phrase = phrase;
    }
    
    
}
