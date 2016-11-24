package me.Zacx.YE.Enchantments;

import org.bukkit.Material;

import me.Zacx.YE.Properties.TypeLists;

public enum EnchantmentType {

	//constant effect, proc's on attack, proc's when attacked
	BUFF(TypeLists.Armour.list), ATTACK(TypeLists.Melee.list), DEFENCE(TypeLists.Armour.list);

	public Material[] types;
	private EnchantmentType(Material[] types) {
		this.types = types;
	}
	
}