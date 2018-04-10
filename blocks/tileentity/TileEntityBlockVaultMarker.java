package fr.fifou.economy.blocks.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityBlockVaultMarker extends TileEntity {

	private byte direction;

	public TileEntityBlockVaultMarker()
	{
	}
	
	public TileEntityBlockVaultMarker(byte directionIn)
	{
		 this.direction = directionIn;
	}
	
	public void setDirection(byte directionIn)
	{
		this.direction = directionIn;
	}
	
	public byte getDirection()
	{
		return this.direction;
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
        compound.setByte("direction", this.direction);
        return super.writeToNBT(compound);
    }
    
    
    @Override
    public void readFromNBT(NBTTagCompound compound) 
    {
        super.readFromNBT(compound);
        this.direction = compound.getByte("direction");
    }
    
	@Override
	public void markDirty() 
	{
		 IBlockState state = this.world.getBlockState(getPos());
	     this.world.notifyBlockUpdate(getPos(), state, state, 3);
	}

	
}
