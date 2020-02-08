import java.awt.Color;

public class LiquidePlusDense extends Liquide {

	public LiquidePlusDense(int x, int y, int haut, int larg, long tempsInitial) {
		super(x, y, haut, larg, tempsInitial, 2000);
		this.couleur=Color.red;
	}

}