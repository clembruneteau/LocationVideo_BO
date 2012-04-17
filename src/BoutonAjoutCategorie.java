import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoutonAjoutCategorie implements ActionListener {

	private Fenetre fenetre;
	public BoutonAjoutCategorie(Fenetre f) {
		this.fenetre = f;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new FenetreAjoutCategorie(this.fenetre);
	}

	

}
