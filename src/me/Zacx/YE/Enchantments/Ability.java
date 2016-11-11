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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

public enum Ability {

	LAUNCH(), LIGHTNING(), TELEPORT(), SPAWN();

	public Map<UUID, Long> cooldowns = new HashMap<UUID, Long>();

	private Material mat;
	private long cooldown;
	private int amount;
	private EntityType en;

	public void tick() {
		List<UUID> uids = new ArrayList<UUID>(cooldowns.keySet());
		for (int i = 0; i < uids.size(); i++) {
			cooldowns.put(uids.get(i), cooldowns.get(uids.get(i)) - 1);
			System.out.println(uids.get(i) + " " + i + " " + this.name());
			if (cooldowns.get(uids.get(i)) <= 0) {
				cooldowns.remove(uids.get(i));
			}
		}

	}

	public void play(Player p) {

		if (cooldowns.containsKey(p.getUniqueId())) {
			return;
		} else {
			System.out.println("d");
			cooldowns.put(p.getUniqueId(), cooldown);
		}
			
		Location l = p.getTargetBlock((HashSet<Byte>) null, 200).getLocation();
		if (this == LAUNCH) {
			mg(p, l);
		} else if (this == LIGHTNING) {
			l.getWorld().strikeLightning(l);
			p.playSound(l, Sound.ENTITY_LIGHTNING_THUNDER, 1f, 1f);
		} else if (this == TELEPORT) {
			p.teleport(l);
		} else if (this == SPAWN) {
			l.getWorld().spawnEntity(l, en);
		}
	}

	public void setProperties(String line) {
		line = line.trim();
		String[] args = line.split(",");
		try {
			for (int i = 0; i < args.length; i++) {
				String s = args[i].trim().replaceAll(",", "");
				System.out.println(s);
				if (mat == null && Material.getMaterial(s) != null && this == LAUNCH) {
					mat = Material.getMaterial(s);
					continue;
				}
				if (this == SPAWN && en == null && EntityType.valueOf(s) != null) {
					en = EntityType.valueOf(s);
					continue;
				}
				if (amount == 0 && (this == LAUNCH || this == SPAWN)) {
					amount = Integer.parseInt(s);
					continue;
				}
				if (cooldown == 0) {
					cooldown = Long.parseLong(s);
					System.out.println(cooldown);
					continue;
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