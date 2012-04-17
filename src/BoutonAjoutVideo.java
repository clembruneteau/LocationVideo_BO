import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoutonAjoutVideo implements ActionListener{

	private Fenetre fenetre;
	public BoutonAjoutVideo(Fenetre f) {
		this.fenetre = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreAjoutVideo(this.fenetre);
	}

	
	

}
