package fr.adaming.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@Transactional
@Service("cmdService")
public class CommandeServiceImpl implements ICommandeService {

	@Autowired // injection depandance du collaborateur
	private ICommandeDao commandeDao;

	// Setter pour l'injection d�pendance
	public void setCommandeDao(ICommandeDao commandeDao) {
		this.commandeDao = commandeDao;
	}

	@Autowired // injection d�pendance du collaborateur
	private IEmailService emailService;

	// setter pour l'injection d�pendance
	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}

	@Autowired // injection d�pendance du collaborateur
	private ILigneCommandeService ligneCommandeService;

	// Setter pour l'injection d�pendance
	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
	}

	@Autowired // injection d�pendance du collaborateur
	IClientService clientService;

	// Setter pour l'injection d�pendance
	public IClientService getClientService() {
		return clientService;
	}

	@Autowired // injection d�pendance du collaborateur
	IProduitService produitService;

	// Setter pour l'injection d�pendance
	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	@Override
	public List<Commande> getAllCommandes() {

		return commandeDao.getAllCommandes();
	}

	@Override
	public Commande getCommandeById(Commande cmd) {

		return commandeDao.getCommandeById(cmd);
	}

	@Override
	public List<Commande> getCommandeByIdOrClient(Commande cmd, Client cl) {

		cmd.setCl(cl);

		return commandeDao.getCommandeByIdOrClient(cmd);
	}

	@Override
	public Commande addCommande(Commande cmd, Client cl) {
		// initialis� la date de la commande et la passer en param�tre de la
		// commande
		Date dateCrea = new Date();
		cmd.setDateCommande(dateCrea);

		cmd.setCl(cl);

		return commandeDao.addCommande(cmd);
	}

	@Override
	public int deleteCommande(Commande cmd, Client cl) {

		cmd.setCl(cl);

		return commandeDao.deleteCommande(cmd);
	}

	@Override
	public int passerCommande(List<LigneCommande> listePanierCommande, Client cl) {

		// instanciation d'une commande
		Commande cmd = new Commande();

		// cr�er cette commande
		cmd = this.addCommande(cmd, cl);

		// v�rification de la bonne cr�ation de la commande
		if (cmd != null) {

			// on parcours la liste des commandes du panier, et pour chaque
			// ligne, elle cr��e une ligne de commande
			for (LigneCommande lc : listePanierCommande) {

				LigneCommande lcOut = ligneCommandeService.addLigneCommande(lc, cmd);

				if (lcOut != null) {
					// pour chaque ligne de commande, on r�cup�re le produit
					// associ�
					Produit prOut = produitService.searchProduitById(lc.getProduit());

					if (prOut != null) {
						// on modifie le produit dans la DB pour soustraire la
						// quantit� du produit command�
						prOut.setQuantite(prOut.getQuantite() - lc.getQuantite());

						int verif = produitService.updateProduit(prOut, prOut.getCategorie());
						
						if (verif == 0) {
							return 3;
						}
					}

				} else {
					return 2;
				}

			}

			// r�cup�ration du client en fonction de son id
			Client clOut = clientService.getClientById(cl);

			// Cr�ation de la facture : on commence par la date, puis l'objet du
			// mail et le corps du mail
			// Ensuite on �dite le titre du pdf et du corps du pdf
			Date dateFacture = new Date();
			String objetMail = "Votre Facture Funzone du " + dateFacture;
			String corpsMail = "Bonjour Monsieur/Madame " + cl.getNomClient() + ", ci-joint votre facture du "
					+ dateFacture + ".";

			String nomPDF = "Facture Funzone";
			String corpsPDF = "Bonjour, ci-joint, ci-joint votre facture du " + dateFacture
					+ ".\nVotre commande est compos� de :\n";

			// ajout de l'ensemble des elements dans un mail
			emailService.email(clOut, objetMail, corpsMail, nomPDF, corpsPDF);

			return 1;
		}

		return 0;
	}

}
