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
import javax.swing.JPanel;

import fr.epsi.location.pojo.Categorie;
import fr.epsi.location.pojo.Video;
import fr.epsi.location.remote.LocationBean;


public class Fenetre extends JFrame {
    
    List<Categorie> listeCategories;
    List<Video> listeVideos;

	JPanel panelVideo = new JPanel();
    JPanel panelCategorie = new JPanel();
    
	JPanel panelBtVideo = new JPanel();
    JPanel panelBtCategorie = new JPanel();
    JPanel panelBtFermer = new JPanel();
    
    JButton ajoutVideo = new JButton("+");
    JButton enleverVideo = new JButton("-");
    JButton ajoutCategorie = new JButton("+");
    JButton enleverCategorie = new JButton("-");
    JButton quitter = new JButton("Quitter");
    
    JList videos = new JList();
    JList categories = new JList();

	DefaultListModel modelCategories = new DefaultListModel();
	DefaultListModel modelVideos = new DefaultListModel();

    private JLabel labelleVideo = new JLabel("Liste des vid�os : ");
    private JLabel labelleCategorie = new JLabel("Liste des cat�gories : ");

    BoutonEnleverCategorie btEnleverCateg = new BoutonEnleverCategorie(this, categories);
    BoutonEnleverVideo btEnleverVideo = new BoutonEnleverVideo(this, videos);
    
    BoutonAjoutCategorie btAjouterCateg = new BoutonAjoutCategorie(this);
    BoutonAjoutVideo btAjouterVideo = new BoutonAjoutVideo(this);
	
	
	public Fenetre(){
		
		this.setTitle("Client location vid�o");
        this.setSize(550, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
//      this.setLayout(new GridLayout(1, 2));   
        this.setLayout(null);
        
        quitter.addActionListener(new ActionListener() {
	    	@Override public void actionPerformed(ActionEvent e) 
	    		{System.exit(0);}
	    	});
        
        ajoutVideo.addActionListener(btAjouterVideo);
        ajoutVideo.setPreferredSize(new Dimension(50, 50));
        enleverVideo.addActionListener(btEnleverVideo);
        enleverVideo.setPreferredSize(new Dimension(50, 50));
        
        ajoutCategorie.addActionListener(btAjouterCateg);
        ajoutCategorie.setPreferredSize(new Dimension(50, 50));
        enleverCategorie.addActionListener(btEnleverCateg);
        enleverCategorie.setPreferredSize(new Dimension(50, 50));
        
        videos.addListSelectionListener(new VideosAction(this));
        categories.addListSelectionListener(new CategoriesAction(this, btEnleverCateg));

        videos.setPreferredSize(new Dimension(180, 400));
        categories.setPreferredSize(new Dimension(180, 400));

        panelVideo.add(labelleVideo);
        panelVideo.add(videos);
        panelVideo.setBounds(20, 0, 180, 430);
        
        panelBtVideo.add(ajoutVideo);
        panelBtVideo.add(enleverVideo);
        panelBtVideo.setBounds(210, 165, 50, 430);
        

        panelCategorie.add(labelleCategorie);
        panelCategorie.add(categories);
        panelCategorie.setBounds(285, 0, 180, 430);
        
        panelBtCategorie.add(ajoutCategorie);
        panelBtCategorie.add(enleverCategorie);
        panelBtCategorie.setBounds(475, 165, 50, 430);
        
        panelBtFermer.add(quitter);
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
		LocationBean location = new LocationBean();
		
		categories.removeAll();
		modelCategories.removeAllElements();
		categories.setModel(modelCategories);
		listeCategories = location.getListeCategories();
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