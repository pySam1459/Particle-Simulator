package main;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {

	public boolean left=false, right=false, prevleft=false, prevright=false;
	private Point p1, p2;
	
	public Mouse() {
		Window.getWindow().addMouseListener(this);
		
	}
	
	@Override
	public void mousePressed(MouseEvent m) {
		if(m.getButton() == MouseEvent.BUTTON1) {
			left = true;
		} else if(m.getButton() == MouseEvent.BUTTON3) {
			right = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		if(m.getButton() == MouseEvent.BUTTON1) {
			left = false;
		} else if(m.getButton() == MouseEvent.BUTTON3) {
			right = false;
		}
	}
	
	public Pointf getXY() {
		p1 = MouseInfo.getPointerInfo().getLocation();
		p2 = Window.getWindow().getLocationOnScreen();
		return new Pointf(p1.x-p2.x, p1.y-p2.y);
	}
	
	public void update() {
		this.prevleft = left;
		this.prevright = right;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}

}
