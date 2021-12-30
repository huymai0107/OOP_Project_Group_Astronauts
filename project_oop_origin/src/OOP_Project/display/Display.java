package OOP_Project.display;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import OOP_Project.sound.Sound;
import OOP_Project.state.StateMachine;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static Sound backgroundMusic;
	BufferedImage pSprite;
	public static void main(String[] args) {
		
		//register font
		try {
			GraphicsEnvironment ge = 
			GraphicsEnvironment.getLocalGraphicsEnvironment();			
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Upheaval TT (BRK).ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("upheavtt.ttf")));
	   } catch (IOException|FontFormatException e) {
			//Handle exception
	   }
		
		
		//create a screen
		Display display = new Display();
		JFrame frame = new JFrame();
		frame.add(display);
		frame.pack();
		frame.setTitle("Space Invaders");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		backgroundMusic.loop();

		display.start();

	}

	private boolean running = false;
	private Thread thread;

	public synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;

		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int WIDTH = 800, HEIGHT = 600;
	public int FPS;

	public static StateMachine state;

	public Display() {
		this.setSize(WIDTH, HEIGHT);
		this.setFocusable(true);
		backgroundMusic = new Sound("/OOP_Project/sounds/test.wav");
		state = new StateMachine(this);
		state.setState((byte) 0); 
	}

	@Override
	public void run() {
		long timer = System.currentTimeMillis();
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		int frames = 0;

		this.createBufferStrategy(3);
		BufferStrategy bs = this.getBufferStrategy();
		while (running) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);

			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				FPS = frames;
				frames = 0;
				System.out.println("fps: "+ (FPS ));
			}

			draw(bs);
			update(delta);

			try {
				Thread.sleep(((lastLoopTime - System.nanoTime()) + OPTIMAL_TIME) / 1000000);
			} catch (Exception e) {
			}
			;
		}
	}

	public void draw(BufferStrategy bs) {
		do {
			do {
				
				Graphics2D g = (Graphics2D) bs.getDrawGraphics();
				g.setColor(new Color(13, 25, 43));
				g.fillRect(0, 0, WIDTH, HEIGHT);
		
				g.setColor(new Color(27, 52, 89));
				for(int i = 0; i <=5; i++)
				g.fillOval(WIDTH - i*200, HEIGHT-220, 400, 400);
				
				
				g.setColor(new Color(35, 68, 117));				
				for(int i = 0; i <=5; i++)
					g.fillOval( WIDTH - i*200,HEIGHT-150, 300, 300);
				g.setColor(new Color(41, 81, 140));	
				for(int i = 0; i <=10; i++)
					g.fillOval( WIDTH - i*150, HEIGHT-70, 200, 200);
				
				
				
				
				g.setColor(new Color(255, 255, 120));			
				g.fillOval(WIDTH-120,50, 50, 50);
				

				int nSpikes = 5;
				int[] ctrX = {
						632,
						490,
						498,
						426,
						586,
						80,
						574,
						559,
						199,
						161,
						728,
						372,
						433,
						447,
						521,
						641,
						643,
						641,
						696,
						685,
						122,	
						257,	
						128,	
						122,	
						305,
						251,	
						2,	
						282,	
						235,	
						49
				};
				int[] ctrY = {
						43,	
						259,	
						177,	
						357,
						44,	
						115,	
						123,	
						287,	
						177,
						173,	
						246,	
						275,	
						250,	
						352,
						52,	
						113,	
						352,	
						285,	
						320,
						170,
						193,
						328,
						176,
						311,
						127,
						365,
						81,
						306,
						365,
				};
		        
		        double RADIUS = 3;
		        double SPIKINESS = 0.5;
		        int nPoints = nSpikes * 2 + 1;

		        int xPoint[] = new int[nPoints];
		        int yPoint[] = new int[nPoints];

		        //generate star
		        g.setColor(Color.white);
		        for (int j = 0; j < ctrX.length-1; j++)
		        {
			        for (int i = 0; i < nPoints; i++)
			        {
			            double iRadius = (i % 2 == 0) ? RADIUS : (RADIUS * SPIKINESS);
			            double angle = (i * 360.0) / (2*nSpikes);
	
			            xPoint[i] = (int) (ctrX[j] + iRadius * Math.cos(Math.toRadians(angle - 90)));
			            yPoint[i] = (int) (ctrY[j] + iRadius * Math.sin(Math.toRadians(angle - 90)));
			        }
			        
			        	g.fillPolygon(xPoint, yPoint, nPoints);
		        }
		    
		
				
				
				state.draw(g);
				g.dispose();
			} while (bs.contentsRestored());
			bs.show();
		} while (bs.contentsLost());
	}

	public void update(double delta) {
		state.update(delta);
	}


}

class Numbers {
    Random ran;

    public Numbers() {
    }

    public int random(int max, int min){
    	return min +(int)(Math.random()*((max-min) + 1));
    }
}

