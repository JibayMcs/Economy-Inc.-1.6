package fr.fifou.economy.items;

import java.util.List;
import java.util.Set;

import fr.fifou.economy.ModEconomy;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemVaultCracker extends Item {

	public static final String NAME = "item_vault_cracker";

	public ItemVaultCracker() 
	{
		super();
	    this.setMaxDamage(100);
		ItemsRegistery.setItemName(this, NAME);
		this.maxStackSize = 1;
		this.setCreativeTab(ModEconomy.tabEconomy);
		this.setUnlocalizedName(NAME);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
       return true;
    }
	
	
}
