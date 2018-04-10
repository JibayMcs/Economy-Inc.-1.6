package fr.fifou.economy;

import java.io.File;

import com.leviathanstudio.craftstudio.client.registry.CSRegistryHelper;
import com.leviathanstudio.craftstudio.client.registry.CraftStudioLoader;
import com.leviathanstudio.craftstudio.client.util.EnumRenderType;
import com.leviathanstudio.craftstudio.client.util.EnumResourceType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.blocks.tesr.TileEntityBillsSpecialRenderer;
import fr.fifou.economy.blocks.tesr.TileEntityVault2by2SpecialRenderer;
import fr.fifou.economy.blocks.tesr.TileEntityVaultCrackedSpecialRenderer;
import fr.fifou.economy.blocks.tesr.TileEntityVaultSpecialRenderer;
import fr.fifou.economy.blocks.tesr.inventory.TileEntityInventoryRenderHelper;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockBills;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.entity.EntityInformater;
import fr.fifou.economy.entity.renderer.RenderInformater;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.model.ModelInformater;

public class EconomyClient extends EconomyCommon 
{
	   
    @Override
    public void preInit(File configFile)
    {
        super.preInit(configFile);
        RenderingRegistry.registerEntityRenderingHandler(EntityInformater.class, rm -> new RenderInformater(rm, new ModelInformater(), 0.5F));
    }

    @Override
    public void init()
    {
        super.init();
        //REGISTER ITEMS MODEL
        ItemsRegistery.registerItemsModels();
        TileEntityItemStackRenderer.instance = new TileEntityInventoryRenderHelper();
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVault.class, new TileEntityVaultSpecialRenderer());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVault2by2.class, new TileEntityVault2by2SpecialRenderer());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVaultCracked.class, new TileEntityVaultCrackedSpecialRenderer());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockBills.class, new TileEntityBillsSpecialRenderer());

    }
    
    @CraftStudioLoader
    public static void registerModels() 
    {
        CSRegistryHelper registry = new CSRegistryHelper(ModEconomy.MODID);
        
        registry.register(EnumResourceType.ANIM, EnumRenderType.BLOCK, "anim_vault2by2");
        registry.register(EnumResourceType.MODEL, EnumRenderType.BLOCK, "model_vault2by2");
        registry.register(EnumResourceType.MODEL, EnumRenderType.BLOCK, "model_vault");
    }
	

}
