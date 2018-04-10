package fr.fifou.economy.packets;

import fr.fifou.economy.ModEconomy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketsRegistery {

	 public static SimpleNetworkWrapper network;

	 public static void registerPackets(FMLPreInitializationEvent event)
	 {
		   network = NetworkRegistry.INSTANCE.newSimpleChannel(ModEconomy.MODID);
	       network.registerMessage(PacketCardChange.Handler.class, PacketCardChange.class, 0, Side.SERVER);
	       network.registerMessage(PacketIsOpen.Handler.class, PacketIsOpen.class, 1, Side.SERVER);
	       network.registerMessage(PacketCardChangeAdmin.Handler.class, PacketCardChangeAdmin.class, 2, Side.SERVER);
	       network.registerMessage(PacketSellerCreated.Handler.class, PacketSellerCreated.class, 3, Side.SERVER);
	       network.registerMessage(PacketSellerFundsTotal.Handler.class, PacketSellerFundsTotal.class, 4, Side.SERVER);
	       network.registerMessage(PacketCardChangeSeller.Handler.class, PacketCardChangeSeller.class, 5, Side.SERVER);
	       network.registerMessage(PacketListNBT.Handler.class, PacketListNBT.class, 6, Side.SERVER);
	       if(event.getSide().isClient())
	       {
		       network.registerMessage(PacketMoneyData.PacketMoneyHandlerClient.class, PacketMoneyData.class, 7, Side.CLIENT);
	       }
	       else if(event.getSide().isServer())
	       {
	    	   network.registerMessage(PacketMoneyData.PacketMoneyHandlerServer.class, PacketMoneyData.class, 7, Side.CLIENT);
	       }
	       network.registerMessage(PacketOpenCracked.Handler.class, PacketOpenCracked.class, 8, Side.SERVER);
	       network.registerMessage(PacketInformaterTrading.Handler.class, PacketInformaterTrading.class, 9, Side.SERVER);
	 }
}
