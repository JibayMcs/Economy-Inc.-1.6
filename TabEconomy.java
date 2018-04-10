package fr.fifou.economy;

import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabEconomy extends CreativeTabs 
{

	public TabEconomy(int i, String label) 
	{
		super(i,label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		//ICON OF THE TAB
		return new ItemStack(ItemsRegistery.ITEM_CREDITCARD, 1, 0);
	}
	public String getTranslatedTabLabel()
	{
	// NAME
	return "Economy Inc.";
	}


}

