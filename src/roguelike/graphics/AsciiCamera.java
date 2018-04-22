package roguelike.graphics;

import asciiPanel.AsciiPanel;
import roguelike.entities.Creature;
import roguelike.entities.Tile;
import roguelike.world.World;

import java.awt.*;

public class AsciiCamera {

    int screenWidth;
    int screenHeight;

    int direction = 0;

    int mapWidth;
    int mapHeight;

    public AsciiCamera(Rectangle Bounds, Rectangle viewArea)
    {
        screenWidth = viewArea.width;
        screenHeight = viewArea.height;
    	
        mapWidth = Bounds.width;
        mapHeight = Bounds.height;   
    }

    
    public Point GetCameraOrigin(int xfocus, int yfocus)
    {
        int spx = Math.max(0, Math.min(xfocus - screenWidth / 2, mapWidth - screenWidth));
        int spy = Math.max(0, Math.min(yfocus - screenHeight / 2, mapHeight - screenHeight));
        return new Point(spx, spy);
    }
    
    /*public Tile GetObjectAt(World world, int x, int y)
    {
        Point playerPosition = GetScreenCoords(world.player.getX(), world.player.getY(), world.player.getX(), world.player.getY());

        int tileX = world.player.getX() + (x - playerPosition.x);
        int tileY = world.player.getY() + (y - playerPosition.y);
        return world.tile(tileX, tileY);
    }*/

    public void Rotate(int direction)
    {
        this.direction = direction;
    }

    public void lookAt(AsciiPanel terminal, World world, int xfocus, int yfocus)
    {
        Tile tile;
        Point origin;
        
        //screenWidth = terminal.getWidthInCharacters();
        //screenHeight = terminal.getHeightInCharacters();
        
        origin = GetCameraOrigin(xfocus, yfocus);
        
		for (int x = 0; x < screenWidth; x++){
			for (int y = 0; y < screenHeight; y++){
                tile = world.tile(origin.x + x, origin.y + y);
                terminal.write(tile.getGlyph(), x, y, tile.getColor(), tile.getBackgroundColor());
            }
        }
        
		int spx;
		int spy;
        for(Creature entity : world.creatures)
        {
        	spx = entity.getX() - origin.x;
        	spy = entity.getY() - origin.y;
        	
        	if ((spx >= 0 && spx < screenWidth) && (spy >= 0 && spy < screenHeight)) {
        		terminal.write(entity.getGlyph(), spx, spy, entity.getColor(), world.tile(entity.getX(), entity.getY()).getBackgroundColor());
        	}
        }
    }
}
