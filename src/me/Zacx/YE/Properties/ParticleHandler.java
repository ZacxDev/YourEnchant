package me.Zacx.YE.Properties;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.Zacx.YE.ParticleLib.ParticleEffect;

public enum ParticleHandler {

	holder(), HEARTS(), SMOKE_LARGE();
	
	public void play(Player p) {
		
		//ParticleEffect.fromName(this.name()).display(new Vector(1,1,1), 10, p.getLocation(), 20);
		ParticleEffect.DRIP_WATER.display(1F, 1F, 1F, 1F, 10, new Location(Bukkit.getWorld("world"), 123, 456, 789), 20.0);
		System.out.println("proc");
		
	}
	
}
