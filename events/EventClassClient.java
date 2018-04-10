package fr.fifou.economy.events;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.text.DecimalFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.capability.Provider;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.items.ItemCreditcard;
import fr.fifou.economy.items.ItemsRegistery;

public class EventClassClient 
{

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event)
	{	
			EntityPlayer playerIn = event.getPlayer();
			World worldIn = playerIn.world;
			if(event.getTarget().typeOfHit ==  event.getTarget().typeOfHit.ENTITY)
			{
				return;
			}
			else
			{
				if(worldIn != null)
				{
					
					if(worldIn.getBlockState(event.getTarget().getBlockPos()).getBlock() == BlocksRegistery.BLOCK_SELLER)
					{
						TileEntityBlockSeller te = (TileEntityBlockSeller)worldIn.getTileEntity(event.getTarget().getBlockPos());
						if(te != null)
						{
							if(te.getCreated())
							{
								int x = event.getTarget().getBlockPos().getX();
								int y = event.getTarget().getBlockPos().getY();
								int z = event.getTarget().getBlockPos().getZ();
								float i = 0f;
								float j = 0.0F;
								RenderManager renderM = Minecraft.getMinecraft().getRenderManager();
								GL11.glPushMatrix();
								GlStateManager.enableRescaleNormal();
								RenderHelper.enableStandardItemLighting();
								if(te.getStackInSlot(0).getUnlocalizedName().substring(0, 4).equals("tile"))
								{
									i = 0.1F;
								}
								if(te.getFacing().substring(0, 4).equals("west"))
								{
									j = 94F;
								}
								else if(te.getFacing().substring(0, 4).equals("east"))
								{
									j = 31.5F;
								}
								else if(te.getFacing().equals("north"))
								{
									j = 188F;
								}
								ItemStack stack = new ItemStack(te.getStackInSlot(0).getItem(), 1, te.getStackInSlot(0).getMetadata());
								if(te.getAmount() == 0)
								{
									stack = new ItemStack(Blocks.BARRIER, 1, 0);
								}
								EntityItem entItem = new EntityItem(worldIn, x + 0.5, y + i, z + 0.5, stack);
								entItem.hoverStart = 0.0F;
								renderM.renderEntityStatic(entItem, 1.0F * j, false);
								RenderHelper.disableStandardItemLighting();
								GlStateManager.disableRescaleNormal();
								GL11.glPopMatrix();		
							}
						}
					}
				}
			}
		}
	
    @SubscribeEvent
    public void onplayerLoggedInEvent(PlayerLoggedInEvent event) 
    {
    	event.player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync((EntityPlayerMP)event.player);
    }
}
