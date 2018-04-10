package fr.fifou.economy.packets;

import fr.fifou.economy.entity.EntityInformater;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketInformaterTrading implements IMessage {

	public static int stackSlot;
	public static int code;
	
	public PacketInformaterTrading() 
	{
		
	}
	
	public PacketInformaterTrading(int stackSlot, int safeCode)
	{
		this.stackSlot = stackSlot;
		this.code = safeCode;
	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.stackSlot = buf.readInt();
		this.code = buf.readInt();
	}

	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(this.stackSlot);
		buf.writeInt(this.code);
	}
	
	public static class Handler implements IMessageHandler<PacketInformaterTrading, IMessage>
	{

		public IMessage onMessage(PacketInformaterTrading message, MessageContext ctx) 
		{
			EntityPlayer player = ctx.getServerHandler().player;
			World world = player.world;

			player.inventory.getStackInSlot(message.stackSlot).setCount(player.inventory.getStackInSlot(message.stackSlot).getCount() - 1);
			player.inventory.addItemStackToInventory(new ItemStack(Items.PAPER).setStackDisplayName("Code : " + String.valueOf(message.code)));
			
			return null;
		}
	}

}
