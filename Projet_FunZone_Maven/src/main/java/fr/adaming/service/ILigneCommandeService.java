package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

/**
 * Interface definissant les methodes de LigneCommandeService
 * 
 * @author Adaming
 */
public interface ILigneCommandeService {

	/**
	 * Récupère l'ensemble des LigneCommande
	 */
	public List<LigneCommande> getAllLigneCommandes();

	/**
	 * Ajout d'une LigneCommande
	 */
	public LigneCommande addLigneCommande(LigneCommande lc, Commande cmd);

	/**
	 * Modifier une LigneCommande
	 */
	public int updateLigneCommande(LigneCommande lc);

	/**
	 * Supprimer une lignecommande
	 */
	public int deleteLigneCommande(LigneCommande lc);

	/**
	 * Recherche une LigneCommande en fonction de son id
	 */
	public LigneCommande getLigneCommandebyId(LigneCommande lc);

	/**
	 * Recherche une LigneCommande en fonction de de l'id d'une commande
	 * 
	 * @param lc
	 * @return
	 */
	public List<LigneCommande> getLigneCommandeByIdCommande(LigneCommande lc, Commande cmd);

}
