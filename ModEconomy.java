package fr.fifou.economy;

/***
 * 	© Florent T. also know as Fifou_BE
 *  Mod created by Florent T. also known as Fifou_BE
 *  Officials threads of this mod are
	 * 	https://minecraft.curseforge.com/projects/economy-inc
	 * 	https://www.planetminecraft.com/mod/economy-inc/
	 * 	https://www.minecraftforgefrance.fr/showthread.php?tid=4715
 *  My official website is http://qdc.esy.es/
 *  For more info read terms and conditions and also read https://account.mojang.com/documents/minecraft_eula parts with MOD
 */

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper;
import com.leviathanstudio.craftstudio.client.registry.RegistryHandler;

import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.blocks.tesr.TileEntityVaultSpecialRenderer;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.blocks.tileentity.TileEntityRegistery;
import fr.fifou.economy.capability.DefaultMoneyHandler;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.capability.Storage;
import fr.fifou.economy.commands.CommandBalance;
import fr.fifou.economy.entity.EntityInformater;
import fr.fifou.economy.events.EventClassClient;
import fr.fifou.economy.events.EventClassClientFull;
import fr.fifou.economy.events.EventClassCommon;
import fr.fifou.economy.events.EventClassServer;
import fr.fifou.economy.events.EventRegistery;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.gui.GuiItem;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.packets.PacketCardChange;
import fr.fifou.economy.packets.PacketCardChangeAdmin;
import fr.fifou.economy.packets.PacketCardChangeSeller;
import fr.fifou.economy.packets.PacketListNBT;
import fr.fifou.economy.packets.PacketMoneyData;
import fr.fifou.economy.packets.PacketOpenCracked;
import fr.fifou.economy.packets.PacketSellerCreated;
import fr.fifou.economy.packets.PacketSellerFundsTotal;
import fr.fifou.economy.packets.PacketsRegistery;
import fr.fifou.economy.removers.RecipeRemover;
import fr.fifou.economy.world.gen.structure.VillageComponentShop;
import fr.fifou.economy.world.gen.structure.VillageHandlerShop;
import fr.fifou.economy.world.storage.loot.CustomLootTableList;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

@Mod.EventBusSubscriber
@Mod(modid = ModEconomy.MODID, name="Mod Economy", version ="1.5", acceptedMinecraftVersions= "[1.12.2]")
public class ModEconomy 
{
	public static final String MODID = "economy";
	
	@Instance(ModEconomy.MODID)
	public static ModEconomy instance;
	
	 @SidedProxy(clientSide = "fr.fifou.economy.EconomyClient", serverSide = "fr.fifou.economy.EconomyServer")
	 public static EconomyCommon proxy;
	
	 public static Logger logger;
	 //CREATE CUSTOM CREATIVE TABS
	 public static CreativeTabs tabEconomy = new TabEconomy(CreativeTabs.getNextID(), "Economy Inc.");

	    
	 @EventHandler
	 public void preInit(FMLPreInitializationEvent event)
	 {
		   
	       logger = event.getModLog();
	       proxy.preInit(event.getSuggestedConfigurationFile());
	       //CONFIG FILE
	       ConfigFile.init(event);
	       //REMOVING SMELTING
	       if(ConfigFile.goldNuggetRecipe)
	       {
		       RecipeRemover.removeFurnaceRecipe(FurnaceRecipes.instance().getSmeltingResult(new ItemStack(Blocks.GOLD_ORE)));
		       //ADDING SMELTING
		       ItemStack result = new ItemStack(ItemsRegistery.ITEM_GOLDNUGGET);
		       result.setTagCompound(null);
		       GameRegistry.addSmelting(new ItemStack(Blocks.GOLD_ORE), result, 1);
	       }  
	       //EVENT
	       EventRegistery.register(event);
	       //REGISTERING TILE ENTITIES
	       TileEntityRegistery.register();
	       //REGISTERING PACKETS
	       PacketsRegistery.registerPackets(event);
	       //REGISTERING CUSTOM LOOT TABLE
	       CustomLootTableList.registerLootTables();
	   }
	   
	   @EventHandler
	   public void init(FMLInitializationEvent event)
	   {
		   proxy.init();
		   //GUI HANDLER
		   NetworkRegistry.INSTANCE.registerGuiHandler(ModEconomy.instance, new GuiHandler());
		   //PERMISSION API
		   PermissionAPI.registerNode("economy.command.balance", DefaultPermissionLevel.OP, "Allows players to use the balance command");
		   PermissionAPI.registerNode("unlimited_stack", DefaultPermissionLevel.OP, "Allows OP players to use the unlimited stack");
		   //CAPABILITIES
		   CapabilityManager.INSTANCE.register(IMoney.class, new Storage(), DefaultMoneyHandler.class);
		   //ADD VILLAGE STRUCTURE
		   if(ConfigFile.doesBankGenerateInVillages)
		   {
			   MapGenStructureIO.registerStructureComponent(VillageComponentShop.class, ModEconomy.MODID + "VillageShopComponent");
			   VillagerRegistry.instance().registerVillageCreationHandler(new VillageHandlerShop());   
		   }
		   //REGISTERING ENTITIES
	       EntityRegistry.registerModEntity(new ResourceLocation(MODID, "entityInformater"), EntityInformater.class, "entityInformater", 1, instance, 64, 1, true, 2566965, 6515058);

	   }
	   
       @EventHandler
       public void PostInit(FMLPostInitializationEvent event)
       {
       }
	   
	   @EventHandler
	   public void serverStarting(FMLServerStartingEvent event) 
	   {
		   // REGISTERING COMMAND
	       event.registerServerCommand(new CommandBalance());
	   }
    
}
