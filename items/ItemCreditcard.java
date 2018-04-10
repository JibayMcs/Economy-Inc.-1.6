package fr.fifou.economy.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import fr.fifou.economy.ConfigFile;
import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.DefaultMoneyHandler;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.capability.Provider;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.gui.GuiItem;
import fr.fifou.economy.packets.PacketCardChange;
import fr.fifou.economy.packets.PacketSellerCreated;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.BlockEntityTag;
import net.minecraft.util.datafix.walkers.EntityTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.server.FMLServerHandler;
 
public class ItemCreditcard extends Item
{
	public static final String NAME = "item_creditcard";
	private double funds;
	private boolean link;
	
	public ItemCreditcard() 
	{
		super();
		ItemsRegistery.setItemName(this, NAME);
		this.maxStackSize = 1;
		this.setCreativeTab(ModEconomy.tabEconomy);
		this.setUnlocalizedName(NAME);
	
	}
	
	 @Override
	    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	    {
		 	ItemStack itemStackIn = playerIn.getHeldItemMainhand();
		 	if(playerIn.getHeldItemOffhand().isItemEqual(new ItemStack(ItemsRegistery.ITEM_CREDITCARD)))
		 	{
		 	}
		 	else
		 	{
		        if(!playerIn.isSneaking())
		        {	
				        if(itemStackIn.hasTagCompound()) 
					    {
				        	String nameCard = playerIn.getHeldItemMainhand().getTagCompound().getString("OwnerUUID");
							String nameGame = playerIn.getUniqueID().toString();
							if(nameCard.equals(nameGame))
							{
								if(worldIn.isRemote)
								{
									if(ConfigFile.canAccessCardWithoutWT)
									{
										if(playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getLinked())
										{
											playerIn.openGui(ModEconomy.instance, GuiHandler.ITEM_CARD_GUI, worldIn,0, 0, 0); 
										}
										else
										{
											playerIn.sendMessage(new TextComponentString("You don't have the wireless technology to access your card."));
										}
									}
								}
								else if(!worldIn.isRemote)
								{
									playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(playerIn);
								}
							}
							else
							{
								/*if(!worldIn.isRemote)
								{
									String playerCard = playerIn.getHeldItemMainhand().getTagCompound().getString("OwnerUUID"); //Get String UUID of card's owner.
									UUID OwnerUUID = UUID.fromString(playerCard); //Transform string of card's owner to UUID.
									EntityPlayer playerCardO = worldIn.getPlayerEntityByUUID(OwnerUUID); //EntityPlayer from the UUID of card's owner.
									
									String[] playerOnline = FMLServerHandler.instance().getServer().getOnlinePlayerNames(); //Get all players online.
										for(String st : playerOnline)
								        {
											String nameGamePlayerS = playerCardO.getName();
								            if(playerCardO.getName().equals(st) || !playerCardO.isDead)
								            {
								            	String nameGameNotOwner = playerIn.getName();
												playerCardO.sendMessage(new TextComponentString(TextFormatting.RED + "WARNING: " + nameGameNotOwner + " is using your card, maybe without permission!"));
								            }
								       }
								} */System.out.println("Will be fix in another version of the mod. Quite bugged for the moment. Fifou_BE - Author");
							}
				        }
		        		
		            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
		        }
		       
		        if(!worldIn.isRemote)
		        {
		            if(!itemStackIn.hasTagCompound())
		            {
		                itemStackIn.setTagCompound(new NBTTagCompound());
		            }
		           
		            if(!itemStackIn.getTagCompound().hasKey("Owner"))
		            {
		              	UUID ownerUUID = playerIn.getUniqueID();
		              	playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setLinked(false);
		              	itemStackIn.getTagCompound().setString("OwnerUUID", ownerUUID.toString());
		              	itemStackIn.getTagCompound().setString("Owner", playerIn.getDisplayNameString());
		                itemStackIn.getTagCompound().setBoolean("Owned", true);
		                itemStackIn.getTagCompound().setBoolean("Linked", false);
		                playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F)); //Play a sound that alert everybody that a credit card was created.
						playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(playerIn);
		            }
		            
		        }

		 	
		 	}
	        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	    }
	 
	 	@Override
	 	@SideOnly(Side.CLIENT)
	 	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	 	{
		 		EntityPlayer playerIn = Minecraft.getMinecraft().player;
		 		
		 		 if(!stack.hasTagCompound())
			        {
			            return;
			        }
			 	 double funds = playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney();
			     boolean linked = stack.getTagCompound().getBoolean("Linked");
			     String linkedValue = "";
		 		 if(linked == true)
		 		 {
		 			 linkedValue = I18n.format("title.yes");
		 		 }
		 		 else
		 		 {
		 			linkedValue = I18n.format("title.no");
		 		 }
			        String ownerName = stack.getTagCompound().getString("Owner");
			        boolean owned = stack.getTagCompound().getBoolean("Owned");
			      
			        tooltip.add(I18n.format("title.ownerCard") + " : " + ownerName);
			        tooltip.add(I18n.format("title.fundsCard") + " : " + String.valueOf(funds));
			        tooltip.add(I18n.format("title.linkdCard") + " : " + linkedValue);
	 	}

	    
	    @Override
		@SideOnly(Side.CLIENT)
	    public boolean hasEffect(ItemStack stack)
	    {
	    	if(!stack.hasTagCompound())
	        {
	            return false;
	        }
	       
	       return true;
	    }
	   }
