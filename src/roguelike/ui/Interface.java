package roguelike.ui;

import asciiPanel.AsciiPanel;
import roguelike.graphics.AsciiCamera;
import roguelike.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Interface extends JFrame implements KeyListener, MouseListener {

	private static final long serialVersionUID = 6408617006915516474L;
	
	private AsciiPanel terminal;
	private AsciiCamera camera;
	private Queue<InputEvent> inputQueue;
	
	private int screenWidth;
	private int screenHeight;

	
    public Interface(int screenWidth, int screenHeight, Rectangle mapDimensions) {
    	super("Roguelike");
    	
    	this.screenWidth = screenWidth;
    	this.screenHeight = screenHeight;
    	inputQueue = new LinkedList<>();

    	Rectangle gameViewArea = new Rectangle(screenWidth, screenHeight-5);
    	terminal = new AsciiPanel(screenWidth, screenHeight);
    	camera = new AsciiCamera(mapDimensions, gameViewArea);
    	
        super.add(terminal);
        super.addKeyListener(this);
        super.addMouseListener(this);
        super.setSize(screenWidth*9, screenHeight*16);
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.repaint();
    }
    
    public AsciiPanel getTerminal() {
    	return terminal;
    }
    
    public InputEvent getNextInput() {
    	return inputQueue.poll();
    }
    
    public void pointCameraAt(World world, int xfocus, int yfocus) {
    	camera.lookAt(terminal, world, xfocus, yfocus);
    }
    
    public void refresh() {
    	terminal.repaint();
    }
    
	@Override
	public void keyPressed(KeyEvent e) {
		inputQueue.add(e);
	}

	public void drawDynamicLegend(Rectangle gameViewArea, World world, Map<String, Map<String, String>> tileData, Map<String, Map<String, String>> creatureData) {
		int x = 5;
		int y = gameViewArea.height;
		char glyph;
		
		for (String tileType : world.getTileTypesInArea(gameViewArea)) {
			glyph = tileData.get(tileType).get("glyph").charAt(0);
			terminal.write(glyph + "   " + tileType, x, y);
			y += 1;
			
			if (y == gameViewArea.height+2) {
				x += 15;
				y = gameViewArea.height;
			}
		}
		
		for (String creatureType : world.getCreatureTypesInArea(gameViewArea)) {
			glyph = creatureData.get(creatureType).get("glyph").charAt(0);
			terminal.write(glyph + "   " + creatureType, x, y);
			y += 1;
			
			if (y == gameViewArea.height+5) {
				x += 15;
				y = gameViewArea.height;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		inputQueue.add(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
