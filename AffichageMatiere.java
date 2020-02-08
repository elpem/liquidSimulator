import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class AffichageMatiere extends JPanel{
	public ArrayList<Matiere> matieres = new ArrayList();

	public AffichageMatiere () {
		this.setLayout(null);
		this.setBounds(25, 25, 1000, 805);
		this.setBackground(Color.orange);
	}

	
	/*
	 * Ajoute une boule de matière à l'ArrayList 
	 * */
	public void ajouterMatiere(Matiere matiere) {
		if(matieres != null) {
			matieres.add(matiere);
		}

		if(matieres == null) { //permet de réafficher après un effacement total, et de créer l'arrayList à l'initialisation
			matieres = new ArrayList();
			matieres.add(matiere);
		}
	}


	/*
	 * METHODE POUR METTRE A JOUR L'AFFICHAGE
	 * 
	 */
	public void paint(Graphics g){
		g.setColor(Color.orange);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		if(matieres != null) {
			for (Matiere n : matieres){
				n.affichage(g);
			}
		}
	}


	/*
	 * VIDE TOUT L'ARRAYLIST => ENLEVE TOUTES LES matieres
	 * 
	 */
	public void effTout() {
		if(matieres != null)
			matieres = null;
	}

	
	
	/**
	 * efface la goutte sur laquelle est le cursseur
	 * @ param les position du curseur
	 */

	public void effacerEau(int x, int y){
		for (Matiere n : matieres){
			if(n.toucheGoutte(x,y)){
				matieres.remove(n);
				break; //pour sortir de la boucle qui n'a plus lieu d'être
			}
		}
	}


	/*
	 * A CHAQUE ITERATION, VERIFIE LES COLLISIONS POUR CHAQUE LIQUIDE DE L'ARRAYLIST AVEC CHAQUE BOULE DE MATIERE
	 */
	public void detecCollision (Matiere n){
		if(matieres != null){
				n.toucheParois();
				n.collision(matieres);
		}
	}
}