package roguelike.world;

import roguelike.entities.Creature;
import roguelike.entities.Entity;
import roguelike.entities.Tile;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class World {
	
	private Tile[][] tiles;
	private int width;
	private int height;
	public Creature player;
	public Set<Creature> creatures;
	
	public int width() { return width; }
	public int height() { return height; }
	
	public World(Tile[][] tiles){
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
	}
	
	public World(Tile[][] tiles, Set<Creature> creatures){
		this.creatures = new HashSet<>();
		this.creatures.addAll(creatures);
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
	}
	
	public void addEntity(Creature creature) {
		this.creatures.add(creature);
	}
	
	public Tile tile(int x, int y){
		if (x < 0 || x >= width || y < 0 || y >= height)
			return null;
		else
			return tiles[x][y];
	}
	
	public char glyph(int x, int y){
		return tile(x, y).getGlyph();
	}
	
	public Color glyphColor(int x, int y){
		return tile(x, y).getColor();
	}	
	
	public Color backgroundColor(int x, int y){
		return tile(x, y).getBackgroundColor();
	}
	
	public Entity getEntityAt(int x, int y) {
		return creatures.stream()
				.filter(entity -> entity.getX() == x && entity.getY() == y)
				.findFirst()
				.orElse(null);
	}
	
	public boolean isBlocked(int x, int y) {
		return (tiles[x][y].isBlocked() || getEntityAt(x, y) != null);
	}
	
	public void update() {
		creatures.stream()
		.filter(creature -> creature.getType() != "player")
		.forEach(creature -> creature.update(this));
	}
	
	public Set<String> getTileTypesInArea(Rectangle rectangle) {
		Set<String> tileTypes = new HashSet<String>();
		Tile tile;
		
		for (int y=(int) rectangle.getY(); y < rectangle.getMaxY(); y += 1) {
			for (int x=(int) rectangle.getX(); x < rectangle.getMaxX(); x += 1) {
				tile = this.tiles[x][y];
				if (tile != null) {
					tileTypes.add(tile.getType());
				}
			}
		}
		return tileTypes;
	}
	
	public Set<String> getCreatureTypesInArea(Rectangle rectangle) {
		Set<String> creatureTypes = new HashSet<>();
		
		creatureTypes.add(player.getType());
		
		for (Creature creature : this.creatures) {
			if (creature.getX() > rectangle.getX() && creature.getX() < rectangle.getMaxX() &&
					creature.getY() > rectangle.getY() && creature.getY() < rectangle.getMaxY()) {
				creatureTypes.add(creature.getType());
			}
		}
		
		return creatureTypes;
	}
}
