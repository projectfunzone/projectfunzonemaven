package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao {

	@Autowired
	private SessionFactory sf;

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produit> getAllProduit() {

		Session s = sf.getCurrentSession();

		String req = "FROM Produit";

		Query query = s.createQuery(req);

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produit> searchProduitBy(Produit produit) {
		Session s = sf.getCurrentSession();

		String req = "FROM Produit pr WHERE pr.idProduit=:pId OR pr.designation=:pDesignation OR pr.categorie.idCategorie=:pIdCategorie";

		Query query = s.createQuery(req);

		query.setParameter("pId", produit.getIdProduit());
		query.setParameter("pDesignation", produit.getDesignation());
		query.setParameter("pIdCategorie", produit.getCategorie().getIdCategorie());

		return query.list();
	}

	@Override
	public Produit searchProduitById(Produit produit) {
		Session s = sf.getCurrentSession();

		String req = "FROM Produit pr WHERE pr.idProduit=:pId";

		Query query = s.createQuery(req);

		query.setParameter("pId", produit.getIdProduit());
		
		return (Produit) query.uniqueResult();
	}



}
