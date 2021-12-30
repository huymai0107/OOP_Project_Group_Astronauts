package OOP_Project.enemy_types;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import OOP_Project.game_screen.BasicBlocks;
import OOP_Project.game_screen.Player;
import OOP_Project.handler.EnemyBulletHandler;

public abstract class EnemyType {

	private EnemyBulletHandler bulletHandler;
	
	public EnemyType(EnemyBulletHandler bulletHandler) {
		this.bulletHandler = bulletHandler;
	}

	public abstract void draw(Graphics2D g);
	// movement
	public abstract void update(double delta, Player player, BasicBlocks blocks);
	//change direction every time colliding to the wall then go down
	public abstract void changeDirection(double delta);
	
	
	public abstract boolean deathScene();
	//check collide
	public abstract boolean collide(int i, Player player, BasicBlocks blocks, ArrayList<EnemyType> enemys);
	public abstract boolean isOutOfBounds();
	//hit box
	public abstract Rectangle getRect();
	
	public EnemyBulletHandler getBulletHandler() {
		return bulletHandler;
	}
	// all of enemy types base on the basic type 

}
