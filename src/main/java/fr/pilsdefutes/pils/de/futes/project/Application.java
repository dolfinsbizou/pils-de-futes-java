/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pilsdefutes.pils.de.futes.project;

import fr.pilsdefutes.pils.de.futes.project.controllers.Controller;
import java.io.IOException;

/**
 *
 * @author Maxime
 */
public class Application {
    public static void main(String[] args)
    {
		System.out.flush();
		// « create » du protocole du Grand Ordonnateur.
		final String USAGE = System.lineSeparator() + "\tUsage : java " + Application.class.getName()
				+ " <hôte> <port>";
		if (args.length != 2) {
			System.out.println("Nombre de paramètres incorrect." + USAGE);
			System.out.flush();
			System.exit(1);
		}
		String hote = args[0];
		int port = -1;
		try {
			port = Integer.valueOf(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Le port doit être un entier." + USAGE);
			System.out.flush();
			System.exit(1);
		}
		try {
			(new Controller(hote, port)).scenario();
		} catch (IOException e) {
			System.out.println("Erreur lors de la création d'une illustration du protocole du Grand Ordonnateur.");
			System.out.flush();
			e.printStackTrace();
			System.err.flush();
			System.exit(1);
		}
	}
}
