import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;


public class Fenetre extends JFrame {

	String listeVideos[] = {"Video1", "Video2", "Video3"};
    String listeCategories[] = {"Categorie1", "Categorie2", "Categorie3"};
	
	JPanel panelVideo = new JPanel();
    JPanel panelCategorie = new JPanel();
    
    JButton ajoutVideo = new JButton("+");
    JButton enleverVideo = new JButton("-");
    JButton ajoutCategorie = new JButton("+");
    JButton enleverCategorie = new JButton("-");
    
    JList videos = new JList(listeVideos);
    JList categories = new JList(listeCategories);
	
	
	public Fenetre(){
		this.setTitle("Client location vidéo");
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        this.setLayout(new GridLayout(1, 2));
        
        ajoutVideo.addActionListener(new BoutonAjoutVideo());
        enleverVideo.addActionListener(new BoutonEnleverVideo());
        
        ajoutCategorie.addActionListener(new BoutonAjoutCategorie());
        enleverCategorie.addActionListener(new BoutonEnleverCategorie());
        
        
        panelVideo.add(videos);
        panelVideo.add(ajoutVideo);
        panelVideo.add(enleverVideo);
        
        panelCategorie.add(categories);
        panelCategorie.add(ajoutCategorie);
        panelCategorie.add(enleverCategorie);
        
        
        this.getContentPane().add(panelVideo);
        this.getContentPane().add(panelCategorie);
        
        
        
        this.setVisible(true);
	}
	
}
