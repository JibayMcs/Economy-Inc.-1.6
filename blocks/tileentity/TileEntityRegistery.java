package fr.fifou.economy.blocks.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistery {

	public static void register() 
	{
		   GameRegistry.registerTileEntity(TileEntityBlockVault.class, "block_seller");
	       GameRegistry.registerTileEntity(TileEntityBlockSeller.class, "block_vault");
	       GameRegistry.registerTileEntity(TileEntityBlockChanger.class, "block_changer");
	       GameRegistry.registerTileEntity(TileEntityBlockVault2by2.class, "block_vault2by2");
	       GameRegistry.registerTileEntity(TileEntityBlockVaultCracked.class, "block_vault_cracked");
	       GameRegistry.registerTileEntity(TileEntityBlockBills.class, "block_bills");
	       GameRegistry.registerTileEntity(TileEntityBlockVaultMarker.class, "block_vault_marker");

	}
}
