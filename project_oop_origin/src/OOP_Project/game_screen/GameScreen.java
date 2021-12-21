package OOP_Project.game_screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import OOP_Project.display.Display;
import OOP_Project.display.imageLoader;
import OOP_Project.handler.EnemyBulletHandler;
import OOP_Project.levels.Level1;
import OOP_Project.state.StateMachine;
import OOP_Project.state.SuperStateMachine;
import OOP_Project.timer.TickTimer;

public class GameScreen extends SuperStateMachine {
	
	private Player player;
	private BasicBlocks blocks;
	private Level1 level;

	private EnemyBulletHandler bulletHandler;
	
	public static int SCORE = 0;
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
		
		if (level.isGameOver()) {
			gameOverTimer.tick(delta);
			if (gameOverTimer.isEventReady()) {
				level.reset();
				blocks.reset();
				getStateMachine().setState((byte) 0);
				SCORE = 0;
			}
		}
		
		if (level.isComplete()) {
			completeTimer.tick(delta);
			if (completeTimer.isEventReady()) {
				level.resetComplete();
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setFont(new Font("Upheaval TT (BRK)", Font.PLAIN, 30));
		g.setColor(Color.red);
		g.drawString("Score: " + SCORE, 7, 17);
		g.setColor(Color.yellow);
		g.drawString("Score: " + SCORE, 8, 18);
		
		g.setColor(Color.red);
		if(player.getHealth() == 3)
		{
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Heart.png"), 5, Display.HEIGHT - 20, null);
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Heart.png"), 25, Display.HEIGHT - 20, null);
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Heart.png"), 45, Display.HEIGHT - 20, null);
		}
		else if(player.getHealth() == 2)
		{
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Heart.png"), 5, Display.HEIGHT - 20, null);
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Heart.png"), 25, Display.HEIGHT - 20, null);
		}
		else if(player.getHealth() == 1)
		{
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Heart.png"), 5, Display.HEIGHT - 20, null);
		}

		
		blocks.draw(g);
		player.draw(g);
		level.draw(g);

		 
		if (level.isGameOver()) {
			g.setFont(new Font("Upheaval TT (BRK)", Font.BOLD, 64));
			g.setColor(Color.YELLOW);
			String gameOver = "GAME OVER!";
			int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
			g.drawString(gameOver, (Display.WIDTH/2)-(gameOverWidth/2), Display.HEIGHT/2);
			g.setColor(Color.RED);
			g.drawString(gameOver, (Display.WIDTH/2)-(gameOverWidth/2)+2, (Display.HEIGHT/2)+2);
		}
		
		if (level.isComplete()) {
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

}
