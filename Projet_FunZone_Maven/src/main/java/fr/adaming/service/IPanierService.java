package fr.adaming.service;

import java.util.List;

import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

public interface IPanierService {

	/**
	 * Sert � cr�er une ligne de commade lors du choix d'un produit
	 * 
	 * @return une ligne de commande
	 */
	public LigneCommande addProdPanier(Produit pr, int quantite);

	
	/**
	 * Permet d'ajouter une quantit� au panier directement sur la ligne dans le panier
	 * @param lc
	 * @param quantite
	 * @return
	 */
	public LigneCommande addProdPanierDirect(LigneCommande lc, int quantite);

	/**
	 * Permet de supprimer une quantit� au panier directement sur la ligne dans le panier
	 * @param lc
	 * @param quantite
	 * @return
	 */
	public LigneCommande supprProdPanierDirect(LigneCommande lc, int quantite);

	
	/**
	 * Permet de rechercher une ligne de commande dans le panier, � partir de l'id du produit
	 * @param listeLCPanier
	 * @param pr
	 * @return
	 */
	public LigneCommande searchLCPanierByIdProduit(List<LigneCommande> listeLCPanier, Produit pr);

}
