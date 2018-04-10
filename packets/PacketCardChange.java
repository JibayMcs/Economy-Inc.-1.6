package fr.fifou.economy.packets;

import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.gui.GuiItem;
import fr.fifou.economy.items.ItemsRegistery;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.server.FMLServerHandler;

public class PacketCardChange implements IMessage {

	private static double funds;
	public PacketCardChange() 
	{
		
	}
	
	public PacketCardChange(double funds)
	{
		this.funds = funds;
	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.funds = buf.readDouble();
	}


	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(this.funds);
	}
	
	public static class Handler implements IMessageHandler<PacketCardChange, IMessage>
	{

		public IMessage onMessage(PacketCardChange message, MessageContext ctx) 
		{
				EntityPlayer player = ctx.getServerHandler().player;
				ItemStack heldItem = player.getHeldItemMainhand();
				IMoney handlerCap = CapabilityLoading.getHandler(player);
				
						if(funds == 1)
						{
							if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= 1)
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money - 1);
								player.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_ONEB));
								handlerCap.sync((EntityPlayerMP)player);
							}
							
						}
						else if(funds == 5)
						{
							if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= 5)
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money - 5);
								player.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIVEB));
								handlerCap.sync((EntityPlayerMP)player);
							}
							
						}
						else if(funds == 10)
						{
							if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= 10)
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money - 10);
								player.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TENB));
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == 20)
						{
							if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= 20)
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money - 20);
								player.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TWENTYB));
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == 50)
						{
							if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= 50)
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money - 50);
								player.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIFTYB));
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == 100)
						{
							if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= 100)
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money - 100);
								player.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_HUNDREEDB));
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == 200)
						{
							if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= 200)
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money - 200);
								player.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB));
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == 500)
						{
							if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= 500)
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money - 500);
								player.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB));
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == -1)
						{
							if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_ONEB))))
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money + 1);
								player.inventory.clearMatchingItems(ItemsRegistery.ITEM_ONEB, 0, 1, null);
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == -5)
						{
							if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_FIVEB))))
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money + 5);
								player.inventory.clearMatchingItems(ItemsRegistery.ITEM_FIVEB, 0, 1, null);
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == -10)
						{
							if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_TENB))))
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money + 10);
								player.inventory.clearMatchingItems(ItemsRegistery.ITEM_TENB, 0, 1, null);
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync((EntityPlayerMP) player);
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == -20)
						{
							if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_TWENTYB))))
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money + 20);
								player.inventory.clearMatchingItems(ItemsRegistery.ITEM_TWENTYB, 0, 1, null);
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == -50)
						{
							if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_FIFTYB))))
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money + 50);
								player.inventory.clearMatchingItems(ItemsRegistery.ITEM_FIFTYB, 0, 1, null);
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == -100)
						{
							if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_HUNDREEDB))))
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money + 100);
								player.inventory.clearMatchingItems(ItemsRegistery.ITEM_HUNDREEDB, 0, 1, null);
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == -200)
						{
							if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_TWOHUNDREEDB))))
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money + 200);
								player.inventory.clearMatchingItems(ItemsRegistery.ITEM_TWOHUNDREEDB, 0, 1, null);
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
						else if(funds == -500)
						{
							if(player.inventory.hasItemStack(new ItemStack((ItemsRegistery.ITEM_FIVEHUNDREEDB))))
							{
								double previous_money = handlerCap.getMoney();
								handlerCap.setMoney(previous_money + 500);
								player.inventory.clearMatchingItems(ItemsRegistery.ITEM_FIVEHUNDREEDB, 0, 1, null);
								handlerCap.sync((EntityPlayerMP) player);

							}
						}
			return null;
		}
			
	}
		
		
}