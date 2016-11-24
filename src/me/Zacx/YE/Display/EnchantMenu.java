package me.Zacx.YE.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Zacx.YE.Enchantments.YEnchant;
import me.Zacx.YE.Main.Access;


public class EnchantMenu implements Listener {

	public static List<EnchantMenu> menus = new ArrayList<EnchantMenu>();
	
	private static Random r;
	private String user;
	private Inventory inv;
	private static ItemStack fill, dia;
	private static boolean firstInit = true;;
	
	private List<UUID> animating = new ArrayList<UUID>();

	public EnchantMenu(Player p) {
		
		if (firstInit) {
			firstInit = false;
		r= new Random();
		Access.c.getServer().getPluginManager().registerEvents(this, Access.c);
		fill = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				DyeColor.GRAY.getData());
		ItemMeta meta = fill.getItemMeta();
		meta.setDisplayName(" ");
		fill.setItemMeta(meta);

		dia = new ItemStack(Material.DIAMOND);
		meta = dia.getItemMeta();
		meta.setDisplayName("§b§lClick To Enchant");
		List<String> lore = new ArrayList<String>();
		lore.add("§fThis will give you a random custom enchanted book.");
		meta.setLore(lore);
		dia.setItemMeta(meta);
		}
		
		inv = Bukkit.createInventory(null, 9, "Enchant");
		user = p.getUniqueId().toString();
		menus.add(this);
	}

	public void openMenu(Player p) {

		
		for (int i = 0; i < 9; i++) {
			if (i != 4)
				inv.setItem(i, fill);
			else
				inv.setItem(i, dia);
		}
		p.openInventory(inv);

	}
	
	int id = 0;
	private void animate(Player p, long to) {
		animating.add(p.getUniqueId());
		Location l = p.getLocation();
		
		id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Access.c, new Runnable() {
			long i = to;
			public void run() {
				i--;
				if (i > 0) {
				l.getWorld().playSound(l, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
				for (int i = 0; i < inv.getContents().length; i++) {
					ItemStack item = inv.getItem(i);
					if (i > 0)
						inv.setItem(i - 1, item);
					else {
						inv.clear(i);
						YEnchant reward = YEnchant.enchants.get(r.nextInt(YEnchant.enchants.size()));
						inv.setItem(8, reward.getItem((r.nextInt(reward.max) + 1) + ""));
					}
				}
				} else {
					l.getWorld().playSound(l, Sound.BLOCK_ANVIL_PLACE, 1f, 1f);
					for (int i = 0; i < 9; i++) {
						if (i != 4)
							inv.setItem(i, new ItemStack(Material.IRON_FENCE ));
					}
					animating.remove(p.getUniqueId());
					Bukkit.getScheduler().cancelTask(id);
				}
			}
		}, 0L, 1L);
	}
	
	public static EnchantMenu getMenu(String uid) {
		for (int i = 0; i < menus.size(); i++) {
			EnchantMenu menu = menus.get(i);
			if (menu.user.equalsIgnoreCase(uid))
				return menu;
		}
		return null;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		EnchantMenu menu = null;
		
		if (item != null && item.getType() != Material.AIR) {
			if (e.getInventory().getName().equalsIgnoreCase(inv.getName())) {
				menu = EnchantMenu.getMenu(p.getUniqueId().toString());
				e.setCancelled(true);
				if (item.getType() == Material.DIAMOND) {
				if (p.getLevel() >= 30) {
					menu.animate(p, 20 * 3L);
					p.setLevel(p.getLevel() - 30);
				} else 
					p.sendMessage("§cYou must be level 30!");
			} else if (item.getType() == Material.BOOK) {
				if (!animating.contains(p.getUniqueId())) {
					p.getInventory().addItem(item);
					p.closeInventory();
					
					Location loc = new Location(p.getWorld(), p.getLocation().getX() + 0.5, p.getLocation().getBlockY() + 2, p.getLocation().getZ() - 0.5);
	                for(int i = 0; i <360; i+=5){
	                    Location l = loc;
	                    l.setZ(l.getZ() + Math.cos(i));
	                    l.setX(l.getX() + Math.sin(i));
	                    loc.getWorld().playEffect(l, Effect.HAPPY_VILLAGER, 0);
	                }
					
				}
			}
			}
		}	
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		
		Player p = (Player) e.getPlayer();
		Inventory inv = e.getInventory();
		
		if (inv.getName().equalsIgnoreCase(this.inv.getName())) {
			EnchantMenu.menus.remove(EnchantMenu.getMenu(p.getUniqueId().toString()));
		}
	}
}