package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

/**
 * Implementation de LignCommandeService pour redéfinir les méthode en lien avec
 * LigneCommmande
 * 
 * @author Adaming
 *
 */
@Transactional
@Service("lcService")
public class LigneCommandeServiceImpl implements ILigneCommandeService {

	@Autowired
	private ILigneCommandeDao ligneCommandeDao;

	public void setLigneCommandeDao(ILigneCommandeDao ligneCommandeDao) {
		this.ligneCommandeDao = ligneCommandeDao;
	}

	@Override
	public List<LigneCommande> getAllLigneCommandes() {
		return ligneCommandeDao.getAllLigneCommandes();
	}

	@Override
	public LigneCommande addLigneCommande(LigneCommande lc, Commande cmd) {
		// lier les objets java
		lc.setCommande(cmd);

		return ligneCommandeDao.addLigneCommande(lc);
	}

	@Override
	public int updateLigneCommande(LigneCommande lc) {
		return ligneCommandeDao.updateLigneCommande(lc);
	}

	@Override
	public int deleteLigneCommande(LigneCommande lc) {
		return ligneCommandeDao.deleteLigneCommande(lc);
	}

	@Override
	public LigneCommande getLigneCommandebyId(LigneCommande lc) {
		return ligneCommandeDao.getLigneCommandebyId(lc);
	}

	@Override
	public List<LigneCommande> getLigneCommandeByIdCommande(LigneCommande lc, Commande cmd) {
		// lier les objets java
		lc.setCommande(cmd);
		return ligneCommandeDao.getLigneCommandeByIdCommande(lc);
	}

}
