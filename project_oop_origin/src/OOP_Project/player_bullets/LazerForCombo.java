package OOP_Project.player_bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import OOP_Project.display.Display;
import OOP_Project.display.imageLoader;
import OOP_Project.game_screen.BasicBlocks;

public class LazerForCombo extends PlayerWeaponType{

	private Rectangle bullet;
	private final double speed = 2.0d;
	
	public LazerForCombo(double xPos, double yPos){
		this.setxPos(xPos);
		this.setyPos(yPos);
		
		this.bullet = new Rectangle((int) getxPos(),(int) getyPos(), 40, 40 );
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(bullet == null)
			return;
		g.drawImage(imageLoader.loadImage("/OOP_Project/images/LazerForCombo.png"), (int) getxPos(),(int) getyPos(), 30, 30, null);
		g.setColor(Color.red);
	}

	@Override
	public void update(double delta, BasicBlocks blocks) {
		if(bullet == null)
			return;
		
		this.setyPos(getyPos() - (delta * speed));
		bullet.y = (int) this.getyPos();
		wallCollide(blocks);
		isOutofBounds();
	}

	@Override
	public boolean collisionRect(Rectangle rect) {
		if(this.bullet == null)
			return false;
		
		if(bullet.intersects(rect)){
			this.bullet = null;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean collisionPoly(Polygon poly) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean destory() {
		if(bullet == null)
			return true;
		
		return false;
	}

	@Override
	protected void wallCollide(BasicBlocks blocks) {
		for(int i = 0; i < blocks.wall.size(); i++){
			if(bullet.intersects(blocks.wall.get(i))){
				blocks.wall.remove(i);
				bullet = null;
				return;
			}
		}
	}

	@Override
	protected void isOutofBounds() {
		if(this.bullet == null)
			return;
		
		if(bullet.y < 0 || bullet.y > Display.HEIGHT || bullet.x < 0 || bullet.x > Display.WIDTH){
			bullet = null;
		}
	}

}
