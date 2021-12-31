package OOP_Project.game_screen;

import java.awt.Graphics2D;
import java.util.ArrayList;

import OOP_Project.explosion.ExplosionManager;
import OOP_Project.player_bullets.Lazer;
import OOP_Project.player_bullets.LazerForCombo;
import OOP_Project.player_bullets.LazerLastForm;
import OOP_Project.player_bullets.LazerUpgraded;
import OOP_Project.player_bullets.MachineGun;
import OOP_Project.player_bullets.PlayerWeaponType;
import OOP_Project.sound.Sound;
import OOP_Project.timer.Timer;

public class PlayerWeapons {

	private Timer timer;
	private ExplosionManager explosionManager;
	public ArrayList<PlayerWeaponType> weapons = new ArrayList<PlayerWeaponType>();
	private Sound shootSound;
	
	public PlayerWeapons(){
		explosionManager = new ExplosionManager();
		timer = new Timer();
		shootSound = new Sound("/OOP_Project/sounds/shoot.wav");
	}
	
	public void draw(Graphics2D g){
		
		explosionManager.draw(g);
		for(int i = 0; i < weapons.size(); i++){
			weapons.get(i).draw(g);
		}
	}
	
	public void update(double delta, BasicBlocks blocks){
		
		explosionManager.update(delta);
		for(int i = 0; i < weapons.size(); i++){
			weapons.get(i).update(delta, blocks);
			if(weapons.get(i).destory()) {
				ExplosionManager.createPixelExplosion(weapons.get(i).getxPos(), weapons.get(i).getyPos());
				weapons.remove(i);
			}
		}
	}
	
	public void shootBullet(double xPos, double yPos){
		if(timer.timerEvent(250)) {
			if (shootSound.isPlaying()) {
				shootSound.stop();
			}
			shootSound.play();
			
			//bullettype follow to weaponCount
			if(GameScreen.comboCheck)
			{
				weapons.add(new LazerForCombo(xPos + 10, yPos + 15));
			}
			else {
				if(GameScreen.weaponcount <= 0)
				{
					weapons.add(new MachineGun(xPos + 22, yPos + 15, 5, 5));
				}	
				else if(GameScreen.weaponcount == 1)
					weapons.add(new Lazer(xPos + 22, yPos + 15));

				else if(GameScreen.weaponcount == 2)
					weapons.add(new LazerUpgraded(xPos + 15, yPos + 15));
				else if(GameScreen.weaponcount > 2)
					weapons.add(new LazerLastForm(xPos + 10, yPos + 15));
			}
			
			
		}
	}

	public void reset() {
		weapons.clear();
	}
}
