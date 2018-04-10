package fr.fifou.economy.blocks.tileentity;

import java.awt.List;

import fr.fifou.economy.ConfigFile;
import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.containers.ContainerChanger;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBlockChanger extends TileEntity implements ITickable
{
	ItemStackHandler inventory = new ItemStackHandler(3);
	private byte direction;
	public int numbUse;
	public EntityPlayer user;
	public String name;
	
	public TileEntityBlockChanger()
	{
	}
	
	public TileEntityBlockChanger(int numbUse)
	{
		 this.numbUse = numbUse;
	}
	
	public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) 
    {
    	readFromNBT(pkt.getNbtCompound());
    }
    
	public ItemStack setStackInSlot(int slot, ItemStack stack, boolean simulate)
	{
		return inventory.insertItem(slot, stack, simulate);
	}
	
	public ItemStackHandler getHandler()
	{
		return inventory;
	}
		
	public int getNumbUse()
	{
		return this.numbUse;
	}
	
	public void setNumbUse(int numbUse)
	{
		this.numbUse = numbUse;
	}
	
	public EntityPlayer getEntityPlayer()
	{
		return this.user;
	}
	
	public void setEntityPlayer(EntityPlayer currentUser)
	{
		this.user = currentUser;
	}

    public byte getDirection()
    {
    	return this.direction;
    }

	public void setDirection(byte direction) 
	{
		this.direction = direction;
	}
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("numbUse", this.numbUse);
		return super.writeToNBT(compound);
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.numbUse = compound.getInteger("numbUse");
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
	}
	
		@Override
		public void markDirty() 
		{
			 IBlockState state = this.world.getBlockState(getPos());
		     this.world.notifyBlockUpdate(getPos(), state, state, 3);
		}
		

		@Override
		public void update() 
		{
			
			TileEntityBlockChanger tile = (TileEntityBlockChanger)world.getTileEntity(pos);
			ItemStack slot0 = tile.getStackInSlot(0);
			ItemStack slot1 = tile.getStackInSlot(1);
			ItemStack slot2 = tile.getStackInSlot(2);
			if(!world.isRemote && !slot0.isEmpty() && slot0.getItem() == ItemsRegistery.ITEM_GOLDNUGGET && slot0.hasTagCompound())
			{
				if(!slot1.isEmpty() && slot1.getItem() == ItemsRegistery.ITEM_CREDITCARD)
				{
					 if(slot1.hasTagCompound() && tile.getEntityPlayer() != null)
					    {
				        	String nameCard = slot1.getTagCompound().getString("OwnerUUID");
							String nameGame =  tile.getEntityPlayer().getUniqueID().toString();
							if(nameCard.equals(nameGame))
							{
								if(slot2.isEmpty())
								{
										EntityPlayer playerIn = getEntityPlayer();
										double fundsPrev = playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney();
										String weight = slot0.getTagCompound().getString("weight");
										double fundsNow = (fundsPrev + (Double.parseDouble(weight) * ConfigFile.multiplierGoldNuggetWeight));
										playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(fundsNow);
										playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(playerIn);
										slot0.splitStack(1);
										ItemStack copyOfCard = slot1.copy();
										slot1.splitStack(1);
										tile.setStackInSlot(2, copyOfCard, false);
								}
							}
					    }
					}
			}
		}

		public ItemStack removeStackFromSlot(int index) 
		{
			return inventory.getStackInSlot(index).splitStack(1);
		}

		public ItemStack getStackInSlot(int index) 
		{
			return inventory.getStackInSlot(index);
		}	
		
		
		public boolean isItemValidForSlot(int index, ItemStack stack) 
		{
			if(index == 0)
			{
				return stack.getItem() == ItemsRegistery.ITEM_GOLDNUGGET;
			}
			if(index == 1)
			{
				return stack.getItem() == ItemsRegistery.ITEM_CREDITCARD;
			}
			if(index == 3)
			{
				return false;
			}
			return true;
		}

		
}
