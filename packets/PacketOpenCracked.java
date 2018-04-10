package fr.fifou.economy.packets;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.gui.GuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenCracked implements IMessage {

	private static int x;
	private static int y;
	private static int z;
	private static boolean cracked;
	
	public PacketOpenCracked() 
	{
		
	}
	
	public PacketOpenCracked(int x, int y, int z, boolean cracked)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.cracked = cracked;
	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.cracked = buf.readBoolean();
	}

	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeBoolean(this.cracked);
	}
	
	public static class Handler implements IMessageHandler<PacketOpenCracked, IMessage>
	{

		public IMessage onMessage(PacketOpenCracked message, MessageContext ctx) 
		{
				EntityPlayer player = ctx.getServerHandler().player;
				TileEntityBlockVaultCracked te = (TileEntityBlockVaultCracked)player.world.getTileEntity(new BlockPos(message.x,message.y,message.z));
				if(te != null)
				{
					if(message.cracked)
					{
						player.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_CRACKED, player.world, message.x, message.y, message.z);
						te.setCracked(true);
						te.markDirty();
					}
					else
					{
						player.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_CRACKING, player.world, message.x, message.y, message.z);
					}
				}
				return null;
		}
	}
}