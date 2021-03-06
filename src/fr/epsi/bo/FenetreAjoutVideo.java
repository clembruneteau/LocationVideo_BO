package fr.epsi.bo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.joda.time.DateTime;

import fr.epsi.location.pojo.Categorie;
import fr.epsi.location.pojo.Video;
import fr.epsi.location.remote.ILocation;
import fr.epsi.location.remote.LocationBean;

public class FenetreAjoutVideo extends JFrame {
    
	private ILocation location = ServiceJNDI.getBeanFromContext();
	private Video video = null;
	private int id = -1;
	private Fenetre fenetrePrincipale;
	
	private JTextField jTextField_titre = new JTextField();
	private JTextField jTextField_duree = new JTextField();
	private JTextField jTextField_date = new JTextField();
	private JComboBox jComboBox_categories = new JComboBox();
	private JTextArea jTextArea_synopsis = new JTextArea();

	public FenetreAjoutVideo(Fenetre f){
		this.fenetrePrincipale = f;
        initialiserFenetre();
	}

	public FenetreAjoutVideo(Fenetre f, String titre) {
		this.fenetrePrincipale = f;
	    
		video = location.getVideoParSonTitre (titre); 
		
		initialiserFenetre();
	}
	
	private void initialiserFenetre(){

		JPanel panelChamps = new JPanel();
		JPanel panelSynopsis = new JPanel();
		JPanel panelBoutons = new JPanel();
		
	    JButton Enregistrer = new JButton("Enregistrer");
	    Enregistrer.addActionListener(new ActionListener() {
	    	@Override public void actionPerformed(ActionEvent e) 
    		{try {
				enregistrerVideo();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}}
    	});
	    JButton annuler = new JButton("Annuler");
	    annuler.addActionListener(new ActionListener() {
	    	@Override public void actionPerformed(ActionEvent e) 
	    		{dispose();}
	    	});
//		JButton exemplaire = new JButton("Gérer les exemplaires");
//		exemplaire.setPreferredSize(new Dimension(450, 40));
//		exemplaire.addActionListener(new ActionListener() {
//	    	@Override public void actionPerformed(ActionEvent e) 
//	    		{new FenetreExemplaire(video);}
//	    	});

		String titreFilm = "";
		int dureeFilm = 0;
		DateTime dateSortie = new DateTime();
		String synopsisFilm = "";
		String titreFenetre = "Ajouter une vidéo";
		
		if(video != null){
			id = video.getId();
			titreFilm = video.getTitre();
			dureeFilm = video.getDuree();
			dateSortie = video.getDateSortie();
			synopsisFilm = video.getSynopsis();
			titreFenetre = "Modifier la vidéo - " + titreFilm;
		}

		JLabel labelTitre = new JLabel("Titre : ");
		jTextField_titre.setText(titreFilm);
		jTextField_titre.setPreferredSize(new Dimension(170,20));
		
		JLabel labelDuree = new JLabel("Durée : ");
		jTextField_duree.setText(String.valueOf(dureeFilm));
		jTextField_duree.setPreferredSize(new Dimension(70,20));
		
		JLabel labelDate = new JLabel("Date : ");
		jTextField_date.setText(dateSortie.toString("dd/MM/yyyy"));
		jTextField_date.setPreferredSize(new Dimension(185,20));
		
		JLabel labelCategories = new JLabel("Catégories : ");
		jComboBox_categories.setPreferredSize(new Dimension(150,20));
		initialiserCategories(jComboBox_categories);
		

		JLabel labelSynopsis = new JLabel("Synopsis : ");
		jTextArea_synopsis.setText(synopsisFilm);
		jTextArea_synopsis.setPreferredSize(new Dimension(350,140));
		jTextArea_synopsis.setBackground(Color.white);
		jTextArea_synopsis.setVisible(true);
		
		
		this.setTitle(titreFenetre);
        this.setSize(500, 375);
        this.setLocationRelativeTo(null);  
        this.setLayout(null);
        
        panelChamps.add(labelTitre);
        panelChamps.add(jTextField_titre);
        panelChamps.add(labelDate);
        panelChamps.add(jTextField_date);
        panelChamps.add(labelDuree);
        panelChamps.add(jTextField_duree);
        panelChamps.add(labelCategories);
        panelChamps.add(jComboBox_categories);
        panelChamps.setBounds(0, 0, 480, 80);
        
        panelSynopsis.add(labelSynopsis);
        panelSynopsis.add(jTextArea_synopsis);
        panelSynopsis.setBounds(0, 80, 480, 150);
        
//        panelBoutons.add(exemplaire);
        panelBoutons.add(Enregistrer);
        panelBoutons.add(annuler);
        panelBoutons.setBounds(0, 240, 480, 100);
        
        this.getContentPane().add(panelChamps);
        this.getContentPane().add(panelSynopsis);
        this.getContentPane().add(panelBoutons);
        
        
        this.setVisible(true);
	}
	
	private void initialiserCategories(JComboBox categories){
		List<Categorie> categ = location.getListeCategories();
		
		for(Categorie c : categ){
			categories.addItem(c.getLibelle());
		}
		if(video != null)
			categories.setSelectedItem(video.getCategorie().getLibelle());
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
	
	public void enregistrerVideo() throws ParseException {
		if(jComboBox_categories.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Choisissez une catégorie","Attention",JOptionPane.ERROR_MESSAGE);
		}else{
			if(id != -1) {
				if (video.getDuree() != Integer.parseInt(jTextField_duree.getText()))
					video.setDuree(Integer.parseInt(jTextField_duree.getText()));
				if (video.getCategorie().getLibelle() != jComboBox_categories.getSelectedItem())
					video.setCategorie(location.getCategorieParSonLibelle(jComboBox_categories.getSelectedItem().toString()));
				if (video.getDateSortie().toString("dd/MM/yyyy") != jTextField_date.getText())
					video.setDateSortie(convertStringToDate(jTextField_date.getText()));
				if (video.getSynopsis() != jTextArea_synopsis.getText())
					video.setSynopsis(jTextArea_synopsis.getText());
				if (video.getTitre() != jTextField_titre.getText())
					video.setTitre(jTextField_titre.getText());

				location.modifierVideo(video);
			}else {
				if (jTextArea_synopsis.getText() != "" && jTextField_date.getText() != "" && jTextField_duree.getText() != "" && jTextField_titre.getText() != "") {
					video = new Video(jTextField_titre.getText(), Integer.parseInt(jTextField_duree.getText()), convertStringToDate(jTextField_date.getText()), jTextArea_synopsis.getText(), location.getCategorieParSonLibelle(jComboBox_categories.getSelectedItem().toString()));
					location.ajouterVideo(video);	
				}
			}
			fenetrePrincipale.actualiserListes();
			dispose();
		}
	}
}
