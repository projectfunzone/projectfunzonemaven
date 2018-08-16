package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
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
		// TODO Auto-generated method stub
		return produitDao.searchProduitById(produit);
	}



}
