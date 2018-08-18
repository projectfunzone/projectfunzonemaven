package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

public interface IPanierService {

	
	
	/**
	 * Sert à créer une ligne de commade lors du choix d'un produit
	 * @return une ligne de commande
	 */
	public LigneCommande ajoutProdPanier(Produit pr, int q);

}
