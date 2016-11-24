package me.Zacx.YE.Properties;

import org.bukkit.entity.Player;

import me.Zacx.YE.ParticleLib.ParticleEffect;

public class YEnchantParticle {

	
	public static void play(Player p, String name) {		
		ParticleEffect.getEffect(name).display(1f, 1f, 1f, 0.9f, 20, p.getLocation(), 128);
	}
	
}