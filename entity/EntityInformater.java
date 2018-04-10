package fr.fifou.economy.entity;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.containers.ContainerInformaterTrade;
import fr.fifou.economy.gui.GuiHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityInformater extends EntityMob {
	
	public static String safeCode = "0000";

	public EntityInformater(World worldIn) {
		super(worldIn);
        this.setSize(0.6F, 1.95F);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
	    this.tasks.addTask(0, new EntityAISwimming(this));
	    this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
	    this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
	    this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
	    this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
	    this.tasks.addTask(2, new EntityAIMoveIndoors(this));
	    this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
	    this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
	    this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
	    this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
	    this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
	}
	
	@Override
    protected boolean canDespawn()
    {
        return false;
    }
	
	@Override
	protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }
	
	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }
	
	@Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) 
	{
		if(!this.safeCode.equals(""))
		{
			player.openGui(ModEconomy.instance, GuiHandler.ENTITY_INFOR_TRADE, world, Integer.valueOf(this.getCode()) /*Here we pass the code of the entity by the X position since we don't need the position after */, this.getPosition().getY(), this.getPosition().getZ());
		}
		return super.processInteract(player, hand);
	}
	
	public void setSafeCode(String code)
	{
		this.safeCode = code;
	}
	
	public String getCode()
	{
		return this.safeCode;
	}

	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
	    super.writeEntityToNBT(compound);
	    compound.setString("Safecode", this.safeCode);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) 
	{
		super.readEntityFromNBT(compound);
		this.safeCode = compound.getString("Safecode");
	}

}
