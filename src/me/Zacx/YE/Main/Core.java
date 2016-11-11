package me.Zacx.YE.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

import me.Zacx.OKits.Net.MAC;
import me.Zacx.YE.Display.EnchantMenu;
import me.Zacx.YE.Enchantments.Ability;
import me.Zacx.YE.Enchantments.YEnchant;
import me.Zacx.YE.Files.FileParser;

public class Core extends JavaPlugin {

	private FileParser fp;
	private EnchantMenu eMenu;
	private Random r;
	// public static String uid = "%%__USER__%%";
	public static String uid = "58767";
	public static String mac;

	public Core() {
		r = new Random();
		new Access(this);

		try {
			InetAddress inet = InetAddress.getLocalHost();
			mac = MAC.GetMacAddress(inet);
			System.out.println("MAC: " + mac);

		} catch (UnknownHostException e) {

		}

		//auth();
	}

	public void onEnable() {
		fp = new FileParser();
		fp.parseEnchants();
		new EventHandle(this);
		eMenu = new EnchantMenu();

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new Runnable() {

					public void run() {
						for (int i = 0; i < Ability.values().length; i++) {
							Ability.values()[i].tick();
						}
					}

				}, 1L, 20L);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

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

	public boolean uidFound = true;

	public void auth() {
		
		try {
		URL url = new URL("");
		Scanner s = new Scanner(url.openStream());
		String line = s.nextLine();
		
		while (line != null) {
			
			line = line.trim();
			
			if (line.contains(uid)) {
				uidFound = true;
				String fm = line.substring(line.lastIndexOf(":") + 1);
				System.out.println("FM: " + fm);
				if (fm.equals(mac)) {
					System.out.println("License Match!");
					return;
				}
			} else {
					//disableLeak();
			}
			
			line = s.nextLine();
		}
		
		if (uidFound) {
		URLConnection con = url.openConnection();
		con.setDoOutput(true);
		OutputStreamWriter w = new OutputStreamWriter(con.getOutputStream());
		w.write(uid + ":" + mac);
		w.flush();
		w.close();
		}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void disableLeak() throws FileNotFoundException {
		System.out.println("[Error] @ 0xb517c");
		getServer().getPluginManager().disablePlugin(this);
		throw new FileNotFoundException();
	}

	public void disableNoInternet() {
		Bukkit.broadcastMessage(ChatColor.RED
				+ "You don't have a valid internet connection, please connect to the internet for the plugin to work!");
		getServer().getPluginManager().disablePlugin(this);
	}

}