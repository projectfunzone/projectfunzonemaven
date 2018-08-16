package fr.adaming.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;

/**
 * Implementation l'interface ClientDao pour redéfinir les méthodes de Client
 * 
 * @author Adaming
 */
@Repository
public class ClientDaoImpl implements IClientDao {

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
	public List<Client> getAllClients() {

		// recuperer la session
		Session s = sf.getCurrentSession();

		// requete criteria
		Criteria cr = s.createCriteria(Client.class);

		return cr.list();
	}

	@Override
	public Client getClientById(Client cl) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		// retourner le client trouvé grâce à son id
		return (Client) s.get(Client.class, cl.getIdClient());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getClientByIdNomMail(Client cl) {

		// requete JPQL
		String req = "FROM Client cl WHERE cl.idClient=:pId OR cl.nomClient=:pNom OR cl.email=:pEmail";

		// recuperer la session
		Session s = sf.getCurrentSession();

		// Récupérer le query
		Query query = s.createQuery(req);

		// ajout des paramètres
		query.setParameter("pId", cl.getIdClient());
		query.setParameter("pNom", cl.getNomClient());
		query.setParameter("pEmail", cl.getEmail());

		// retourner la liste de résultat
		return query.list();
	}

	@Override
	public Client addClient(Client cl) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		// envoie l'élément dans le context
		s.persist(cl);

		return cl;
	}

	@Override
	public int deleteClient(Client cl) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		try {
			Client clDel = (Client) s.get(Client.class, cl.getIdClient());
			s.delete(clDel);
			;
			return 1;
		} catch (Exception exce) {
			exce.printStackTrace();

		}
		return 0;
	}

	@Override
	public int updateClient(Client cl) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		String req = "UPDATE Client cl SET cl.nomClient=:pNom, cl.adresse=:pAdresse, cl.email=:pEmail, cl.tel=:pTel WHERE cl.idClient=:pId";

		Query query = s.createQuery(req);

		query.setParameter("pNom", cl.getNomClient());
		query.setParameter("pAdresse", cl.getAdresse());
		query.setParameter("pEmail", cl.getEmail());
		query.setParameter("pTel", cl.getTel());
		query.setParameter("pId", cl.getIdClient());

		return query.executeUpdate();
	}

	@Override
	public int updateClientMdp(Client cl) {

		// recuperer la session
		Session s = sf.getCurrentSession();

		String req = "UPDATE Client cl SET cl.mdpClient=:pMdp WHERE cl.idClient=:pId";

		Query query = s.createQuery(req);

		query.setParameter("pMdp", cl.getMdpClient());
		query.setParameter("pId", cl.getIdClient());

		return query.executeUpdate();
	}
}
