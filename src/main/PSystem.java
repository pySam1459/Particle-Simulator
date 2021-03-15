package main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

public class PSystem {
	
	enum Spread {
		random,
		uniform,
		block;
	}
	
	private Config config;
	private Pointf[] particles;
	private Mouse mouse;
	private Keyboard keyboard;
	
	public PSystem() {
		this.config = new Config();
		initParticles(config.spread);
		
		this.mouse = new Mouse();
		this.keyboard = new Keyboard();
		
	}
	
	private void initParticles(Spread spread) {
		int m;
		double m_d;
		
		switch(spread) {
		case random:
			Random r = new Random();
			r.setSeed(System.currentTimeMillis() - System.nanoTime());
			this.particles = new Pointf[config.n];
			for(int i=0; i<particles.length; i++) {
				particles[i] = new Pointf(r.nextInt(Window.dim.width), r.nextInt(Window.dim.height));
			}
			break;
			
		case uniform:
			m = (int)Math.sqrt(config.n);
			m_d = Math.sqrt(config.n);
			this.particles = new Pointf[m*m];
			for (int j=0; j<m; j++) {
				for(int i=0; i<m; i++) {
					particles[i + j*m] = new Pointf(Window.dim.width*i/m_d, Window.dim.height*j/m_d);
					
				}
			}
			break;
			
		case block:
			m = (int)Math.sqrt(config.n);
			m_d = Math.sqrt(config.n);
			this.particles = new Pointf[m*m];
			for (int j=0; j<m; j++) {
				for(int i=0; i<m; i++) {
					particles[i + j*m] = new Pointf(config.xoff + 0.5*i*m_d/Window.dim.width, 
													config.yoff + 0.5*j*m_d/Window.dim.height);
					
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
		double d2, F, mul;
		for(Pointf p: particles) {
			if(mouse.left || mouse.right) {
				d2 = p.distanceSquared(xy);
				mul = mouse.left ? 1 : -1;
				F = config.G*dt*mul / d2;
				p.vx += F * (xy.x - p.x); // possible /d?
				p.vy += F * (xy.y - p.y);
			}
			
			p.x += p.vx * dt;
			p.y += p.vy * dt;
			
			p.vx -= p.vx * config.resistance * dt;
			p.vy -= p.vy * config.resistance * dt;
			
			if(config.boxed) {
				if(p.x < 0 && p.vx < 0) {
					p.x = -p.x;
					p.vx *= -1;
				} else if(p.x > Window.dim.width && p.vx > 0) {
					p.x = 2*Window.dim.width - p.x;
					p.vx *= -1;
				} if(p.y < 0 && p.vy < 0) {
					p.y = -p.y;
					p.vy *= -1;
				} else if(p.y > Window.dim.height && p.vy > 0) {
					p.y = 2*Window.dim.height - p.y;
					p.vy *= -1;
				} 
			}
		}
		
	}
	
	private void checkReset() {
		if(keyboard.get(KeyEvent.VK_R)) {
			initParticles(config.spread);
			
		}
	}
	
	
	public void render(Graphics2D g) {
		g.setColor(config.backgroundColour);
		g.fillRect(0, 0, Window.dim.width, Window.dim.height);
		
		g.setColor(config.particleColour);
		for(Pointf p: particles) {
			g.fillRect((int)p.x, (int)p.y, 1, 1);
			
		}
	}

}
