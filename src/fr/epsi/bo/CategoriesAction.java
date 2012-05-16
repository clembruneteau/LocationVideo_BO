package fr.epsi.bo;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CategoriesAction implements ListSelectionListener {
	
	private BoutonEnleverCategorie bouton;
	private Fenetre fen;
	
	public CategoriesAction(Fenetre f, BoutonEnleverCategorie btEnleverCateg){
		this.bouton = btEnleverCateg;
		this.fen = f;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()){
			JList liste = (JList) e.getSource();
			this.bouton.changerSelection((String)liste.getSelectedValue());
			new FenetreAjoutCategorie(this.fen, (String)liste.getSelectedValue());
		}
	}

}
