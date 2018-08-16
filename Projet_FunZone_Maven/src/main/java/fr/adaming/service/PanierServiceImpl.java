package fr.adaming.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@Transactional
@Service("panService")
public class PanierServiceImpl implements IPanierService {

	@Autowired
	ICommandeService commandeService;

	@Autowired
	IClientService clientService;

	@Autowired
	ILigneCommandeService lignecommandeService;

	
	/**
	 * @return the commandeService
	 */
	public ICommandeService getCommandeService() {
		return commandeService;
	}

	/**
	 * @param commandeService the commandeService to set
	 */
	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	/**
	 * @return the clientService
	 */
	public IClientService getClientService() {
		return clientService;
	}

	/**
	 * @param clientService the clientService to set
	 */
	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	/**
	 * @return the lignecommandeService
	 */
	public ILigneCommandeService getLignecommandeService() {
		return lignecommandeService;
	}

	/**
	 * @param lignecommandeService the lignecommandeService to set
	 */
	public void setLignecommandeService(ILigneCommandeService lignecommandeService) {
		this.lignecommandeService = lignecommandeService;
	}

	@Override
	public int créerCommande(List<LigneCommande> listePanierCommande, Client cl) {

		// instanciation d'une commande
		Commande cmd = new Commande();

		// créer cette commande
		cmd = commandeService.addCommande(cmd, cl);

		// vérification de la bonne création de la commande
		if (cmd != null) {

			// on parcours la liste des commandes du panier, et pour chaque
			// ligne, elle créée une ligne de commande
			for (LigneCommande lc : listePanierCommande) {

				LigneCommande lcOut = lignecommandeService.addLigneCommande(lc, cmd);

				if (lcOut == null) {
					return 2;
				}

			}

			// récupération du client en fonction de son id
			Client clOut = clientService.getClientById(cl);

			// Création de la facture : on commence par la date, puis l'objet du
			// mail et le corps du mail
			// Ensuite on édite le titre du pdf et du corps du pdf
			Date dateFacture = new Date();
			String objetMail = "Votre Facture Funzone du " + dateFacture;
			String corpsMail = "Bonjour Monsieur/Madame " + cl.getNomClient() + ", ci-joint votre facture du "
					+ dateFacture + ".";

			String nomPDF = "Facture Funzone";
			String corpsPDF = "Bonjour, ci-joint, ci-joint votre facture du " + dateFacture
					+ ".\nVotre commande est composé de :\n";

			// ajout de l'ensemble des elements dans un mail
			this.email(clOut, objetMail, corpsMail, nomPDF, corpsPDF);

			return 1;
		}

		return 0;
	}
	
	/**
	 *  Le produit choisi par le client ainsi que sa quantité sont
	 * en parametre. On crée une ligne de commande et on vérifié que la quantité
	 * demandée est disponible 
	 * Si c’est le cas alors on ajoute à la ligne de commande. 
	 * On stocke le produit, la quantité et le prix dans la ligne de
	 * commande 
	 * @return En sortie on récupère lc.
	 */
	@Override
	public LigneCommande ajoutProdPanier(Produit pr, int q) {

		LigneCommande lc = new LigneCommande();
		

		if (q <= pr.getQuantite()) {
			
			lc.setProduit(pr);
			lc.setQuantite(q);
			lc.setPrix(pr.getPrix() * q);
			
			return lc;
		} else {
			
			return null;
		}

	}
	

	/**
	 * création de l'email : on définit l'adresse emetrice (+mdp) et on récupere les informations sur les serveurs
	 * @param cl
	 * @param objetMail
	 * @param corpsMail
	 * @param nomPDF
	 * @param corpsPDF
	 */
	public void email(Client cl, String objetMail, String corpsMail, String nomPDF, String corpsPDF) {
		final String username = "projectfunzone@gmail.com";
		final String password = "12root34";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// on récupère la session
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		ByteArrayOutputStream outputStream = null;

		//mise en forme des éléments de l'email
		try {
			// construct the text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(corpsMail);

			// now write the PDF content to the output stream
			outputStream = new ByteArrayOutputStream();
			writePdf(outputStream, corpsPDF);
			byte[] bytes = outputStream.toByteArray();

			// construct the pdf body part
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfBodyPart = new MimeBodyPart();
			pdfBodyPart.setDataHandler(new DataHandler(dataSource));
			pdfBodyPart.setFileName(nomPDF + ".pdf");

			// construct the mime multi part
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(textBodyPart);
			mimeMultipart.addBodyPart(pdfBodyPart);

			// create the sender/recipient addresses
			InternetAddress iaSender = new InternetAddress(username);
			InternetAddress iaRecipient = new InternetAddress(cl.getEmail());

			// construct the mime message
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setSender(iaSender);
			mimeMessage.setSubject(objetMail);
			mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
			mimeMessage.setContent(mimeMultipart);

			// send off the email
			Transport.send(mimeMessage);

			System.out.println("mail envoyé");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// clean off
			if (null != outputStream) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * Writes the content of a PDF file (using iText API) to the
	 * {@link OutputStream}.
	 * 
	 * @param outputStream
	 *            {@link OutputStream}.
	 * @throws Exception
	 */
	public void writePdf(OutputStream outputStream, String corpsPDF) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);

		document.open();

		document.addTitle("Facture FUN ZONE");
		document.addSubject("Votre facture FunZone");
		document.addKeywords("Commande, Funzone");
		document.addAuthor("FunZone");
		document.addCreator("FunZone");

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Chunk(corpsPDF));
		document.add(paragraph);

		document.close();
	}

}
