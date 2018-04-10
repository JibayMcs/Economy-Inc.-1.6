package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemPacketFive extends Item {
	
	public static final String NAME = "item_packetfive";


	public ItemPacketFive() 
	{
		super();
		ItemsRegistery.setItemName(this, NAME);
		this.maxStackSize = 64;
		this.setCreativeTab(ModEconomy.tabEconomy);
		this.setUnlocalizedName(NAME);
	}
}
