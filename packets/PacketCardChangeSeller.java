package fr.fifou.economy.packets;

import java.util.UUID;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemCreditcard;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCardChangeSeller implements IMessage {

	private static double cost;

	
	public PacketCardChangeSeller() 
	{
		
	}
	
	public PacketCardChangeSeller(double cost)
	{
		this.cost = cost;

	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.cost = buf.readDouble();
	}


	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(this.cost);
	}
	
    
	public static class Handler implements IMessageHandler<PacketCardChangeSeller, IMessage>
	{

		public IMessage onMessage(PacketCardChangeSeller message, MessageContext ctx) 
		{
			EntityPlayer player = ctx.getServerHandler().player; // GET PLAYER
			World worldIn = player.world; // GET WORLD
			player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() - message.cost);		
			player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
			return null;
		}
	}

}
