package fr.fifou.economy.packets;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSellerCreated implements IMessage 
{
	private static boolean created; 
	private double cost;
	private int x;
	private int y;
	private int z;
	private String name = "";
	private int amount = 0;
	private boolean admin;

	public PacketSellerCreated() 
	{
		
	}

	public PacketSellerCreated(boolean createdS, double costS, String nameS, int amountS, int xS, int yS, int zS, boolean adminS)
	{
		this.created = createdS; // WE TAKE BACK THE INFOS FROM GuiSeller
		this.x = xS;
		this.y = yS;
		this.z = zS;
		this.name = nameS;
		this.amount = amountS;
		this.cost = costS;
		this.admin = adminS;
	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.created =  buf.readBoolean();
		this.cost = buf.readDouble();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.name = ByteBufUtils.readUTF8String(buf);
		this.amount = buf.readInt();
		this.admin = buf.readBoolean();
	}


	public void toBytes(ByteBuf buf) 
	{
		buf.writeBoolean(this.created);
		buf.writeDouble(this.cost);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		ByteBufUtils.writeUTF8String(buf, this.name);
		buf.writeInt(this.amount);
		buf.writeBoolean(this.admin);

	}
	
	public static class Handler implements IMessageHandler<PacketSellerCreated, IMessage>
	{
		public IMessage onMessage(PacketSellerCreated message, MessageContext ctx) 
		{
			EntityPlayer player = ctx.getServerHandler().player;
			World world = player.world;  
			BlockPos pos = new BlockPos(message.x, message.y, message.z);
			TileEntityBlockSeller te = (TileEntityBlockSeller)world.getTileEntity(pos); //WE TAKE THE POSITION OF THE TILE ENTITY TO ADD INFO
			if(te != null) // CHECK IF PLAYER HAS NOT DESTROYED TILE ENTITY IN THE SHORT TIME OF SENDING PACKET
			{
				te.setCreated(message.created); // SERVER ADD CREATED TO TILE ENTITY
				te.setCost(message.cost); // SERVER ADD COST TO TILE ENTITY
				te.setItem(message.name); // SERVER ADD NAME TO TILE ENTITY
				te.setAmount(message.amount); // SERVER ADD AMOUNT TO TILE ENTITY
				te.setAdmin(message.admin); // SERVER ADD ADMIN TO TILE ENTITY
				te.markDirty(); //UPDATE THE TILE ENTITY

			}
			return null;
		}
	}
}