package fr.fifou.economy.packets;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.gui.GuiVaultSettings;
import fr.fifou.economy.items.ItemCreditcard;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.actors.threadpool.TimeUnit;

public class PacketListNBT implements IMessage {

	private static String names;
	private static int x;
	private static int y;
	private static int z;
	private static boolean isBlock2x2;
	private static String addrem;
	private static int index;

	
	public PacketListNBT() 
	{
		
	}
	
	public PacketListNBT(String names, int x, int y, int z, boolean isBlock2x2, String addrem, int index)
	{
		this.names = names;
		this.x = x;
		this.y = y;
		this.z = z;
		this.isBlock2x2 = isBlock2x2;
		this.addrem = addrem;
		this.index = index;
	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.names = ByteBufUtils.readUTF8String(buf);
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.isBlock2x2 = buf.readBoolean();
		this.addrem = ByteBufUtils.readUTF8String(buf);
		this.index = buf.readInt();
	}


	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeUTF8String(buf, this.names);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeBoolean(this.isBlock2x2);
		ByteBufUtils.writeUTF8String(buf, this.addrem);
		buf.writeInt(this.index);
	}
	
    
	public static class Handler implements IMessageHandler<PacketListNBT, IMessage>
	{

		public IMessage onMessage(PacketListNBT message, MessageContext ctx) 
		{
			EntityPlayer player = ctx.getServerHandler().player; // GET PLAYER
			World worldIn = player.world; // GET WORLD
				if(message.addrem.equals("add"))
				{
					if(message.isBlock2x2)
					{
						TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(message.x, message.y, message.z));
						if(te != null)
						{
							te.setOthers(message.names);
							te.addToMax();
							te.markDirty();
							
						}
					}
					else
					{
						TileEntityBlockVault te = (TileEntityBlockVault)worldIn.getTileEntity(new BlockPos(message.x, message.y, message.z));
						if(te != null)
						{
							te.setOthers(message.names);
							te.addToMax();
							te.markDirty();
						}
					}
				}
				else if(message.addrem.equals("remove"))
				{
					if(message.isBlock2x2)
					{
						TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(message.x, message.y, message.z));
						if(te != null)
						{
							te.getOthers().remove(message.index);
							te.removeToMax();
							te.markDirty();
						}
					}
					else
					{
						TileEntityBlockVault te = (TileEntityBlockVault)worldIn.getTileEntity(new BlockPos(message.x, message.y, message.z));
						if(te != null)
						{
							te.getOthers().remove(message.index);
							te.removeToMax();
							te.markDirty();
						}
					}	
				}		
			return null;
		}
	}
}

