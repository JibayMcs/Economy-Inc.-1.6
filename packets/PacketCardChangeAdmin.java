package fr.fifou.economy.packets;

import java.util.UUID;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemCreditcard;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCardChangeAdmin implements IMessage {

	private static String uuid;
	private static double funds;
	private static String type;
	
	public PacketCardChangeAdmin() 
	{
		
	}
	
	public PacketCardChangeAdmin(double funds, String uuid, String type)
	{
		this.funds = funds;
		this.uuid = uuid;
		this.type = type;
	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.funds = buf.readDouble();
		this.uuid = ByteBufUtils.readUTF8String(buf);
		this.type = ByteBufUtils.readUTF8String(buf);
	}


	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(this.funds);
		ByteBufUtils.writeUTF8String(buf, this.uuid);
		ByteBufUtils.writeUTF8String(buf, this.type);
	}
	
    
	public static class Handler implements IMessageHandler<PacketCardChangeAdmin, IMessage>
	{

		public IMessage onMessage(PacketCardChangeAdmin message, MessageContext ctx) 
		{
			EntityPlayer player = ctx.getServerHandler().player;		
			World worldIn = player.world;
			UUID uuidMP = UUID.fromString(uuid);
			EntityPlayer playerCard = worldIn.getPlayerEntityByUUID(uuidMP);
			
				if(type.equals("add"))
				{
					for(int i = 0; i < playerCard.inventory.getSizeInventory(); i++)
						{
							if(playerCard.inventory.getStackInSlot(i) != null)
							{
								if(playerCard.inventory.getStackInSlot(i).getItem() instanceof ItemCreditcard)
								{
										ItemStack hasCardIS = playerCard.inventory.getStackInSlot(i);
										if(hasCardIS.hasTagCompound() && hasCardIS.getTagCompound().hasKey("Owner"))
										{
											String playerCardO = hasCardIS.getTagCompound().getString("OwnerUUID"); //Get String UUID of card's owner.
											if(playerCardO != null)
											{
												if(playerCardO.equals(player.getUniqueID().toString()))
												{
													IMoney handlerCap = CapabilityLoading.getHandler(playerCard);
													double fundsCardPrev = hasCardIS.getTagCompound().getDouble("Funds");
													double fundsCardNow = fundsCardPrev + funds;
													hasCardIS.getTagCompound().setDouble("Funds", fundsCardNow);
											    }
											}										
										}
								}
							}
						}
				}
				else if(type.equals("remove"))
				{
					for(int i = 0; i < playerCard.inventory.getSizeInventory(); i++)
					{
						if(playerCard.inventory.getStackInSlot(i) != null)
						{
							if(playerCard.inventory.getStackInSlot(i).getItem() instanceof ItemCreditcard)
							{
									ItemStack hasCardIS = playerCard.inventory.getStackInSlot(i);
									if(hasCardIS.hasTagCompound() && hasCardIS.getTagCompound().hasKey("Owner"))
									{
										
										String playerCardO = hasCardIS.getTagCompound().getString("OwnerUUID"); //Get String UUID of card's owner.
										if(playerCardO != null)
										{
											if(playerCardO.equals(player.getUniqueID().toString()))
											{
												IMoney handlerCap = CapabilityLoading.getHandler(playerCard);
												double fundsCardPrev = hasCardIS.getTagCompound().getDouble("Funds");
												double fundsCardNow = fundsCardPrev - funds;
												hasCardIS.getTagCompound().setDouble("Funds", fundsCardNow);
										    }
										}
									
									}
							}
						}
					}
				}
			return null;
		}
	}
}