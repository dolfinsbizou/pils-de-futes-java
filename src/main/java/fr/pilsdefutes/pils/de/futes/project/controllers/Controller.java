/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pilsdefutes.pils.de.futes.project.controllers;

import fr.pilsdefutes.pils.de.futes.project.views.TcpGrandOrdonnateur;
import java.io.IOException;

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

	public Controller(String hote, int port)
        {
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
                
                //Initialisation modèles TODO
                
                while(true)
                {
                    for(int i = 0 ; i < ordre-1 ; i++)
                        tcpGdOrdo.receptionChaine(); //TODO envoyer le résultat
                    
                    //PHASE TRAITEMENT
                    String phraseDeJeu = "";
                    
                    tcpGdOrdo.envoiChaine(phraseDeJeu);
                    
                    
                    for(int i = 0 ; i < nbManutentionnaires-ordre ; i++)
                        tcpGdOrdo.receptionChaine(); //TODO envoyer le résultat
                }
		/*
		// - déconnexion
		try {
			tcpGdOrdo.deconnexion();
		} catch (IOException e) {
			// Qu'importe si le serveur s'est arrêté avant le client.
		} //*/
	}
}
