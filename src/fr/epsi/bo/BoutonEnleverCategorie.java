package fr.epsi.bo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;

import fr.epsi.location.remote.LocationBean;
import fr.epsi.location.pojo.Categorie;;


public class BoutonEnleverCategorie implements ActionListener {

	private LocationBean location = new LocationBean();
	private String categorie;
	private Fenetre fenetre;
	private JList categories;
	
	public BoutonEnleverCategorie(Fenetre fenetre, JList c) {
		this.fenetre = fenetre;
		this.categories = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(categories.getSelectedIndex() == -1 ){
			JOptionPane.showMessageDialog(null, "Choisissez la cat�gorie � supprimer","Attention",JOptionPane.ERROR_MESSAGE);
		}else{
			List<Categorie> categories = location.getListeCategories();
				for(Categorie c : categories){
					if(categorie == c.getLibelle()){
						location.supprimerCategorie(c);
						fenetre.actualiserListes();
					}
				}
			fenetre.actualiserListes();
		}
	}
	
	public void changerSelection(String categ){
		this.categorie = categ;
	}
}
