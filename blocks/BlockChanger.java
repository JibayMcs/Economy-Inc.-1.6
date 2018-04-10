package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class BlockChanger extends BlockContainer implements ITileEntityProvider {

    public static final String NAME = "block_changer";
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

		public BlockChanger() 
		{
			super(Material.IRON);
			BlocksRegistery.setBlockName(this, NAME);
			setUnlocalizedName(NAME);
			setCreativeTab(ModEconomy.tabEconomy);
			setBlockUnbreakable();
			setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		}
		
		public TileEntity createNewTileEntity(World worldIn, int meta) 
		{
			return new TileEntityBlockChanger();
		}
		

		@Override
		public EnumBlockRenderType getRenderType(IBlockState state) {
			return EnumBlockRenderType.MODEL;
		}
		
		@Override
		public boolean onBlockActivated(World worldIn, BlockPos pos,IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
		{
			if(!worldIn.isRemote)
			{
				TileEntityBlockChanger te = (TileEntityBlockChanger)worldIn.getTileEntity(pos);
				if(te != null)
				{
					if(te.getNumbUse() < 1)
					{
						playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_CHANGER, worldIn, pos.getX(), pos.getY(), pos.getZ());
						te.setNumbUse(1);
						te.setEntityPlayer(playerIn);
						int X = pos.getX();
						int Y = pos.getY();
						int Z = pos.getZ();
						te.markDirty();
	
					}
					else
					{
						playerIn.sendMessage(new TextComponentString(I18n.format("title.alreadyUsed")));
					}
				}
			}
	         return true;
	     }
		
		@Override
		public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) 
		{
			TileEntityBlockChanger te = (TileEntityBlockChanger)worldIn.getTileEntity(pos);
			ItemStack stack = playerIn.getHeldItemMainhand();
			IBlockState state = worldIn.getBlockState(pos);
				
				if(te != null)
				{
					if(stack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
					{
						if(te.getNumbUse() < 1)
						{
							worldIn.destroyBlock(pos, true);
							worldIn.removeTileEntity(pos);
						}
						else if(FMLCommonHandler.instance().getSide().CLIENT.isClient())
						{
							worldIn.destroyBlock(pos, true);
							worldIn.removeTileEntity(pos);
						}
					}
				}
		}
		   
		   @Override
			public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
			{
				TileEntityBlockChanger te = (TileEntityBlockChanger)worldIn.getTileEntity(pos);
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
		    
		    @Override
		    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
		    {
		        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		    }
		    
		    @Override
		    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
		    {
		        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		    }
		    
		    @Override
		    public IBlockState withRotation(IBlockState state, Rotation rot)
		    {
		        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
		    }
		    
		    @Override
		    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
		    {
		        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
		    }

		    @Override
		    protected BlockStateContainer createBlockState()
		    {
		        return new BlockStateContainer(this, new IProperty[] {FACING});
		    }

		    /**
		     * Convert the given metadata into a BlockState for this Block
		     */
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
		   
}
