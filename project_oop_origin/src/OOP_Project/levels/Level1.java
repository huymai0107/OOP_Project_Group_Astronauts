package OOP_Project.levels;

import java.awt.Graphics2D;
import java.util.ArrayList;

import OOP_Project.enemy_types.Boss;
import OOP_Project.enemy_types.EnemyType;
import OOP_Project.enemy_types.EnemyTypeBasic;
import OOP_Project.enemy_types.EnemyMiniBossBasic;
import OOP_Project.enemy_types.EnemyTypeUpgraded;
import OOP_Project.enemy_types.EnemyMiniBossUpgraded;
import OOP_Project.game_screen.BasicBlocks;
import OOP_Project.game_screen.Player;
import OOP_Project.handler.EnemyBulletHandler;
import OOP_Project.sound.Sound;

public class Level1 implements SuperLevel{

	private Player player;
	private ArrayList<EnemyType> enemies = new ArrayList<EnemyType>();
	private EnemyBulletHandler bulletHandler;

	private Sound beep, boop;
	private boolean beepboop;
	
	public Level1(Player player, EnemyBulletHandler bulletHandler){
		this.player = player;
		this.bulletHandler = bulletHandler;
		addEnemies();
		
		beep = new Sound("/OOP_Project/sounds/beep.wav");
		boop = new Sound("/OOP_Project/sounds/boop.wav");
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(enemies == null)
			return;
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		bulletHandler.draw(g);
	}

	@Override
	public void update(double delta, BasicBlocks blocks) {
		if(enemies == null)
			return;
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update(delta, player, blocks);
		}
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).collide(i, player, blocks, enemies);
		}
		hasDirectionChange(delta);
		bulletHandler.update(delta, blocks, player);
	}

	@Override
	public void hasDirectionChange(double delta) {
		if(enemies == null)
			return;
		
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).isOutOfBounds()){
				changeDurectionAllEnemys(delta);
			}
		}
	}

	@Override
	public void changeDurectionAllEnemys(double delta) {
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).changeDirection(delta);
		}
		if (beepboop) {
			beepboop = false;
			boop.play();
		} else {
			beepboop = true;
			beep.play();
		}
	}

	@Override
	public boolean isGameOver() {
		return player.getHealth() <= 0;
	}

	@Override
	public void destory() {
		
	}
	@Override
	public void reset() {
		player.resetfull();
		enemies.clear();
		addEnemies();		
		bulletHandler.reset();
	}
	@Override
	public void resetStage1() {
		player.reset();
		enemies.clear();
		addEnemies();		
		bulletHandler.reset();
	}
	
	@Override
	public void resetStage2() {
		player.reset();
		enemies.clear();
		addEnemiesUpgraded();		
		bulletHandler.reset();
	}
	@Override
	public void resetStage3() {
		player.reset();
		enemies.clear();
		addBoss();		
		bulletHandler.reset();
	}
	public void addBoss() {
				EnemyType e = new Boss(150 , 25 , 1 , 3, bulletHandler);
				enemies.add(e);	
	}
	public void addEnemiesUpgraded() {
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 7; x++){
				
				if(y == 2 && x == 3) {					
					EnemyType us = new EnemyMiniBossUpgraded(150 + (x * 80), 25 + (y * 50), 1 , 3, bulletHandler);
					enemies.add(us);
					continue;	
				}
				EnemyType e = new EnemyTypeUpgraded(150 + (x * 80), 25 + (y * 50), 1 , 3, bulletHandler);
				enemies.add(e);
			}
		}
	}	
	public void addEnemies() 
	{
		for(int y = 0; y < 5; y++)
		{
			for(int x = 0; x < 11; x++){

				if(y == 2 && x == 5) {					
					EnemyType s = new EnemyMiniBossBasic(150 + (x * 40), 25 + (y * 40), 1 , 3, bulletHandler);
					enemies.add(s);
					continue;	
				}
				EnemyType e = new EnemyTypeBasic(150 + (x * 40), 25 + (y * 40), 1 , 3, bulletHandler);				
				enemies.add(e);
			}		
		}
		
		
//		EnemyType s = new EnemyMiniBossBasic(150 + (1 * 40), 25 + (1 * 40), 1 , 3, bulletHandler);
//		enemies.add(s);

	}
	

	@Override
	public boolean isComplete() {
		return enemies.isEmpty();
	}
	
	@Override
	public boolean isCompleteBanner() {
		return enemies.isEmpty();
		
	}

}
