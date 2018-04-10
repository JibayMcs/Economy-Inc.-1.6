package fr.fifou.economy.containers;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.packets.PacketCardChangeSeller;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerChanger extends Container {

	public int X;
	public int Y;
	public int Z;
	public TileEntityBlockChanger te;
	
	public ContainerChanger(InventoryPlayer inventoryPlayer, TileEntityBlockChanger tile)
	{
			IItemHandler inventory = tile.getHandler();
			this.addSlotToContainer(new SlotItemHandler(inventory, 0, 56, 16));
			this.addSlotToContainer(new SlotItemHandler(inventory, 1, 56, 52));
			this.addSlotToContainer(new SlotItemHandler(inventory, 2, 116, 34));
			this.bindPlayerInventory(inventoryPlayer);
			this.X = tile.getPos().getX();
			this.Y = tile.getPos().getY();
			this.Z = tile.getPos().getZ();
			this.te = tile;
	}
	
	private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		int i;
		for(i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 48 + i * 18 + 35));
			}
		}

		for(i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 106 + 35));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return true;
	}

	
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		  ItemStack stack = ItemStack.EMPTY;
	        Slot slot = inventorySlots.get(index);

		        if (slot != null && slot.getHasStack()) {
		            ItemStack stackInSlot = slot.getStack();
		            stack = stackInSlot.copy();
	
		            int containerSlots = inventorySlots.size() - playerIn.inventory.mainInventory.size();
	
		            if (index < containerSlots) {
		                if (!this.mergeItemStack(stackInSlot, containerSlots, inventorySlots.size(), true)) {
		                    return ItemStack.EMPTY;
		                }
		            } else if (!this.mergeItemStack(stackInSlot, 0, containerSlots, false)) {
		                return ItemStack.EMPTY;
		            }
	
		            if (stackInSlot.getCount() == 0) {
		                slot.putStack(ItemStack.EMPTY);
		            } else {
		                slot.onSlotChanged();
		            }
	
		            slot.onTake(playerIn, stackInSlot);
	
		        }
		        return stack;
		        
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);
		World worldIn = playerIn.world;
		if(!worldIn.isRemote)
		{
			TileEntityBlockChanger te = (TileEntityBlockChanger)worldIn.getTileEntity(new BlockPos(X,Y,Z));
			if(te.getNumbUse() > 0)
			{
				te.setNumbUse(0);
				te.setEntityPlayer((EntityPlayer)null);
				te.markDirty();
			}
			
			
				ItemStack itemstack = te.getStackInSlot(0).splitStack(1);

	            if (!itemstack.isEmpty())
	            {
	            	worldIn.spawnEntity(new EntityItem(worldIn, X, Y, Z, itemstack));
	            }

	            itemstack = te.getStackInSlot(1).splitStack(1);

	            if (!itemstack.isEmpty())
	            {
	            	worldIn.spawnEntity(new EntityItem(worldIn, X, Y, Z, itemstack));
	            }
	            
	            itemstack = te.getStackInSlot(2).splitStack(1);

	            if (!itemstack.isEmpty())
	            {
	            	worldIn.spawnEntity(new EntityItem(worldIn, X, Y, Z, itemstack));
	            }
	    }
	}		
}

