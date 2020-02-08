import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Wall extends Matiere{
	
	public Wall(int x, int y, int haut, int larg) {
		super(x,y,haut,larg,50);
		couleur = Color.darkGray;
		this.liquide=false;
	}

	@Override
	public void toucheParois() {				
	}

	@Override
	public void collision(ArrayList<Matiere> matieres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chute(ArrayList<Matiere> matieres) {
		// TODO Auto-generated method stub
		
	}
	

}