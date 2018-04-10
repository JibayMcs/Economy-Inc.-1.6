package fr.fifou.economy.blocks.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityBlockSeller extends TileEntity 
{
	ItemStackHandler inventory_seller = new ItemStackHandler(1); //STACK HANDLER FOR ONE SLOT = 0 
	private String owner = ""; 
	private String ownerName = "";
	private double funds_total;
	private double cost;
	private boolean created;
	private int amount;
	private String item = "";
	private boolean admin;
	private String resources;
	private String facing = "";
	
	public TileEntityBlockSeller()
	{
		
	}
	
	public ItemStackHandler getHandler()
	{
		return inventory_seller;	
	}
	
	public TileEntityBlockSeller(String ownerUUID, String ownerName, double costS, int amountS, String itemS , double fundsTotalS, boolean createdS, boolean adminS, String facingS)
	{
		 this.owner = ownerUUID; //STORE OWNER UUID
		 this.ownerName = ownerName;
		 this.cost = costS; //STORE COST
		 this.amount = amountS; //STORE AMOUNT
		 this.item = itemS; //STORE ITEM NAME
		 this.funds_total = fundsTotalS; //STORE FUNDS TOTAL
		 this.created = createdS; //STORE CREATED BOOLEAN
		 this.admin = adminS; //STORE ADMINS BOOLEAN
		 this.facing = facingS;
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
    

	public ItemStack getStackInSlot(int slot)
	{
		return inventory_seller.getStackInSlot(slot);
	}
	
	public ItemStack removeStackInSlot(int slot)
	{
		return inventory_seller.getStackInSlot(slot).splitStack(1);
	}
	
	public void setFacing(String face)
	{
		this.facing = face;
	}
	
	public String getFacing()
	{
		return this.facing;
	}

	public void setAdmin(Boolean adminS)
	{
		this.admin = adminS;
	}
	
	public boolean getAdmin()
	{
		return this.admin;
	}
	
    public void setOwner(String string)
    {
        this.owner = string;
    }
    
    public String getOwner()
    {
        return this.owner;
    }
    
    public void setOwnerName(String stringName)
    {
    	this.ownerName = stringName;
    }
    
    public String getOwnerName()
    {
    	return this.ownerName;
    }
    
    public void setCost(double costS)
    {
    	this.cost = costS;
    }
    
    public double getCost()
    {
        return this.cost;
    }
    
    public void setFundsTotal(double fundsS)
    {
    	this.funds_total = fundsS;
    }
    
    public double getFundsTotal()
    {
    	return this.funds_total;
    }
    public void setCreated(boolean createdS)
    {
    	this.created = createdS;
    }
    
    public boolean getCreated()
    {
        return this.created;
    }
    
    public void setItem(String itemS)
    {
    	this.item = itemS;
    }
    
    public String getItem()
    {
    	return this.item;
    }
    
    public void setAmount(int amountS)
    {
    	this.amount = amountS;
    }
    
    public int getAmount()
    {
    	return this.amount;
    }
    
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setTag("inventory", inventory_seller.serializeNBT());
		compound.setString("ownerS", this.owner);
		compound.setString("ownerName", this.ownerName);
		compound.setDouble("cost", this.cost);
		compound.setInteger("amount", this.amount);
		compound.setString("item", this.item);
		compound.setDouble("funds_total", this.funds_total);
		compound.setBoolean("created", this.created);
		compound.setBoolean("admin", this.admin);
		compound.setString("facing", this.facing);
		return super.writeToNBT(compound);
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		inventory_seller.deserializeNBT(compound.getCompoundTag("inventory"));
		this.owner = compound.getString("ownerS");
		this.ownerName = compound.getString("ownerName");
		this.cost = compound.getDouble("cost");
		this.amount = compound.getInteger("amount");
		this.item = compound.getString("item");
		this.funds_total = compound.getDouble("funds_total");
		this.created = compound.getBoolean("created");
		this.admin = compound.getBoolean("admin");
		this.facing = compound.getString("facing");
	}	
	
	 @Override
	 public void markDirty() 
	 	{
	 
		 IBlockState state = this.world.getBlockState(getPos());
		 this.world.notifyBlockUpdate(getPos(), state, state, 3); 
	 	}
	
}
