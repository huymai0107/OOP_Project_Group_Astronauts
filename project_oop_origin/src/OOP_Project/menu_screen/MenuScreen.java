	package OOP_Project.menu_screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import OOP_Project.display.Display;
import OOP_Project.state.StateMachine;
import OOP_Project.state.SuperStateMachine;

public class MenuScreen extends SuperStateMachine implements KeyListener {
	
	private Font tittleFont = new Font("Upheaval TT (BRK)", Font.BOLD, 64);
	private Font startFont = new Font("Upheaval TT (BRK)", Font.BOLD, 32);
	private String tittle = "Space Invaders";
	private String start = "Press Enter";
	
	public MenuScreen(StateMachine stateMachine) {
		super(stateMachine);
	}

	@Override
	public void update(double delta) {

	}

	@Override
	public void draw(Graphics2D g) {

		g.setFont(tittleFont);
		int tittleWidth = g.getFontMetrics().stringWidth(tittle);
		g.setColor(Color.yellow);
		g.drawString(tittle, ((Display.WIDTH/2)-(tittleWidth/2))-2, (Display.HEIGHT/2)-123);
		g.setColor(Color.green);
		g.drawString(tittle, (Display.WIDTH/2)-(tittleWidth/2), (Display.HEIGHT/2)-125);
		
		g.setFont(startFont);
		g.setColor(Color.white);
		int startWidth = g.getFontMetrics().stringWidth(start);
		g.drawString(start, (Display.WIDTH/2)-(startWidth/2), (Display.HEIGHT/2)+75);
	}

	@Override
	public void init(Canvas canvas) {
		canvas.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) 
		{
			
			getStateMachine().setState((byte) 1);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
