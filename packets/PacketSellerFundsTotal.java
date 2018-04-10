package fr.fifou.economy.packets;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemCreditcard;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSellerFundsTotal implements IMessage {

	private double fundstotal;
	private int x;
	private int y;
	private int z;
	private int amount;
	private boolean recovery;
	
	public PacketSellerFundsTotal() 
	{
		
	}
	
	public PacketSellerFundsTotal(double fundstotalS, int xS, int yS, int zS, int amountS, boolean recoveryS)
	{
		this.fundstotal = fundstotalS;
		this.x = xS;
		this.y = yS;
		this.z = zS;
		this.amount = amountS;
		this.recovery = recoveryS;
		
	}
	
	public void fromBytes(ByteBuf buf) 
	{
		this.fundstotal = buf.readDouble();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.amount = buf.readInt();
		this.recovery = buf.readBoolean();
	}


	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(this.fundstotal);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.amount);
		buf.writeBoolean(this.recovery);
	}
	
	public static class Handler implements IMessageHandler<PacketSellerFundsTotal, IMessage>
	{
		public IMessage onMessage(PacketSellerFundsTotal message, MessageContext ctx) 
		{
			EntityPlayer player = ctx.getServerHandler().player; // GET PLAYER
			World world = player.world;  // GET WORLD FROM PLAYER
			BlockPos pos = new BlockPos(message.x, message.y, message.z); // NEW BLOCK POS FOR TILE ENTITY COORDINATES
			TileEntityBlockSeller te = (TileEntityBlockSeller)world.getTileEntity(pos); // GET THE TILE ENTITY IN WORLD THANKS TO COORDINATES
			
			if(te != null) // IF TILE ENTITY EXIST
			{
				if(message.recovery == false)
				{
					if(!te.getStackInSlot(0).isEmpty()) // IF THE SLOT IS NOT EMPTY
					{
						boolean admin = te.getAdmin();
						if(admin == false) // NOT UNLIMITED STACK
						{
							te.setFundsTotal(message.fundstotal); // SERVER SET THE FUNDS TOTAL FROM WHAT WE SENT
							te.getStackInSlot(0);
							player.addItemStackToInventory(te.getStackInSlot(0).splitStack(1)); // SERVER ADD STACK IN SLOT BY 1
							int newAmount = message.amount - 1; // INT THE NEW AMOUNT OF STACK IN SLOT
							te.setAmount(newAmount); // SERVER SET THE NEW AMOUNT OF STACK IN SLOT
							te.markDirty();
						}
						else if(admin == true) // UNLIMITED STACK
						{
							te.setFundsTotal(message.fundstotal); // SERVER SET THE FUNDS TOTAL FROM WHAT WE SENT
							te.setAmount(message.amount); // SERVER SET THE NEW AMOUNT OF STACK IN SLOT
							ItemStack stackToGive = te.getStackInSlot(0).copy().splitStack(1);
							player.addItemStackToInventory(stackToGive); // SERVER ADD STACK IN SLOT BY 1
							te.markDirty();	
						}
					}
				}
				else if(message.recovery == true)
				{
					player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() + message.fundstotal);	
					player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
					te.setFundsTotal(0);
					te.markDirty();
				}
			}

			return null;
		}
	}
}
