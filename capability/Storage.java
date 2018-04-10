package fr.fifou.economy.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class Storage implements IStorage<IMoney> {

	@Override
	public NBTBase writeNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side){
		
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setDouble("money", instance.getMoney());
		tag.setBoolean("linked", instance.getLinked());
		tag.setString("name", instance.getName());
		tag.setString("onlineUUID", instance.getOnlineUUID());
		return tag;
	}

	@Override
	public void readNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side, NBTBase nbt){
		
		final NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.setMoney(tag.getDouble("money"));
		instance.setLinked(tag.getBoolean("linked"));
		instance.setName(tag.getString("name"));
		instance.setOnlineUUID(tag.getString("onlineUUID"));

	}

}
