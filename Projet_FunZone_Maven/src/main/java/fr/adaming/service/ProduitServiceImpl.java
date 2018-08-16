package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Service("prService")
@Transactional
public class ProduitServiceImpl implements IProduitService {

	@Autowired
	private IProduitDao produitDao;

	public void setProduitDao(IProduitDao produitDao) {
		this.produitDao = produitDao;
	}

	@Override
	public List<Produit> getAllProduit() {

		return produitDao.getAllProduit();
	}

	@Override
	public List<Produit> searchProduitBy(Produit produit) {

		return produitDao.searchProduitBy(produit);
	}

	@Override
	public Produit searchProduitById(Produit produit) {
		return produitDao.searchProduitById(produit);
	}

	@Override
	public Produit addProduit(Produit pr, Categorie c) {
		// lier les objets java
		pr.setCategorie(c);

		return produitDao.addProduit(pr);
	}

	@Override
	public int updateProduit(Produit pr, Categorie c) {

		// lier les objets java
		pr.setCategorie(c);

		return produitDao.updateProduit(pr);
	}

	@Override
	public int deleteProduit(Produit pr) {
		// chercher l'étudiant avec son id
		Produit pDel = this.searchProduitById(pr);

		// verifier si l'étudiant existe et appartient au formateur
		if (pDel != null) {

			return produitDao.deleteProduit(pDel);

		} else {

			return 0;

		}
	}

}
