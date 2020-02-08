import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer; 


public class FenetreAffichage extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	private JPanel globalContener;				//La fenetre en arri�re plan, qui contient tout les JPanels
	private JPanel selectionOutil;				//La fenetre o� se trouvent les boutons pour ajouter des boules, ou en supprimer
	private AffichageMatiere affichageMatiere;	//D�riv� de JPanel g�rant l'affichage et la liste de boules de mati�re
	private JButton ajouterEau;					//
	private JButton ajouterMoinsLourd;			//
	private JButton ajouterPlusLourd;			//
	private JButton ajouterMemeDensite;			//		Boutons qui d�clenche le changement de fonction du clic
	private JButton ajouterMur;					//
	private JButton enleverLiquide;				//
	private JButton effTout;					//
	private Timer chrono;						// Timer pour g�rer le taux de raffraichissement de la fen�tre
	private boolean clicPressed=false;			// V�rifie si le clic de la souris est toujours enfonc�
	private int x;								//coordonnees de la souris
	private int y;								//Coordonnees de la souris
	private int n=0;							//Reduit la fr�quence d'apparition des gouttes gr�ce � un syst�me de division euclidienne
	private int etatCurseur = 0; 				//0:effacer , 1:eau , 2 : liqMoinsLourd , 3 : liqPlusLourd, 4:liqMemeDens, 5:mur


	public FenetreAffichage() {


		//FENETRE PRINCIPALE
		super("Liquide SIMULATOR 2018");
		setLayout(null);
		setSize(1440, 900);
		setLocation(0, 0);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chrono = new Timer(1, this);

		this.addMouseMotionListener(this);	


		//SETTING DE TOUS LES JFRAMES
		globalContener=new JPanel();
		globalContener.setLayout(null);
		globalContener.setBounds(0, 0, 1440, 900);
		globalContener.setBackground(Color.BLACK);

		affichageMatiere=new AffichageMatiere();

		selectionOutil=new JPanel();
		selectionOutil.setLayout(null);
		selectionOutil.setBounds(1050, 25, 355, 805);
		selectionOutil.setBackground(Color.orange);

		//CREATION DES BOUTONS

		ajouterEau=new JButton("Ajouter de l'eau.");
		ajouterEau.setBounds(20,20, 300, 100);
		ajouterEau.setBackground(Color.LIGHT_GRAY);

		ajouterMoinsLourd=new JButton("Ajouter un liquide moins dense.");
		ajouterMoinsLourd.setBounds(20, 120, 300, 100);
		ajouterMoinsLourd.setBackground(Color.LIGHT_GRAY);

		ajouterMemeDensite=new JButton("Ajouter un liquide de densit� similaire.");
		ajouterMemeDensite.setBounds(20,320, 300, 100);
		ajouterMemeDensite.setBackground(Color.LIGHT_GRAY);		

		ajouterPlusLourd=new JButton("Ajouter un liquide plus dense.");
		ajouterPlusLourd.setBounds(20,220, 300, 100);
		ajouterPlusLourd.setBackground(Color.LIGHT_GRAY);

		ajouterMur=new JButton("Ajouter un mur.");
		ajouterMur.setBounds(20,420, 300, 100);
		ajouterMur.setBackground(Color.LIGHT_GRAY);


		enleverLiquide=new JButton("Enlever de la matiere.");
		enleverLiquide.setBounds(20,520, 300, 100);
		enleverLiquide.setBackground(Color.LIGHT_GRAY);

		effTout = new JButton ("Effacer tout");
		effTout.setBounds(20,650,300,100);
		effTout.setBackground(Color.RED);
		
		
		//Ajout des boutons dans le JPanel de droite

		selectionOutil.add(ajouterEau);
		selectionOutil.add(ajouterMoinsLourd);
		selectionOutil.add(ajouterPlusLourd);
		selectionOutil.add(ajouterMur);
		selectionOutil.add(ajouterMemeDensite);
		selectionOutil.add(enleverLiquide);
		selectionOutil.add(effTout);

		
		// Ajout des JPanel dans le JPanel de fond

		globalContener.add(affichageMatiere);
		globalContener.add(selectionOutil);
		

		setContentPane(globalContener);
		
		
		//Initialisation de toutes les fonctions trigger

		effTout.addActionListener(this);
		ajouterMur.addActionListener(this);
		ajouterEau.addActionListener(this);
		ajouterMoinsLourd.addActionListener(this);
		ajouterPlusLourd.addActionListener(this);
		enleverLiquide.addActionListener(this);
		ajouterMemeDensite.addActionListener(this);
		addMouseListener(this);
		chrono.start();

	}


	
	/*
	 * Change la fonction du clic selon le bouton press� en dernier (Cf variable etatCurseur)
	 * Appel�e � chaque d�clenchement du chrono, mais n'ajoute de gouttes de liquide qu'une fois par 7 d�clenchement, pour plus de lisibilit�
	 * 
	 * */
	public void gererGoutte(){
		if (x>25 && x<1025 && y>25 && y<840 && clicPressed ==true) { // VERIFIER SI LE CLIC EST DANS LA FENETRE EAU
			if (n%7==0) {
				if (etatCurseur == 1) {
					Water goutte = new Water(x-30, y-50,affichageMatiere.getHeight(),affichageMatiere.getWidth(),System.currentTimeMillis()); // PASSER LES COORDONNEES DU CLIC DEPUIS LA FENETRE PRINCIPALE DANS LA FENETRE EAU
					affichageMatiere.ajouterMatiere(goutte);
				} else if (etatCurseur == 2) {
					LiquideMoinsDense goutte = new LiquideMoinsDense(x-30, y-50,affichageMatiere.getHeight(),affichageMatiere.getWidth(),System.currentTimeMillis()); 
					affichageMatiere.ajouterMatiere(goutte);
				}else if (etatCurseur == 3) {
					LiquidePlusDense goutte = new LiquidePlusDense (x-30, y-50,affichageMatiere.getHeight(),affichageMatiere.getWidth(),System.currentTimeMillis());
					affichageMatiere.ajouterMatiere(goutte);
				}else if (etatCurseur == 4) {
					LiquideMemeDense goutte = new LiquideMemeDense (x-30, y-50,affichageMatiere.getHeight(),affichageMatiere.getWidth(),System.currentTimeMillis());
					affichageMatiere.ajouterMatiere(goutte);
				}
			} else if(etatCurseur==0 && affichageMatiere.matieres!=null) {
				//supression goutte unique
				affichageMatiere.effacerEau(x-30, y-50);
			} else if(etatCurseur==5) {
				Wall mur = new Wall(x-30, y-50, affichageMatiere.getHeight(),affichageMatiere.getWidth());
				affichageMatiere.ajouterMatiere(mur);
			}
		}
	}


	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		clicPressed = true;
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clicPressed = false;
	}

	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();//actualise la position de la souris en temps r�el
	}

	public void mouseMoved(MouseEvent e){

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		n++;
		gererGoutte();	
		if(e.getSource()==ajouterEau) {		//s�lection "ajouter eau"
			etatCurseur= 1;	
		}

		else if(e.getSource()==ajouterMoinsLourd) {		//s�lection "ajouter liquide moins dense"
			etatCurseur= 2;
		}

		else if(e.getSource()==ajouterPlusLourd) { 	//s�lection "ajouter liq plus dense"
			etatCurseur= 3;
		}

		else if(e.getSource() == ajouterMemeDensite) { //S�lection "ajouter liquide m�me densit�"
			etatCurseur=4;
		}

		else if(e.getSource() == ajouterMur) {		//s�lection "ajouter mur"
			etatCurseur=5;
		}

		else if (e.getSource() == enleverLiquide) {//s�lectionner "enlever eau"
			etatCurseur = 0;
		}

		else if(e.getSource() == effTout){		//s�lection "Tout effacer"
			affichageMatiere.effTout();
		}

		if (affichageMatiere.matieres != null){				// Si la liste n'est pas vide, 
			for(Matiere n : affichageMatiere.matieres){		// Fait tomber les liquides, pas les solides
				if (n.liquide) {
					n.chute(affichageMatiere.matieres);
					affichageMatiere.detecCollision(n);
				}
			}
		}
		affichageMatiere.repaint();
	}


}