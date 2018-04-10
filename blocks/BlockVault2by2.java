package fr.fifou.economy.blocks;

import java.util.Random;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class BlockVault2by2 extends BlockContainer implements ITileEntityProvider {

    public static final String NAME = "block_vault2by2";

    
	public BlockVault2by2() 
	{
        super(Material.IRON);
		BlocksRegistery.setBlockName(this, NAME);
		setUnlocalizedName(NAME);
		setResistance(20000.0F);
		setBlockUnbreakable();
   }


	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksRegistery.BLOCK_VAULT);
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) 
	{
		return 4;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) 
	{
		TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);
		if(te != null)
		{
			switch (te.getDirection()) 
			{
			case 0:
				return new AxisAlignedBB(-1.0D, 0.0D, 1.0D, 1.0D, 2.0D, 0.0D);
			case 1:
				return new AxisAlignedBB(0.0D, 0.0D, 1.0D, 1.0D, 2.0D, -1.0D);
			case 2:
				return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 2.0D, 2.0D, 1.0D);
			case 3:
				return new AxisAlignedBB(0.0D, 0.0D, 2.0D, 1.0D, 2.0D, 0.0D);
			default:
				break;
			}
		}
		return null;
	}
	


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityBlockVault2by2();
	}
	
	
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) 
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
	
	public boolean isOpaqueCube() 
	{
	     return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
	{
		TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);
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
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
	{
    	TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);
    	te.setString(placer.getUniqueID().toString());
    	te.ownerS = placer.getUniqueID().toString();
        int direction = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        te.setDirection((byte) direction);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);
		if(te != null)
		{
			if(te.getOwnerS() != null)
			{
				String checkONBT = te.getOwnerS();
				String checkOBA = playerIn.getUniqueID().toString();
				
				if(checkONBT.equals(checkOBA))
				{
						playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_2BY2, worldIn, pos.getX(), pos.getY(), pos.getZ());
						/*if (!worldIn.isRemote && !te.getAnimationHandler().isAnimationActive(ModEconomy.MODID, "anim_vault2by2", te))
						{
					        te.getAnimationHandler().networkStartAnimation(ModEconomy.MODID, "anim_vault2by2", te);
					    }*/
				}
				else
				{
					for(int i = 0; i < te.getOthers().size(); i++)
					{
						String checkList = te.getOthers().get(i).toString();
						if(playerIn.getName().equals(checkList))
						{
							playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_2BY2, worldIn, pos.getX(), pos.getY(), pos.getZ());
							/*if (worldIn.isRemote && !te.getAnimationHandler().isAnimationActive(ModEconomy.MODID, "anim_vault2by2", te))
							{
						        te.getAnimationHandler().startAnimation(ModEconomy.MODID, "anim_vault2by2", te);
						    }*/
						}
					}
				}

			}
		}
         return true;
     }

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) 
	{
			TileEntityBlockVault2by2 te = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);
			ItemStack stack = playerIn.getHeldItemMainhand();
			IBlockState state = worldIn.getBlockState(pos);
			
			if(te != null)
			{
				if(stack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
				{
					String checkONBT = te.getOwnerS();
					String checkOBA = playerIn.getUniqueID().toString();
					
					if(checkONBT.equals(checkOBA))
					{
						worldIn.destroyBlock(pos, true);
						worldIn.removeTileEntity(pos);
					}
				}
			}
	}	
	
	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) 
	{
		 super.eventReceived(state, worldIn, pos, id, param);
	     TileEntity tileentity = worldIn.getTileEntity(pos);
	     return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	     
	}
	
}
