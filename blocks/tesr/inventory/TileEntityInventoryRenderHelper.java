package fr.fifou.economy.blocks.tesr.inventory;

import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockBills;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class TileEntityInventoryRenderHelper extends TileEntityItemStackRenderer 
{
    private TileEntityBlockVault te = new TileEntityBlockVault();
    private TileEntityBlockVault2by2 te2by2 = new TileEntityBlockVault2by2();
    private TileEntityBlockVaultCracked tecracked = new TileEntityBlockVaultCracked();
    private TileEntityBlockBills teBills = new TileEntityBlockBills();

    @Override
    public void renderByItem(ItemStack itemStack) 
    {
	    Block block = Block.getBlockFromItem(itemStack.getItem());
	
	    if (block == BlocksRegistery.BLOCK_VAULT) 
	    {
	         TileEntityRendererDispatcher.instance.render(this.te, 0.0D, 0.0D, 0.0D, 0.0F);
	    } 
	    else if(block == BlocksRegistery.BLOCK_VAULT_2BY2)
	    {
	         TileEntityRendererDispatcher.instance.render(this.te2by2, 0.0D, 0.0D, 0.0D, 0.0F);
	    }
	    else if(block == BlocksRegistery.BLOCK_VAULT_CRACKED)
	    {
	         TileEntityRendererDispatcher.instance.render(this.tecracked, 0.0D, 0.0D, 0.0D, 0.0F);
	    }
	    else if(block == BlocksRegistery.BLOCK_BILLS)
	    {
	    	TileEntityRendererDispatcher.instance.render(this.teBills,  0.0D, 0.0D, 0.0D, 0.0F);
	    }
	    else 
	    {
	        super.renderByItem(itemStack);
	    }
    }
}