package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;

/**
 * Classe qui implement l'interface ClientService pour red�finir les m�thodes
 * m�tier en lien avec Client
 * 
 * @author Adaming
 *
 */
@Transactional
@Service("clService")
public class ClientServiceImpl implements IClientService {

	@Autowired // injection depandance du collaborateur
	private IClientDao clDao;

	@Override
	public List<Client> getAllClients() {
		return clDao.getAllClients();
	}

	@Override
	public Client getClientById(Client cl) {
		return clDao.getClientById(cl);
	}

	@Override
	public List<Client> getClientByIdNomMail(Client cl) {

		return clDao.getClientByIdNomMail(cl);
	}

	@Override
	public Client addClient(Client cl) {
		return clDao.addClient(cl);
	}

	@Override
	public int deleteClient(Client cl) {
		return clDao.deleteClient(cl);
	}

	@Override
	public int updateClient(Client cl) {
		return clDao.updateClient(cl);
	}

	@Override
	public int updateClientMdp(Client cl) {
		return clDao.updateClientMdp(cl);
	}

	/**
	 * On r�cup�re un client via son email pour ensuite comparer le mdp du
	 * client r�cup�rer (du coup dans clOut) avec le mdp renseign� sur la vue
	 * (dans cl).
	 * 
	 * @return Retourne un int avec des valeurs diff�rentes selon la situation.
	 *         0 si le client n'existe pas, 1 si le client existe et le mdp est
	 *         bon 2 si le mot de passe renseign� ne correspond pas au mdp dans
	 *         la base de donn�e
	 */
	@Override
	public int connectionClient(Client cl) {
		
		// on r�cup�re une liste avec normalement 1 client qui correspond au
		// mail unique
		List<Client> listeRecupMail = clDao.getClientByIdNomMail(cl);
		
		// on verifie qu'il n'y a qu'un client avec cet adresse mail, car sinon,
		// cela veut dire qu'il y a 2 comptes clients avec la m�me adresse mail
		if (listeRecupMail.size() == 1) {
			
			// On r�cup�re le client dans la liste dans un clOut pour ne pas
			// �craser le mdp rentr� dans cl
			Client clOut = listeRecupMail.get(0);

			// On compare les 2 mots de passe, s'ils sont diff�rents, c'est que
			// ce n'est pas le bon mdp rentr�
			if (clOut.getMdpClient().contentEquals(cl.getMdpClient())) {
				return 1;
			}

			return 2;

		}
		return 0;
	}

}
