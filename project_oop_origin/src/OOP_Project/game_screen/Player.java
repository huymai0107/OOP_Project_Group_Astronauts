package OOP_Project.game_screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import OOP_Project.display.Display;
import OOP_Project.display.imageLoader;

public class Player implements KeyListener{
	
	private double speed = 2d;
	
	private int health;
	private boolean hitcheck = false;
	
	private Rectangle rect;
	private double xPos, yPos, startXPos, startYPos;
	private int width, height;
	private BasicBlocks blocks;
	private boolean left = false, right = false, shoot = false;

	public boolean isHitcheck() {
		return hitcheck;
	}
	public void setHitcheck(boolean hitcheck) {
		this.hitcheck = hitcheck;
	}
	
	
	
	public PlayerWeapons playerWeapons;
	
	public Player(double xPos, double yPos, int width, int height, BasicBlocks blocks){
		this.xPos = xPos;
		this.yPos = yPos;
		this.startXPos = xPos;
		this.startYPos = yPos;
		this.width = width;
		this.height = height;
		this.health = 5;
		
		rect = new Rectangle((int) xPos,(int) yPos+25, width, height-25);
		this.blocks = blocks;
		playerWeapons = new PlayerWeapons();
	}
	
	public void draw(Graphics2D g){
		//draw aircraft
		if(GameScreen.comboCheck)
			g.drawImage(imageLoader.loadImage("/OOP_Project/images/Player2.png"),(int) xPos,(int) yPos, width, height, null);
		else g.drawImage(imageLoader.loadImage("/OOP_Project/images/Player.png"),(int) xPos,(int) yPos, width, height, null);

		
		//draw combo bar	
			if(GameScreen.combo <= 10)
				g.setColor(new Color(221, 66, 245));
			else if(GameScreen.combo <= 20)
				g.setColor(new Color(242, 66, 245));
			else if(GameScreen.combo <= 30)
				g.setColor(new Color(245, 66, 176));			
		if(GameScreen.comboCheck)	
		{
			Font S = new Font("Upheaval TT (BRK)", Font.BOLD, 40);
			g.setFont(S);
			g.setColor(new Color(24, 97, 251));

			g.drawString("S", 30*5 + 10,Display.HEIGHT - 10) ;	
			g.setColor(new Color(254, 60, 50));
			g.drawString("S", 31*5 + 10 - 2,Display.HEIGHT - 10 - 2);
		}
		else g.fillRect(80, Display.HEIGHT - 20, GameScreen.combo*5, 5);
		
		playerWeapons.draw(g);
	}
	
	public void update(double delta){
		if(right && !left && xPos < Display.WIDTH-width){
			xPos += speed * delta;
			rect.x = (int) xPos;
		}if(!right && left && xPos > 10){
			xPos -= speed * delta;
			rect.x = (int) xPos;
		}
		playerWeapons.update(delta, blocks);
		if(shoot){
			playerWeapons.shootBullet(xPos, yPos);
		}
		
		
		 if(GameScreen.combo >= 30)
		{
			 GameScreen.comboCheck = true;
		}
		 else GameScreen.comboCheck = false;
			
		if(GameScreen.comboCheck)
		{	
			speed = 3.0d;
		}
		else speed = 2.5d;
		
			if(hitcheck)
		{
			GameScreen.combo = 0;
			hitcheck = false;
			if(GameScreen.comboCheck)
			{
				
			}
			else GameScreen.weaponcount -=1;
			GameScreen.comboCheck = false;
			
			if(GameScreen.weaponcount <0)
				GameScreen.weaponcount =0;
		}		
			
	}	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//key to move
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			right = true;
		}
		else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			left = true;
		}
		if (key == KeyEvent.VK_SPACE){
			shoot = true;
		}
		
		
		//exit
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
		{
			System.exit(0);			
		}
		
		// cheat
		if (e.getKeyCode() == KeyEvent.VK_H) 
		{	
			setHealth(getHealth()+1);
			GameScreen.weaponcount += 1;	
		}
		
		if (e.getKeyCode() == KeyEvent.VK_J) 
		{	
			GameScreen.combo = 30;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			right = false;
		}else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			left = false;
		}		
		if (key == KeyEvent.VK_SPACE){
			shoot = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void hit() {	
		setHealth(getHealth()-1);
	}
	
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	} 

	public Rectangle getRect() {
		return rect;
	}
// start game 
	public void resetfull() {
		health = 5;
		left = false;
		right = false;
		shoot = false;		
		xPos = startXPos;
		yPos = startYPos;
		rect.x = (int) xPos;
		rect.y = (int) yPos+25;
		playerWeapons.reset();
	}
//reset to another stage
	public void reset() {
//		health = 3;
		left = false;
		right = false;
		shoot = false;		
		xPos = startXPos;
		yPos = startYPos;
		rect.x = (int) xPos;
		rect.y = (int) yPos+25;
		playerWeapons.reset();
	}


}
