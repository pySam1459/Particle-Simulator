package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Keyboard extends HashMap<Integer, Boolean> implements KeyListener {

	private static final long serialVersionUID = 3592956128040680657L;
	
	public Keyboard() {
		Window.getWindow().addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent k) {
		put(k.getKeyCode(), true);

	}

	@Override
	public void keyReleased(KeyEvent k) {
		put(k.getKeyCode(), false);

	}
	
	public boolean get(int k) {
		if(containsKey(k)) {
			return super.get(k);
			
		} return false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
