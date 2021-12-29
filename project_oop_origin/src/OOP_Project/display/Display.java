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
				g.setColor(new Color(20, 51, 71));
				g.fillRect(0, 0, WIDTH, HEIGHT);
		

				g.setColor(new Color(26, 97, 142));
				for(int i = 0; i <=4; i++)
				g.fillOval(i*200, HEIGHT-250, 300, 300);
				
				
				g.setColor(new Color(52, 140, 196));				
				for(int i = 0; i <=4; i++)
					g.fillOval( HEIGHT - i*200, HEIGHT*3/4, 300, 300);
				g.setColor(new Color(207, 147, 60));
				int baseline = 0;
				g.fillOval(WIDTH-100,baseline - 100, 200, 200);
				g.setColor(new Color(223, 137, 36));
				g.fillOval(WIDTH-65,baseline - 100, 200, 200);
				
				
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


