package fr.adaming.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Transactional
@Service("cmdService")
public class CommandeServiceImpl implements ICommandeService {

	@Autowired // injection depandance du collaborateur
	private ICommandeDao commandeDao;

	
	public ICommandeDao getCommandeDao() {
		return commandeDao;
	}

	public void setCommandeDao(ICommandeDao commandeDao) {
		this.commandeDao = commandeDao;
	}

	@Override
	public List<Commande> getAllCommandes() {

		return commandeDao.getAllCommandes();
	}

	@Override
	public Commande getCommandeById(Commande cmd) {

		return commandeDao.getCommandeById(cmd);
	}

	@Override
	public List<Commande> getCommandeByIdOrClient(Commande cmd, Client cl) {

		cmd.setCl(cl);

		return commandeDao.getCommandeByIdOrClient(cmd);
	}

	@Override
	public Commande addCommande(Commande cmd, Client cl) {
		// initialisé la date de la commande et la passer en paramètre de la
		// commande
		Date dateCrea = new Date();
		cmd.setDateCommande(dateCrea);

		cmd.setCl(cl);

		return commandeDao.addCommande(cmd);
	}

	@Override
	public int deleteCommande(Commande cmd, Client cl) {

		cmd.setCl(cl);

		return commandeDao.deleteCommande(cmd);
	}

}
