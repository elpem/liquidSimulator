import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Matiere {
	
	protected int x;		// Coordonn�s du point
	protected int y;		// Coordonn�s du point
	protected int hauteur;
	protected int largeur;
	protected Vecteur acceleration;
	protected Vecteur vitesse;
	protected Vecteur v0;	//Vitesse intiale du point, utile pour les liquides
	protected int rayon;
	protected int masse=314159;	//Masse d'une goutte, fix�e pour avoir une goutte d'eau de 10 px de rayon. Le rayon des gouttes des autres liquides est fonction de cette masse et de leur masse volumique (rho)
	protected Color couleur;
	protected boolean liquide;	//permet de v�rifier si il s'agit d'un liquide ou d'un mur
	
	public Matiere(int x, int y,int haut, int larg, int rayon) {
		vitesse = new Vecteur(0, 0);
		acceleration=new Vecteur(0, 0);
		v0 = new Vecteur(0, 0);
		this.x=x;
		this.y=y;
		this.hauteur = haut;
		this.largeur = larg;
		this.rayon = rayon;
	}
	
	
	/*
	 * Permet de savoir si une boule de mati�re en touche une autre
	 * dont les coordonn�es sont entr�es en param�tre
	 * 
	 * */
	public boolean toucheGoutte (int x0,int y0){
		return(distance(x0,y0)<=rayon);
	}
	
	/*
	 * Renvoie la distance entre le centre d'une boule de mati�re
	 * Et un point dont les coordonn�es sont pass�es en param�tre
	 * 
	 * */
	public int distance(int x0,int y0){
		return (int)(Math.sqrt(Math.pow(x0-this.x,2)+Math.pow(y0-this.y,2)));
	}
	
	/*
	 * Affiche une boule de mati�re
	 * */
	public void affichage (Graphics g ){
		g.setColor(this.couleur);
		g.fillOval(x-rayon, y-rayon, 2*rayon, 2*rayon);
	}


	public abstract void toucheParois(); //Permet de d�finir ce qu'il se passe si une boule touche un mur, utilis�e pour les liquides, mais d�finie ici pour pouvoir parcourir la liste de mati�re sans erreur
	public abstract void collision(ArrayList<Matiere> matieres); //Permet de d�finir ce qu'il se passe si une boule en touche une autre, utilis�e pour les liquides, mais d�finie ici pour pouvoir parcourir la liste de mati�re sans erreur
	public abstract void chute (ArrayList<Matiere> matieres); //Permet de d�finir la trajectoire d'une boule, utilis�e pour les liquides, mais d�finie ici pour parcourir la liste de mati�re sans erreur
}