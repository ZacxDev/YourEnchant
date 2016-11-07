package me.Zacx.YE.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import me.Zacx.YE.Files.FileParser;

public class Core extends JavaPlugin {

	private FileParser fp;
	
	public Core() {
		fp.parseEnchants();
	}
	
	
	public void onEnable() {
		new EventHandle(this);
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
	
}