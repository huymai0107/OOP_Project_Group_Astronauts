package OOP_Project.state;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.util.ArrayList;

import OOP_Project.game_screen.GameScreen;
import OOP_Project.menu_screen.MenuScreen;

public class StateMachine {

	private ArrayList<SuperStateMachine> states = new ArrayList<SuperStateMachine>();
	private Canvas canvas;
	private byte selectState = 0;
	
	public StateMachine(Canvas canvas){
		SuperStateMachine game = new GameScreen(this);
		SuperStateMachine menu = new MenuScreen(this);
		states.add(menu);
		states.add(game);
		
		this.canvas = canvas;
	}
	
	public void draw(Graphics2D g){
		states.get(selectState).draw(g);
	}
	
	public void update(double delta){
		states.get(selectState).update(delta);
	}
	
	public void setState(byte i){
		for(int r = 0; r < canvas.getKeyListeners().length; r++)
			canvas.removeKeyListener(canvas.getKeyListeners()[r]);
		selectState = i;
		states.get(selectState).init(canvas);
	}

	public byte getStates() {
		return selectState;
	}
}
