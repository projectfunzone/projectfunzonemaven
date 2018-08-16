package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Commande;

/**
 * Interface CommandeDao qui définir Commande
 * @author Adaming
 *
 */
public interface ICommandeDao {
	
	/**
	 * Charger toute la liste des commandes enregistrée dans la base de donnée
	 * @return Retourne une liste de commande
	 */
	public List<Commande> getAllCommandes();
	
	/**
	 * Rechercher une commande particulière par son id.
	 * @param cmd
	 * @return Retourne la commande recherchée
	 */
	public Commande getCommandeById(Commande cmd);
	
	/**
	 * Rechercher un client par son id ou par l'id du client associé
	 * @param cmd
	 * @return liste de commande par client
	 */
	public List<Commande> getCommandeByIdOrClient(Commande cmd);
	
	
	/**
	 * Créer une commande
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
