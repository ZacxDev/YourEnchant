package me.Zacx.YE.Enchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.Zacx.YE.Main.Access;

public enum Ability {

	LAUNCH(), LIGHTNING(), TELEPORT();

	public static Map<UUID, Long> cooldowns = new HashMap<UUID, Long>();

	private Material mat;
	private long cooldown;
	private int amount;

	public void tick() {
		List<UUID> uids = new ArrayList<UUID>(cooldowns.keySet());
		for (int i = 0; i < uids.size(); i++) {
			cooldowns.put(uids.get(i), cooldowns.get(uids.get(i)) - 1);
		}

	}

	public void play(Player p) {

		if (cooldowns.containsKey(p.getUniqueId())
				&& cooldowns.get(p.getUniqueId()) > 0) {
			return;
		}

		cooldowns.put(p.getUniqueId(), cooldown);

		Location l = p.getTargetBlock((HashSet<Byte>) null, 200).getLocation();
		if (this == LAUNCH) {
			mg(p, l);
		} else if (this == LIGHTNING) {
			l.getWorld().strikeLightning(l);
			p.playSound(l, Sound.ENTITY_LIGHTNING_THUNDER, 1f, 1f);
		} else if (this == TELEPORT) {
			p.teleport(l);
		}
	}

	public void setProperties(String line) {
		line = line.trim();
		String[] args = line.split(",");
		try {
			for (int i = 0; i < args.length; i++) {
				String s = args[i].trim().replaceAll(",", "");
				if (mat == null && Material.getMaterial(s) != null) {
					mat = Material.getMaterial(s);
					return;
				}
				if (cooldown == 0) {
					cooldown = Long.parseLong(s);
					return;
				}
				if (amount == 0) {
					amount = Integer.parseInt(s);
					return;
				}
			}
		} catch (NumberFormatException e) {
		}
	}

	private void mg(Player p, final Location l) {
		Snowball sb = p.launchProjectile(Snowball.class);
		Entity e = l.getWorld().dropItemNaturally(l, new ItemStack(mat));
		sb.setPassenger(e);
	}
}