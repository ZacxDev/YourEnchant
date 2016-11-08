package me.Zacx.YE.Enchantments;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.Zacx.YE.Main.Access;
import me.Zacx.YE.Main.Core;

public enum Modifier {
	DAMAGE(), HEAL(), TELEPORT(), EXPLOSION(), ARMOUR_DAMAGE();
	
	public int multi, tpBuffer;
	public Material mat;
	public long dur;
	public Location loc;
	private Random r;
		
	
	private Modifier() {
		r = new Random();
	}
	
	private Modifier(int multi) {
		this.multi = multi;
		r = new Random();
	}
	
	private Modifier(Material mat) {
		this.mat = mat;
		r = new Random();
	}
	
	private Modifier(long dur) {
		this.dur = dur;
		r = new Random();
	}
	
	private Modifier(Location loc) {
		this.loc = loc;
		r = new Random();
	}
		
	
	public void play(Player p, LivingEntity tar) {
		if (this == DAMAGE) {
			tar.damage(multi);
		} else if (this == HEAL) {
			p.setHealth(p.getHealth() + multi);
		} else if (this == TELEPORT) {
			p.teleport(rdmLoc(p.getLocation()));
		} else if (this == EXPLOSION) {
			tar.getLocation().getWorld().createExplosion(tar.getLocation(), multi, false);
		} else if (this == ARMOUR_DAMAGE) {
			if (tar instanceof Player) {
				Player ptar = (Player) tar;
				for (int i = 0; i < ptar.getInventory().getArmorContents().length; i++) {
					ItemStack is = ptar.getInventory().getArmorContents()[i];
					is.setDurability((short) (is.getDurability() - (1*multi)));
				}
			}
		}
	}
	
	
	public void setProperties(String s) {
		
		String[] args = s.split(",");
		
		for (int i = 0; i < args.length; i++) {
			String str = args[i].trim().replaceAll(",", "");
			
			try {
				if (multi == 0) {
					multi = Integer.parseInt(str);
					continue;
				}
				else if (dur == 0) {
					dur = Long.parseLong(str);
					continue;
				}
			} catch (NumberFormatException n) {}
			
			if (Material.getMaterial(str) != null) {
				mat = Material.getMaterial(str);
				continue;
			}
			if (Core.stringToLoc(str) != null) {
				loc = Core.stringToLoc(str);
				continue;
			}
		}
	}
	
	public int getMulti() {
		return multi;
	}
	public long getDur() {
		return dur;
	}
	public Material getMaterial() {
		return mat;
	}
	public Location getLocation() {
		return loc;
	}
	
	private Location rdmLoc(Location l) {
		return l.add(new Location(l.getWorld(), r.nextInt(tpBuffer), 0, r.nextInt(tpBuffer)));
	}
	
		
}