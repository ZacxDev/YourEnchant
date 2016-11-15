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

import me.Zacx.YE.Enchantments.Ability;
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
		File ranksFile = new File(c.getDataFolder() + "/YourEnchantments.txt");

		int i = 0;
		try {

			if (!folder.exists())
				folder.mkdir();
			if (!ranksFile.exists())
				ranksFile.createNewFile();

			BufferedReader br = new BufferedReader(
					new FileReader(c.getDataFolder() + "/YourEnchantments.txt"));
			String line = br.readLine();

			boolean readingEnchant = false;
			boolean readingModifiers = false;
			boolean readingAbilities = false;
			
			YEnchant ye = null;
			

			while (line != null) {
				line = line.trim();
				if (line.isEmpty() || line.equalsIgnoreCase(" ") || line.equalsIgnoreCase("\r\n") || line.equalsIgnoreCase("")) {
					line = br.readLine();
					continue;
				}
								
				if (line.startsWith("#"))
					line = br.readLine();
				
				line = line.replaceAll("&", "§");
				
				if (!readingEnchant || line.contains("Name:")) {
					readingEnchant = true;
					ye = new YEnchant(line);
					i++;
				}
				
				if (readingEnchant) {
					ye.setProperty(line);
				}
				
				if (line.contains("Modifiers")) {
					readingModifiers = true;
					readingAbilities = false;
				}
				
				if (line.startsWith("-") && readingModifiers) {
					String s = line.substring(line.indexOf(" "), line.indexOf("(")).trim();
					String arg = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
					Modifier m = Modifier.valueOf(s);
					m.setProperties(arg);
					ye.addModifier(m);
				}
				
				if (line.contains("Abilities")) {
					readingAbilities = true;
					readingModifiers = false;
				}
				
				if (line.startsWith("-") && readingAbilities) {
					String s = line.substring(line.indexOf(" "), line.indexOf("(")).trim();
					String arg = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
					Ability ab = Ability.valueOf(s);
					ab.setProperties(arg);
					ye.addAbility(ab);
				}

				line = br.readLine();
			}

			if (line == null && !(YEnchant.enchants.isEmpty())) {
				//YEnchant.enchants.remove(YEnchant.enchants.size() - 1);
				//i--;
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