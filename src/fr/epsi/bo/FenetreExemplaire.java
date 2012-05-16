package fr.epsi.bo;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import fr.epsi.location.pojo.Exemplaire;
import fr.epsi.location.pojo.Video;
import fr.epsi.location.remote.LocationBean;

public class FenetreExemplaire extends JFrame {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
	
	private List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
	private Video video;
	private Exemplaire exemplaireSelectionne = null;
	private LocationBean location = new LocationBean();
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
	    JButton modifier = new JButton("Modifier");
	    modifier.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
    		{
	    		modifierExemplaire();
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
	    
		listeExemplaires.addListSelectionListener(new ListSelectionListener(){
			@Override 
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()){
					JList liste = (JList) e.getSource();
					String selection = (String) liste.getSelectedValue();
					int idSelectionne =  Integer.parseInt(selection.split(" - ")[0]); // 0 : id / 1 : date
					exemplaireSelectionne = location.getExemplaire(idSelectionne);

					try {
						dateAchat.setText(dateFormat.format(exemplaireSelectionne.getDateAchat()));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		listeExemplaires.setPreferredSize(new Dimension(150, 350));

		dateAchat.setPreferredSize(new Dimension(150, 20));
		modifier.setPreferredSize(new Dimension(150, 20));
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
        panelBoutons.add(modifier);
        panelBoutons.add(supprimer);
        panelBoutons.add(ajouter);
        panelBoutons.add(quitter);
        
        this.getContentPane().add(panelListe);
        this.getContentPane().add(panelBoutons);
        
        this.setVisible(true);
	}
	private void actualiserListe() throws ParseException {
		List<Exemplaire>tousEx = location.getListeExemplaires();
		
		exemplaires.clear();
		listeExemplaires.removeAll();
		modelExemplaires.removeAllElements();
		listeExemplaires.setModel(modelExemplaires);

		for(Exemplaire e : tousEx){
			if(video.getId() == e.getVideo().getId()){
				exemplaires.add(e);
				modelExemplaires.addElement(e.getId() + " - " + dateFormat.format(e.getDateAchat()));
			}
		}
	}

	private void ajouterExemplaire(){
		String dateJour = dateFormat.format(new Date());
		try {
			Exemplaire e = new Exemplaire(dateFormat.parse(dateJour), video);
			location.ajouterExemplaire(e);

			actualiserListe();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void modifierExemplaire() {
		if(exemplaireSelectionne == null){
			JOptionPane.showMessageDialog(null, "Vous devez choisir un exemplaire","Attention",JOptionPane.ERROR_MESSAGE);
		}else{
			location.modifierExemplaire(exemplaireSelectionne);

			try {
				actualiserListe();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void supprimerExemplaire() {
		if(exemplaireSelectionne == null){
			JOptionPane.showMessageDialog(null, "Vous devez choisir un exemplaire","Attention",JOptionPane.ERROR_MESSAGE);
		}else{
			location.supprimerExemplaire(exemplaireSelectionne);

			try {
				actualiserListe();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
