package fr.fifou.economy.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class Provider implements ICapabilitySerializable<NBTTagCompound>{

	IMoney instance = CapabilityLoading.CAPABILITY_MONEY.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability == CapabilityLoading.CAPABILITY_MONEY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

		return hasCapability(capability, facing) ? CapabilityLoading.CAPABILITY_MONEY.<T>cast(instance) : null;
	}


    @Override
    public NBTTagCompound serializeNBT() {
        
        return (NBTTagCompound) CapabilityLoading.CAPABILITY_MONEY.getStorage().writeNBT(CapabilityLoading.CAPABILITY_MONEY, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        
    	CapabilityLoading.CAPABILITY_MONEY.getStorage().readNBT(CapabilityLoading.CAPABILITY_MONEY, instance, null, nbt);
    }
}

