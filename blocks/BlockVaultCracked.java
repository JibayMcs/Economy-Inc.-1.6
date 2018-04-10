package fr.fifou.economy.blocks;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.Soundbank;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.packets.PacketOpenCracked;
import fr.fifou.economy.packets.PacketsRegistery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class BlockVaultCracked extends BlockContainer implements ITileEntityProvider {

    public static final String NAME = "block_vault_cracked";

	public BlockVaultCracked() 
	{
		super(Material.IRON);
		BlocksRegistery.setBlockName(this, NAME);
		setUnlocalizedName(NAME);
		setResistance(20000.0F);
		setBlockUnbreakable();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
	{
		TileEntityBlockVaultCracked te = (TileEntityBlockVaultCracked)worldIn.getTileEntity(pos);   	
		int direction = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
		te.setDirection((byte) direction);
    	String pass_0 = String.valueOf(worldIn.rand.nextInt(8));
    	String pass_1 = String.valueOf(worldIn.rand.nextInt(8));
    	String pass_2 = String.valueOf(worldIn.rand.nextInt(8));
    	String pass_3 = String.valueOf(worldIn.rand.nextInt(8));
    	te.setPassword(pass_0 + pass_1 + pass_2 + pass_3);
    	te.setCracked(false);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		TileEntityBlockVaultCracked te = (TileEntityBlockVaultCracked)worldIn.getTileEntity(pos);
		if(playerIn.getHeldItemMainhand().getItem().equals(ItemsRegistery.ITEM_VAULT_CRACKER))
		{
			if(!te.getCracked())
			{
				playerIn.getHeldItemMainhand().damageItem(11, playerIn);
				playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_CRACKING, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		else
		{
			if(te.getCracked())
			{
				PacketsRegistery.network.sendToServer(new PacketOpenCracked(pos.getX(), pos.getY(), pos.getZ(), true));
			}
		}		
			return false;	
	}
	//OTHERS
		@Override
		public boolean isOpaqueCube(IBlockState state) 
		{
			return false;
		}
		
		public int getRenderType() {
		     return -1;
		}
	  
		public TileEntity createNewTileEntity(World worldIn, int meta) 
		{
			return new TileEntityBlockVaultCracked();
		}

		  
		@Override
		public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
		{
			TileEntityBlockVaultCracked te = (TileEntityBlockVaultCracked)worldIn.getTileEntity(pos);
			if(te !=null)
			{
				IItemHandler inventory = te.getHandler();
				if(inventory != null)
				{
					for(int i=0; i < inventory.getSlots(); i++)
					{
						if(inventory.getStackInSlot(i) != ItemStack.EMPTY)
						{
							EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, inventory.getStackInSlot(i));
							
							float multiplier = 0.1f;
							float motionX = worldIn.rand.nextFloat() - 0.5F;
							float motionY = worldIn.rand.nextFloat() - 0.5F;
							float motionZ = worldIn.rand.nextFloat() - 0.5F;
							
							item.motionX = motionX * multiplier;
							item.motionY = motionY * multiplier;
							item.motionZ = motionZ * multiplier;
							
							worldIn.spawnEntity(item);
						}
					}
				}
			}			
			super.breakBlock(worldIn, pos, state);
			worldIn.removeTileEntity(pos);
		}
}
