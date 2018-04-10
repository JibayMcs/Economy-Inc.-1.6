package fr.fifou.economy.packets;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.gui.GuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIsOpen implements IMessage {

	private static int x;
	private static int y;
	private static int z;
	private static boolean open;
	
	public PacketIsOpen() 
	{
		
	}
	
	public PacketIsOpen(int x, int y, int z, boolean openIn)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.open = openIn;
	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.open = buf.readBoolean();
	}

	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeBoolean(this.open);
	}
	
	
	public static class Handler implements IMessageHandler<PacketIsOpen, IMessage>
	{

		public IMessage onMessage(PacketIsOpen message, MessageContext ctx) 
		{
			EntityPlayer playerIn = ctx.getServerHandler().player;
			TileEntityBlockVault te = (TileEntityBlockVault)playerIn.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
			if(te != null)
			{
				te.setIsOpen(false);
				te.markDirty();
			}
			return null;
		}
	}
}
