package fr.epsi.bo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.epsi.location.pojo.Categorie;
import fr.epsi.location.pojo.Video;
import fr.epsi.location.remote.LocationBean;

public class FenetreAjoutVideo extends JFrame {
    
	private LocationBean location = new LocationBean();
	private Video video = null;
	private int id = -1;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
	private Fenetre fenetrePrincipale;
	
	private JTextField titre = new JTextField();
	private JTextField duree = new JTextField();
	private JTextField date = new JTextField();
	private JComboBox categories = new JComboBox();
	private JTextArea synopsis = new JTextArea();

	public FenetreAjoutVideo(Fenetre f){
		this.fenetrePrincipale = f;
        initialiserFenetre();
	}

	public FenetreAjoutVideo(Fenetre f, String titre) {
		this.fenetrePrincipale = f;
	    List<Video> videos;
		videos = location.getListeVideos();
		
		for(Video v : videos){
			if(titre == v.getTitre()){
				video = v;
			}
		}
		
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
		JButton exemplaire = new JButton("Gérer les exemplaires");
		exemplaire.setPreferredSize(new Dimension(450, 40));
		exemplaire.addActionListener(new ActionListener() {
	    	@Override public void actionPerformed(ActionEvent e) 
	    		{new FenetreExemplaire(video);}
	    	});
		if(video == null){
			exemplaire.setEnabled(false);
		}

		String titreFilm = "";
		int dureeFilm = 0;
		String dateSortie = "";
		String synopsisFilm = "";
		String titreFenetre = "Ajouter une vidéo";
		
		if(video != null){
			id = video.getId();
			titreFilm = video.getTitre();
			dureeFilm = video.getDuree();
			dateSortie = dateFormat.format(video.getDateSortie());
			synopsisFilm = video.getSynopsis();
			titreFenetre = "Modifier la vidéo - " + titreFilm;
		}

		JLabel labelTitre = new JLabel("Titre : ");
		titre.setText(titreFilm);
		titre.setPreferredSize(new Dimension(170,20));
		
		JLabel labelDuree = new JLabel("Durée : ");
		duree.setText(String.valueOf(dureeFilm));
		duree.setPreferredSize(new Dimension(70,20));
		
		JLabel labelDate = new JLabel("Date : ");
		date.setText(dateSortie.toString());
		date.setPreferredSize(new Dimension(185,20));
		
		JLabel labelCategories = new JLabel("Catégories : ");
		categories.setPreferredSize(new Dimension(150,20));
		initialiserCategories(categories);
		

		JLabel labelSynopsis = new JLabel("Synopsis : ");
		synopsis.setText(synopsisFilm);
		synopsis.setPreferredSize(new Dimension(350,140));
		synopsis.setBackground(Color.white);
		synopsis.setVisible(true);
		
		
		this.setTitle(titreFenetre);
        this.setSize(500, 375);
        this.setLocationRelativeTo(null);  
        this.setLayout(null);
        
        panelChamps.add(labelTitre);
        panelChamps.add(titre);
        panelChamps.add(labelDate);
        panelChamps.add(date);
        panelChamps.add(labelDuree);
        panelChamps.add(duree);
        panelChamps.add(labelCategories);
        panelChamps.add(categories);
        panelChamps.setBounds(0, 0, 480, 80);
        
        panelSynopsis.add(labelSynopsis);
        panelSynopsis.add(synopsis);
        panelSynopsis.setBounds(0, 80, 480, 150);
        
        panelBoutons.add(exemplaire);
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
	
	public void enregistrerVideo() throws ParseException{
		if(categories.getSelectedIndex() == -1){
			JOptionPane.showMessageDialog(null, "Choisissez une catégorie","Attention",JOptionPane.ERROR_MESSAGE);
		}else{
			video.setSynopsis(synopsis.getText());
			video.setTitre(titre.getText());
			
			Date dateSortie = dateFormat.parse(date.getText());
			
			video = new Video(titre.getText(), Integer.parseInt(duree.getText()), dateSortie, synopsis.getText(), new Categorie(categories.getSelectedItem().toString()));
	
			if(id != -1){
				video.setId(id);
				location.modifierVideo(video);
			}else{
				location.ajouterVideo(video);
			}
			fenetrePrincipale.actualiserListes();
			dispose();
		}
	}
}
