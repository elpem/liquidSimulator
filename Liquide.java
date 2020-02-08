import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Liquide extends Matiere{
	
	protected int rho;
	protected int masse=314159; //Masse d'une goutte, fix�e pour avoir une goutte d'eau de 10 px de rayon. Le rayon des gouttes des autres liquides est fonction de cette masse et de leur masse volumique (rho)
	protected long tempsGoutte; //Temps depuis le dernier reset d'acc�l�ration, pour calculer la vitesse en int�grant l'acc�l�ration
	protected double epsilon; //Coeff de rebond

	public Liquide(int x, int y,int haut, int larg, long tempsInitial, int rho) {
		super(x, y, haut, larg,(int)(Math.sqrt(314159/(Math.PI*rho))));
		v0 = new Vecteur((int)(Math.random()*4)-2, (int)(Math.random()*4)); // Vitesse initiale al�atoire dans un petit c�ne pour �viter une chute trop lin�aire
		tempsGoutte=tempsInitial;
		this.acceleration.dy = 10;
		this.liquide=true;
		this.epsilon = 0.02;
	}

	
	/*
	 * V�rifie pour une goutte de liquide si elle entre en collision avec une boule de mati�re dans la liste
	 * */
	public void collision(ArrayList<Matiere> matieres) {

		for (Matiere n : matieres) {
			if(n != this){
				if (this.distance(n.x, n.y)<=(n.rayon+this.rayon)) {
					this.rebondGoutte(n);
				}
			}
			else toucheMur();
		}
	}
	/**Detecte une collision de la goutte courant avec une des 4 parois
	 */
	public void toucheParois(){

		if((x-rayon)<0){					//GAUCHE
			x = rayon;
			acceleration.dx = -acceleration.dx;
			v0.dx=-epsilon*v0.dx;
		}
		else if((x+rayon)>largeur){			//DROITE
			x = largeur -rayon;
			acceleration.dx = -acceleration.dx;
			v0.dx=-epsilon*v0.dx;
		}
		if((y-rayon)<0){					//HAUT
		}
		else if((y+rayon)>hauteur){			//BAS
			y = hauteur-rayon;
			this.rebondSol();
		}
	}
	
	

	/*
	 * R�adapte les vecteurs vitesses d'une goutte de liquide qui touche les bords gauche, droit et inf�rieur de la fen�tre affichageLiquide
	 * */
	public void toucheMur(){
		
		if((x-rayon)<0){					//GAUCHE
			x = rayon;
			acceleration.dx = -acceleration.dx;
			v0.dx=-epsilon*v0.dx;
		}
		else if((x+rayon)>largeur){			//DROITE
			x = largeur -rayon;
			acceleration.dx = -acceleration.dx;
			v0.dx=-epsilon*v0.dx;
		}
		else if((y+rayon)>hauteur){			//BAS
			y = hauteur-rayon;
			this.rebondSol();
		}
	}

	/*
	 * R�initialise le temps si on touche le sol, ce qui permet de r�initialiser l'acc�l�ration.
	 * */
	public void rebondSol (){
		this.tempsGoutte = System.currentTimeMillis();
		v0.dy = -epsilon*vitesse.dy;
	}

	
	/*
	 * Calcule les vitesse d'une goutte de liquide apr�s sa collision avec une boule de mati�re, tout en emp�chant la superposition de 2 gouttes de liquide
	 * */
	public void rebondGoutte(Matiere goutteTouchee) {
		this.tempsGoutte = System.currentTimeMillis();
		if(goutteTouchee.x==this.x && this.y == goutteTouchee.y){
			this.x = x + (int)(Math.random()*10);		//Evite la superposition totale de gouttes
			this.y = y + (int)(Math.random()*10);		//Evite la superposition totale de gouttes
		}
		{
			this.x = x+(x-goutteTouchee.x)/2;		//Evite la superposition partielle de gouttes
			this.y = y+(y-goutteTouchee.y)/2;		//Evite la superposition partielle de gouttes
		}
		this.v0.dy=(1-epsilon)*goutteTouchee.vitesse.dy-epsilon*this.vitesse.dy;		//Calcul de la vitesse apr�s rebond (cf bibliographie)
		this.v0.dx=(1-epsilon)*goutteTouchee.vitesse.dx-epsilon*this.vitesse.dx;		//Calcul de la vitesse apr�s rebond (cf bibliographie)
	}
	
	
	
	/*
	 * Calcule la position d'une goutte d'apr�s sa vitesse, v�rifie si cette position ne rentre pas dans une boule d�j� pr�sente, la d�place le cas �ch�ant.
	 * */
	public void chute (ArrayList<Matiere> matieres){
		this.vitesse.dy = v0.dy + acceleration.dy*(System.currentTimeMillis()-tempsGoutte)*0.001;
		int yTheorique = (int) (y + this.vitesse.dy);
		int xTheorique = (int)(x + this.vitesse.dx + this.v0.dx);
		//CALCUL DE L'ENDROIT OU DEVRAIT SE TROUVER LA GOUTTE, PUIS VERIFICATION QUE CE NE SOIT PAS DANS UNE AUTRE GOUTTE
		if (!collision2(xTheorique, yTheorique, matieres)) {
			this.x=xTheorique;
			this.y=yTheorique;
		}
	}

	@Override
	public String toString() {
		return "Liquide [x=" + x + ", y=" + y + ", rho=" + rho + ", masse=" + masse + ", rayon=" + rayon
				+ ", tempsGoutte=" + tempsGoutte + ", vitesse=" + vitesse + ", acceleration=" + acceleration
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	
	
	/**
	 * detecte si on est sur la goutte
	 * @ param les position du curseur
	 * @ return vrai si on touche la goute
	 */
	public boolean collision2 (int x0, int y0, ArrayList<Matiere> matieres) {

		boolean collide = false;

		for (Matiere n : matieres) {
			if(n != this){
				if (Math.sqrt(Math.pow(x0-n.x,2)+Math.pow(y0-n.y,2))<rayon){
					collide=true;
				}
			}
		}

		return collide;

	}

}