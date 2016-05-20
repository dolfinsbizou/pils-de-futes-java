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
    /**
	 * Hôte du Grand Ordonnateur.
	 */
	private String hote = null;

	/**
	 * Port du Grand Ordonnateur.
	 */
	private int port = -1;

	/**
	 * Interface pour le protocole du Grand Ordonnateur.
	 */
	private TcpGrandOrdonnateur tcpGdOrdo = null;

	/**
	 * Création d'une illustration du protocole du Grand Ordonnateur.
	 * 
	 * @param hote
	 *            Hôte.
	 * @param port
	 *            Port.
	 */
	public Controller(String hote, int port) {
		this.hote = hote;
		this.port = port;
		this.tcpGdOrdo = new TcpGrandOrdonnateur();
	}

	/**
	 * Scénario illustrant le protocole du Grand Ordonnateur.
	 * 
	 * Cf. TcpGrandOrdonnateur.chaineValidePourTransmission pour connaître les
	 * caractères autorisés par le protocole du Grand Ordonnateur.
	 * 
	 * @throws IOException
	 */
	public void scenario() throws IOException {
		// Finalisation du « create » du protocole du Grand Ordonnateur.
		// - connexion
		tcpGdOrdo.connexion(hote, port);
		// Réponse au « create » du protocole du Grand Ordonnateur.
		// - envoi du nom de notre équipe
		tcpGdOrdo.envoiChaine("nom de notre equipe");
		// - envoi de l'IUT où les membres de notre équipe étudient
		tcpGdOrdo.envoiChaine("IUT ou nous etudions Java");
		// Initialisation du protocole du Grand Ordonnateur.
		// - réception du nombre de lignes de la cave
		tcpGdOrdo.receptionEntier();
		// - réception du nombre de colonnes de la cave
		tcpGdOrdo.receptionEntier();
		// - réception des casiers (nombre d'emplacements et position de
		// l'escalier) de la cave
		tcpGdOrdo.receptionChaine();
		// - réception du nombre de manutentionnaires ; 3 ici
		tcpGdOrdo.receptionEntier();
		// - réception de l'ordre (entre 1 et le nombre de manutentionnaires)
		// dans lequel l'application singeant notre manutentionnaire joue ; 2
		// ici
		tcpGdOrdo.receptionEntier();
		// Premier tour de jeu :
		// - réception des actions du premier manutentionnaire
		tcpGdOrdo.receptionChaine();
		// - envoi de nos actions (comme deuxième manutentionnaire) ; ici, pour
		// changer de l'envoi de nos actions du second tour de jeu, envoi des
		// actions une à une
		for (char action : "EPPOOPP".toCharArray()) {
			tcpGdOrdo.envoiCaractere(action);
		}
		// - réception des actions du troisième manutentionnaire
		tcpGdOrdo.receptionChaine();
		// Second tour de jeu :
		// - réception des actions du premier manutentionnaire
		tcpGdOrdo.receptionChaine();
		// - envoi de nos actions (comme deuxième manutentionnaire) ; ici, pour
		// changer de l'envoi de nos actions du premier tour de jeu, envoi
		// simultané des actions
		tcpGdOrdo.envoiChaine("EREPEI");
		// - réception des actions du troisième manutentionnaire
		tcpGdOrdo.receptionChaine();
		// Remarque : le reste du code de cette méthode est purement esthétique
		// puisque l'application va être arrêtée par un « destroy » du protocole
		// du Grand Ordonnateur.
		// - attente
		try {
			Thread.sleep(1 * 60 * 1000);
		} catch (InterruptedException e) {
			System.out.println("Erreur lors de l'attente du « destroy » du protocole du Grand Ordonnateur.");
			System.out.flush();
			e.printStackTrace();
			System.err.flush();
			System.exit(1);
		}
		// - déconnexion
		try {
			tcpGdOrdo.deconnexion();
		} catch (IOException e) {
			// Qu'importe si le serveur s'est arrêté avant le client.
		}
	}
}
