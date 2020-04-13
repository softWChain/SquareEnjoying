package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



public class Game extends Canvas implements Runnable,MouseMotionListener,MouseListener{
	
	public static int WIDTH = 600;
	public static int HEIGHT = 500;
	
	private Window window;
	private boolean running = false;
	private Thread thread;

	
	private Random rand = new Random();
	private ArrayList<Rectangle> kareler;
	private Rectangle selectedSquare = null;
	
	private int selectedX,selectedY;
	private int randomlyX,randomlyY,randomlyWitdh,randomlyHeight;
	
	
	public Game(){
		setFocusable(true);
		window = new Window(WIDTH,HEIGHT,this);
		
	}
	
	public void init(){
		kareler = new ArrayList<Rectangle>();
		
		kareler.add(new Rectangle(10,50,25,25));
		kareler.add(new Rectangle(50,120,25,25));
		kareler.add(new Rectangle(130,180,25,25));
		kareler.add(new Rectangle(250,300,25,25));
		addMouseListener(this);
		addMouseMotionListener(this);
	
	}
	
	public void tick(){
		

		
		

		
	}
	public void render(){
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.LIGHT_GRAY);
		
		Iterator<Rectangle> it = kareler.iterator();
		Rectangle r;
		while(it.hasNext()){
			
			r = it.next();
			
			if(selectedSquare != null && r.equals(selectedSquare)){
				g.setColor(Color.RED);
				g.drawRect(r.x, r.y, r.width, r.height);
				g.setColor(Color.LIGHT_GRAY);
			}else{
			g.drawRect(r.x, r.y, r.width, r.height);
		
			}
		}

		
		bs.show();
		g.dispose();
		
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		

		

		Iterator<Rectangle> it = kareler.iterator();
		Rectangle r;
		while(it.hasNext()){
			
			r = it.next();
			if(r.contains(e.getPoint())){
				selectedSquare = r;
				selectedX = (int) (e.getX() - selectedSquare.getX());
				selectedY = (int) (e.getY() - selectedSquare.getY());
				return;
			}		
		}
		selectedSquare = null;
	
	}


	@Override
	public void mouseDragged(MouseEvent e) {
	
		
		if(selectedSquare != null){
			//selectedSquare.setLocation(e.getX() - selectedSquare.width/2, e.getY() - selectedSquare.height/2); // you can touch by center
			selectedSquare.setLocation(e.getX() - selectedX, e.getY() - selectedY); // you can touch wherever you pressed on square
			return;
		}
		
		
	}
	


	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}
	

	
	public void run(){
		init();
		
		int FPS = 40;
		double targetFPS = 1000000000 / FPS;
		double delta = 0;
		
		long lastTime = System.nanoTime();
		long now;
		long timer = System.currentTimeMillis();
		
		int ticks=0;
		int updates = 0;
		
		while(running){
			
			now = System.nanoTime();
			delta +=(now - lastTime ) / targetFPS;
			lastTime = now;
			
			if(delta>=1){
				tick();
				ticks++;
				delta--;
			}
			render();
			updates++;
			
			if(System.currentTimeMillis() - timer >= 1000){
				timer += 1000;
				System.out.println("FPS : " + ticks + "  - UPDATES : " + updates);
				ticks = 0;
				updates = 0;
			}
			
			
		}
		
		stop();
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this,"ThreadGame");
		thread.start();
		
	}
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String args[]){
		new Game().start();
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	

}
