package OOP_Project.enemy_bullets;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import OOP_Project.display.Display;
import OOP_Project.display.imageLoader;
import OOP_Project.game_screen.BasicBlocks;
import OOP_Project.game_screen.Player;

public class AddHealth extends EnemyWeaponType{

	private Rectangle bullet;
	private double speed = 1d;
	private int xPos, yPos;
	
	public AddHealth(double xPos, double yPos) {
		bullet = new Rectangle((int) xPos, (int) yPos, 20, 20);
		setxPos((int) xPos); 
		setyPos((int) yPos); 
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (bullet == null) {
			return;
		}	
		g.drawImage(imageLoader.loadImage("/OOP_Project/images/Heart.png"), xPos, yPos, 20, 20, null);
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
		
		
		if(collision(player.getRect()))
			player.setHealth(player.getHealth()+2);
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
		// this item can go throught the wall
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
