package me.Zacx.YE.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Access {

	public static Core c;
	public static Random r;
	
	public Access(Core c) {
		this.c = c;
		r = new Random();
	}
	
	
	public static ItemStack buildItem(Material material, int amount, int data,
			String name, String desc, Enchantment[] ench, int[] level) {
		int lvl = 0;
		ItemStack item = new ItemStack(material, amount, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (ench != null)
		for (Enchantment enchant : ench) {
			for (int i = 0; i < level.length; i++) {
				lvl = level[i];
				meta.addEnchant(enchant, lvl, true);
			}
		}
		List<String> lore = buildLore(desc, 16, "§7");
		lore.add("§aSuccess: " + (r.nextInt(70) + 30) + "%");
		lore.add("§4Destroy: " + r.nextInt(100) + "%");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	private static List<String> buildLore(String text, int length, String cc) {
		List<String> lore = new ArrayList<String>();
		int max = length; // Max characters per line
		String line = "";
		int timesThrough = 0;

		for (String str : text.split(" ")) {

			if (line.trim().length() + str.trim().length() >= max
					|| str.endsWith(".") || str.endsWith("!")
					|| str.endsWith("?")) {
				lore.add(cc + line.trim() + " " + str.trim());
				line = "";
				timesThrough++;
			} else {

				if (timesThrough == text.split(" ").length) {
					lore.add(cc + line.trim() + " " + str.trim());
					break;
				}

				line += str + " ";
			}

		}

		return lore;
	}
	
}
