package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class PSystem {
	
	enum Spread {
		random,
		uniform;
	}
	
	// Default Variables
	private int n = 100000;
	private double G = 90000.0;
	private Spread spread = Spread.random;
	
	private Pointf[] particles;
	private Mouse mouse;
	private Keyboard keyboard;
	
	public PSystem() {
		loadConfig();
		
		this.particles = new Pointf[n];
		initParticles(spread);
		
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		
	}
	
	private void initParticles(Spread spread) {
		switch(spread) {
		case random:
			Random r = new Random();
			r.setSeed(System.currentTimeMillis() - System.nanoTime());
			
			for(int i=0; i<particles.length; i++) {
				particles[i] = new Pointf(r.nextInt(Window.dim.width), r.nextInt(Window.dim.height));
			}
			break;
		case uniform:
			int n = (int) Math.sqrt(particles.length);
			this.particles = new Pointf[n*n];
			for (int j=0; j<n; j++) {
				for(int i=0; i<n; i++) {
					particles[i + j*n] = new Pointf((double)i*n/Window.dim.width, (double)j*n/Window.dim.height);
					
				}
			}
			break;
		}
	}
	
	
	public void tick(double dt) {
		tickParticles(dt);
		checkReset();
		
	}
	
	private void tickParticles(double dt) {
		Pointf xy = mouse.getXY();
		double d, F;
		for(Pointf p: particles) {
			if(mouse.left) {
				d = p.distance(xy);
				F = G*dt / (d*d);
				p.vx += F * (xy.x - p.x); // possible /d?
				p.vy += F * (xy.y - p.y);
			}
			
			p.x += p.vx * dt;
			p.y += p.vy * dt;
			
			if(p.x < 0 || p.x > Window.dim.width) {
				p.vx *= -1;
			} if(p.y < 0 || p.y > Window.dim.height) {
				p.vy *= -1;
			}
		}
		
	}
	
	private void checkReset() {
		if(keyboard.get(KeyEvent.VK_R)) {
			this.particles = new Pointf[n];
			initParticles(spread);
			
		}
	}
	
	
	public void render(Graphics2D g) {
		g.setColor(Color.ORANGE);
		
		for(Pointf p: particles) {
			g.fillRect((int)p.x, (int)p.y, 1, 1);
			
		}
	}
	
	
	private void loadConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("res/system.cfg")));
			String line;
			String[] keyValue;
			while((line = br.readLine()) != null) {
				keyValue = line.replace(" ", "").split("=");
				switch(keyValue[0]) {
				case "n":
					if(keyValue[1].matches("\\d+")) {
						this.n = Integer.parseInt(keyValue[1]);
					
					}
					break;
				case "G":
					if(keyValue[1].matches("\\d*\\.\\d+")) {
						this.G = Double.parseDouble(keyValue[1]);
					
					}
					break;
				case "spread":
					if(Spread.valueOf(keyValue[1]) != null) {
						this.spread = Spread.valueOf(keyValue[1]);
					
					}
					break;
				default:
					break;
				}
			}
		} catch(IOException e) {
			System.out.println("Invalid 'system.cfg' file");
			e.printStackTrace();
		}
	}

}
