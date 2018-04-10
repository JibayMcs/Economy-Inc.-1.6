package fr.fifou.economy.world.gen.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import fr.fifou.economy.blocks.BlockAtm;
import fr.fifou.economy.blocks.BlockVault2by2;
import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.entity.EntityInformater;
import fr.fifou.economy.world.storage.loot.CustomLootTableList;
import io.netty.util.concurrent.BlockingOperationException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDoubleStoneSlab;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockQuartz.EnumType;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraft.world.gen.structure.template.Template.EntityInfo;
import net.minecraft.world.storage.loot.LootTableList;

public class VillageComponentShop extends StructureVillagePieces.Village 
{
    public final ITextComponent[] signText = new ITextComponent[] {new TextComponentString(""), new TextComponentString("BANK"), new TextComponentString(""), new TextComponentString("")};
	private String safeCode = "";
    private int villagersSpawned;


	public VillageComponentShop()
	{
		
	}
	
	public VillageComponentShop(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox sbb, EnumFacing facing)
	{ 
		this.boundingBox = sbb;
		setCoordBaseMode(facing);
	}

	public static VillageComponentShop createPiece(StructureVillagePieces.Start start, List<StructureComponent> comps, Random rand, int p1, int p2, int p3, EnumFacing facing, int p5)
	{
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 9, 7, 12, facing);
		return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(comps, structureboundingbox) == null ? new VillageComponentShop(start, p5, rand, structureboundingbox, facing) : null;
		 
	}
		 
   
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
    {
        if (this.averageGroundLvl < 0)
        {
            this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

            if (this.averageGroundLvl < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
        }

        IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
        IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.DARK_OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.DARK_OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
        IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.DARK_OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
        IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.DARK_OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
        IBlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK));
        IBlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK));
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate5, iblockstate5, false);
        this.setBlockState(worldIn, iblockstate5, 0, 4, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 0, 4, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 8, 4, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 8, 4, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 8, 4, 4, structureBoundingBoxIn);
        IBlockState iblockstate7 = iblockstate1;
        IBlockState iblockstate8 = iblockstate2;
        IBlockState iblockstate9 = iblockstate4;
        IBlockState iblockstate10 = iblockstate3;

        for (int i = -1; i <= 2; ++i)
        {
            for (int j = 0; j <= 8; ++j)
            {
                this.setBlockState(worldIn, iblockstate7, j, 4 + i, i, structureBoundingBoxIn);

                if ((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j <= 4 || j >= 6))
                {
                    this.setBlockState(worldIn, iblockstate8, j, 4 + i, 5 - i, structureBoundingBoxIn);
                }
            }
        }

        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, iblockstate5, iblockstate5, false);

        for (int k = 4; k >= 1; --k)
        {
            this.setBlockState(worldIn, iblockstate5, k, 2 + k, 7 - k, structureBoundingBoxIn);

            for (int k1 = 8 - k; k1 <= 10; ++k1)
            {
                this.setBlockState(worldIn, iblockstate10, k, 2 + k, k1, structureBoundingBoxIn);
            }
        }

        
        this.setBlockState(worldIn, iblockstate5, 6, 6, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 7, 5, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate4, 6, 6, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 7, 2, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 7, 3, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 3, 1, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 3, 5, structureBoundingBoxIn);

        this.setBlockState(worldIn, iblockstate5, 6, 4, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 5, 4, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 5, 5, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 5, 5, 4, structureBoundingBoxIn);      
        this.setBlockState(worldIn, iblockstate5, 4, 4, 5, structureBoundingBoxIn);


        this.setBlockState(worldIn, Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 6, 1, 6, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 4, 1, 6, structureBoundingBoxIn);

        this.setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState().withProperty(BlockDoubleStoneSlab.SEAMLESS, true), 7, 1, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState().withProperty(BlockDoubleStoneSlab.SEAMLESS, true), 7, 1, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, BlocksRegistery.BLOCK_ATM.getDefaultState().withProperty(BlockAtm.FACING, EnumFacing.WEST), 7, 2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, BlocksRegistery.BLOCK_ATM.getDefaultState().withProperty(BlockAtm.FACING, EnumFacing.WEST), 7, 2, 3, structureBoundingBoxIn);
       
        
        this.setBlockState(worldIn, iblockstate5, 2, 3,-1, structureBoundingBoxIn);
        this.generateVault(worldIn, structureBoundingBoxIn, 5,1,9);
        this.generateSign(worldIn, structureBoundingBoxIn, 2, 3,-2);
        this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 4, 1, 1, CustomLootTableList.CHESTS_SHOP_VILLAGE);
        this.spawnInformater(worldIn, structureBoundingBoxIn, 5, 1, 7, 1);

        for (int l = 6; l <= 8; ++l)
        {
            for (int l1 = 5; l1 <= 10; ++l1)
            {
                this.setBlockState(worldIn, iblockstate9, l, 12 - l, l1, structureBoundingBoxIn);
            }
        }

        this.setBlockState(worldIn, iblockstate6, 0, 2, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 0, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 4, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 6, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 8, 2, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 8, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 8, 2, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 8, 2, 6, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 8, 2, 9, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 2, 2, 6, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 2, 2, 9, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 4, 4, 10, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 6, 4, 10, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 5, 5, 10, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
        this.placeTorch(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);
        this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

        if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
        {
            this.setBlockState(worldIn, iblockstate7, 2, 0, -1, structureBoundingBoxIn);

            if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
            {
                this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
            }
        }

        for (int i1 = 0; i1 < 5; ++i1)
        {
            for (int i2 = 0; i2 < 9; ++i2)
            {
                this.clearCurrentPositionBlocksUpwards(worldIn, i2, 7, i1, structureBoundingBoxIn);
                this.replaceAirAndLiquidDownwards(worldIn, iblockstate, i2, -1, i1, structureBoundingBoxIn);
            }
        }

        for (int j1 = 5; j1 < 11; ++j1)
        {
            for (int j2 = 2; j2 < 9; ++j2)
            {
                this.clearCurrentPositionBlocksUpwards(worldIn, j2, 7, j1, structureBoundingBoxIn);
                this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j2, -1, j1, structureBoundingBoxIn);
            }
        }

        return true;
    }
	/**
	 * Adds the informater in the villager at the bank
	 */
	 protected void spawnInformater(World worldIn, StructureBoundingBox structurebb, int x, int y, int z, int count)
     {
		 if (this.villagersSpawned < count)
         {
             for (int i = this.villagersSpawned; i < count; ++i)
             {
                 int j = this.getXWithOffset(x + i, z);
                 int k = this.getYWithOffset(y);
                 int l = this.getZWithOffset(x + i, z);

                 if (!structurebb.isVecInside(new BlockPos(j, k, l)))
                 {
                     break;
                 }

                 ++this.villagersSpawned;

		          EntityInformater entityInformater = new EntityInformater(worldIn);
		          entityInformater.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
		          entityInformater.setSafeCode(this.safeCode);
		          worldIn.spawnEntity(entityInformater);
             }
         }
     }
	
	 /**
     * Adds sign to the structure and sets its contents
     */
    protected boolean generateSign(World worldIn, StructureBoundingBox structurebb, int x, int y, int z)
    {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
        return this.generateSign(worldIn, structurebb, blockpos, (IBlockState)null);
    }

    protected boolean generateSign(World worldIn, StructureBoundingBox sbb, BlockPos pos, @Nullable IBlockState state)
    {
        if (sbb.isVecInside(pos) && worldIn.getBlockState(pos).getBlock() != Blocks.WALL_SIGN)
        {
            if (state == null)
            {
                state = Blocks.CHEST.correctFacing(worldIn, pos, Blocks.WALL_SIGN.getDefaultState());
            }
            worldIn.setBlockState(pos, state, 2);
            
            TileEntity tileEntityIn = new TileEntitySign();
            tileEntityIn.validate();
            
            worldIn.setTileEntity(pos, tileEntityIn);
        
            TileEntitySign signTe = (TileEntitySign)worldIn.getTileEntity(pos);
            
            if(signTe != null)
            {
               signTe.signText[1] = new TextComponentString("BANK").setStyle(new Style().setBold(true).setColor(TextFormatting.YELLOW).setUnderlined(true));
               signTe.markDirty();
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * Adds vault to the structure and sets its contents
     */
    protected boolean generateVault(World worldIn, StructureBoundingBox structurebb, int x, int y, int z)
    {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
        return this.generateVault(worldIn, structurebb, blockpos, (IBlockState)null);
    }

    protected boolean generateVault(World worldIn, StructureBoundingBox sbb, BlockPos pos, @Nullable IBlockState state)
    {
        if (sbb.isVecInside(pos) && worldIn.getBlockState(pos).getBlock() != BlocksRegistery.BLOCK_VAULT)
        {
        	int direction = 0;
        	if(state == null)
        	{
                state = BlocksRegistery.BLOCK_VAULT_CRACKED.getDefaultState(); 
        	}
            worldIn.setBlockState(pos, state, 2);
        	TileEntity tileEntityIn = new TileEntityBlockVaultCracked();
            tileEntityIn.validate(); 
            worldIn.setTileEntity(pos, tileEntityIn);  

            TileEntityBlockVaultCracked vaultTe = (TileEntityBlockVaultCracked)worldIn.getTileEntity(pos);
         
            BlockPos blockpos = new BlockPos(this.getXWithOffset(6, 6), this.getYWithOffset(1), this.getZWithOffset(6, 6));
            if(worldIn.getBlockState(blockpos).getBlock().equals(Blocks.QUARTZ_STAIRS))
            {
            	IBlockState block = worldIn.getBlockState(blockpos);
            	if(block.getValue(BlockStairs.FACING) == EnumFacing.WEST)
            	{
            		direction = 3;
            	}
            	else if(block.getValue(BlockStairs.FACING) == EnumFacing.EAST)
            	{
            		direction = 1;
            	}
            	else if(block.getValue(BlockStairs.FACING) == EnumFacing.NORTH)
            	{
            		direction = 0;
            	}
            	else if(block.getValue(BlockStairs.FACING) == EnumFacing.SOUTH)
            	{
            		direction = 2;
            	}
            }
            if(vaultTe != null)
            {
            	vaultTe.setDirection((byte)direction);
            	vaultTe.markDirty();
            	String pass_0 = String.valueOf(worldIn.rand.nextInt(8));
            	String pass_1 = String.valueOf(worldIn.rand.nextInt(8));
            	String pass_2 = String.valueOf(worldIn.rand.nextInt(8));
            	String pass_3 = String.valueOf(worldIn.rand.nextInt(8));
            	vaultTe.setPassword(pass_0 + pass_1 + pass_2 + pass_3);
            	this.safeCode = pass_0 + pass_1 + pass_2 + pass_3;
        		Random r = new Random();
            	/** GENERATE A RANDOM NUMBER IN A CERTAIN LIMIT (0-10) AT EACH INVENTORY IF IT'S 0 IT PLACES SOMETHING IN THE VAULT **/
            	for(int i = 0; i < 27; i++)
            	{
            		int valeur = 0 + r.nextInt(20 - 0);
            		if(valeur == 0)
            		{
            			int valeur_stack = 0 + r.nextInt(15 - 0);
            			vaultTe.getHandler().setStackInSlot(i, new ItemStack(Items.DIAMOND, valeur_stack));
            		}
            		else if(valeur == 20)
            		{
            			vaultTe.getHandler().setStackInSlot(i, new ItemStack(Items.NETHER_STAR, 1));
            		}

            	}
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
