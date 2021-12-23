package OOP_Project.enemy_types;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import OOP_Project.display.Display;
import OOP_Project.enemy_bullets.EnemyBasicBullet;
import OOP_Project.enemy_bullets.EnemyUpgradedBullet;
import OOP_Project.game_screen.BasicBlocks;
import OOP_Project.game_screen.GameScreen;
import OOP_Project.game_screen.Player;
import OOP_Project.handler.EnemyBulletHandler;
import OOP_Project.sound.Sound;
import OOP_Project.sprite.SpriteAnimation;
import OOP_Project.timer.Timer;

public class EnemyTypeBasic extends EnemyType{

	private double speed = 1d; 
	Random random;
	private Rectangle rect;
	private SpriteAnimation enemySprite;
	
	private int shootTime;
	private Timer shootTimer;
	
	private Sound explosionSound;
	
	public EnemyTypeBasic(double xPos, double yPos, int rows, int columns, EnemyBulletHandler bulletHandler){
		super(bulletHandler);
		
		enemySprite = new SpriteAnimation(xPos, yPos, rows, columns, 300, "/OOP_Project/images/Invaders.png");
		enemySprite.setWidth(25);
		enemySprite.setHeight(25);
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
			getBulletHandler().addBullet(new EnemyBasicBullet(getRect().x, getRect().y));
			shootTime = new Random().nextInt(20000);
		}
	}

	@Override
	public void changeDirection(double delta) {
		speed *= -1.0d;
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		this.getRect().x = (int) enemySprite.getxPos();

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
			if(enemys != null && player.playerWeapons.weapons.get(w).collisionRect(((EnemyTypeBasic)enemys.get(i)).getRect())) 
			{
				enemySprite.resetLimit();
				enemySprite.setAnimationSpeed(60);
				enemySprite.setPlay(true, true);
				GameScreen.SCORE += 2 +(int)(Math.random()*((10-2) + 1));
				return true;
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
