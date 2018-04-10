package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemPacketOne extends Item {

	public static final String NAME = "item_packetone";

	public ItemPacketOne() 
	{
		super();
		ItemsRegistery.setItemName(this, NAME);
		this.maxStackSize = 64;
		this.setCreativeTab(ModEconomy.tabEconomy);
		this.setUnlocalizedName(NAME);
	}
}
