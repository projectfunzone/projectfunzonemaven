package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Client;

/**
 * Interface de la couche ClientDao qui définit les méthodes d'un Client
 * 
 * @author Adaming
 *
 */
public interface IClientDao {

	/**
	 * Recuperation de la liste de tous les clients
	 */
	public List<Client> getAllClients();

	/**
	 * Recherche un client par son id
	 */
	public Client getClientById(Client cl);

	/**
	 * Rechercher un client par son nom
	 */
	public List<Client> getClientByIdNomMail(Client cl);

	/**
	 * Créer un client dans la base de donnée
	 */
	public Client addClient(Client cl);

	/**
	 * Supprimer un client dans la base de donnée 
	 * @return retourne 1 si supprimé, retourne 0 si non supprimé
	 */
	public int deleteClient(Client cl);

	/**
	 * Modifier un compte client (toutes les infos sauf le mdp) dans
	 * la base de donnée 
	 * @return retourne 1 si modifié, retourne 0 si non modifié
	 */
	public int updateClient(Client cl);

	/**
	 * Modifier le mdp d'un client dans la base de donnée 
	 * @return retourne 1 si modifié, retourne 0 si non modifié
	 */
	public int updateClientMdp(Client cl);

}
