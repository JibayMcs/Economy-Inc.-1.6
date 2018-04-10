package fr.fifou.economy.containers;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.BlockVault;
import fr.fifou.economy.blocks.models.ModelVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerVault extends Container
{
	public int X;
	public int Y;
	public int Z;
	public ContainerVault(InventoryPlayer inventoryPlayer, TileEntityBlockVault tile)
	{
			this.X = tile.getPos().getX();
			this.Y = tile.getPos().getY();
			this.Z = tile.getPos().getZ();
			IItemHandler inventory = tile.getHandler();
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					this.addSlotToContainer(new SlotItemHandler(inventory, j + i * 9, 8 + j * 18, 17 + i * 18));
				}
			}
			this.bindPlayerInventory(inventoryPlayer);
			
	}
	
	private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		int i;
		for(i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 48 + i * 18 + 37));
			}
		}

		for(i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 106 + 37));
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
	
	/*@Override
	public void onContainerClosed(EntityPlayer playerIn) 
	{
		super.onContainerClosed(playerIn);
		World worldIn = playerIn.world;
		if(!worldIn.isRemote)
		{
			TileEntityBlockVault te = (TileEntityBlockVault)worldIn.getTileEntity(new BlockPos(X,Y,Z));
			te.removeNumbUse();
			te.markDirty();
		}
	}*/
	
}
