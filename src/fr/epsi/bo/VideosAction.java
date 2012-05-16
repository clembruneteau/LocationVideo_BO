package fr.epsi.bo;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class VideosAction implements ListSelectionListener {

	private Fenetre fen;
	
	public VideosAction(Fenetre fenetre) {
		this.fen = fenetre;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()){
			JList liste = (JList) e.getSource();
			
			new FenetreAjoutVideo(this.fen, (String)liste.getSelectedValue());
		}
	}

}
