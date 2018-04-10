package fr.fifou.economy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigFile {

	 public static boolean canPreviewItemInBlock;
	 public static int multiplierGoldNuggetWeight;
	 public static boolean canAccessCardWithoutWT;
	 public static boolean doesBankGenerateInVillages;
	 public static boolean goldNuggetRecipe;
	 
	 public static String dbName;
	 public static String urlDB;
	 public static String userDB;
	 public static String passwordDB;
	 public static boolean connectDB;
	 
	public static void init(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
	       try
	       {
	    	   cfg.load();
	    	   if(event.getSide().isServer())//Only for server
	    	   {		    	   
		    	   connectDB = cfg.getBoolean("connectDB", "DATABASE", false, "Allow you to connect to a data base and synchronize account.");
		    	   urlDB = cfg.getString("urlDB", "DATABASE", "localhost:3306", "The host of your database.");
		    	   userDB = cfg.getString("userDB", "DATABASE", "root", "User of the database.");
		    	   passwordDB = cfg.getString("passwordDB", "DATABASE", "", "Password for the user in database.");
		    	   dbName = cfg.getString("dbName", "DATABASE", "EconomyInc", "Leave empty if no data base name.");   
	    	   }
	    	   else if(event.getSide().isClient())//Only for client
	    	   {
	    		   canPreviewItemInBlock = cfg.getBoolean("itemPreviewSeller", "EconomyInc", true, "Allow you to disable the item preview in block seller when you hover it.");
	    	   }
	    	   //For both
	    	   multiplierGoldNuggetWeight = cfg.getInt("multiplierGoldNuggetWeight", "EconomyInc", 2, 1, 9999, "It will multiply the weight of the nugget with this number to create a funds to add to the credit card.");
	    	   goldNuggetRecipe = cfg.getBoolean("goldNuggetRecipe", "EconomyInc", true, "Allow the mod to replace the furnace recipe for the gold ore and give instead the EconomyInc's nugget.");
	    	   doesBankGenerateInVillages = cfg.getBoolean("doesBankGenerateInVillages", "EconomyInc", true, "Allow or not the bank to generate in villages, by default it generates turn it to false to disable its generation.");
	    	   canAccessCardWithoutWT = cfg.getBoolean("canAccessCardWithoutWT", "EconomyInc", true, "Allow player that have the wireless technology to access their account without an ATM nearby.");



	       }
	       catch(Exception e)
	       {
	    	   event.getModLog().fatal("Failed to load configuration of Economy Inc. Please report to mod author.");
	       }
	       finally
	       {
	    	   if(cfg.hasChanged())
	    	   {
	    		   cfg.save();
	    	   }
	       }
	       
	}
}
