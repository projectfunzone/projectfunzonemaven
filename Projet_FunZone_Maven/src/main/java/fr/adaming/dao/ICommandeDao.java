package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Commande;

/**
 * Interface CommandeDao qui d�finir Commande
 * @author Adaming
 *
 */
public interface ICommandeDao {
	
	/**
	 * Charger toute la liste des commandes enregistr�e dans la base de donn�e
	 * @return Retourne une liste de commande
	 */
	public List<Commande> getAllCommandes();
	
	/**
	 * Rechercher une commande particuli�re par son id.
	 * @param cmd
	 * @return Retourne la commande recherch�e
	 */
	public Commande getCommandeById(Commande cmd);
	
	/**
	 * Rechercher un client par son id ou par l'id du client associ�
	 * @param cmd
	 * @return liste de commande par client
	 */
	public List<Commande> getCommandeByIdOrClient(Commande cmd);
	
	
	/**
	 * Cr�er une commande
	 * @param cmd
	 * @return une commande
	 */
	public Commande addCommande(Commande cmd);
	
	/**
	 * Supprimer une commande
	 * @param cmd
	 * @return
	 */
	public int deleteCommande (Commande cmd);
	
	
}
