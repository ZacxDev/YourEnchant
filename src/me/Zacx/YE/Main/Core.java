package me.Zacx.YE.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.Zacx.YE.Display.EnchantMenu;
import me.Zacx.YE.Enchantments.YEnchant;
import me.Zacx.YE.Files.FileParser;

public class Core extends JavaPlugin {

	private FileParser fp;
	private EnchantMenu eMenu;
	
	public Core() {
		new Access(this);
	}
	
	
	public void onEnable() {
		fp = new FileParser();
		fp.parseEnchants();
		new EventHandle(this);
		eMenu = new EnchantMenu();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("testitem")) {
			ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			for (int i = 0; i < YEnchant.enchants.size(); i++) {
				YEnchant ench = YEnchant.enchants.get(i);
				lore.add(ench.name + " 1");
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
		} else if (cmd.getName().equalsIgnoreCase("enchant")) {
			eMenu.openMenu(p);
		}
		
		return true;
	}


	public static Location stringToLoc(String s) {
		String[] a = s.split(" ");
		
		if (a.length < 4)
			return null;
		
		int x = Integer.parseInt(a[1]);
		int y = Integer.parseInt(a[2]);
		int z = Integer.parseInt(a[3]);
		
		return new Location(Bukkit.getWorld(a[0]), x, y, z);
	}
	
	public static String parseCustomEnchant(String s) {
		if (s.contains(ChatColor.COLOR_CHAR + ""))
		while (s.contains(ChatColor.COLOR_CHAR + "")) {
			int i = s.indexOf(ChatColor.COLOR_CHAR);
			s = s.replace(s.substring(i, i + 2), "");
			}
		return s;
	}
	
}