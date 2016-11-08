package me.Zacx.YE.Enchantments;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.Zacx.YE.Main.Access;

public enum Ability {

	
	LAUNCH();
	
	
	private Material mat;
	private long cooldown;
	private int amount;
	
	public void play(Player p, LivingEntity tar) {
		if (this == LAUNCH) {
			mg(1, p.getLocation());
		}
	}
	
	
	public void setProperties(String line) {
		line = line.trim();
		String[] args = line.split(",");
		for (int i = 0; i < args.length; i++) {
			String s = args[i].trim().replaceAll(",", "");
		if (mat == null && Material.getMaterial(s) != null) {
			mat = Material.getMaterial(s);
		}
		if (cooldown == 0) {
			cooldown = Long.parseLong(s);
		}
		if (amount == 0) {
			amount = Integer.parseInt(s);
		}
		}
	}
	
	
	private void mg(final int x, final Location l) {
		 
        final Bat bat = (Bat) l.getWorld().spawnEntity(l.add(1, 0, 0), EntityType.BAT);
 
        Access.c.getServer().getScheduler().scheduleSyncRepeatingTask(Access.c, new Runnable() {
 
            @Override
            public void run() {
                List<Entity> lEnt = bat.getNearbyEntities(10, 10, 10);
                for (Entity ent : lEnt) { 
                    if (ent instanceof Player) {
                        Player p = (Player) ent;
 
                        int h = 1;
                        if (p.getLocation().distance(bat.getLocation()) >= 8) {
                            h = 2;
                        }
                        if (p.isFlying()) {
                            h = 1;
                        }
 
                        Vector v1 = l.toVector();
                        Vector v2 = p.getLocation().add(0, h, 0).toVector();
 
                        Vector st = v1.subtract(v2).normalize();
                        st.setX(st.getX() * -1);
                        st.setY(st.getY() * -1);
                        st.setZ(st.getZ() * -1);
 
                        Snowball sb = bat.launchProjectile(Snowball.class);
                        Entity e = l.getWorld().dropItemNaturally(l, new ItemStack(mat));
                        sb.setPassenger(e);
                        sb.setVelocity(st);
                        bat.remove();
                    }
                }
            }
        }, 1, 12);
    }

	
}
