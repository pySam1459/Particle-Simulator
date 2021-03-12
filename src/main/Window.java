package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = -4810618286807932601L;
	private static final String TITLE = "Particle Simulator";
	public static Dimension dim = new Dimension(1280, 1280);
	
	private static Window window;
	private JFrame frame;
	private BufferStrategy bs;
	
	public Window() {
		this.frame = new JFrame(TITLE);
		Window.window = this;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		setSize();
		frame.add(this);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				Simulator.getSim().stop();
				
			}
		});
	}
	
	public Graphics2D getGraphics2D() {
		bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			bs = this.getBufferStrategy();
		}
		
		return (Graphics2D) bs.getDrawGraphics();
	}
	
	
	public void renderGraphics2D() {
		bs.show();
		
	}
	
	public void setSize() {
		Insets insets = frame.getInsets();
		frame.setSize(dim.width+insets.left+insets.right, dim.height+insets.top+insets.bottom);
	}
	
	public static Window getWindow() {
		return Window.window;
	}

}
