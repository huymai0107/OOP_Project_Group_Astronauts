	package OOP_Project.menu_screen;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import OOP_Project.display.Display;
import OOP_Project.state.StateMachine;
import OOP_Project.state.SuperStateMachine;

public class Storyline extends SuperStateMachine implements KeyListener {
	
	private Font storyFont = new Font("Upheaval TT (BRK)", Font.PLAIN, 28);
	private Font startFont = new Font("Upheaval TT (BRK)", Font.BOLD, 60);
	private String storyline1 = "In 2077, Unidentified Flying Objects appear.";
	private String storyline2 = "the safety of the Earth is Threatened."	;
	private String storyline3 = "to save the world from invasion";
	private String storyline4 = "- An Unique Aircraft is created.";
	private String start = "- Enter -";
	
	public Storyline(StateMachine stateMachine) {
		super(stateMachine);
	}

	@Override
	public void update(double delta) {

	}

	@Override
	public void draw(Graphics2D g) {
		g.setFont(storyFont);
		g.setColor(Color.cyan);
		g.drawString(storyline1, 50, 100);
		g.drawString(storyline2, 50, 120);
		g.drawString(storyline3, 50, 140);
		g.drawString(storyline4, 50, 160);
		
		
		g.setFont(startFont);
		g.setColor(Color.red);
		int startWidth = g.getFontMetrics().stringWidth(start);
		g.drawString(start, (Display.WIDTH/2)-(startWidth/2), (Display.HEIGHT/2)+150);
		g.setColor(Color.yellow);
		g.drawString(start, (Display.WIDTH/2)-(startWidth/2) - 2, (Display.HEIGHT/2)+150 - 2);
		
		


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
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
		{
			System.exit(0);			
		}
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			getStateMachine().setState((byte) 2);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
