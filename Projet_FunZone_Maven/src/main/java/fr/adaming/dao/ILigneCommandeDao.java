package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.LigneCommande;

/**
 *  Interface Dao d�finisant les m�thodes de LigneCommande
 *  @author Adaming
 */
public interface ILigneCommandeDao {
	
	/**
	 * Recuperer la liste des commandes effectu�es
	 */
	public List<LigneCommande> getAllLigneCommandes();
	
	/**
	 * Ajouter une ligne de commande
	 */
	public LigneCommande addLigneCommande(LigneCommande lc);
	
	/**
	 * Modifier une ligne de commande
	 */
	public int updateLigneCommande(LigneCommande lc);
	
	/**
	 * Supprimer une ligne de commande
	 */
	public int deleteLigneCommande(LigneCommande lc);
	
	/**
	 * Rechercher une ligne de commande
	 */
	public LigneCommande getLigneCommandebyId (LigneCommande lc);
	
	/**
	 * Rechercher une ligne de commande
	 */
	public List<LigneCommande> getLigneCommandeByIdCommande(LigneCommande lc);

}
