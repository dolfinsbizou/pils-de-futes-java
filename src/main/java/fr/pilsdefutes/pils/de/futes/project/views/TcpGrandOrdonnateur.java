/**
 * Remarques du gentil organisateur destinées aux compétiteurs : 
 * - utilisez cette classe pour communiquer avec le Grand Ordonnateur
 * - ne changez rien au code de cette classe 
 * 
 * Interface pour le protocole du Grand Ordonnateur.
 */
package fr.pilsdefutes.pils.de.futes.project.views;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 *
 * @author Olivier plagiant éhontément Olivier (l'autre)
 */
public class TcpGrandOrdonnateur
{
    /**
    * Séparateur entre les chaînes de caractères échangées par TCP.
    */
   public static final char SEPARATEUR = '~';

   /**
    * Caractère minimal (inclus) autorisé par le protocole du Grand
    * Ordonnateur.
    */
   public static final int CARACTERE_MINIMAL_INCLUS = 32;

   /**
    * Caractère maximal (exclu) autorisé par le protocole du Grand Ordonnateur.
    */
   public static final int CARACTERE_MAXIMAL_EXCLU = (int) SEPARATEUR;

   /**
    * Socket.
    */
   private Socket socket;

   /**
    * Création d'une interface pour le protocole du Grand Ordonnateur.
    */
   public TcpGrandOrdonnateur() {
           socket = null;
   }

   /**
    * Connexion au serveur.
    * 
    * @param hote
    *            Hôte.
    * @param port
    *            Port.
    * @throws IOException
    */
   public void connexion(String hote, int port) throws IOException {
           socket = new Socket(hote, port);
           socket.getInputStream();
           socket.getOutputStream();
   }

   /**
    * Envoi d'un entier au serveur.
    * 
    * @param entier
    *            Entier à envoyer.
    * @throws IOException
    */
   public void envoiEntier(int entier) throws IOException {
           testeSocket();
           final OutputStream fluxSortie = socket.getOutputStream();
           if (fluxSortie == null) {
                   throw new IllegalArgumentException("Le flux en sortie est null.");
           } else {
                   fluxSortie.write(entier);
                   fluxSortie.flush();
           }
   }

   /**
    * Envoi d'un caractère au serveur.
    * 
    * @param caractere
    *            Caractère à envoyer.
    * @throws IOException
    */
   public void envoiCaractere(char caractere) throws IOException {
           envoiEntier(caractere);
   }

   /**
    * Envoi d'une chaîne de caractères au serveur.
    * 
    * @param chaine
    *            Chaîne de caractères à envoyer.
    * @throws IOException
    */
   public void envoiChaine(String chaine) throws IOException {
           if (chaine != null) {
                   if (chaineValidePourTransmission(chaine)) {
                           for (char caractere : chaine.toCharArray()) {
                                   envoiEntier(caractere);
                           }
                           envoiEntier(SEPARATEUR);
                   } else {
                           throw new IllegalArgumentException(
                                           "La chaîne de caractères à envoyer « " + chaine + " » n'est pas valide.");
                   }
           }
   }

   /**
    * Teste si une chaîne de caractères est valide (dans un certain intervalle
    * de la table ASCII (cf. https://fr.wikipedia.org/wiki/
    * American_Standard_Code_for_Information_Interchange)) pour être envoyée,
    * c'est-à-dire si elle ne contient pas de caractère spécial.
    * 
    * @param chaine
    *            Chaîne de caractères à envoyer.
    * @return La chaîne de caractères est-elle valide pour être envoyée ?
    */
   private boolean chaineValidePourTransmission(String chaine) {
           return chaine == null ? false
                           : Pattern.matches(
                                           "[" + ((char) CARACTERE_MINIMAL_INCLUS) + "-" + ((char) (CARACTERE_MAXIMAL_EXCLU - 1)) + "]*",
                                           chaine);
   }

   /**
    * Réception d'un entier depuis le serveur.
    * 
    * @return Entier reçu.
    * @throws IOException
    */
   public int receptionEntier() throws IOException {
           testeSocket();
           final InputStream fluxEntree = socket.getInputStream();
           if (fluxEntree == null) {
                   throw new IllegalArgumentException("Le flux en entrée est null.");
           } else {
                   int entierRecu = -1;
                   entierRecu = fluxEntree.read();
                   return entierRecu;
           }
   }

   /**
    * Réception d'un caractère depuis le serveur.
    * 
    * @return Caractère reçu.
    * @throws IOException
    */
   public char receptionCaractere() throws IOException {
           return (char) receptionEntier();
   }

   /**
    * Réception d'une chaîne de caractères depuis le serveur.
    * 
    * @return Chaîne de caractères reçue.
    * @throws IOException
    */
   public String receptionChaine() throws IOException {
           testeSocket();
           final StringBuffer chaine = new StringBuffer();
           boolean separateurRecu = false;
           boolean ok = true;
           while (ok && !separateurRecu) {
                   final char recu = (char) receptionEntier();
                   ok = (recu != (char) -1);
                   separateurRecu = (recu == SEPARATEUR);
                   if (ok && !separateurRecu) {
                           chaine.append(recu);
                   }
           }
           return ok ? chaine.toString() : null;
   }

   /**
    * Déconnexion du serveur.
    * 
    * @throws IOException
    */
   public void deconnexion() throws IOException {
           testeSocket();
           InputStream fluxEntree = null;
           fluxEntree = socket.getInputStream();
           if (fluxEntree != null) {
                   fluxEntree.close();
           }
           OutputStream fluxSortie = null;
           fluxSortie = socket.getOutputStream();
           if (fluxSortie != null) {
                   fluxSortie.close();
           }
           socket.close();
   }

   /**
    * Teste si le socket est initialisé ; sinon, arrêt du programme.
    */
   private void testeSocket() {
           if (socket == null) {
                   System.out.println("Le socket n'est pas initialisé.");
                   System.out.flush();
                   System.exit(1);
           }
   }
}
