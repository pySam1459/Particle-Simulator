package main;

public class Pointf {

	public double x, y, vx, vy;
	
	public Pointf(double x, double y) {
		this.x = x;
		this.y = y;
		this.vx = 0.0;
		this.vy = 0.0;
	}
	
	public double distance(Pointf xy) {
		return Math.sqrt((xy.x-x)*(xy.x-x) + (xy.y-y)*(xy.y-y));
		
	}
	
	public double distanceSquared(Pointf xy) {
		return (xy.x-x)*(xy.x-x) + (xy.y-y)*(xy.y-y);
	}
}
