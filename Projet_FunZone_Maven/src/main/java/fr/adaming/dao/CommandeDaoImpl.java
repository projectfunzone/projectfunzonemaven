package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Commande;

@Repository
public class CommandeDaoImpl implements ICommandeDao {

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
	public List<Commande> getAllCommandes() {

		// recuperer la session
		Session s = sf.getCurrentSession();
		
		String req = "FROM Commande AS cmd";

		Query query = s.createQuery(req);

		return query.list();
	}

	@Override
	public Commande getCommandeById(Commande cmd) {
		
		// recuperer la session
		Session s = sf.getCurrentSession();
		
		return (Commande) s.get(Commande.class, cmd.getIdCommande());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commande> getCommandeByIdOrClient(Commande cmd) {
		
		// recuperer la session
		Session s = sf.getCurrentSession();

		String req = "FROM Commande AS cmd WHERE cmd.idCommande=:pIdCmd OR cmd.cl.idClient=:pIdCl";

		Query query = s.createQuery(req);

		query.setParameter("pIdCmd", cmd.getIdCommande());
		query.setParameter("pIdCl", cmd.getCl().getIdClient());

		return query.list();
	}

	@Override
	public Commande addCommande(Commande cmd) {
		
		// recuperer la session
		Session s = sf.getCurrentSession();

		s.persist(cmd);

		return cmd;
	}

	@Override
	public int deleteCommande(Commande cmd) {
		
		// recuperer la session
		Session s = sf.getCurrentSession();

		try {
			Commande cmdOut = (Commande) s.get(Commande.class, cmd.getIdCommande());

			s.delete(cmdOut);

			return 1;

		} catch (Exception e) {
			e.printStackTrace();

		}

		return 0;
	}

}
