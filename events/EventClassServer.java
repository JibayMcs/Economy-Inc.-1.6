package fr.fifou.economy.events;

import java.util.Random;

import fr.fifou.economy.ConfigFile;
import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.capability.Provider;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.items.ItemCreditcard;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.mysql.MySQL;
import net.minecraft.client.Minecraft;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventClassServer {
	
	    
	    @SubscribeEvent
	    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
	        
	        if (event.getObject() instanceof EntityPlayer)
	            event.addCapability(new ResourceLocation(ModEconomy.MODID, ":MONEY"), new Provider());
	        	
	    }
	    
	    @SubscribeEvent
	    public void clonePlayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
	        
	        final IMoney original = CapabilityLoading.getHandler(event.getOriginal());
	        final IMoney clone = CapabilityLoading.getHandler(event.getEntity());
	        clone.setMoney(original.getMoney());
	        clone.setName(original.getName());
	        clone.setOnlineUUID(original.getOnlineUUID());
	    }  
	    
	    @SubscribeEvent
	    public void onplayerLoggedInEvent(PlayerLoggedInEvent event) 
	    {
	    	event.player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync((EntityPlayerMP)event.player);
	    	event.player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setName(event.player.getName());
	    	event.player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setOnlineUUID(event.player.getUniqueID().toString());
	    	if(ConfigFile.connectDB)
	    	{
	    		MySQL.check(event.player);
	    	}
	    }
	    
	    @SubscribeEvent
	    public void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event)
	    {
	    	if(ConfigFile.connectDB)
	    	{
	    		MySQL.update(event.player);
	    	}
	    }
}
