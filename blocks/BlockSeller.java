package fr.fifou.economy.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BlockSeller extends BlockContainer
{
	 	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	    public static final String NAME = "block_seller";

		public BlockSeller() 
		{
			super(Material.IRON);
			BlocksRegistery.setBlockName(this, NAME);
	        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
			setUnlocalizedName(NAME);
			setCreativeTab(ModEconomy.tabEconomy);
			setResistance(20000.0F);
			setBlockUnbreakable();
		}

		public TileEntity createNewTileEntity(World worldIn, int meta) 
		{
			return new TileEntityBlockSeller();
		}

		
		@Override
		  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
		    {
		    	worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
		    	TileEntityBlockSeller te = (TileEntityBlockSeller)worldIn.getTileEntity(pos);
		    	te.setOwner(placer.getUniqueID().toString());
		    	te.setFacing(state.toString().substring(28, 33));
		    	te.setOwnerName(placer.getName());   	
		    }
		  
		  @Override
			public boolean onBlockActivated(World worldIn, BlockPos pos,IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
			{
				  TileEntityBlockSeller te = (TileEntityBlockSeller)worldIn.getTileEntity(pos);
					if(te != null)
					{
						if(te.getOwner() != null)
						{
							String checkONBT = te.getOwner();
							String checkOBA = playerIn.getUniqueID().toString();
								
								if(checkONBT.equals(checkOBA))
								{
									if(!te.getCreated())
									{
										playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_SELLER, worldIn, pos.getX(), pos.getY(), pos.getZ());
									}
									else
									{
										playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_SELLER_BUY, worldIn, pos.getX(), pos.getY(), pos.getZ());
									}
								}
								else
								{
									playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_SELLER_BUY, worldIn, pos.getX(), pos.getY(), pos.getZ());
								}
						}
					}
		         return true;
		     }
		
		@Override
		public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
		{
			TileEntityBlockSeller te = (TileEntityBlockSeller)worldIn.getTileEntity(pos);
			if(te != null)
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
		}
		
		@Override
		public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) 
		{
			TileEntityBlockSeller te = (TileEntityBlockSeller)worldIn.getTileEntity(pos);
				ItemStack stack = playerIn.getHeldItemMainhand();
				IBlockState state = worldIn.getBlockState(pos);
				
				if(te != null)
				{
					if(stack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
					{
						String checkONBT = te.getOwner().toString();
						String checkOBA = playerIn.getUniqueID().toString();
						
							if(checkONBT.equals(checkOBA))
							{
								worldIn.destroyBlock(pos, true);
								worldIn.removeTileEntity(pos);

							}
						}
					}
				}	
		//FACING 
		
		@Override
		public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
		    {
		   	 this.setDefaultFacing(worldIn, pos, state);	 
		    }

		    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
		    {
		        if (!worldIn.isRemote)
		        {
		            IBlockState iblockstate = worldIn.getBlockState(pos.north());
		            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
		            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
		            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
		            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

		            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
		            {
		                enumfacing = EnumFacing.SOUTH;
		            }
		            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
		            {
		                enumfacing = EnumFacing.NORTH;
		            }
		            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
		            {
		                enumfacing = EnumFacing.EAST;
		            }
		            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
		            {
		                enumfacing = EnumFacing.WEST;
		            }

		            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		        }
		    }
		    
		    public static void setState(boolean active, World worldIn, BlockPos pos)
		    {
		        IBlockState iblockstate = worldIn.getBlockState(pos);
		        TileEntity tileentity = worldIn.getTileEntity(pos);

		        if (active)
		        {
		            worldIn.setBlockState(pos, BlocksRegistery.BLOCK_SELLER.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
		            worldIn.setBlockState(pos, BlocksRegistery.BLOCK_SELLER.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
		        }
		        else
		        {
		            worldIn.setBlockState(pos, BlocksRegistery.BLOCK_SELLER.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
		            worldIn.setBlockState(pos, BlocksRegistery.BLOCK_SELLER.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
		        }


		        if (tileentity != null)
		        {
		            tileentity.validate();
		            worldIn.setTileEntity(pos, tileentity);
		        }
		    }
		    
		    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
		    {
		        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		    }

		    public IBlockState getStateFromMeta(int meta)
		    {
		        EnumFacing enumfacing = EnumFacing.getFront(meta);

		        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		        {
		            enumfacing = EnumFacing.NORTH;
		        }

		        return this.getDefaultState().withProperty(FACING, enumfacing);
		    }

		    /**
		     * Convert the BlockState into the correct metadata value
		     */
		    public int getMetaFromState(IBlockState state)
		    {
		        return ((EnumFacing)state.getValue(FACING)).getIndex();
		    }

		    /**
		     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
		     * blockstate.
		     */
		    public IBlockState withRotation(IBlockState state, Rotation rot)
		    {
		        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
		    }

		    /**
		     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
		     * blockstate.
		     */
		    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
		    {
		        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
		    }

		    protected BlockStateContainer createBlockState()
		    {
		        return new BlockStateContainer(this, new IProperty[] {FACING});
		    }
		
		//OTHERS

		@Override
		public boolean isFullCube(IBlockState state) 
		{
			return false;
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public boolean isOpaqueCube(IBlockState state) 
		{
			return false;
		}
		 
		@Override
		public EnumBlockRenderType getRenderType(IBlockState state)
		{
			return EnumBlockRenderType.MODEL;
		}
		
	    @SideOnly(Side.CLIENT)
	    public BlockRenderLayer getBlockLayer()
	    {
	        return BlockRenderLayer.CUTOUT_MIPPED;
	    }
	    
	    


}
