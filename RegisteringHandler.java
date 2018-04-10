package fr.fifou.economy;

import fr.fifou.economy.blocks.BlockBills;
import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegisteringHandler 
{
	 @SubscribeEvent
	 public void registerBlocks(RegistryEvent.Register<Block> event)
	 {
		 //BLOCKS
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_VAULT);
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_SELLER);
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_CHANGER);
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_VAULT_2BY2);
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_ATM);
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_VAULT_CRACKED);
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_BILLS);
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_VAULT_MARKER);
		 event.getRegistry().registerAll(BlocksRegistery.BLOCK_VAULT_MS);

	 }
	    
	 @SubscribeEvent
	 public void registerItems(RegistryEvent.Register<Item> event) 
	 {
		 //ITEMS
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_CREDITCARD);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_ONEB); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_FIVEB); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_TENB); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_TWENTYB); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_FIFTYB); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_HUNDREEDB); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_TWOHUNDREEDB); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_FIVEHUNDREEDB); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_REMOVER);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_GEAR); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_GEARSMECHANISM); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_MICROCHIP); 
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_GOLDNUGGET);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_PACKET_ONEB);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_PACKET_FIVEB);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_PACKET_TENB);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_PACKET_TWENTYB);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_PACKET_FIFTYB);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_PACKET_HUNDREEDB);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_PACKET_TWOHUNDREEDB);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_PACKET_FIVEHUNDREEDB);
		 event.getRegistry().registerAll(ItemsRegistery.ITEM_VAULT_CRACKER);

		 //ITEMS BLOCK
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_VAULT_ITEM);
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_SELLER_ITEM); 
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_CHANGER_ITEM); 
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_VAULT_2BY2_ITEM); 
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_ATM);
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_VAULT_CRACKED); 
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_BILLS); 
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_VAULT_MARKER); 
		 event.getRegistry().registerAll(ItemsRegistery.BLOCK_VAULT_MS); 



	 }
	 //ITEMS AND ITEMS BLOCK MODELS REGISTER
	 @SubscribeEvent
     public static void registerItemModels(ModelRegistryEvent event)
     {
		 //ITEMS
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_CREDITCARD, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_ONEB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_FIVEB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_TENB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_TWENTYB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_FIFTYB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_HUNDREEDB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_TWOHUNDREEDB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_FIVEHUNDREEDB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_GEAR, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_GEARSMECHANISM, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_MICROCHIP, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_REMOVER, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_GOLDNUGGET, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_ONEB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_FIVEB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_TENB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_TWENTYB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_FIFTYB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_HUNDREEDB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_TWOHUNDREEDB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_FIVEHUNDREEDB, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.ITEM_VAULT_CRACKER, 0);


			 //BLOCKS
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_VAULT_ITEM, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_SELLER_ITEM, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_CHANGER_ITEM, 0); 
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_VAULT_2BY2_ITEM, 0); 
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_ATM, 0); 
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_VAULT_CRACKED, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_BILLS, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_VAULT_MARKER, 0);
			 ItemsRegistery.registerModel(ItemsRegistery.BLOCK_VAULT_MS, 0);


     }
	 
	 //BLOCKS MODELS REGISTER
	 @SubscribeEvent
	 public static void registerBlockModels(ModelRegistryEvent event)
     {
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_VAULT, 0);
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_SELLER, 0);
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_CHANGER, 0);
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_VAULT_2BY2, 0);
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_ATM, 0);
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_VAULT_CRACKED, 0);
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_BILLS, 0);
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_VAULT_MARKER, 0);
			 BlocksRegistery.registerModel(BlocksRegistery.BLOCK_VAULT_MS, 0);



     }
}
