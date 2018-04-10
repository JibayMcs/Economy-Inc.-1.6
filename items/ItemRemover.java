package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemRemover extends Item 
{
	public static final String NAME = "item_remover";
	
	public ItemRemover() 
	{
		super();
		ItemsRegistery.setItemName(this, NAME);
		this.maxStackSize = 1;
		this.setCreativeTab(ModEconomy.tabEconomy);
		this.setUnlocalizedName(NAME);
	}
}
