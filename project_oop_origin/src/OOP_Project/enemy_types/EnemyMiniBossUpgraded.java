package OOP_Project.enemy_types;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import OOP_Project.display.Display;
import OOP_Project.enemy_bullets.EnemyBossWeapon;
import OOP_Project.game_screen.BasicBlocks;
import OOP_Project.game_screen.GameScreen;
import OOP_Project.game_screen.Player;
import OOP_Project.handler.EnemyBulletHandler;
import OOP_Project.sound.Sound;
import OOP_Project.sprite.SpriteAnimation;
import OOP_Project.timer.Timer;

public class EnemyMiniBossUpgraded extends EnemyType{

	private double speed = 1d; 
	Random random;
	private Rectangle rect;
	private SpriteAnimation enemySprite;
	
	private int shootTime;
	private Timer shootTimer;
	
	private Sound explosionSound;
	
	private int health;
	public static int HEALTH = 3;
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void hit() {
		setHealth(getHealth()-1);
	}
	public EnemyMiniBossUpgraded(double xPos, double yPos, int rows, int columns, EnemyBulletHandler bulletHandler){
		super(bulletHandler);
		setHealth(HEALTH);
		enemySprite = new SpriteAnimation(xPos, yPos, rows, columns, 300, "/OOP_Project/images/InvadersUltraSpecial.png");
		enemySprite.setWidth(40);
		enemySprite.setHeight(40);
		enemySprite.setLimit(2);
		
		this.setRect(new Rectangle((int) enemySprite.getxPos(), (int) enemySprite.getyPos(), enemySprite.getWidth(), enemySprite.getHeight()));
		enemySprite.setLoop(true);
		
		shootTimer = new Timer();
		shootTime = new Random().nextInt(20000);
		
		explosionSound = new Sound("/OOP_Project/sounds/explosion.wav");
	}
	
	@Override
	public void draw(Graphics2D g) {
		enemySprite.draw(g);
	}

	@Override
	public void update(double delta, Player player, BasicBlocks blocks) {
		enemySprite.update(delta);	
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		this.getRect().x = (int) enemySprite.getxPos();
		
		if (shootTimer.timerEvent(shootTime)) {
			getBulletHandler().addBullet(new EnemyBossWeapon(getRect().x, getRect().y));
			shootTime = new Random().nextInt(8000);
		}
	}

	@Override
	public void changeDirection(double delta) {
		speed *= -1.05d;
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		this.getRect().x = (int) enemySprite.getxPos();
		
		enemySprite.setyPos(enemySprite.getyPos() + (delta * 7));
		this.getRect().y = (int) enemySprite.getyPos();
	}

	@Override
	public boolean deathScene() {
		if(!enemySprite.isPlay())
			return false;
		
		if(enemySprite.isSpriteAnimDestroyed()) {
			if (!explosionSound.isPlaying()) {
				explosionSound.play();
			}
			return true;
		}
		
		return false;
	}

	@Override
	public boolean collide(int i, Player player, BasicBlocks blocks, ArrayList<EnemyType> enemys) {
		
		if(enemySprite.isPlay()) {
			if(enemys.get(i).deathScene()) {
				
				enemys.remove(i);
			}
			return false;
		}
		
		for(int w = 0; w < player.playerWeapons.weapons.size(); w++) {
			if(enemys != null && player.playerWeapons.weapons.get(w).collisionRect(((EnemyMiniBossUpgraded)enemys.get(i)).getRect())) 
			{	hit();
				if(getHealth() == 0)
				{
					enemySprite.resetLimit();
					enemySprite.setAnimationSpeed(60);
					enemySprite.setPlay(true, true);
					GameScreen.SCORE += 100+(int)(Math.random()*((200-100) + 1));
					return true;
				}



			}
		}
		
		return false;
	}

	

	@Override
	public boolean isOutOfBounds() {
		if(rect.x > 0 && rect.x < Display.WIDTH - rect.width)
			return false;
		return true;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

}
