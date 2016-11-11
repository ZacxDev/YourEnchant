package me.Zacx.YE.Enchantments;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum Modifier {
	DAMAGE(), HEAL(), TELEPORT(), EXPLOSION(), ARMOUR_DAMAGE();
	
	public int multi, tpBuffer;
	private Random r;
		
	
	private Modifier() {
		r = new Random();
	}
	
	private Modifier(int multi) {
		this.multi = multi;
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
			
				if (multi == 0) {
					multi = Integer.parseInt(str);
					continue;
				}
		}
	}
	
	public int getMulti() {
		return multi;
	}
	
	private Location rdmLoc(Location l) {
		return l.add(new Location(l.getWorld(), r.nextInt(tpBuffer), 0, r.nextInt(tpBuffer)));
	}
}