package roguelike.entities;

import roguelike.world.World;

import java.util.Map;
import java.util.Random;

public class Creature extends Entity {

    private int hitpoints;
    private String behaviour;
    
    public Creature(Map<String, String> creatureData, int x, int y)
    {
        super(creatureData, x, y);
        behaviour = creatureData.get("behaviour");
    }
    
    private int getHitpoints() {
    	return hitpoints;
    }
    
    private void setHitpoints(int amount) {
		hitpoints -= 10;
	}
 
    public void move(World world, int dx, int dy)
    {
        if (world.isBlocked(x + dx, y + dy) != true)
        {
            x += dx;
            y += dy;
        } else {
        	Entity entity = world.getEntityAt(x + dx, y + dx);
        		
        	if (entity instanceof Item) {
        		useItem((Item) entity);
        	} else if (entity instanceof Creature) {
        		attackCreature((Creature) entity);
        	}
        }
    }
    
    public void useItem(Item item) {
    	if (item.getEffect() == "health" && hitpoints <= 90) {
    		hitpoints += 10;
    	}
    }
    
    public void attackCreature(Creature creature) {
    	creature.setHitpoints(-50);
    }

	public void moveTo(World world, int x, int y)
    {
        //
    }

    public void update(World world)
    {
    	Random rnd = new Random();
    	int performAction = rnd.nextInt(100);
    	if (behaviour.equals("docile") && performAction > 98) {
    		// walk around and flee if attacked
    		
    		int rndNr = rnd.nextInt(3);
    		    		
    		if (rndNr == 0) {
    			move(world, 1, 0);
    		} else if (rndNr == 1) {
    			move(world, -1, 0);
    		} else if (rndNr == 2) {
    			move(world, 0, 1);
    		} else if (rndNr == 3) {
    			move(world, 0, -1);
    		}

    	} else if (behaviour.equals("aggressive") && performAction > 98) {
    		// look for other npcs and hunt them
    	}

    }
}
