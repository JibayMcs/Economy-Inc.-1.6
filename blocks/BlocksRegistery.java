package fr.fifou.economy.blocks;

import java.util.HashMap;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.items.ItemsRegistery;

import it.unimi.dsi.fastutil.Hash;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksRegistery 
{
	public static Block BLOCK_VAULT = new BlockVault();
	public static Block BLOCK_SELLER = new BlockSeller();
	public static Block BLOCK_CHANGER = new BlockChanger();
	public static Block BLOCK_VAULT_2BY2 = new BlockVault2by2();
	public static Block BLOCK_ATM = new BlockAtm();
	public static Block BLOCK_VAULT_CRACKED = new BlockVaultCracked();
	public static Block BLOCK_BILLS = new BlockBills();
	public static Block BLOCK_VAULT_MARKER = new BlockVaultMarker();
	public static Block BLOCK_VAULT_MS = new BlockVaultMS();

	public static void setBlockName(Block block, String name)
    {
		 block.setRegistryName(ModEconomy.MODID, name).setUnlocalizedName(ModEconomy.MODID + "." + name);
	}
	
	public static void registerModel(Block block, int metadata)
	 {
	   	 if (metadata < 0) metadata = 0;
	   	 String resourceName = block.getRegistryName().toString();
	   	 if (metadata > 0) resourceName += "_m" + String.valueOf(metadata);
	   	 ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	 }
	
}
