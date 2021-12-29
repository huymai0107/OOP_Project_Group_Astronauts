package OOP_Project.enemy_types;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import OOP_Project.display.Display;
import OOP_Project.enemy_bullets.AddHealth;
import OOP_Project.enemy_bullets.AddWeapon;
import OOP_Project.enemy_bullets.EnemyBasicBullet;
import OOP_Project.enemy_bullets.EnemyBossWeapon;
import OOP_Project.enemy_bullets.EnemyUpgradedBullet;
import OOP_Project.game_screen.BasicBlocks;
import OOP_Project.game_screen.GameScreen;
import OOP_Project.game_screen.Player;
import OOP_Project.handler.EnemyBulletHandler;
import OOP_Project.sound.Sound;
import OOP_Project.sprite.SpriteAnimation;
import OOP_Project.timer.Timer;

public class Boss extends EnemyType{

	private double speed = 3d; 
	Random random;
	private Rectangle rect;
	private SpriteAnimation enemySprite;	
	private int shootTime;	
	private Timer shootTimer;
	
//	private int itemHealthTime;
//	private Timer itemHealthTimer;
//	
//	
	private int itemWeaponTime;
	private Timer itemWeaponTimer;
	private Sound explosionSound;
	private int health;
	public static int BOSSHEALTH = 50;
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void hit() {
		setHealth(getHealth()-1);
	}
	public Boss(double xPos, double yPos, int rows, int columns, EnemyBulletHandler bulletHandler){
		super(bulletHandler);
		setHealth(BOSSHEALTH);
		enemySprite = new SpriteAnimation(xPos, yPos, rows, columns, 300, "/OOP_Project/images/Boss.png");
		enemySprite.setWidth(150);
		enemySprite.setHeight(150);
		enemySprite.setLimit(2);
		
		this.setRect(new Rectangle((int) enemySprite.getxPos(), (int) enemySprite.getyPos(), enemySprite.getWidth(), enemySprite.getHeight()));
		enemySprite.setLoop(true);
		
		shootTimer = new Timer();
		shootTime = new Random().nextInt(12000);
		
//		itemHealthTimer = new Timer();
//		itemHealthTime = new Random().nextInt(50000);
//		
		itemWeaponTimer = new Timer();
		itemWeaponTime = new Random().nextInt(30000);
		explosionSound = new Sound("/OOP_Project/sounds/explosion.wav");
	}
	
	@Override
	public void draw(Graphics2D g) {

		enemySprite.draw(g);
		if(getHealth() <= BOSSHEALTH/4) 
		{
		g.setColor(Color.red);
		
		}
		else if(getHealth() <= BOSSHEALTH/2 )
		{
			g.setColor(Color.yellow);
		}
		else if(getHealth() <= BOSSHEALTH )
		{
			g.setColor(Color.green);
		}
		g.fillRect(250, 10, getHealth()*10, 15);



	}

	@Override
	public void update(double delta, Player player, BasicBlocks blocks) {
		enemySprite.update(delta);	
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		this.getRect().x = (int) enemySprite.getxPos();
		
		if (shootTimer.timerEvent(shootTime)) {
			getBulletHandler().addBullet(new EnemyBasicBullet(getRect().x, getRect().y));
			getBulletHandler().addBullet(new EnemyUpgradedBullet(getRect().x, getRect().y));
			getBulletHandler().addBullet(new EnemyBossWeapon(getRect().x, getRect().y));
			shootTime = new Random().nextInt(1500);
		}
		
//		if (itemHealthTimer.timerEvent(itemHealthTime)) {
//			getBulletHandler().addBullet(new AddHealth(getRect().x, getRect().y));
//			itemHealthTime = new Random().nextInt(50000);
//		}
//		
		if (itemWeaponTimer.timerEvent(itemWeaponTime)) {
			getBulletHandler().addBullet(new AddWeapon(getRect().x, getRect().y));
			itemWeaponTime = new Random().nextInt(30000);
		}
		
		if(this.getRect().y >=				
				blocks.getBlockHeight())
			player.setHealth(0);
	}

	@Override
	public void changeDirection(double delta) {
		speed *= -1.0d;
		float test = (float) (delta * speed);
		System.out.println(test);
		enemySprite.setxPos(enemySprite.getxPos() - (delta * speed));
		this.getRect().x = (int) enemySprite.getxPos();
		
		enemySprite.setyPos(enemySprite.getyPos() + (delta * 2));
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
			if(enemys != null && player.playerWeapons.weapons.get(w).collisionRect(((Boss)enemys.get(i)).getRect())) 
			{	hit();
				if(getHealth() == 0)
				{
					enemySprite.resetLimit();
					enemySprite.setAnimationSpeed(60);
					enemySprite.setPlay(true, true);
					if(GameScreen.comboCheck)
						GameScreen.SCORE += (4000 + (int)(Math.random()*((8000-4000) + 1)))*2;
					else GameScreen.SCORE += 4000 + (int)(Math.random()*((8000-4000) + 1));
					player.setHealth(player.getHealth()+ 3);
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
		else return true;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

}
