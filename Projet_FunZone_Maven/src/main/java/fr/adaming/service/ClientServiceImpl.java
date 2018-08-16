package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;

/**
 * Classe qui implement l'interface ClientService pour redéfinir les méthodes
 * métier en lien avec Client
 * 
 * @author Adaming
 *
 */
@Transactional
@Service("clService")
public class ClientServiceImpl implements IClientService {

	@Autowired // injection depandance du collaborateur
	private IClientDao clientDao;

	@Override
	public List<Client> getAllClients() {
		return clientDao.getAllClients();
	}

	@Override
	public Client getClientById(Client cl) {
		return clientDao.getClientById(cl);
	}

	@Override
	public List<Client> getClientByIdNomMail(Client cl) {

		return clientDao.getClientByIdNomMail(cl);
	}

	@Override
	public Client addClient(Client cl) {
		return clientDao.addClient(cl);
	}

	@Override
	public int deleteClient(Client cl) {
		return clientDao.deleteClient(cl);
	}

	@Override
	public int updateClient(Client cl) {
		return clientDao.updateClient(cl);
	}

	@Override
	public int updateClientMdp(Client cl) {
		return clientDao.updateClientMdp(cl);
	}

	/**
	 * On récupère un client via son email pour ensuite comparer le mdp du
	 * client récupérer (du coup dans clOut) avec le mdp renseigné sur la vue
	 * (dans cl).
	 * 
	 * @return Retourne un int avec des valeurs différentes selon la situation.
	 *         0 si le client n'existe pas, 1 si le client existe et le mdp est
	 *         bon 2 si le mot de passe renseigné ne correspond pas au mdp dans
	 *         la base de donnée
	 */
	@Override
	public int connectionClient(Client cl) {
		
		// on récupère une liste avec normalement 1 client qui correspond au
		// mail unique
		List<Client> listeRecupMail = clientDao.getClientByIdNomMail(cl);
		
		// on verifie qu'il n'y a qu'un client avec cet adresse mail, car sinon,
		// cela veut dire qu'il y a 2 comptes clients avec la même adresse mail
		if (listeRecupMail.size() == 1) {
			
			// On récupère le client dans la liste dans un clOut pour ne pas
			// écraser le mdp rentré dans cl
			Client clOut = listeRecupMail.get(0);

			// On compare les 2 mots de passe, s'ils sont différents, c'est que
			// ce n'est pas le bon mdp rentré
			if (clOut.getMdpClient().contentEquals(cl.getMdpClient())) {
				return 1;
			}

			return 2;

		}
		return 0;
	}

}
