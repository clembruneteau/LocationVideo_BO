package fr.epsi.bo;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.epsi.location.pojo.Categorie;
import fr.epsi.location.pojo.Video;
import fr.epsi.location.remote.ILocation;


public class Fenetre extends JFrame {
    
	private List<Categorie> listeCategories;
	private List<Video> listeVideos;
	private ILocation location = ServiceJNDI.getBeanFromContext();

	private JPanel panelVideo = new JPanel();
	private JPanel panelCategorie = new JPanel();
    
	private JPanel panelBtVideo = new JPanel();
	private JPanel panelBtCategorie = new JPanel();
	private JPanel panelBtFermer = new JPanel();
    
	private JButton btnAjoutVideo = new JButton("Ajouter");
	private JButton btnEnleverVideo = new JButton("Supprimer");
	private JButton btnAjoutCategorie = new JButton("Ajouter");
	private JButton btnEnleverCategorie = new JButton("Supprimer");
	private JButton btnGererExemplaire = new JButton("Gérer Exemplaires");
	private JButton btnQuitter = new JButton("Quitter");
    
	private JList videos = new JList();
	private JList categories = new JList();

	private DefaultListModel modelCategories = new DefaultListModel();
	private DefaultListModel modelVideos = new DefaultListModel();

    private JLabel labelleVideo = new JLabel("Liste des vidéos : ");
    private JLabel labelleCategorie = new JLabel("Liste des catégories : ");

	
	
	public Fenetre(){
		
		this.setTitle("Client location vidéo");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        this.setLayout(null);
        
        btnQuitter.addActionListener(new ActionListener() {
	    	@Override public void actionPerformed(ActionEvent e) 
	    		{System.exit(0);}
	    	});
        
        btnAjoutVideo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new FenetreAjoutVideo(Fenetre.this);
			}
		});
        btnAjoutVideo.setPreferredSize(new Dimension(180, 50));
        btnEnleverVideo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(videos.getSelectedIndex() == -1 ){
					JOptionPane.showMessageDialog(null, "Choisissez la vidéo à supprimer","Attention",JOptionPane.ERROR_MESSAGE);
				}else{
					Video maVideo = location.getVideoParSonTitre(videos.getSelectedValue().toString());
					location.supprimerVideo(maVideo);
					Fenetre.this.actualiserListes();
				}
				
			}
		});
        btnEnleverVideo.setPreferredSize(new Dimension(180, 50));
        
		btnGererExemplaire.addActionListener(new ActionListener() {
	    	
			@Override 
	    	public void actionPerformed(ActionEvent e) 
    		{
				if(videos.getSelectedIndex() == -1 ){
					JOptionPane.showMessageDialog(null, "Choisissez la vidéo à gérer","Attention",JOptionPane.ERROR_MESSAGE);
				}else{
					new FenetreExemplaire(location.getVideoParSonTitre(videos.getSelectedValue().toString()));
				}
    		}
    	});
		btnGererExemplaire.setPreferredSize(new Dimension(180, 50));
        
        btnAjoutCategorie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new FenetreAjoutCategorie(Fenetre.this);
			}
		});
        btnAjoutCategorie.setPreferredSize(new Dimension(180, 50));
        btnEnleverCategorie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(categories.getSelectedIndex() == -1 ) {
					JOptionPane.showMessageDialog(null, "Choisissez la catégorie à supprimer","Attention",JOptionPane.ERROR_MESSAGE);
				}else{
					
					int option = JOptionPane.showConfirmDialog(null, "Supprimer la catégorie supprimera également toutes les vidéos associées.\nSouhaitez-vous les supprimer tout de même?","Attention",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (option == JOptionPane.OK_OPTION) {
						Categorie maCategorie = location.getCategorieParSonLibelle(categories.getSelectedValue().toString());
						List<Video> videosASupprimer = location.getListeVideosParCategorie(maCategorie.getId());
						
						for(Video v : videosASupprimer){
							location.supprimerVideo(v);
						}
						
						location.supprimerCategorie(maCategorie);
						Fenetre.this.actualiserListes();
					}
					
					
				}
			}
		});
        btnEnleverCategorie.setPreferredSize(new Dimension(180, 50));
        
        videos.setPreferredSize(new Dimension(180, 400));
        categories.setPreferredSize(new Dimension(180, 400));

        panelVideo.add(labelleVideo);
        panelVideo.add(videos);
        panelVideo.setBounds(20, 0, 180, 430);
        
        panelBtVideo.add(btnAjoutVideo);
        panelBtVideo.add(btnEnleverVideo);
        panelBtVideo.add(btnGererExemplaire);
        panelBtVideo.setBounds(210, 165, 180, 430);
        

        panelCategorie.add(labelleCategorie);
        panelCategorie.add(categories);
        panelCategorie.setBounds(400, 0, 180, 430);
        
        panelBtCategorie.add(btnAjoutCategorie);
        panelBtCategorie.add(btnEnleverCategorie);
        panelBtCategorie.setBounds(600, 165, 180, 430);
        
        panelBtFermer.add(btnQuitter);
        panelBtFermer.setBounds(220, 450, 90, 50);

        this.getContentPane().add(panelBtFermer);
        this.getContentPane().add(panelVideo);
        this.getContentPane().add(panelBtVideo);
        this.getContentPane().add(panelCategorie);
        this.getContentPane().add(panelBtCategorie);
        
        this.setVisible(true);
        actualiserListes();
	}
	
	public void actualiserListes(){		

		//location = ServiceJNDI.getBeanFromContext();
		
		categories.removeAll();
		modelCategories.removeAllElements();
		categories.setModel(modelCategories);
		listeCategories = (List<Categorie>)location.getListeCategories();
		Iterator i = listeCategories.iterator();
		
		while(i.hasNext()){
			Categorie c = (Categorie) i.next();
			modelCategories.addElement(c.getLibelle());
			i.remove();
		}
		
		videos.removeAll();
		modelVideos.removeAllElements();
		videos.setModel(modelVideos);
		
		listeVideos = location.getListeVideos();
		i = listeVideos.iterator();
		while(i.hasNext()){
			Video v = (Video) i.next();
			modelVideos.addElement(v.getTitre());
			i.remove();
		}
	}
	
}
