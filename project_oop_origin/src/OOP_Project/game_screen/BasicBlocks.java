package OOP_Project.game_screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BasicBlocks {

	public ArrayList<Rectangle> wall = new ArrayList<Rectangle>();
	
	public BasicBlocks(){
		
		basicBlocks(75, 450);
		basicBlocks(275, 450);
		basicBlocks(475, 450);
		basicBlocks(675, 450);
	}
	
	public void draw(Graphics2D g){
		Color c = new Color(125, 52, 211);
		g.setColor(c);
		for(int i = 0; i < wall.size(); i++){
			g.fill(wall.get(i));
		}
	}
	
	public void basicBlocks(int xPos, int yPos){
		int wallWidth = 1;
		int x = 0;
		int y = 0;
		
		for(int i = 0; i < 13; i++){
			if((14 + (i * 2) + wallWidth < 22 + wallWidth)){
				row(14 + (i * 2) + wallWidth, xPos - (i * 3), yPos + (i * 3));
				x = (i * 3) + 3;
			}else{
				row(22 + wallWidth, xPos - x, yPos + (i * 3));
				y = (i * 3);
			}
		}
		
//		//Left side.
//		for(int i = 0; i < 5; i++){
//			row(8 + wallWidth - i, xPos - x, (yPos + y) + (i * 3));
//		}
//		
//		//Right side.
//		for(int i = 0; i < 5; i++){
//			row(8 + wallWidth - i, (xPos - x + (14 * 3)) + (i * 3), (yPos + y) + (i * 3));
//		}
	}
	
	public void row(int rows, int xPos, int yPos){
		for(int i = 0; i < rows; i++){
			Rectangle brick = new Rectangle(xPos + (i * 3), yPos, 3, 3);
			wall.add(brick);
		}
	}
	
	public void reset(){
		wall.clear();
		
		basicBlocks(75, 450);
		basicBlocks(275, 450);
		basicBlocks(475, 450);
		basicBlocks(675, 450);
	}
}
