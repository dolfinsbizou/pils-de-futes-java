/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pilsdefutes.pils.de.futes.project.models;

/**
 *
 * @author Kevin
 */
public class Salle {
    //Coordonnées ABSOLUE de chaque salle
    private int x;
    private int y;
    
    private boolean escalier;
    private int nbEmplacements;
    private int distanceManutentionnaire;
    private int distanceEscalier;

    public Salle(int x, int y, boolean escalier, int nbEmplacements) 
    {
        this.x = x;
        this.y = y;
        this.escalier = escalier;
        this.nbEmplacements = nbEmplacements;
        this.distanceManutentionnaire = 0;
        this.distanceEscalier = 0;
    }
    
    public Salle(int x, int y, boolean escalier, int nbEmplacements, int xEscalier, int yEscalier) 
    {
        this.x = x;
        this.y = y;
        this.escalier = escalier;
        this.nbEmplacements = nbEmplacements;
        this.distanceManutentionnaire = 0;
        this.distanceEscalier = Math.abs(x - xEscalier) + Math.abs(y - yEscalier);
    }
    

    public int getX() { return x; } 
    public void setX(int x) { this.x = x; }
    
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public boolean isEscalier() { return escalier; }
    public void setEscalier(boolean escalier) { this.escalier = escalier; }

    public int getNbEmplacements() { return nbEmplacements; }
    public void setNbEmplacements(int nbEmplacements) { this.nbEmplacements = nbEmplacements; }

    public int getDistanceManutentionnaire() { return distanceManutentionnaire; }
    public void setDistanceManutentionnaire(int distanceManutentionnaire) { this.distanceManutentionnaire = distanceManutentionnaire; }

    public int getDistanceEscalier() { return distanceEscalier; }
    public void setDistanceEscalier(int distanceEscalier) { this.distanceEscalier = distanceEscalier; }
    
    public void updateDistanceManutentionnaire(int dx, int dy)
    {
        this.x = dx;
        this.y = dy;
    }
    
}
