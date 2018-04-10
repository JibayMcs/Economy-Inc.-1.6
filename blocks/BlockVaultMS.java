package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockVaultMS extends Block {

	public static final String NAME = "block_vault_ms";

	public BlockVaultMS() 
	{
		super(Material.IRON);
		BlocksRegistery.setBlockName(this, NAME);
		setUnlocalizedName(NAME);
		setCreativeTab(ModEconomy.tabEconomy);

	}
}
