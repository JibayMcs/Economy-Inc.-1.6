package fr.fifou.economy.blocks.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.animation.CSAnimChannel;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.AnimatedTileEntity;

import fr.fifou.economy.ModEconomy;
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

public class TileEntityBlockVault2by2 extends AnimatedTileEntity 
{
	protected static AnimationHandler animHandler = CraftStudioApi.getNewAnimationHandler(TileEntityBlockVault2by2.class);
	
	ItemStackHandler inventory = new ItemStackHandler(54);
	public String ownerS = "";
	private byte direction;
	private List<String> allowedPlayers = new ArrayList<String>();
	private int maxAllowedPlayers = 0;
	
	static {
		TileEntityBlockVault2by2.animHandler.addAnim(ModEconomy.MODID, "anim_vault2by2", "model_vault2by2", false);
	}
	
	public TileEntityBlockVault2by2()
	{
		super();
	}
	//ANIMATION
	
    @Override
    public AnimationHandler getAnimationHandler() 
    {
        return TileEntityBlockVault2by2.animHandler;
    }
	
    @Override
    public void update() 
    {
    	super.update();

    }
    
	//REST
	public ItemStackHandler getHandler()
	{
		return inventory;
	}
		
	
	
	public TileEntityBlockVault2by2(String ownerData)
	{
		 this.ownerS = ownerData;
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
	
    public void setString(String string)
    {
        this.ownerS = string;
    }
    
    public String getOwnerS()
    {
        return this.ownerS;
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
    	this.maxAllowedPlayers = this.maxAllowedPlayers -1;
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
        NBTTagList tagList = compound.getTagList("allowedList", NBT.TAG_STRING);
        for(int i = 0; i < tagList.tagCount(); i++)
        {    
            this.allowedPlayers.add(i, tagList.getStringTagAt(i));
        }

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
	public void markDirty() 
	{
		 IBlockState state = this.world.getBlockState(getPos());
	     this.world.notifyBlockUpdate(getPos(), state, state, 3);
	}


}
