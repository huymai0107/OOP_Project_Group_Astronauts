package OOP_Project.enemy_bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import OOP_Project.display.Display;
import OOP_Project.display.imageLoader;
import OOP_Project.game_screen.BasicBlocks;
import OOP_Project.game_screen.Player;

public class EnemyBossWeapon extends EnemyWeaponType{

	private Rectangle bullet;
	private double speed = 2d;
	private int xPos, yPos;
	
	public EnemyBossWeapon(double xPos, double yPos) {
		bullet = new Rectangle((int) xPos, (int) yPos, 35, 35);
		setxPos((int) xPos); 
		setyPos((int) yPos); 
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (bullet == null) {
			return;
		}
		g.setColor(new Color(255, 138, 0));
		g.fillRect((int) xPos, (int) yPos, 35, 35);
	}

	@Override
	public void update(double delta, BasicBlocks blocks, Player player) {
		if (bullet == null) {
			return;
		}
		setyPos((int) (getyPos() + (delta * speed))); 
		bullet.y = getyPos();
		
		isOutofBounds();
		wallCollide(blocks);
	}

	@Override
	public boolean collision(Rectangle rect) {
		if (bullet != null && bullet.intersects(rect)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean destory() {
		return false;
	}

	@Override
	protected void wallCollide(BasicBlocks blocks) {
		if (bullet == null) {
			return;
		}
		
		for (int w = 0; w < blocks.wall.size(); w++) {
			if(bullet.intersects(blocks.wall.get(w))) {
				for(int i = 0; i <= 3; i++)
					blocks.wall.remove(w + i );
				bullet = null;
				break;
			}
		}
	}

	@Override
	protected void isOutofBounds() {
		if(bullet != null && bullet.y < 0 || bullet.y > Display.HEIGHT || bullet.x < 0 || bullet.x > Display.WIDTH){
			bullet = null;
		}
	}

	public Rectangle getBullet() {
		return bullet;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}

//Like and subscribe kenh!
