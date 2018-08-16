package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Service("catService")
@Transactional
public class CategorieServiceImpl implements ICategorieService {

	// injection dépendance de dao
	@Autowired
	private ICategorieDao categorieDao;

	public void setCategorieDao(ICategorieDao categorieDao) {
		this.categorieDao = categorieDao;
	}

	@Override
	public List<Categorie> getAllCategorie() {

		return categorieDao.getAllCategorie();
	}

	@Override
	public Categorie getCategorieByIdOrNom(Categorie categorie) {

		return categorieDao.getCategorieByIdOrNom(categorie);
	}

	@Override
	public Categorie addCategorie(Categorie categorie) {

		Categorie catOut = categorieDao.addCategorie(categorie);

		if (catOut.getIdCategorie() != 0) {
			return catOut;
		}
		return null;
	}

	@Override
	public int deleteCategorie(Categorie categorie) {
		
		return categorieDao.deleteCategorie(categorie);
	}

	@Override
	public int updateCategorie(Categorie categorie) {
		
		return categorieDao.updateCategorie(categorie);
	}

}
