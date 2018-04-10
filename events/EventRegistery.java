package fr.fifou.economy.events;

import fr.fifou.economy.ConfigFile;
import fr.fifou.economy.RegisteringHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class EventRegistery {

	public static void register(FMLPreInitializationEvent event)
	{
		   MinecraftForge.EVENT_BUS.register(new RegisteringHandler());
	       if(event.getSide().isClient())
	       {
		   		if(ConfigFile.canPreviewItemInBlock)
				{
		   			MinecraftForge.EVENT_BUS.register(new EventClassClient());
				}
	   			MinecraftForge.EVENT_BUS.register(new EventClassClientFull());
	   			MinecraftForge.EVENT_BUS.register(EventClassClientFull.class);
	       }
	       else if(event.getSide().isServer())
	       {
		    	MinecraftForge.EVENT_BUS.register(new EventClassServer());
		    	MinecraftForge.EVENT_BUS.register(EventClassServer.class);
	       }
	       MinecraftForge.EVENT_BUS.register(EventClassCommon.class);
	}
}
