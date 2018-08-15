package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAdminDao;
import fr.adaming.model.Admin;

/**
 * Classe qui implement l'interface IAdminService pour redéfinir les méthodes
 * métier en lien avec Admin
 * 
 * @author Adaming
 *
 */
@Service("adService")
@Transactional
public class AdminServiceImpl implements IAdminService {

	// transformation de l'association UML en JAVA
	@Autowired
	private IAdminDao adDao;

	
	public void setAdDao(IAdminDao adDao) {
		this.adDao = adDao;
	}


	@Override
	public Admin connectionAdmin(Admin admin) {

		return adDao.connectionAdmin(admin);
	}

}
