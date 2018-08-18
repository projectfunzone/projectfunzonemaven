package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

/**
 * Interface CommandeService qui d�finir les m�thodes Commande
 * @author Adaming
 *
 */
public interface ICommandeService {
	
	/**
	 * Charger la liste des commandes enregistr�e dans la base de donn�e
	 * @return la liste des commandes pass�es
	 */
	public List<Commande> getAllCommandes();
	
	/**
	 * Rechercher une commande par son id
	 * @param cmd
	 * @return la commande
	 */
	public Commande getCommandeById(Commande cmd);
	
	
	/**
	 * Rechercher un client par son id ou par l'id du client associ�
	 * @param cmd
	 * @return liste des commandes par client
	 */
	public List<Commande> getCommandeByIdOrClient(Commande cmd,Client cl);
	
	
	/**
	 * Cr�er une commande
	 * @param cmd
	 * @return
	 */
	public Commande addCommande(Commande cmd,Client cl);
	
	/**
	 * Supprimer une commande de la base de donn�e
	 * @param cmd
	 * @param cl
	 * @return
	 */
	public int deleteCommande(Commande cmd,Client cl);
	
	
	
	/**
	 * Cr�er une commande � partir du panier.
	 * @param client, ligne de commande
	 * @return
	 */
	public int passerCommande(List<LigneCommande> listePanierCommande, Client cl);

}
