package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Admin;


/**
 * Implementation l'interface AdminDao pour définir les méthodes d'Admin
 * @author Adaming
 *
 */
@Repository
public class AdminDaoImpl implements IAdminDao {

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

	/**
	 * Methode de vérification de l'existence de l'admin dans la base de donnée en fonction de ses identifiants : mail et mdp
	 */
	@Override
	public Admin connectionAdmin(Admin admin) {

		//redaction de la requete
		String req = "SELECT ad FROM Admin ad WHERE ad.idAdmin=:pId AND ad.mdpAdmin=:pMdp";

		// recuperer la session
		Session s = sf.getCurrentSession();

		//creer la requete
		Query query = s.createQuery(req);

		query.setParameter("pId", admin.getIdAdmin());
		query.setParameter("pMdp", admin.getMdpAdmin());

		//envoyer la requete et recuperer le resultat à retourner
		return (Admin) query.uniqueResult();
	}

}
