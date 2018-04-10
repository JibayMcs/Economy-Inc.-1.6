package fr.fifou.economy.gui;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.containers.ContainerChanger;
import fr.fifou.economy.containers.ContainerInformaterTrade;
import fr.fifou.economy.containers.ContainerSeller;
import fr.fifou.economy.containers.ContainerVault;
import fr.fifou.economy.containers.ContainerVault2by2;
import fr.fifou.economy.containers.ContainerVaultCracked;
import fr.fifou.economy.entity.EntityInformater;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class GuiHandler implements IGuiHandler
{
	public static final int ITEM_CARD_GUI = 0;
	public static final int ITEMCARD_ATM_GUI = 1;
	public static final int BLOCK_VAULT_NEW = 2;
	public static final int BLOCK_SELLER = 3;
	public static final int BLOCK_SELLER_BUY = 4;
	public static final int BLOCK_CHANGER = 5;
	public static final int BLOCK_VAULT_2BY2 = 6;
	public static final int BLOCK_VAULT_SETTINGS = 7;
	public static final int BLOCK_VAULT2BY2_SETTINGS = 8;
	public static final int BLOCK_VAULT_CRACKED = 9;
	public static final int BLOCK_VAULT_CRACKING = 10;
	public static final int ENTITY_INFOR_TRADE = 11;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		BlockPos pos = new BlockPos(x,y,z);
		TileEntity te = world.getTileEntity(pos);
		if(ID == BLOCK_VAULT_NEW)
		{
			 return new ContainerVault(player.inventory, (TileEntityBlockVault)te);
		}
		else if(ID == BLOCK_SELLER)
		{
			return new ContainerSeller(player.inventory, (TileEntityBlockSeller)te);
		}
		else if(ID == BLOCK_CHANGER)
		{
			return new ContainerChanger(player.inventory, (TileEntityBlockChanger)te);
		}
		else if(ID == BLOCK_VAULT_2BY2)
		{
			 return new ContainerVault2by2(player.inventory, (TileEntityBlockVault2by2)te);
		}
		else if(ID == BLOCK_VAULT_CRACKED)
		{
			 return new ContainerVaultCracked(player.inventory, (TileEntityBlockVaultCracked)te);
		}
		else if(ID == ENTITY_INFOR_TRADE)
		{
			return new ContainerInformaterTrade(player.inventory);
		}
		return null;
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		BlockPos pos = new BlockPos(x,y,z);
		TileEntity te = world.getTileEntity(pos);
		if(ID == ITEM_CARD_GUI)
		{
			return new GuiItem();
		}
		else if(ID == BLOCK_VAULT_NEW)
		{
			return new GuiVaultNew(player.inventory, (TileEntityBlockVault)te);
		}
		else if(ID == BLOCK_SELLER)
		{
			return new GuiSeller(player.inventory, (TileEntityBlockSeller)te);
		}
		else if(ID == BLOCK_SELLER_BUY)
		{
			return new GuiSellerBuy((TileEntityBlockSeller)te);
		}
		else if(ID == BLOCK_CHANGER)
		{
			return new GuiChanger(player.inventory, (TileEntityBlockChanger)te);
		}
		else if(ID == BLOCK_VAULT_2BY2)
		{
			return new GuiVault2by2(player.inventory, (TileEntityBlockVault2by2)te);
		}
		else if(ID == BLOCK_VAULT_SETTINGS)
		{
			return new GuiVaultSettings((TileEntityBlockVault)te);
		}
		else if(ID == BLOCK_VAULT2BY2_SETTINGS)
		{
			return new GuiVault2by2Settings((TileEntityBlockVault2by2)te);
		}
		else if(ID == ITEMCARD_ATM_GUI)
		{
			return new GuiItemATM();
		}
		else if(ID == BLOCK_VAULT_CRACKED)
		{
			return new GuiVaultCracked(player.inventory, (TileEntityBlockVaultCracked)te);
		}
		else if(ID == BLOCK_VAULT_CRACKING)
		{
			return new GuiCracking((TileEntityBlockVaultCracked)te);
		}
		else if(ID == ENTITY_INFOR_TRADE)
		{
			return new GuiInformaterTrade(player.inventory, x);
		}
		return null;
	}

 



}
 