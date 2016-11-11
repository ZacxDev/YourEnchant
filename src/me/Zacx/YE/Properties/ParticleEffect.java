package me.Zacx.YE.Properties;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

public enum ParticleEffect {

	holder(), HEARTS();
	
	public void play(Player p) {
		
		if (this == HEARTS) {
			p.getWorld().playEffect(p.getLocation(), Effect.HEART, 1);
		}
		
	}
	
}
