package fr.fifou.economy.items;

import java.util.List;

import javax.annotation.Nullable;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMicroChip extends Item 
{
	public static final String NAME = "item_microchip";
	
	public ItemMicroChip() 
	{
		super();
		ItemsRegistery.setItemName(this, NAME);
		this.maxStackSize = 1;
		this.setCreativeTab(ModEconomy.tabEconomy);
		this.setUnlocalizedName(NAME);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn,EntityPlayer player, EnumHand handIn) 
	{
		ItemStack itemStackIn = player.getHeldItemOffhand();
		ItemStack itemStackInC = player.getHeldItemMainhand();
		int totalcount = 0;
		if(!worldIn.isRemote)
		{
			if(!player.inventory.hasItemStack(itemStackIn))
			{
				if(player.inventory.hasItemStack(itemStackInC))
				{
					for(int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if(player.inventory.getStackInSlot(i) != null)
						{
							if(player.inventory.getStackInSlot(i).getItem() instanceof ItemCreditcard)
							{
									totalcount++;
									ItemStack hasCardIS = player.inventory.getStackInSlot(i);
									if(!(totalcount > 1))
									{
											if(hasCardIS.hasTagCompound() && hasCardIS.getTagCompound().hasKey("Owner"))
											{
												
												String nameCard = hasCardIS.getTagCompound().getString("OwnerUUID");
												String nameGame = player.getUniqueID().toString();

												if(nameCard.equals(nameGame))
												{
													boolean linked = hasCardIS.getTagCompound().getBoolean("Linked");
													if(linked == false)
													{
														player.sendMessage(new TextComponentString("Card updated !"));
														hasCardIS.getTagCompound().setBoolean("Linked", true);
														player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setLinked(true);
														player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
													}
													else
													{
														player.sendMessage(new TextComponentString("Card is already linked"));
														player.addItemStackToInventory(itemStackInC);
														player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
													}
													
												}
											}
											else
											{
												player.addItemStackToInventory(itemStackInC);	
											}

								}
								return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
							}
						}
						else
						{
								player.sendMessage(new TextComponentString("You can only linked one card, please remove the uncessary cards"));
								return new ActionResult(EnumActionResult.FAIL, itemStackIn);
						}
					}
				}
			}
		}
		return new ActionResult(EnumActionResult.FAIL, itemStackIn);
	}
		
			

	
	
	@Override
 	@SideOnly(Side.CLIENT)
 	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
 	{
 		 tooltip.add(I18n.format("title.wireless"));
 	}
}
