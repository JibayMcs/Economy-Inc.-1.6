package fr.fifou.economy.blocks;

import java.util.Iterator;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.authlib.properties.Property;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.packets.PacketCardChange;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLever;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockVault extends BlockContainer implements ITileEntityProvider
{
    public static final String NAME = "block_vault";

	public BlockVault() 
	{
		super(Material.IRON);
		BlocksRegistery.setBlockName(this, NAME);
		setUnlocalizedName(NAME);
		setCreativeTab(ModEconomy.tabEconomy);
		setResistance(20000.0F);
		setBlockUnbreakable();

	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	    {

	    	TileEntityBlockVault te = (TileEntityBlockVault)worldIn.getTileEntity(pos);
	    	te.setOwner(placer.getUniqueID().toString());
	    	te.ownerS = placer.getUniqueID().toString();	   	
	        int direction = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
	        te.setDirection((byte) direction);
	        byte direction_te = te.getDirection();
			if(direction_te == 0)
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();
				if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos + i, yPos + j, zPos));
						}
					}
					worldIn.setBlockState(new BlockPos(xPos + 1, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos + 1, yPos, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos - i, yPos + j, zPos));
						}
					}
					
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos - i, yPos - j, zPos));
						}
					}
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos + i, yPos - j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos + 1, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos + 1, yPos - 1, zPos));
					te2by2.setDirection((byte)0);
					te2by2.setString(te.getOwnerS());
				}
				
			}
			else if(direction_te ==  2)
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();
				if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos - i, yPos + j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos - 1, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos - 1, yPos, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos + i, yPos + j, zPos));
						}
					}				
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				else if(worldIn.getBlockState(new BlockPos(xPos + 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos + 1, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos + i, yPos - j, zPos));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				else if(worldIn.getBlockState(new BlockPos(xPos - 1, yPos, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos - 1, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos - i, yPos - j, zPos));
						}
					}			
					worldIn.setBlockState(new BlockPos(xPos - 1, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos - 1, yPos - 1, zPos));
					te2by2.setDirection((byte)2);
					te2by2.setString(te.getOwnerS());

				}
				
			}
			else if(direction_te == 1)
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();

				if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos, yPos + i, zPos + j));
						}
					}			
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos + 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos + 1));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos , yPos + 1, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos, yPos + i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos , yPos, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos, yPos - i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos, yPos - i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos + 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos + 1));
					te2by2.setDirection((byte)1);
					te2by2.setString(te.getOwnerS());
				}
			}
			else if(direction_te == 3)
			{
				int xPos = te.getPos().getX();
				int yPos = te.getPos().getY();
				int zPos = te.getPos().getZ();

				if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos, yPos + i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos - 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos - 1));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos , yPos + 1, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos + 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN BAS A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos, yPos + i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos, zPos));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos , yPos, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A DROITE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos, yPos - i, zPos + j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
				else if(worldIn.getBlockState(new BlockPos(xPos, yPos, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(xPos, yPos - 1, zPos)).getBlock() == BlocksRegistery.BLOCK_VAULT)
				{
					//EN HAUT A GAUCHE
					for(int i = 0; i <= 1; i++)
					{
						for(int j = 0; j <= 1; j++)
						{
							worldIn.setBlockToAir(new BlockPos(xPos, yPos - i, zPos - j));
						}
					}	
					worldIn.setBlockState(new BlockPos(xPos, yPos - 1, zPos - 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
					TileEntityBlockVault2by2 te2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(xPos, yPos - 1, zPos - 1));
					te2by2.setDirection((byte)3);
					te2by2.setString(te.getOwnerS());
				}
			}
		}
	  
	  
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(!worldIn.isRemote)
		{
			TileEntityBlockVault te = (TileEntityBlockVault)worldIn.getTileEntity(pos);
			if(te != null)
			{
				
				if(te.getOwnerS() != null)
				{
					String checkONBT = te.getOwnerS();
					String checkOBA = playerIn.getUniqueID().toString();
					
					if(checkONBT.equals(checkOBA))
					{
						playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_NEW, worldIn, pos.getX(), pos.getY(), pos.getZ());
						if (!worldIn.isRemote && !te.getAnimationHandler().isAnimationActive(ModEconomy.MODID, "anim_vault2by2", te) && !te.getIsOpen())
						{
					        te.getAnimationHandler().networkStartAnimation(ModEconomy.MODID, "anim_vault2by2", te);
					    }
						te.setIsOpen(true);
						te.markDirty();
						
					}
					else
					{
						for(int i = 0; i < te.getOthers().size(); i++)
						{
							String checkList = te.getOthers().get(i).toString();
							if(playerIn.getName().equals(checkList))
							{
								playerIn.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_NEW, worldIn, pos.getX(), pos.getY(), pos.getZ());
								te.markDirty();
							}
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
			TileEntityBlockVault te = (TileEntityBlockVault)worldIn.getTileEntity(pos);
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
	
	//OTHERS
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) 
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
  
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityBlockVault();
	}

	  
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
	{
		TileEntityBlockVault te = (TileEntityBlockVault)worldIn.getTileEntity(pos);
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
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) 
	{
		 super.eventReceived(state, worldIn, pos, id, param);
	     TileEntity tileentity = worldIn.getTileEntity(pos);
	     return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	     
	}
	
	

}
