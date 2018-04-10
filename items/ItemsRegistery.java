package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.BlockBills;
import fr.fifou.economy.blocks.BlocksRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemsRegistery 
{
	//ITEMS
	public static Item ITEM_CREDITCARD = new ItemCreditcard();
	public static Item ITEM_ONEB = new ItemOneb();
	public static Item ITEM_FIVEB = new ItemFiveb();
	public static Item ITEM_TENB = new ItemTenb();
	public static Item ITEM_TWENTYB = new ItemTwentyb();
	public static Item ITEM_FIFTYB= new ItemFiftyb();
	public static Item ITEM_HUNDREEDB = new ItemHundreedb();
	public static Item ITEM_TWOHUNDREEDB = new ItemTwohundreedb();
	public static Item ITEM_FIVEHUNDREEDB = new ItemFivehundreedb();
	public static Item ITEM_REMOVER = new ItemRemover();
	public static Item ITEM_GEAR = new ItemGear();
	public static Item ITEM_GEARSMECHANISM = new ItemGearsMecanism();
	public static Item ITEM_MICROCHIP = new ItemMicroChip();
	public static Item ITEM_GOLDNUGGET = new ItemGoldNuggetSubs();
	public static Item ITEM_PACKET_ONEB = new ItemPacketOne();
	public static Item ITEM_PACKET_FIVEB = new ItemPacketFive();
	public static Item ITEM_PACKET_TENB = new ItemPacketTen();
	public static Item ITEM_PACKET_TWENTYB= new ItemPacketTwenty();
	public static Item ITEM_PACKET_FIFTYB = new ItemPacketFifty();
	public static Item ITEM_PACKET_HUNDREEDB = new ItemPacketHundreed();
	public static Item ITEM_PACKET_TWOHUNDREEDB = new ItemPacketTwoHundreed();
	public static Item ITEM_PACKET_FIVEHUNDREEDB = new ItemPacketFiveHundeed();
	public static Item ITEM_VAULT_CRACKER = new ItemVaultCracker();

	//(ITEMS)BLOCKS
	public static final Item BLOCK_VAULT_ITEM = new ItemBlock(BlocksRegistery.BLOCK_VAULT).setRegistryName(BlocksRegistery.BLOCK_VAULT.getRegistryName());
	public static final Item BLOCK_SELLER_ITEM = new ItemBlock(BlocksRegistery.BLOCK_SELLER).setRegistryName(BlocksRegistery.BLOCK_SELLER.getRegistryName());
	public static final Item BLOCK_CHANGER_ITEM = new ItemBlock(BlocksRegistery.BLOCK_CHANGER).setRegistryName(BlocksRegistery.BLOCK_CHANGER.getRegistryName());
	public static final Item BLOCK_VAULT_2BY2_ITEM = new ItemBlock(BlocksRegistery.BLOCK_VAULT_2BY2).setRegistryName(BlocksRegistery.BLOCK_VAULT_2BY2.getRegistryName());	
	public static final Item BLOCK_ATM = new ItemBlock(BlocksRegistery.BLOCK_ATM).setRegistryName(BlocksRegistery.BLOCK_ATM.getRegistryName());
	public static final Item BLOCK_VAULT_CRACKED = new ItemBlock(BlocksRegistery.BLOCK_VAULT_CRACKED).setRegistryName(BlocksRegistery.BLOCK_VAULT_CRACKED.getRegistryName());
	public static final Item BLOCK_BILLS = new ItemBlock(BlocksRegistery.BLOCK_BILLS).setRegistryName(BlocksRegistery.BLOCK_BILLS.getRegistryName());
	public static final Item BLOCK_VAULT_MARKER = new ItemBlock(BlocksRegistery.BLOCK_VAULT_MARKER).setRegistryName(BlocksRegistery.BLOCK_VAULT_MARKER.getRegistryName());
	public static final Item BLOCK_VAULT_MS = new ItemBlock(BlocksRegistery.BLOCK_VAULT_MS).setRegistryName(BlocksRegistery.BLOCK_VAULT_MS.getRegistryName());

	
	public static void setItemName(Item item, String name)
	   {
	       item.setRegistryName(ModEconomy.MODID, name).setUnlocalizedName(ModEconomy.MODID + "." + name);
	   }
	   
		 @SideOnly(Side.CLIENT)
		 public static void registerItemsModels()
		 {
			 registerModel(ITEM_CREDITCARD, 0);
			 registerModel(ITEM_ONEB, 0);
			 registerModel(ITEM_FIVEB, 0);
			 registerModel(ITEM_TENB, 0);
			 registerModel(ITEM_TWENTYB, 0);
			 registerModel(ITEM_FIFTYB, 0);
			 registerModel(ITEM_HUNDREEDB, 0);
			 registerModel(ITEM_TWOHUNDREEDB, 0);
			 registerModel(ITEM_FIVEHUNDREEDB, 0);
			 registerModel(ITEM_REMOVER, 0);
			 registerModel(ITEM_GEAR, 0);
			 registerModel(ITEM_GEARSMECHANISM, 0);
			 registerModel(ITEM_MICROCHIP, 0);
			 registerModel(ITEM_GOLDNUGGET, 0);
			 registerModel(ITEM_PACKET_ONEB, 0);
			 registerModel(ITEM_PACKET_FIVEB, 0);
			 registerModel(ITEM_PACKET_TENB, 0);
			 registerModel(ITEM_PACKET_TWENTYB, 0);
			 registerModel(ITEM_PACKET_FIFTYB, 0);
			 registerModel(ITEM_PACKET_HUNDREEDB, 0);
			 registerModel(ITEM_PACKET_TWOHUNDREEDB, 0);
			 registerModel(ITEM_PACKET_FIVEHUNDREEDB, 0);
			 registerModel(ITEM_VAULT_CRACKER, 0);
			 
			 //BLOCKS
			 registerModel(BLOCK_VAULT_ITEM, 0);
			 registerModel(BLOCK_SELLER_ITEM, 0);
			 registerModel(BLOCK_CHANGER_ITEM, 0);
			 registerModel(BLOCK_VAULT_2BY2_ITEM, 0);
			 registerModel(BLOCK_ATM, 0);
			 registerModel(BLOCK_VAULT_CRACKED, 0);
			 registerModel(BLOCK_BILLS, 0);
			 registerModel(BLOCK_VAULT_MARKER, 0);
			 registerModel(BLOCK_VAULT_MS, 0);


		 }
		 
		 @SideOnly(Side.CLIENT)
		 public static void registerModel(Item item, int metadata)
		 {
	    	 if (metadata < 0) metadata = 0;
	    	 String resourceName = item.getRegistryName().toString();
	    	 if (metadata > 0) resourceName += "_m" + String.valueOf(metadata);

	    	 ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		 }
}
