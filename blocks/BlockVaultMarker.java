package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultMarker;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockVaultMarker extends Block implements ITileEntityProvider {

	 public static final String NAME = "block_vault_marker";

		public BlockVaultMarker() 
		{
			super(Material.IRON);
			BlocksRegistery.setBlockName(this, NAME);
			setUnlocalizedName(NAME);
			setCreativeTab(ModEconomy.tabEconomy);
		}
		
		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta) 
		{
			return new TileEntityBlockVaultMarker();
		}
		
		@Override
		public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
		{
			TileEntityBlockVaultMarker te = (TileEntityBlockVaultMarker)worldIn.getTileEntity(pos);	   	
	       
			if(!worldIn.isRemote)
			{
				int direction = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
				te.setDirection((byte) direction);
				te.markDirty();
			}
		}
		
		@Override
		public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
		{
			if(!worldIn.isRemote)
			{
				if(playerIn.getHeldItem(hand).getItem() == ItemsRegistery.ITEM_REMOVER)
				{
					int counter = 0;
					for(int i = 0; i < 7; i++)
					{
						for(int j = 0; j < 12; j++)
						{
							for(int k = 0; k < 6; k++)
							{
								if(worldIn.getBlockState(new BlockPos(pos.getX() + i, pos.getY() + k, pos.getZ() + j)).getBlock() == BlocksRegistery.BLOCK_VAULT_MS)
								{
									++counter;
									System.out.println(counter);
								}
								else
								{
									System.out.println("not ok" + String.valueOf(i) + String.valueOf(j) + String.valueOf(k));
								}
							}
						}
					}
				}

			}
			return true;
		}
			
		
		
		@Override
		public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) 
		{
			 super.eventReceived(state, worldIn, pos, id, param);
		     TileEntity tileentity = worldIn.getTileEntity(pos);
		     return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
		     
		}
}
