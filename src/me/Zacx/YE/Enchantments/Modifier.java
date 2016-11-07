package me.Zacx.YE.Enchantments;

import org.bukkit.Location;
import org.bukkit.Material;

public enum Modifier {
	DAMAGE(), HEAL(), LAUNCH(), TELEPORT(), EXPLOSION(), BURN(), ARMOUR_DAMAGE();
	
	public int multi;
	public Material mat;
	public long dur;
	public Location loc;
	
	private Object optional;
	
	
	private Modifier() {}
	
	private Modifier(int multi) {
		this.multi = multi;
	}
	
	private Modifier(Material mat) {
		this.mat = mat;
	}
	
	private Modifier(long dur) {
		this.dur = dur;
	}
	
	private Modifier(Location loc) {
		this.loc = loc;
	}
		
	
	public void optional(Object o) {
		optional = o;
		if (o instanceof Integer)
			multi = (int) o;
		else if (o instanceof Material)
			mat = (Material) o;
		else if (o instanceof Long)
			dur = (long) o;
		else if (o instanceof Location) {
			loc = (Location) o;
		}
		
	}
}