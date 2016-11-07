package me.Zacx.YE.Enchantments;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Zacx.YE.Properties.ParticleEffect;

public class YEnchant {

	public static List<YEnchant> enchants = new ArrayList<YEnchant>();
	
	private String name, desc;
	private int proc;
	private ParticleEffect particle;
	private PotionEffectType pEffect, tarEffect;
	private Sound sound;
	private EnchantmentType type;
	private List<Modifier> modifiers;
	private Random r;
	
	public YEnchant(String name) {
		this.name = name;
		enchants.add(this);
		modifiers = new ArrayList<Modifier>();
		r = new Random();
	}
	
	
	public void proc(Player p, LivingEntity tar, int lvl) {
		
		Location loc = p.getLocation();
		
		if (r.nextInt(100) <= proc) {
			particle.play(p);
			p.playSound(loc, sound, 1f, 1f);
			p.addPotionEffect(new PotionEffect(pEffect, lvl, (5 * lvl)));
			
			for (int i = 0; i < modifiers.size(); i++) {
				modifiers.get(i).play(p, tar);
			}
			
		}
		
	}
	
	
	public void setProperty(String line) {
		if (!line.contains("Items:") && line.contains(":")) {
		String p = "";
		String s = line.substring(line.lastIndexOf(":") + 2).toLowerCase();
		if (line.contains("name")) {
			this.name = s;
			p = "Name";
		} else if (line.contains("desc")) {
			this.desc = s;
			p = "Lore";
		} else if (line.contains("procrate")) {
			this.proc = Integer.parseInt(s);
			p = "Proc Rate";
		} else if (line.contains("particle")) {
			
		} else if (line.contains("playereffect")) {
			pEffect = PotionEffectType.getByName(s);
			p = "effect";
		} else if (line.contains("sound")) {
			sound = Sound.valueOf(s);
			p = "sound";
		} else if (line.contains("type")) {
			type = EnchantmentType.valueOf(s);
			p = "Type";
		} else if (line.contains("targeteffect")) {
			tarEffect = PotionEffectType.getByName(s);
		} else
			return;
		
		System.out.println("Set Property: " + p + " to " + s);
	} else
		return;
	}
	
	public static YEnchant getEnchant(String tar) {
		for (int i = 0; i < enchants.size(); i++) {
			YEnchant ench = enchants.get(i);
			if (ench.name.equalsIgnoreCase(tar.trim())) {
				return ench;
			}
		}
		return null;
	}
	
	public void addModifier(Modifier m) {
		modifiers.add(m);
	}
	
	public EnchantmentType getType() {
		return type;
	}
	
	public static Map<YEnchant, Integer> getArmourEnchantments(Player p) {
		
		Map<YEnchant, Integer> r = new LinkedHashMap<YEnchant, Integer>();
		
		for (int a = 0; a < p.getInventory().getArmorContents().length; a++) {
			ItemStack ar = p.getInventory().getArmorContents()[a];
			if (ar.hasItemMeta() && ar.getItemMeta().hasLore())
				for (int l = 0; l < ar.getItemMeta().getLore().size(); l++) {
					String line  = ar.getItemMeta().getLore().get(l);
					String sub = line.substring(0, line.lastIndexOf(" "));
					int lvl = Integer.parseInt(line.substring(line.lastIndexOf(" ")).trim());
					if (YEnchant.enchants.contains(sub)) {
						//get encahnt
						YEnchant ench = YEnchant.getEnchant(sub);
						r.put(ench, lvl);
					}
				}
		}
		return r;
	}
	
	public static Map<YEnchant, Integer> getItemEnchantments(ItemStack is) {
		
		Map<YEnchant, Integer> r = new LinkedHashMap<YEnchant, Integer>();
		
		if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
			for (int i = 0; i < is.getItemMeta().getLore().size(); i++) {
				String line = is.getItemMeta().getLore().get(i);
				String sub = line.substring(0, line.lastIndexOf(" "));
				int lvl = Integer.parseInt(line.substring(line.lastIndexOf(" ")).trim());
				if (YEnchant.enchants.contains(sub)) {
					YEnchant ench = YEnchant.getEnchant(sub);
					r.put(ench, lvl);
				}
			}
		}
		return r;
	}
}