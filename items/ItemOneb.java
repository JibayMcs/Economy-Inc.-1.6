package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemOneb extends Item {

	public static final String NAME = "item_oneb";
	
	public ItemOneb() 
	{
		super();
		ItemsRegistery.setItemName(this, NAME);
		this.maxStackSize = 64;
		this.setCreativeTab(ModEconomy.tabEconomy);
		this.setUnlocalizedName(NAME);
	}
	

}
