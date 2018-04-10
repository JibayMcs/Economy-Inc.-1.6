package fr.fifou.economy.blocks.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBlockVaultCracked extends TileEntity
{
	ItemStackHandler inventory = new ItemStackHandler(27);
	private byte direction;
	private boolean alreadyCracked = false;
	private String password = "";
	
	public TileEntityBlockVaultCracked()
	{
	}
	
	public TileEntityBlockVaultCracked(byte direction)
	{
		this.direction = direction;
	}
		
	public ItemStackHandler getHandler()
	{
		return inventory;
	}
	
	public byte getDirection()
	{
		return this.direction;
	}
	
	public void setDirection(byte direction)
	{
		this.direction = direction;
	}
	
	public boolean getCracked()
	{
		return this.alreadyCracked;
	}
	
	public void setCracked(boolean c)
	{
		this.alreadyCracked = c;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setPassword(String pass)
	{
		this.password = pass;
	}
	
    public Boolean hasItems()
    {
    	for(int i = 0; i < 27; i++)
    	{
    		if(inventory.getStackInSlot(i) != ItemStack.EMPTY)
    		{
    			return true;
    		}
    	}
		return false;	
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
	
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) 
    {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setByte("direction", this.direction);
        compound.setBoolean("alreadyCracked", this.alreadyCracked);
        compound.setString("pass", this.password);
        return super.writeToNBT(compound);
    }
    
    
    @Override
    public void readFromNBT(NBTTagCompound compound) 
    {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        this.direction = compound.getByte("direction");
        this.alreadyCracked = compound.getBoolean("alreadyCracked");
        this.password = compound.getString("pass");
    }
    
	@Override
	public void markDirty() 
	{
		 IBlockState state = this.world.getBlockState(getPos());
	     this.world.notifyBlockUpdate(getPos(), state, state, 3);
	}
    
}