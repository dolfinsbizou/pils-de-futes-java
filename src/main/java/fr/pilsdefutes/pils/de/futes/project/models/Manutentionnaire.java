/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pilsdefutes.pils.de.futes.project.models;

import java.util.ArrayList;

/**
 *
 * @author Maxime
 */
public class Manutentionnaire {

    // Attributs 
    private int x; // Position par rapport à l'origine en X
    private int y; // Position par rapport à l'origine en Y
    private int nbBouteillesDansSac; // Nombre de bouteilles dans le sac du manutentionnaire
    private String nom; // Nom du manutentionnaire
    private int couleur; // 1 : Rouge, 2 : Blanc, 3 : Rosé
    private int nbRestants;

    public int getNbRestants() {
        return nbRestants;
    }

    public void setNbRestants(int nbRestants) {
        this.nbRestants = nbRestants;
    }
    private boolean enRetour; // Booleen pour savoir s'il revient vers le stock ou pas
    private ArrayList<String> historique; // Historique du manutentionnaire : PPOOPPO PPOENNEE

    // Accesseurs
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNbBouteillesDansSac() {
        return nbBouteillesDansSac;
    }

    public void setNbBouteillesDansSac(int nbBouteillesDansSac) {
        this.nbBouteillesDansSac = nbBouteillesDansSac;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public boolean isEnRetour() {
        return enRetour;
    }

    public void setEnRetour(boolean enRetour) {
        this.enRetour = enRetour;
    }

    public ArrayList<String> getHistorique() {
        return historique;
    }

    public void setHistorique(ArrayList<String> historique) {
        this.historique = historique;
    }

    // Constructeur
    /**
     *
     * @param x : Position X du manutentionnaire
     * @param y : Position Y du manutentionnaire
     * @param nbBouteillesDansSac : Nombre de bouteilles dans son sac au départ
     * @param nom : Nom du manutentionnaire
     * @param couleur : Couleur de l'équipe ( 1 : Rouge, 2 : Blanc, 3 : Rosé)
     */
    public Manutentionnaire(int x, int y, int nbBouteillesDansSac, String nom, int couleur, int nb) {
        this.x = x;
        this.y = y;
        this.nbBouteillesDansSac = nbBouteillesDansSac;
        this.nom = nom;
        this.couleur = couleur;
        this.enRetour = false;
        this.historique = new ArrayList();
        this.nbRestants = nb;
    }

    // Méthodes
    /**
     * Calcule la distance entre le manutentionnaire et une case, dont les
     * coords sont passées en paramètre
     *
     * @param x : x de la case à tester
     * @param y : y de la case à tester
     * @return : distance entre le manutentionnaire et la case
     */
    public int distance(int x, int y) {
        return Math.abs(y - this.y) + Math.abs(x - this.x);
    }

    /**
     * Déplacement vers le nord du manutentionnaire
     */
    public void actionN() {
        this.y--;
    }

    /**
     * Déplacement vers le sud du manutentionnaire
     */
    public void actionS() {
        this.y++;
    }

    /**
     * Déplacement vers l'est du manutentionnaire
     */
    public void actionE() {
        this.x++;
    }

    /**
     * Déplacement vers l'ouest du manutentionnaire
     */
    public void actionO() {
        this.y--;
    }

    /**
     * Le manutentionnaire pose une bouteille
     */
    public void actionP() {
        this.nbBouteillesDansSac--;
    }

    /**
     * Le manutentionnaire récupère une bouteille
     */
    public void actionR() {
        this.nbBouteillesDansSac++;
    }

    /**
     * Ajoute une chaîne de caractères passée en paramètre à l'historique du
     * manutentionnaire
     *
     * @param str : Chaine d'instruction à ajouter à l'historique du
     * manutentionnaire
     */
    public void ajouterPhraseHistorique(String phrase) {
        this.historique.add(phrase);
    }

    @Override
    public String toString() {
        return "Manutentionnaire{" + "x=" + x + ", y=" + y + ", nbBouteillesDansSac=" + nbBouteillesDansSac + ", nom=" + nom + ", couleur=" + couleur + ", enRetour=" + enRetour + ", historique=" + historique + '}';
    }
}
