package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;

/**
 * Interface de la couche ClientService qui définit les méthodes métiers de Client
 * 
 * @author Adaming
 *
 */

public interface IClientService {

	/**
	 * Génére la liste de tous les clients enregistrés
	 * @return une list de Client
	 */
	public List<Client> getAllClients();

	/**
	 * Recherche un client par son id. 
	 * @return un Client
	 */
	public Client getClientById(Client cl);

	/**
	 * Recherche un client par son nom
	 * @return une liste de Client
	 */
	public List<Client> getClientByIdNomMail(Client cl);

	/**
	 * Ajouter un client
	 * @return 
	 */
	public Client addClient(Client cl);
	
	/**
	 * Supprimer un client
	 * @return 
	 */
	public int deleteClient(Client cl);

	/**
	 * Modifier un compte client dans la base de donnée
	 * @return retourne 1 si modifié, retourne 0 si non modifié
	 */
	public int updateClient(Client cl);
	
	
	/**
	 * Modifier le mdp d'un client  dans la base de donnée
	 * @return retourne 1 si modifié, retourne 0 si non modifié
	 */
	public int updateClientMdp(Client cl);
}
