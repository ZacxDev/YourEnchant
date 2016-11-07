package me.Zacx.YE.Main;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
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
		
		if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof LivingEntity))
			return;
		
		Player p = (Player) e.getDamager();
		LivingEntity tar = (LivingEntity) e.getEntity();
		
		//get enchantments on p, check type, proc
		
		Map<YEnchant, Integer> enchMap = YEnchant.getItemEnchantments(p.getItemInHand());
		List<YEnchant> enchs = new LinkedList<YEnchant>(enchMap.keySet());
		for (int i = 0; i < enchs.size(); i++) {
			YEnchant ench = enchs.get(i);
			int lvl = enchMap.get(ench);
			ench.proc(p, tar, lvl);
		}
		
		//get enchantmets on tar, check type, proc
		enchMap = YEnchant.getArmourEnchantments(p);
		enchs = new LinkedList<YEnchant> (enchMap.keySet());
		for (int i = 0; i < enchs.size(); i++) {
			YEnchant ench = enchs.get(i);
			int lvl = enchMap.get(ench);
			if (ench.getType() == EnchantmentType.DEFENCE) {
				ench.proc(p, tar, lvl);
			}
		}
		
	}
}