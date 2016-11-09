package me.Zacx.YE.Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import me.Zacx.YE.Enchantments.Ability;
import me.Zacx.YE.Enchantments.YEnchant;
import me.Zacx.YE.Files.FileParser;

public class Core extends JavaPlugin {

	private FileParser fp;
	private EnchantMenu eMenu;
	private Random r;
	public static String uid = "%%__USER__%%";
	public static String licID;
	
	public Core() {
		r = new Random();
		licID = r.nextInt(1000) + "-" + uid + "-" + r.nextInt(1000);
		new Access(this);
	}
	
	
	public void onEnable() {
		fp = new FileParser();
		fp.parseEnchants();
		new EventHandle(this);
		eMenu = new EnchantMenu();
		
		
	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			public void run() {
				for (int i = 0; i < Ability.values().length; i++) {
					Ability.values()[i].tick();
				}
			}
			
		}, 1L, 20L);
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
	
	public boolean sts = true;
    public void auth()
    {
      try
      {
        URLConnection localURLConnection = new URL("Your website link").openConnection();
        localURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        localURLConnection.connect();
       
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURLConnection.getInputStream(), Charset.forName("UTF-8")));
        BufferedWriter localBufferedWRiter = new BufferedWriter(new OutputStream(localURLConnection.getOutputStream()), Charset.forName("UTF-8"));
       
        StringBuilder localStringBuilder = new StringBuilder();
        String str1; 
        while ((str1 = localBufferedReader.readLine()) != null) {
          localStringBuilder.append(str1);
        }
        String str2 = localStringBuilder.toString();
        if (!str2.contains(String.valueOf(uid)))
        {
          disableLeak();
          return;
        }
        this.sts = true;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        disableNoInternet();
        return;
      }
    }
   
    public void disableLeak()
    {
        int x = 0;
        while(x != 5000){
          Bukkit.broadcastMessage(ChatColor.RED + "You leaked my plugin, 5k broadcast!");
          x++;
        }
      getServer().getPluginManager().disablePlugin(this);
      sts = false;
    }
   
    public void disableNoInternet() {
        Bukkit.broadcastMessage(ChatColor.RED + "You don't have a valid internet connection, please connect to the internet for the plugin to work!");
        getServer().getPluginManager().disablePlugin(this);
        sts = false;
    }
	
}