package game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import objects.*;


/**
 * StarCommand.java
 * 
 * Game about starships duking it out in space
 * 
 * Use WASD to move the cursor around in the strategic map Use E to select a
 * friendly ship, use WASD to move it around, and finally press E again to put
 * it in that location You can use Q to deselect the ship that is currently
 * selected In battle mode use A and D to move the ship back and forth and use
 * SPACE to fire your gun
 * 
 * TODO: Text Boxes with Ship readouts / stats 
 * Beginning Splash Screen / Tutorial 
 * Implement Art 
 * Turns
 * Enemy Movement 
 * Health 
 * Different Weapons 
 * Types of Ships 
 * Asteroids in strategic view and battlemode 
 * Levels
 * InterLevel Splash Screen 
 * Entering BattleMode scene 
 * 
 * 
 *
 * @author Timothy Buente, Matthew Kinzler
 * @version march 2017
 */
public class StarCommand extends Application {
	final String appName = "Star Command";
	final int FPS = 30; // frames per second
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	private GridEntry[][] map = new GridEntry[16][12]; // 50 * 50 pixel blocks
														// and the int value
														// represents what is
														// supposed to be
														// displayed in that
														// block
	private ArrayList<Ship> ships = new ArrayList(); // Everything to be updated
														// goes in here
	private ArrayList<EnemyShip> enemies = new ArrayList();
	private ArrayList<Objects> display = new ArrayList(); 			// Everything to be
	private ArrayList<Obstruction> obstructions = new ArrayList();	// rendered goes in
																	// here
	private ArrayList<Path> paths = new ArrayList();
	private ArrayList<Bullets> bullets = new ArrayList();
	private Cursor cursor;
	private Ship selected = null;
	private Fighter fighter;
	private EnemyFighter enemy;
	private Obstruction asteroid;
	private int[] moves = new int[5];
	private final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	private int move = 0;
	
	private int state;
	private final int STRAT = 0, BATTLE = 1, WIN = 2, LOSE = 3, INTRO = 4, TUT = 5;
	private AudioClip select;
	public static Font font1 = Font.font("TimesRoman", FontPosture.ITALIC, 60.0);
	public static Font font2 = Font.font("TimesRoman", FontPosture.REGULAR, 30);
    private MediaPlayer mp;
    private Media song;
    private Intro intro;
    private Tutorial tut;
    private int obstructDirect;
	/**
	 * Set up initial data structures/values
	 */
	
	void initialize() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) { // Intitialize the map
				map[i][j] = new GridEntry(i, j, 1);
			}
		}
		
		select = new AudioClip(ClassLoader.getSystemResource("select.wav").toString());
		
		state = TUT;
		cursor = new Cursor(0, 0);
		song = new Media(ClassLoader.getSystemResource("strat_ambience.wav").toString());
		mp = new MediaPlayer(song);
		mp.play();
		// Add all ships for this level 1 to ships array list
		ships.add(new Ship(0, 0, 5));
		display.add(ships.get(ships.size() - 1));
		enemies.add(new EnemyShip(1, 2, 0));
		obstructions.add(new Obstruction(8, 10, "asteroid"));
		display.add(enemies.get(enemies.size() - 1));
		display.add(obstructions.get(obstructions.size()-1));
		
		for (int i = 0; i < moves.length; i++)
			moves[i] = -1;
		
		intro = new Intro();
		tut = new Tutorial();
	}
	
	private boolean checkExpression(int i){
		if(move < 5 && move != 0)
			return moves[move - 1] == i;
		else
			return true;
	}

	void setHandlers(Scene scene) {

		scene.setOnKeyPressed(e -> {
			switch (state) {
			case STRAT:
				switch (e.getCode()) {
				case W:
					if ((move < 5 &&(checkObstruction() == false || (checkObstruction() == true && obstructDirect != UP)))|| checkExpression(DOWN)){
						cursor.setY(cursor.getY() - 1);
					if (cursor.getY() == -1)
						cursor.setY(0);
					if (selected != null) {
						move('w');
						selected.setDirection(0);
						select.play();
					}
					}
					else{
						System.out.println("Can't move");
					}
					break;
				case A:
					if ((move < 5 && (checkObstruction() == false || (checkObstruction() == true && obstructDirect != LEFT))) || moves[move - 1] == RIGHT){
						cursor.setX(cursor.getX() - 1);
					if (cursor.getX() == -1)
						cursor.setX(0);
					if (selected != null) {
						move('a');
						selected.setDirection(1);
						select.play();
					}
					}
					else{
						System.out.println("Can't move");
					}
					break;
				case S:
					if ((move < 5 && (checkObstruction() == false || (checkObstruction() == true && obstructDirect != DOWN)))|| moves[move - 1] == UP){
						cursor.setY(cursor.getY() + 1);
					if (cursor.getY() == 12)
						cursor.setY(11);
					if (selected != null) {
						move('s');
						selected.setDirection(3);
						select.play();
					}
					}
					else{
						System.out.println("Can't move");
					}
					break;
				case D:
					if ((move < 5 && (checkObstruction() == false || (checkObstruction() == true && obstructDirect != RIGHT))) || moves[move - 1] == LEFT){
						cursor.setX(cursor.getX() + 1);
					if (cursor.getX() == 16)
						cursor.setX(15);
					if (selected != null) {
						move('d');
						selected.setDirection(2);
						select.play();
					}
					}
					else{
						System.out.println("Can't move");
					}
					break;
				case E:
					if (selected == null)
						selected = hovering();
					else if (selected != null && !ishoveringEnemy())
						move('e');
					else if (selected != null && ishoveringEnemy())
						battle(hoveringEnemy());
					break;
				case Q:
					if (selected != null)
						move('q');
					break;
				default:
					break;
				}
				break;
			case BATTLE:
				switch (e.getCode()) {
				case A:
					fighter.battlemove('a');
					break;
				case D:
					fighter.battlemove('d');
					break;
				case SPACE:
					fighter.fire();
					break;
				default:
					break;
				}
				break;
			case WIN:

				break;
			case LOSE:
				
				break;
			case INTRO:
				switch(e.getCode()){
				case SPACE:
					state = STRAT;
					break;
				case E:
					state = TUT;
					break;
				default:
					break;
				}
				break;
			case TUT:
				switch (e.getCode()){
				case SPACE:
					state = INTRO;
					break;
				default:
					break;
				}
				break;
			}

		});

		scene.setOnKeyReleased(e -> {			
			switch (state) {
			case STRAT:
				
				break;
			case BATTLE:
				switch (e.getCode()) {
				case A:
					fighter.battlemove('z');
					break;
				case D:
					fighter.battlemove('c');
					break;
				default:
					break;
				}
				break;
			case WIN:

				break;
			case LOSE:
				
				break;
			case INTRO:
				
				break;
			case TUT:
				
				break;
			default:
				break;
			}
		});

	}

	/*
	 * Check and see if the cursor is hovering over anything
	 */
	public Ship hovering() {
		for (int i = 0; i < ships.size(); i++) {
			if (cursor.getX() == ships.get(i).getX()
					&& cursor.getY() == ships.get(i).getY()) {
				return ships.get(i);
			}
		}

		return null;
	}

	public boolean ishoveringEnemy() {
		for (int i = 0; i < enemies.size(); i++) {
			if (cursor.getX() == enemies.get(i).getX()
					&& cursor.getY() == enemies.get(i).getY()) {
				return true;
			}
		}

		return false;
	}
	
	public boolean checkObstruction() {
		Boolean A,B,C,D,E,F;
		
		
		for(int i = 0; i<obstructions.size(); i++){
			A = cursor.getX() == obstructions.get(i).getX();
			B = cursor.getX() - 1 == obstructions.get(i).getX();
			C = cursor.getX() + 1 == obstructions.get(i).getX();
			D = cursor.getY() == obstructions.get(i).getY();
			E = cursor.getY() - 1 == obstructions.get(i).getY();
			F = cursor.getY() + 1 == obstructions.get(i).getY();
			if((A && !B && !C && !D && !E && F)){
				obstructDirect = DOWN;
				return true;
			}
			else if((A && !B && !C && !D && E && !F)){
				obstructDirect = UP;
				return true;
			}
			else if((!A && !B && C && D && !E && !F)){
				obstructDirect = RIGHT;
				return true;
			}
			else if((!A && B && !C && D && !E && !F)){
				obstructDirect = LEFT;
				return true;
			}
		}
		
		return false;
		
	}

	public EnemyShip hoveringEnemy() {
		for (int i = 0; i < enemies.size(); i++) {
			if (cursor.getX() == enemies.get(i).getX()
					&& cursor.getY() == enemies.get(i).getY()) {
				return enemies.get(i);
			}
		}

		return null;
	}

	/*
	 * Lay down the path to show the path the ship will move along
	 */
	
	public void move(char c){
		switch (c){
		case 'e':
			selected.setX(cursor.getX());
			selected.setY(cursor.getY());
			paths.clear();
			selected = null;
			for (int i = 0; i < moves.length; i++)
				moves[i] = -1;
			move = 0;
			break;
		
		case 'q':
			selected = null;
			paths.clear();
			for (int i = 0; i < moves.length; i++)
				moves[i] = -1;
			move = 0;
			break;
			
		case 'w': 
			if (move == 0){
					paths.add(new Path(cursor.getX(), cursor.getY(), 2));
					moves[move] = UP;
					move++;
			}else if (move < 5){
				if (moves[move - 1] == DOWN){
					paths.remove(move - 1);
					moves[move - 1] = -1;
					move--;
				}else if (moves[move - 1] == UP){
					
						paths.get(move - 1).setType(0);
						paths.add(new Path(cursor.getX(), cursor.getY(), 2));
						moves[move] = UP;
						move++;
					
				}else if (moves[move - 1] == LEFT){
					
					paths.get(move - 1).setType(7);
					paths.add(new Path(cursor.getX(), cursor.getY(), 2));
					moves[move] = UP;
					move++;
					
				}else if (moves[move - 1] == RIGHT){
					paths.get(move - 1).setType(8);
					paths.add(new Path(cursor.getX(), cursor.getY(), 2));
					moves[move] = UP;
					move++;
					
					
				}
			}else if (move == 5){
				if (moves[move - 1] == DOWN){
					paths.remove(move - 1);
					moves[move - 1] = -1;
					move--;
				}
			}
			break;
			
		case 's':
			if (move == 0){
				
				paths.add(new Path(cursor.getX(), cursor.getY(), 4));
				moves[move] = DOWN;
				move++;
				
			}
			
			else if (move < 5){
				if (moves[move - 1] == DOWN){
					paths.get(move - 1).setType(0);
					paths.add(new Path(cursor.getX(), cursor.getY(), 4));
					moves[move] = DOWN;
					move++;
				}else if (moves[move - 1] == RIGHT){
					paths.get(move - 1).setType(9);
					paths.add(new Path(cursor.getX(), cursor.getY(), 4));
					moves[move] = DOWN;
					move++;
				}else if (moves[move - 1] == LEFT){
					paths.get(move - 1).setType(6);
					paths.add(new Path(cursor.getX(), cursor.getY(), 4));
					moves[move] = DOWN;
					move++;
				}else if (moves[move - 1] == UP){
					paths.remove(move - 1);
					moves[move - 1] = -1;
					move--;
				}
			}else if (move == 5){
				if (moves[move - 1] == UP){
					paths.remove(move - 1);
					moves[move - 1] = -1;
					move--;
				}
			}
			break;
			
		case 'a':
			if (move == 0){
				paths.add(new Path(cursor.getX(), cursor.getY(), 3));
				moves[move] = LEFT;
				move++;
			}
			else if (move < 5){
				if (moves[move - 1] == DOWN){
					paths.get(move - 1).setType(8);
					paths.add(new Path(cursor.getX(), cursor.getY(), 3));
					moves[move] = LEFT;
					move++;
				}else if (moves[move - 1] == UP){
					paths.get(move - 1).setType(9);
					paths.add(new Path(cursor.getX(), cursor.getY(), 3));
					moves[move] = LEFT;
					move++;
				}else if (moves[move - 1] == LEFT){
					paths.get(move - 1).setType(1);
					paths.add(new Path(cursor.getX(), cursor.getY(), 3));
					moves[move] = LEFT;
					move++;
				}else if (moves[move - 1] == RIGHT){
					paths.remove(move - 1);
					moves[move - 1] = -1;
					move--;
				}
			}else if (move == 5){
				if (moves[move - 1] == RIGHT){
					paths.remove(move - 1);
					moves[move - 1] = -1;
					move--;
				}
			}
			break;
			
		case 'd':
			if (move == 0){
				paths.add(new Path(cursor.getX(), cursor.getY(), 5));
				moves[move] = RIGHT;
				move++;
			}else if (move < 5){
				if (moves[move - 1] == DOWN){
					paths.get(move - 1).setType(7);
					paths.add(new Path(cursor.getX(), cursor.getY(), 5));
					moves[move] = RIGHT;
					move++;
				}else if (moves[move - 1] == UP){
					paths.get(move - 1).setType(6);
					paths.add(new Path(cursor.getX(), cursor.getY(), 5));
					moves[move] = RIGHT;
					move++;
				}else if (moves[move - 1] == RIGHT){
					paths.get(move - 1).setType(1);
					paths.add(new Path(cursor.getX(), cursor.getY(), 5));
					moves[move] = RIGHT;
					move++;
				}else if (moves[move - 1] == LEFT){
					paths.remove(move - 1);
					moves[move - 1] = -1;
					move--;
				}
			}else if (move == 5){
				if (moves[move - 1] == LEFT){
					paths.remove(move - 1);
					moves[move - 1] = -1;
					move--;
				}
			}
			break;
			
		}
	}

	public void battle(EnemyShip e) {
		if (state == BATTLE) {
			state = STRAT;
			fighter = null;
			selected = null;
			paths.clear();
		} else if (selected != null) {
			mp.pause();
			song = new Media(ClassLoader.getSystemResource("battlemusic.wav").toString());
			mp = new MediaPlayer(song);
			mp.play();
			state = BATTLE;
			fighter = new Fighter(WIDTH / 2, HEIGHT - 50, display, bullets);
			enemy = new EnemyFighter(WIDTH / 2, 10, display, bullets, fighter);
		}
	}

	/**
	 * Update variables for one time step
	 */
	public void update() {
		switch (state) {
		case STRAT:
			if (ships.size() == 0) {
				state = LOSE;
			}
			if (enemies.size() == 0) {
				state = WIN;
			}
			if (selected != null)
				cursor.update();
			else
				cursor.setType(0);
			break;
		case BATTLE:
			fighter.update();
			enemy.update();
			for (int i = 0; i < bullets.size(); i++)
				bullets.get(i).update();

			collision();
			clearBullets();
			break;
		case WIN:

			break;
		case LOSE:
			
			break;
		case INTRO:
			
			break;
		case TUT:
			tut.update();
			break;
		default:
			break;
		}


	}
	
	public void clearBullets(){
		if (bullets.size() != 0) {
			for (int i = 0; i < bullets.size(); i++) {
				if (bullets.get(i).getY() > HEIGHT || bullets.get(i).getY() < 0) {
					display.remove(bullets.get(i));
					bullets.remove(i);
					
				}
			}
		}
	}

	public void collision() {
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).getX() + 4 > fighter.getX()
					&& bullets.get(i).getX() < fighter.getX() + 30
					&& bullets.get(i).getY() > HEIGHT - 60
					&& bullets.get(i).getY() < HEIGHT - 10) {
				ships.remove(selected);
				display.remove(selected);
				for (int k = 0; k < bullets.size(); k++) {
					for (int j = 0; j < display.size(); j++) {
						if (display.get(j) == bullets.get(k))
							display.remove(j);
					}
				}
				bullets.clear();
				battle(null);
				break;
			}
			if (bullets.get(i).getX() > enemy.getX()
					&& bullets.get(i).getX() + 4 < enemy.getX() + 30
					&& bullets.get(i).getY() < 60 && bullets.get(i).getY() > 10) {
				display.remove(hoveringEnemy());
				enemies.remove(hoveringEnemy());
				for (int k = 0; k < bullets.size(); k++) {
					for (int j = 0; j < display.size(); j++) {
						if (display.get(j) == bullets.get(k))
							display.remove(j);
					}
				}
				bullets.clear();
				battle(null);
				break;
			}
		}
	}

	/**
	 * Draw the game world
	 */
	void render(GraphicsContext gc) {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				map[i][j].render(gc);
			}
		}
		
		switch (state) {
		case STRAT:
			gc.setStroke(Color.WHITE);
			for (int i = 0; i < 17; i++)
				gc.strokeLine(i * 50, 0, i * 50, HEIGHT);
			for (int i = 0; i < 13; i++)
				gc.strokeLine(0, i * 50, WIDTH, i * 50);
			for (int i = 0; i < display.size(); i++)
				display.get(i).render(gc);
			for (int i = 0; i < paths.size(); i++)
				paths.get(i).render(gc);
			cursor.render(gc);
			break;
		case BATTLE:
			fighter.render(gc);
			enemy.render(gc);
			for (int i = 0; i < bullets.size(); i++)
				bullets.get(i).render(gc);
			break;
		case WIN:
			gc.setFont(font1);
			gc.fillText("You Win!", WIDTH / 2 - 100, HEIGHT / 2);
			break;
		case LOSE:
			gc.setFont(font1);
			gc.fillText("You Lose", WIDTH / 2 - 100, HEIGHT / 2);
			break;
		case INTRO:
			intro.render(gc);
			break;
		case TUT:
			tut.render(gc);
			break;
		default:
			break;
		}

	}

	/*
	 * Begin boiler-plate code... [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Initial setup
		initialize();
		setHandlers(theScene);

		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS), e -> {
			// update position
				update();
				// draw frame
				render(gc);
			});
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
	/*
	 * ... End boiler-plate code
	 */
}