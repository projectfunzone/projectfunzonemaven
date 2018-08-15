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
 * @author Adaming
 *
 */
@Transactional
@Service("clService")
public class ClientServiceImpl implements IClientService{

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

}
