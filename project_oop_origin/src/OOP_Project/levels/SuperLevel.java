package OOP_Project.levels;

import java.awt.Graphics2D;

import OOP_Project.game_screen.BasicBlocks;

public interface SuperLevel {

	void draw(Graphics2D g);
	void update(double delta, BasicBlocks blocks);
	void hasDirectionChange(double delta);
	void changeDurectionAllEnemys(double delta);
	
	boolean isGameOver();
	boolean isComplete();
	
	void destory();
	void reset();
	void resetComplete();
}
