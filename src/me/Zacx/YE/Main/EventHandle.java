package me.Zacx.YE.Main;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.Zacx.YE.Enchantments.EnchantmentType;
import me.Zacx.YE.Enchantments.YEnchant;

public class EventHandle implements Listener {

	private Core c;
	
	public EventHandle(Core c) {
		this.c = c;
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		
		if (!(e.getDamager() instanceof Player))
			return;
		
		Player p = (Player) e.getDamager();
		Entity tar = e.getEntity();
		
		//get enchantments on p, check type, proc
		
		List<YEnchant> enchs = YEnchant.getItemEnchantments(p.getItemInHand());
		for (int i = 0; i < enchs.size(); i++) {
			YEnchant ench = enchs.get(i);
			ench.proc(p);
		}
		
		//get enchantmets on tar, check type, proc
		enchs = YEnchant.getArmourEnchantments(p);
		
		for (int i = 0; i < enchs.size(); i++) {
			YEnchant ench = enchs.get(i);
			if (ench.getType() == EnchantmentType.DEFENCE) {
				ench.proc(p);
			}
		}
		
	}
}