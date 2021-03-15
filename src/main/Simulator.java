package main;

import java.awt.Graphics2D;


public class Simulator implements Runnable {

	private static Simulator simulator;
	private Thread mainThread;
	private volatile boolean running = false;
	
	private Window window;
	private PSystem system;
	
	public Simulator() {
		this.window = new Window();
		this.system = new PSystem();
		
		start();
	}
	
	
	private void tick(double dt) {
		system.tick(dt);
		
	}
	
	private void render() {
		Graphics2D g = window.getGraphics2D();
		
		system.render(g);
		
		window.renderGraphics2D();
		g.dispose();
	}
	
	
	@Override
	public void run() {
		final double TPS = 30.0;
		double ns = 1000000000.0 / TPS, delta = 0.0;
		long now, lastTime = System.nanoTime();
		double dt = 0.0;
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			while(delta >= 1.0) {
				now = System.nanoTime();
				dt = (now - lastTime) / 1000000000.0;
				lastTime = now;
				
				tick(dt);
				render();
				delta--;			
			}
		}
	}
	
	public synchronized void start() {
		running = true;
		this.mainThread = new Thread(this, "Main Thread");
		mainThread.start();
	}
	
	public synchronized void stop() {
		running = false;
		mainThread.interrupt();
		
	}
	
	
	public static Simulator getSim() {
		return Simulator.simulator;
		
	}

	public static void main(String[] args) {
		Simulator.simulator = new Simulator();

	}

}
