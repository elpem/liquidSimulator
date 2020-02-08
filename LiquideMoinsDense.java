import java.awt.Color;

public class LiquideMoinsDense extends Liquide {

	public LiquideMoinsDense(int x, int y, int haut, int larg, long tempsInitial) {
		super(x, y, haut, larg, tempsInitial, 500);
		this.couleur=Color.magenta;
	}

}