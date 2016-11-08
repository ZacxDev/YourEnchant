package me.Zacx.YE.Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Zacx.YE.Enchantments.EnchantmentType;
import me.Zacx.YE.Enchantments.YEnchant;

public class EventHandle implements Listener {

	private Core c;
	private Random r;
	
	public EventHandle(Core c) {
		this.c = c;
		c.getServer().getPluginManager().registerEvents(this, c);
		r = new Random();
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
		
		if (tar instanceof Player) {
		
			Player pTar = (Player) tar;
			
		enchMap = YEnchant.getArmourEnchantments(pTar);
		enchs = new LinkedList<YEnchant> (enchMap.keySet());
		for (int i = 0; i < enchs.size(); i++) {
			YEnchant ench = enchs.get(i);
			int lvl = enchMap.get(ench);
			if (ench.getType() == EnchantmentType.DEFENCE) {
				ench.proc(pTar, tar, lvl);
			}
		}
		}
	}
	
	
	public boolean hasEnchantment(ItemStack item, String enchantmentName,
			int level, ArrayList<String> lore) {
		ItemMeta meta = item.getItemMeta();
		boolean toReturn = false;
		for (String line : meta.getLore()) {
			if (!line.contains(enchantmentName)) {
				toReturn = false;
			} else {
				toReturn = true;
				break;
			}
		}
		return toReturn;
	}
	
	
	public void applyEnchantment(ItemStack target, ItemStack current, Player p, int slot) {

		String currentName = current.getItemMeta().getDisplayName();
		
		String enchantmentName = Core.parseCustomEnchant(currentName);
		int level = Integer.parseInt(Core.parseCustomEnchant(currentName.substring(currentName.lastIndexOf(" ")).trim()));
		int success = getSuccessRate(current);
		int s = r.nextInt(100);
		int destroy = getDestroyRate(current);
		int d = r.nextInt(100);
	
		if (s <= success) {
			// enchant clicked item
			ItemStack item = target;
			ItemMeta meta = item.getItemMeta();
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
				ArrayList<String> lore = (ArrayList<String>) meta.getLore();
				if (this.hasEnchantment(target, enchantmentName, level, lore) == false) {
					lore.add(enchantmentName);
					meta.setLore(lore);
					item.setItemMeta(meta);
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
					current.setType(Material.AIR);
				} else {
					p.sendMessage("§4That item cannot use this enchantment!");
				}

			} else {
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("§e§l" + enchantmentName );

				meta.setLore(lore);
				item.setItemMeta(meta);
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				current.setType(Material.AIR);

			}
			p.getInventory().remove(target);
			p.getInventory().addItem(item);
		} else {
			if (d <= destroy) {
				p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
				target.setType(Material.AIR);
				System.out.println("item shoudl be ded");
			}
			current.setType(Material.AIR);
		}
	}
	
	public int getSuccessRate(ItemStack item) {
		if (item.hasItemMeta()) {
			for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
				String line = item.getItemMeta().getLore().get(i);
				if (line.contains("Success")) {
					line = line.substring(line.lastIndexOf(":") + 2, line.length() - 1);
					return Integer.parseInt(line);
				}
			}
		}
		return 0;
	}
	
	public int getDestroyRate(ItemStack item) {
		if (item.hasItemMeta()) {
			for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
				String line = item.getItemMeta().getLore().get(i);
				if (line.contains("Destroy")) {
					line = line.substring(line.lastIndexOf(":") + 2, line.length() - 1);
					return Integer.parseInt(line);
				}
			}
		}
		return 0;
	}
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		ItemStack cursor = e.getCursor();
		
		if (current != null && cursor != null && cursor.getType() == Material.BOOK && current.getType() != Material.AIR && cursor.hasItemMeta()) {
			e.setCancelled(true);
			applyEnchantment(current, cursor, p, e.getRawSlot());
		}
		
		
	}
	
}