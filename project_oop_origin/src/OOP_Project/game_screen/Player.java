package OOP_Project.game_screen;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import OOP_Project.display.Display;

public class Player implements KeyListener{
	
	private final double speed = 2d;
	private int health;
	private BufferedImage pSprite;
	private Rectangle rect;
	private double xPos, yPos, startXPos, startYPos;
	private int width, height;
	private BasicBlocks blocks;
	
	private boolean left = false, right = false, shoot = false;
	
	public PlayerWeapons playerWeapons;
	
	public Player(double xPos, double yPos, int width, int height, BasicBlocks blocks){
		this.xPos = xPos;
		this.yPos = yPos;
		this.startXPos = xPos;
		this.startYPos = yPos;
		this.width = width;
		this.height = height;
		this.health = 3;
		
		rect = new Rectangle((int) xPos,(int) yPos+25, width, height-25);
		
		try{
			URL url = this.getClass().getResource("/OOP_Project/images/Player.png");
			pSprite = ImageIO.read(url);
		}
		catch(IOException e){};
		this.blocks = blocks;
		playerWeapons = new PlayerWeapons();
	}
	
	public void draw(Graphics2D g){
		g.drawImage(pSprite,(int) xPos,(int) yPos, width, height, null);
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
			playerWeapons.shootBullet(xPos, yPos, 5, 5);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			right = true;
		}else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			left = true;
		}
		
		if (key == KeyEvent.VK_SPACE){
			shoot = true;

		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
		{
			System.exit(0);			
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
		System.out.println("pressed");
		
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
// reset when losing
	public void resetfull() {
		health = 3;
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
