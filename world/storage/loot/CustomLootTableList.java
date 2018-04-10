package fr.fifou.economy.world.storage.loot;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import fr.fifou.economy.ModEconomy;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootTableManager;

import static fr.fifou.economy.world.storage.loot.CustomLootTableList.RegistrationHandler.create;


public class CustomLootTableList {

	public static final ResourceLocation CHESTS_SHOP_VILLAGE = create("chests/shop_bonus_chest");

	public static void registerLootTables() 
	{
		RegistrationHandler.LOOT_TABLES.forEach(LootTableList::register);
	}

	public static class RegistrationHandler {

		private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();

		protected static ResourceLocation create(String id) {
			final ResourceLocation lootTable = new ResourceLocation(ModEconomy.MODID, id);
			RegistrationHandler.LOOT_TABLES.add(lootTable);
			return lootTable;
		}
	}
}
