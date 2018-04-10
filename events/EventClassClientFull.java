package fr.fifou.economy.events;
import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.capability.Provider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventClassClientFull 
{
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

    }  
}
