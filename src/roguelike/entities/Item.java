package roguelike.entities;

import java.util.Map;

public class Item extends Entity {

	private String effect;
	
	public Item(Map<String, String> itemData, int x, int y) {
		super(itemData, x, y);
        effect = itemData.get("effect");
	}
	
	public String getEffect() {
		return effect;
	}

}
