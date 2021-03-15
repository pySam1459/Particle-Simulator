package main;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.PSystem.Spread;

public class Config {
	
	// Default Variables
	public int n = 100000;
	public double G = 90000.0;
	public Spread spread = Spread.random;
	public double xoff=100, yoff=100;
	public boolean boxed = true;
	public double resistance = 0.0;
	public Color particleColour = Color.ORANGE;
	public Color backgroundColour = Color.BLACK;
	
	public Config() {
		loadConfig();
		
	}
	
	private void loadConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("system.cfg")));
			String line;
			String[] keyValue, p2;
			Color k;
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
					
				case "offset":
					p2 = keyValue[1].split(",");
					if(p2[0].matches("\\d+") && p2[1].matches("\\d+")) {
						this.xoff = Integer.parseInt(p2[0]);
						this.yoff = Integer.parseInt(p2[1]);
					}
					break;
					
				case "dim":
					p2 = keyValue[1].split(",");
					if(p2[0].matches("\\d+") && p2[1].matches("\\d+")) {
						Window.dim = new Dimension(Integer.parseInt(p2[0]), Integer.parseInt(p2[1]));
						Window.getWindow().setSize();
					}
					break;
					
				case "boxed":
					this.boxed = "true".equals(keyValue[1]);
 					break;
 					
				case "resistance":
					if(keyValue[1].matches("\\d*\\.\\d+")) {
						this.resistance = Double.parseDouble(keyValue[1]);
					}
					break;
					
				case "colour":
					p2 = keyValue[1].split(",");
					k = convertToColour(p2);
					if(k != null) { this.particleColour = k; }
					break;
					
				case "background":
					p2 = keyValue[1].split(",");
					k = convertToColour(p2);
					if(k != null) { this.backgroundColour = k; }
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
	
	private Color convertToColour(String[] parts) {
		if(parts[0].matches("\\d+") && parts[1].matches("\\d+") && parts[2].matches("\\d+")) {
			if(parts.length == 4 && parts[3].matches("\\d+")) {
				return new Color(Integer.parseInt(parts[0]),
						Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]),
						Integer.parseInt(parts[3]));
			} else {
				return new Color(Integer.parseInt(parts[0]),
						Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]));
			}	
		}
		return null;
	}

}
