package fr.epsi.bo;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.joda.time.DateTime;

import fr.epsi.location.pojo.Exemplaire;
import fr.epsi.location.pojo.Video;
import fr.epsi.location.remote.ILocation;

public class FenetreExemplaire extends JFrame {
	
	private List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
	private Video video;
	private ILocation location = ServiceJNDI.getBeanFromContext();
	private JList listeExemplaires = new JList();
	DefaultListModel modelExemplaires = new DefaultListModel();
	
	private JTextField dateAchat = new JTextField();
	
	public FenetreExemplaire(Video video) {
		this.video = video;
		try {
			actualiserListe();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		initialiserFenetre();
	}

	private void initialiserFenetre(){

		JPanel panelListe = new JPanel();
		JPanel panelBoutons = new JPanel();
		

		JButton ajouter = new JButton("Ajouter");
	    ajouter.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
    		{
	    		ajouterExemplaire();
			}
    	});
	    JButton supprimer = new JButton("Supprimer");
	    supprimer.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
    		{
	    		supprimerExemplaire();
			}

    	});
	    JButton quitter = new JButton("Quitter");
	    quitter.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    		{dispose();}
	    	});
	   
		listeExemplaires.setPreferredSize(new Dimension(150, 350));

		dateAchat.setPreferredSize(new Dimension(150, 20));
		supprimer.setPreferredSize(new Dimension(150, 20));
		ajouter.setPreferredSize(new Dimension(150, 20));
		quitter.setPreferredSize(new Dimension(150, 20));

		JLabel labelDate = new JLabel("Date d'achat : ");
		labelDate.setPreferredSize(new Dimension(170,20));
		
		this.setTitle(video.getTitre() + " - Exemplaires");
        this.setSize(350, 400);
        this.setLocationRelativeTo(null);  
        this.setLayout(new GridLayout(1, 2));
        
        panelListe.add(listeExemplaires);

        panelBoutons.add(labelDate);
        panelBoutons.add(dateAchat);
        panelBoutons.add(supprimer);
        panelBoutons.add(ajouter);
        panelBoutons.add(quitter);
        
        this.getContentPane().add(panelListe);
        this.getContentPane().add(panelBoutons);
        
        this.setVisible(true);
	}
	private void actualiserListe() throws ParseException {
		List<Exemplaire> tousEx = location.getListeExemplaires();
		
		exemplaires.clear();
		listeExemplaires.removeAll();
		modelExemplaires.removeAllElements();
		listeExemplaires.setModel(modelExemplaires);

		for(Exemplaire e : tousEx) {
			if(video.getId() == e.getVideo().getId()){
				exemplaires.add(e);
				modelExemplaires.addElement(e.getId() + " - " + e.getDateAchat().toString("dd/MM/yyyy"));
			}
		}
	}
	
	private DateTime convertStringToDate(String sDatedenaissance) {
		if(sDatedenaissance.length() == 10) {
			int jour = Integer.valueOf(sDatedenaissance.substring(0, 2)).intValue();
			int mois = Integer.valueOf(sDatedenaissance.substring(3, 5)).intValue();
			int annee = Integer.valueOf(sDatedenaissance.substring(6, 10)).intValue();
	
			return new DateTime(annee, mois, jour, 0, 0, 0);
		}
		else
			return null;
	}

	private void ajouterExemplaire(){
		try {
			Exemplaire e = new Exemplaire(convertStringToDate(dateAchat.getText()), video);
			location.ajouterExemplaire(e);

			actualiserListe();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void supprimerExemplaire() {
		if(listeExemplaires.getSelectedIndex() == -1){
			JOptionPane.showMessageDialog(null, "Vous devez choisir un exemplaire","Attention",JOptionPane.ERROR_MESSAGE);
		}else{
			int idSelectionne =  Integer.parseInt(listeExemplaires.getSelectedValue().toString().split(" - ")[0]); // 0 : id / 1 : date
			Exemplaire exemplaire = location.getExemplaire(idSelectionne);
			location.supprimerExemplaire(exemplaire);

			try {
				actualiserListe();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
