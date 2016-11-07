package me.Zacx.YE.Main;

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
	
	
	
}