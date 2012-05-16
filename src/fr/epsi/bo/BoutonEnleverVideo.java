package fr.epsi.bo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;

import fr.epsi.location.pojo.Video;
import fr.epsi.location.remote.LocationBean;


public class BoutonEnleverVideo implements ActionListener  {

	LocationBean location = new LocationBean();
	private String video;
	private Fenetre fenetre;
	private JList videos;
	
	public BoutonEnleverVideo(Fenetre fenetre, JList v) {
		this.fenetre = fenetre;
		this.videos = v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(videos.getSelectedIndex() == -1 ){
			JOptionPane.showMessageDialog(null, "Choisissez la vid�o � supprimer","Attention",JOptionPane.ERROR_MESSAGE);
		}else{
			List<Video> videos = location.getListeVideos();
				for(Video v : videos){
					if(video == v.getTitre()){
						location.supprimerVideo(v);
						fenetre.actualiserListes();
					}
				}
			fenetre.actualiserListes();
		}
	}
	
	public void changerSelection(String video){
		this.video = video;
	}
}