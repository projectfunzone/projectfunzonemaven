package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.LigneCommande;

/**
 * Implementation de l'interface LigneCommandeDao pour redéfinir les méthodes de
 * LigneCommande
 * 
 * @author Thibault
 */
@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	// declaration des attributs
	@Autowired
	private SessionFactory sf;

	// getters et setters
	public SessionFactory getSf() {
		return sf;
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LigneCommande> getAllLigneCommandes() {

		// recuperer la session
		Session s = sf.getCurrentSession();

		String req = "FROM LigneCommande as lc";

		Query query = s.createQuery(req);

		return query.list();
	}

	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		s.persist(lc);

		return lc;
	}

	@Override
	public int updateLigneCommande(LigneCommande lc) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		String req = "UPDATE LigneCommande lc SET lc.quantite =:pQte, lc.prix=:pPrix WHERE lc.idLigneCommande =:pIdLC";

		Query query = s.createQuery(req);

		query.setParameter("pQte", lc.getQuantite());
		query.setParameter("pPrix", lc.getPrix());
		query.setParameter("pIdLC", lc.getIdLigneCommande());

		return query.executeUpdate();
	}

	@Override
	public int deleteLigneCommande(LigneCommande lc) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		String req = "DELETE FROM LigneCommande lc WHERE lc.idLigneCommande=:pIdLC";

		// recuperer la query
		Query query = s.createQuery(req);

		// assignation des params
		query.setParameter("pIdLC", lc.getIdLigneCommande());

		return query.executeUpdate();
	}

	@Override
	public LigneCommande getLigneCommandebyId(LigneCommande lc) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		String req = "SELECT lc FROM LigneCommande as lc WHERE lc.idLigneCommande=:pIdLC";

		Query query = s.createQuery(req);

		query.setParameter("pIdLC", lc.getIdLigneCommande());

		return (LigneCommande) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LigneCommande> getLigneCommandeByIdCommande(LigneCommande lc) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		String req = "SELECT lc FROM LigneCommande AS lc WHERE lc.commande.idCommande=:pIdCmd";

		Query query = s.createQuery(req);

		query.setParameter("pIdCmd", lc.getCommande().getIdCommande());

		return (List<LigneCommande>) query.uniqueResult();
	}

}
