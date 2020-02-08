import java.awt.Color;

public class Water extends Liquide {


	public Water(int x, int y, int haut, int larg, long tempsInitial) {
		super(x, y, haut, larg, tempsInitial, 1000);
		this.couleur=Color.blue;
	}
	
	

}