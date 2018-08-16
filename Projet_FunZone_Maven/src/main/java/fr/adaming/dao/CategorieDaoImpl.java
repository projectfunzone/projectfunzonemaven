package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;

@Repository
public class CategorieDaoImpl implements ICategorieDao {

	@Autowired
	private SessionFactory sf;
	
	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Categorie> getAllCategorie() {
		Session s=sf.getCurrentSession();
		
		String req="FROM Categorie cat";
		
		Query query=s.createQuery(req);
		
		
		return query.list();
	}


	@Override
	public Categorie getCategorieByIdOrNom(Categorie categorie) {
		Session s=sf.getCurrentSession();
		
		String req="FROM Categorie cat WHERE cat.idCategorie=:pIdCat OR cat.nomCategorie=:pNomCat";
		
		Query query=s.createQuery(req);
		
		query.setParameter("pIdCat", categorie.getIdCategorie());
		query.setParameter("pNomCat", categorie.getNomCategorie());
		
		return (Categorie) query.uniqueResult();
	}


}
