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
		Session s = sf.getCurrentSession();

		String req = "FROM Categorie cat";

		Query query = s.createQuery(req);

		return query.list();
	}

	@Override
	public Categorie getCategorieByIdOrNom(Categorie categorie) {
		Session s = sf.getCurrentSession();

		String req = "FROM Categorie cat WHERE cat.idCategorie=:pIdCat OR cat.nomCategorie=:pNomCat";

		Query query = s.createQuery(req);

		query.setParameter("pIdCat", categorie.getIdCategorie());
		query.setParameter("pNomCat", categorie.getNomCategorie());

		return (Categorie) query.uniqueResult();
	}

	@Override
	public Categorie addCategorie(Categorie categorie) {

		Session s = sf.getCurrentSession();

		s.save(categorie);

		return categorie;
	}

	@Override
	public int deleteCategorie(Categorie categorie) {

		Session s = sf.getCurrentSession();

		String req = "DELETE FROM Categorie cat WHERE cat.idCategorie=:pId";

		Query query = s.createQuery(req);

		query.setParameter("pId", categorie.getIdCategorie());

		return query.executeUpdate();
	}

	@Override
	public int updateCategorie(Categorie categorie) {
		Session s = sf.getCurrentSession();

		String req = "UPDATE Categorie AS cat SET cat.nomCategorie =:pNomCat, cat.description =:pDescription, cat.photo=:pPhotoC WHERE cat.idCategorie =:pIdC";
		
		Query query=s.createQuery(req);
		
		query.setParameter("pNomCat", categorie.getNomCategorie());
		query.setParameter("pDescription", categorie.getDescription());
		query.setParameter("pPhotoC", categorie.getPhoto());
		query.setParameter("pIdC", categorie.getIdCategorie());
		
		return query.executeUpdate();
	}

}
