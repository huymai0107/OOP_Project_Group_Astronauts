package OOP_Project.game_screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import OOP_Project.display.Display;
import OOP_Project.display.imageLoader;
import OOP_Project.handler.EnemyBulletHandler;
import OOP_Project.levels.Level1;
import OOP_Project.state.StateMachine;
import OOP_Project.state.SuperStateMachine;
import OOP_Project.timer.TickTimer;

public class GameScreen extends SuperStateMachine implements KeyListener {
	private Player player;
	private BasicBlocks blocks;
	private Level1 level;

	private EnemyBulletHandler bulletHandler;
	
	public static int SCORE = 0;
	public static int weaponcount = 0;
	public static int combo = 0;
	public static boolean comboCheck = false;

	private TickTimer gameOverTimer = new TickTimer(180);
	private TickTimer completeTimer = new TickTimer(180);
	
	public GameScreen(StateMachine stateMachine){
		super(stateMachine);
		blocks = new BasicBlocks();
		bulletHandler = new EnemyBulletHandler();
		player = new Player(Display.WIDTH/2-50, Display.HEIGHT-75, 50, 50, blocks);
		level = new Level1(player, bulletHandler);

	}
	
	@Override
	public void update(double delta) {
		
		player.update(delta);
		level.update(delta, blocks);
	// reset all stats	
		if (level.isGameOver()) {
			gameOverTimer.tick(delta);
			if (gameOverTimer.isEventReady()) {
				level.reset();
				blocks.reset();
				getStateMachine().setState((byte) 0);
				SCORE = 0;
				weaponcount = 0;
				combo = 0;
			}
		}
// after finishing first stage, the next stage will be chosen randomly between boss and upgraded enemy
		if (level.isComplete() ) {
			Random random = new Random();
			boolean n = random.nextBoolean();	
				if(n)					
					{
					completeTimer.tick(delta);
					if (completeTimer.isEventReady())
						level.resetStage2();
					}
	
				else {
					completeTimer.tick(delta);
					if (completeTimer.isEventReady())
						level.resetStage3();
					}
				}						
				

			}
		

	@Override
	public void draw(Graphics2D g) {
		//draw health score
		g.setFont(new Font("Upheaval TT (BRK)", Font.PLAIN, 30));
		g.setColor(Color.red);
		g.drawString("Score: " + SCORE, 7, 17);
		g.setColor(Color.yellow);
		g.drawString("Score: " + SCORE, 8, 18);
		
		//draw health counter
			if(player.getHealth() >= 10)
			g.setColor(Color.cyan);
			else if(player.getHealth() >= 5)
				g.setColor(new Color(90, 245, 66));	
			else if(player.getHealth() >= 2)
				g.setColor(new Color(245, 227, 66));
			else g.setColor(new Color(245, 66, 66));
			g.drawString(Integer.toString(player.getHealth()), 6,Display.HEIGHT - 25);
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Heart.png"), 5, Display.HEIGHT - 20, null);
			
		//draw upgrade counter
			g.setColor(Color.white);
			g.drawString(Integer.toString(GameScreen.weaponcount), 37,Display.HEIGHT - 25);	
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Lazer.png"), 35, Display.HEIGHT - 21, null);
		
		blocks.draw(g);
		level.draw(g);
		player.draw(g);
		

		 //game over pop-up
		if (level.isGameOver()) {
			g.setFont(new Font("Upheaval TT (BRK)", Font.BOLD, 64));
			g.setColor(Color.YELLOW);
			String gameOver = "GAME OVER!";
			int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
			g.drawString(gameOver, (Display.WIDTH/2)-(gameOverWidth/2), Display.HEIGHT/2);
			g.setColor(Color.RED);
			g.drawString(gameOver, (Display.WIDTH/2)-(gameOverWidth/2)+2, (Display.HEIGHT/2)+2);
		}
		// finish stage pop-up		
		if (level.isCompleteBanner()) {
			g.setFont(new Font("Upheaval TT (BRK)", Font.BOLD, 60));
			g.setColor(Color.CYAN);
			String complete = "MISSION ACCOMPLISHED!";
			int completeWidth = g.getFontMetrics().stringWidth(complete);
			g.drawString(complete, (Display.WIDTH/2)-(completeWidth/2), (Display.HEIGHT/2));
			g.setColor(Color.BLUE);
			g.drawString(complete, (Display.WIDTH/2)-(completeWidth/2)+2, (Display.HEIGHT/2)+2);
		}
	}

	@Override
	public void init(Canvas canvas) {
		canvas.addKeyListener(player);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
