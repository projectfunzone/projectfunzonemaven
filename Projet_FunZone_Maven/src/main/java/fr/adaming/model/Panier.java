package fr.adaming.model;

import java.io.Serializable;
import java.util.List;


/**
 * Definition de la classe Panier
 */
public class Panier implements Serializable {

	/**
	 * transformation de l'association UML en JAVA
	 */
	private List<LigneCommande> listeCommande;
	
	/**
	 * Constructeur vide
	 */
	public Panier() {
		super();
	}

	/**
	 * Getters et setters
	 */
	/**
	 * @return the listeCommande
	 */
	public List<LigneCommande> getListeCommande() {
		return listeCommande;
	}

	/**
	 * @param listeCommande the listeCommande to set
	 */
	public void setListeCommande(List<LigneCommande> listeCommande) {
		this.listeCommande = listeCommande;
	}	
	
}
