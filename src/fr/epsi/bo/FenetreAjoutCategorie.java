package fr.epsi.bo;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.epsi.location.pojo.Categorie;
import fr.epsi.location.remote.LocationBean;

public class FenetreAjoutCategorie extends JFrame {
    
	private LocationBean location = new LocationBean();
	private Categorie categorie = new Categorie();
	private int id = -1;
	private Fenetre fenetrePrincipale;
	
	private JTextField libelle = new JTextField();

	public FenetreAjoutCategorie(Fenetre f){
		this.fenetrePrincipale = f;
        initialiserFenetre();
	}

	public FenetreAjoutCategorie(Fenetre f, String libelle) {
		this.fenetrePrincipale = f;
		List <Categorie> categories = location.getListeCategories();
		for(Categorie c : categories){
			if(libelle == c.getLibelle()){
				categorie = c;
			}
		}
		initialiserFenetre();
	}
	
	private void initialiserFenetre(){

		JPanel panelLibelle = new JPanel();
		JPanel panelBoutons = new JPanel();
		

	    JButton Enregistrer = new JButton("Enregistrer");
	    Enregistrer.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
    		{
	    		enregistrerCategorie();
			}
    	});
	    JButton annuler = new JButton("Annuler");
	    annuler.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    		{dispose();}
	    	});

		String libelleFilm = "";
		String titreFenetre = "Ajouter une vid�o";
		
		if(id != -1){
			id = categorie.getId();
			libelleFilm = categorie.getLibelle();
			titreFenetre = "Modifier la vid�o - " + libelleFilm;
		}

		JLabel labelTitre = new JLabel("Libell� : ");
		libelle.setText(libelleFilm);
		libelle.setPreferredSize(new Dimension(170,20));
		
		
		this.setTitle(titreFenetre);
        this.setSize(350, 130);
        this.setLocationRelativeTo(null);  
        this.setLayout(null);
        
        panelLibelle.add(labelTitre);
        panelLibelle.add(libelle);
        panelLibelle.setBounds(0, 0, 340, 35);

        panelBoutons.add(Enregistrer);
        panelBoutons.add(annuler);
        panelBoutons.setBounds(0, 40, 340, 90);
        
        this.getContentPane().add(panelLibelle);
        this.getContentPane().add(panelBoutons);
        
        
        this.setVisible(true);
	}
	
	public void enregistrerCategorie(){
		categorie.setLibelle(libelle.getText());
		if(id != -1){
			categorie.setId(id);
			location.modifierCategorie(categorie);
		}else{
			location.ajouterCategorie(categorie);
		}
		fenetrePrincipale.actualiserListes();
		dispose();
	}
}
