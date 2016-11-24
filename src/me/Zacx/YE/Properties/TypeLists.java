package me.Zacx.YE.Properties;

import org.bukkit.Material;

public enum TypeLists {

	
	Picks(new Material[] {Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE}),
	Shovels(new Material[] {Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE}),
	Axes(new Material[] {Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE}),
	Swords(new Material[] {Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD}),
	Bow(new Material[] {Material.BOW}), Boots(new Material[] {Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS}),
	Leggings(new Material[] {Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS}),
	Chests(new Material[] {Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.DIAMOND_CHESTPLATE}),
	Helmets(new Material[] {Material.LEATHER_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET}),
	Tools(combine(combine(Picks.list, Shovels.list), Axes.list)),
	Melee(combine(Swords.list, Axes.list)),
	Armour(combine(combine(Boots.list, Leggings.list), combine(Chests.list, Helmets.list)));
	
	public Material[] list;
	
	private TypeLists(Material[] list) {
		this.list = list;
	}
	
	private static Material[] combine(Material[] tar, Material[] src) {
		Material[] r = new Material[tar.length + src.length];
		for (int i = 0; i < tar.length; i++) {
			r[i] = tar[i];
		}
		for (int i = 0; i < src.length; i++) {
			r[tar.length + i] = src[i];
		}
		return r;
	}
	
}
