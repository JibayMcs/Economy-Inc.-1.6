package fr.fifou.economy.blocks.tileentity;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityBlockBills extends TileEntity
{
	private byte direction;
	public int numbBills = 0;
	public String billRef = "";
	
	public TileEntityBlockBills() 
	{
		
	}
	
	public TileEntityBlockBills(byte dirIn, int numbBillsIn, String billRefIn)
	{
		 this.numbBills = numbBillsIn;
		 this.direction = dirIn;
		 this.billRef = billRefIn;
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
    
    public String getBill()
    {
    	return this.billRef;
    }
    
    public void setBillRef(String billRefIn)
    {
    	this.billRef = billRefIn;
    }
    
    public int getNumbBills()
	{
		return this.numbBills;
	}
	
	public void setNumbUse(int numbBillsIn)
	{
		this.numbBills = numbBillsIn;
	}
	
	public void addBill()
	{
		this.numbBills = this.numbBills + 1;
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
		compound.setInteger("numbBills", this.numbBills);
		compound.setByte("direction", this.direction);
		compound.setString("billRef", this.billRef);
		return super.writeToNBT(compound);
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.numbBills = compound.getInteger("numbBills");
		this.direction = compound.getByte("direction");
		this.billRef = compound.getString("billRef");
	}
	
		@Override
		public void markDirty() 
		{
			 IBlockState state = this.world.getBlockState(getPos());
		     this.world.notifyBlockUpdate(getPos(), state, state, 3);
		}


}
