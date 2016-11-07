package me.Zacx.YE.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Zacx.YE.Enchantments.Modifier;
import me.Zacx.YE.Enchantments.YEnchant;
import me.Zacx.YE.Main.Access;
import me.Zacx.YE.Main.Core;

public class FileParser {

	private Core c;
	
	public FileParser() {
		this.c = Access.c;
	}
	
	public void parseEnchants() {
		
		File folder = c.getDataFolder();
		File ranksFile = new File(c.getDataFolder() + "/kits.txt");

		int i = 0;
		try {

			if (!folder.exists())
				folder.mkdir();
			if (!ranksFile.exists())
				ranksFile.createNewFile();

			BufferedReader br = new BufferedReader(
					new FileReader(c.getDataFolder() + "/kits.txt"));
			String line = br.readLine();
			line = br.readLine();

			boolean readingEnchant = false;
			boolean readingModifiers = false;
			
			YEnchant ye = null;
			

			while (line != null) {
				line = line.trim();
				if (line.isEmpty() || line.equalsIgnoreCase(" ") || line.equalsIgnoreCase("\r\n") || line.equalsIgnoreCase("")) {
					line = br.readLine();
					continue;
				}
				line = line.replaceAll("&", "�");
				
				if (!readingEnchant || line.contains("Name:")) {
					readingEnchant = true;
					ye = new YEnchant(line);
				}
				
				if (readingEnchant) {
					ye.setProperty(line);
				}
				
				if (line.contains("Modifiers")) {
					readingModifiers = true;
				}
				
				if (line.startsWith("-")) {
					String s = line.substring(line.indexOf(" "), line.indexOf("(")).trim();
					String arg = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
					Modifier m = Modifier.valueOf(s);
					System.out.println("[PARSE] " + arg);
					m.setProperties(arg);
					ye.addModifier(m);
				}
				

				line = br.readLine();
			}

			if (line == null) {
				YEnchant.enchants.remove(YEnchant.enchants.size() - 1);
				i--;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Imported " + i + " Enchantments.");
		}

	
		
	}
	
}