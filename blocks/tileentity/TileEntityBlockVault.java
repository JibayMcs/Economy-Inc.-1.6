package fr.fifou.economy.blocks.tileentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedTileEntity;

import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.containers.ContainerVault;
import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.BlockVault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBlockVault extends AnimatedTileEntity
{
	protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(TileEntityBlockVault.class);

	ItemStackHandler inventory = new ItemStackHandler(27);
	public String ownerS = "";
	private byte direction;
	private List<String> allowedPlayers = new ArrayList<String>();
	private int maxAllowedPlayers = 0;
	private boolean isOpen;
	
	
	static {
		//TileEntityBlockVault.animHandler.addAnim(ModEconomy.MODID, "anim_vault2by2", "model_vault", false);
	}
	
	public TileEntityBlockVault()
	{
		super();
	}
	
	//ANIMATION
	
    @Override
    public AnimationHandler getAnimationHandler() 
    {
        return TileEntityBlockVault2by2.animHandler;
    }

	public ItemStackHandler getHandler()
	{
		return inventory;
	}
		
	public TileEntityBlockVault(String ownerData)
	{
		 this.ownerS = ownerData;
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
    	this.allowedPlayers.clear();
    	readFromNBT(pkt.getNbtCompound());
    }
    // ANIMATION
	
	public boolean getIsOpen()
	{
		return this.isOpen;
	}
	
	public void setIsOpen(boolean isOpenIn)
	{
		this.isOpen = isOpenIn;
	}
	
	// AUTRES
    public void setOwner(String string)
    {
        this.ownerS = string;
    }
    
    public String getOwnerS()
    {
        return this.ownerS;
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
    
    public void setOthers(String allowed)
    {
    	this.allowedPlayers.add(allowed);
    }
    
    public List getOthers()
    {
		return this.allowedPlayers; 	
    }
      
    public int getMax()
    {
    	return this.maxAllowedPlayers;
    }
    
    public void addToMax()
    {
    	this.maxAllowedPlayers = this.maxAllowedPlayers + 1;
    }
    
    public void removeToMax()
    {
    	this.maxAllowedPlayers = this.maxAllowedPlayers - 1;
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
        compound.setString("ownerS", this.ownerS);
        compound.setByte("direction", this.direction);
        compound.setInteger("maxallowed", this.maxAllowedPlayers);
        NBTTagList tagList = new NBTTagList();
         for(int i = 0; i < this.allowedPlayers.size(); i++)
         {
          String s = allowedPlayers.get(i);
          if(s != null)
          {
              tagList.appendTag(new NBTTagString(s));
          }
         }
         compound.setTag("allowedList", tagList);
         compound.setBoolean("isOpen", this.isOpen);
        return super.writeToNBT(compound);
    }
    
    
    @Override
    public void readFromNBT(NBTTagCompound compound) 
    {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        this.ownerS = compound.getString("ownerS");
        this.direction = compound.getByte("direction");
        this.maxAllowedPlayers = compound.getInteger("maxallowed");
		this.isOpen = compound.getBoolean("isOpen");
        NBTTagList tagList = compound.getTagList("allowedList", NBT.TAG_STRING);
        for(int i = 0; i < tagList.tagCount(); i++)
        {    
            this.allowedPlayers.add(i, tagList.getStringTagAt(i));
        }
    }
    
	@Override
	public void markDirty() 
	{
		 IBlockState state = this.world.getBlockState(getPos());
	     this.world.notifyBlockUpdate(getPos(), state, state, 3);
	}

	
}
 
