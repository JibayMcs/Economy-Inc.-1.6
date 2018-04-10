package fr.fifou.economy.blocks;

import java.util.Random;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockBills;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockBills extends Block implements ITileEntityProvider {
	
	public static final String NAME = "block_bills";
	public EntityItem item;

		public BlockBills() 
		{
			super(Material.CLOTH);
			BlocksRegistery.setBlockName(this, NAME);
			setUnlocalizedName(NAME);
			setCreativeTab(ModEconomy.tabEconomy);
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
			return new TileEntityBlockBills();
		}
		
		@Override
		public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
		{
		     TileEntityBlockBills te = (TileEntityBlockBills)worldIn.getTileEntity(pos);
		     for(int i = 0; i < te.getNumbBills(); i++)
				{ 	
		    	        checkBillRefForDrop(te, worldIn, pos);
						float multiplier = 0.1f;
						float motionX = worldIn.rand.nextFloat() - 0.5F;
						float motionY = worldIn.rand.nextFloat() - 0.5F;
						float motionZ = worldIn.rand.nextFloat() - 0.5F;
						
						item.motionX = motionX * multiplier;
						item.motionY = motionY * multiplier;
						item.motionZ = motionZ * multiplier;
						
						worldIn.spawnEntity(item);
				}
		     	worldIn.removeTileEntity(pos);
		}
		
		@Override
		public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) 
		{
			 super.eventReceived(state, worldIn, pos, id, param);
		     TileEntity tileentity = worldIn.getTileEntity(pos);
		     return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
		     
		}
		
		@Override
		public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
		{
	    	TileEntityBlockBills te = (TileEntityBlockBills)worldIn.getTileEntity(pos);
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
		    	TileEntityBlockBills te = (TileEntityBlockBills)worldIn.getTileEntity(pos);
		    	if(te.getNumbBills() != 64)
		    	{
	    			String unNa = playerIn.getHeldItem(hand).getUnlocalizedName();
		    		if(te.getNumbBills() == 0)
		    		{
			    		if(unNa.equals("item.item_oneb") || unNa.equals("item.item_fiveb") || unNa.equals("item.item_tenb") || unNa.equals("item.item_twentyb") || unNa.equals("item.item_fiftybe") || unNa.equals("item.item_hundreedb") || unNa.equals("item.item_twohundreedb") || unNa.equals("item.item_fivehundreedb"))
			    		{
			    			checkBillRef(te, worldIn, playerIn, hand);
					    	te.addBill();
					    	playerIn.getHeldItem(hand).setCount(playerIn.getHeldItemMainhand().getCount() - 1);
					    	te.markDirty();
			    		}
		    		}
		    		else
		    		{
		    			if(te.getBill().equals(unNa))
		    			{
		    				te.addBill();
					    	playerIn.getHeldItem(hand).setCount(playerIn.getHeldItemMainhand().getCount() - 1);
					    	te.markDirty();
		    			}
		    		}

		    	}
			}
			return true;
		}
		
		public void checkBillRef(TileEntityBlockBills te, World worldIn, EntityPlayer playerIn, EnumHand hand)
		{
			if(!worldIn.isRemote)
			{
				switch (playerIn.getHeldItem(hand).getUnlocalizedName()) {
					case "item.item_oneb":
						te.setBillRef("item.item_oneb");
						te.markDirty();
						break;
					case "item.item_fiveb":
						te.setBillRef("item.item_fiveb");
						te.markDirty();
						break;
					case "item.item_tenb":
						te.setBillRef("item.item_tenb");
						te.markDirty();
						break;
					case "item.item_twentyb":
						te.setBillRef("item.item_twentyb");
						te.markDirty();
						break;
					case "item.item_fiftybe":
						te.setBillRef("item.item_fiftybe");
						te.markDirty();
						break;
					case "item.item_hundreedb":
						te.setBillRef("item.item_hundreedb");
						te.markDirty();
						break;
					case "item.item_twohundreedb":
						te.setBillRef("item.item_twohundreedb");
						te.markDirty();
						break;
					case "item.item_fivehundreedb":
						te.setBillRef("item.item_fivehundreedb");
						te.markDirty();
						break;
					default:
						te.setBillRef("item.item_zerob");
						te.markDirty();
						break;
				}
			}
		}
		
		public void checkBillRefForDrop(TileEntityBlockBills te, World worldIn, BlockPos pos)
		{
			if(!worldIn.isRemote)
			{
				switch (te.getBill()) {
					case "item.item_oneb":
						item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_ONEB));
						break;
					case "item.item_fiveb":
						item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_FIVEB));
						break;
					case "item.item_tenb":
						item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_TENB));
						break;
					case "item.item_twentyb":
						item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_TWENTYB));
						break;
					case "item.item_fiftybe":
						item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_FIFTYB));
						break;
					case "item.item_hundreedb":
						item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_HUNDREEDB));
						break;
					case "item.item_twohundreedb":
						item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB));
						break;
					case "item.item_fivehundreedb":
						item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY()+0.5, pos.getZ() +0.5, new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB));
						break;
					default:
						break;
				}
			}
		}
	
		
	
	
}
