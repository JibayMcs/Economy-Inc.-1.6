package fr.fifou.economy.items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import fr.fifou.economy.ModEconomy;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGoldNuggetSubs extends Item {

	public static final String NAME = "item_goldnuggetsubs";


	public ItemGoldNuggetSubs() 
	{
		super();
		ItemsRegistery.setItemName(this, NAME);
		this.maxStackSize = 1;
		this.setCreativeTab(ModEconomy.tabEconomy);
		this.setUnlocalizedName(NAME);
	}
	
	@Override
 	@SideOnly(Side.CLIENT)
 	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
 	{
 		 if(!stack.hasTagCompound())
	        {
	            return;
	        }
 		 
	        String weight = stack.getTagCompound().getString("weight");
	        tooltip.add(I18n.format("title.weight") + weight +"g");
 	}


}
