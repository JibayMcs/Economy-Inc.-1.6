package fr.fifou.economy.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.containers.ContainerVault;
import fr.fifou.economy.containers.ContainerVaultCracked;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiVaultCracked extends GuiContainer 
{
	protected TileEntityBlockVaultCracked tile_getter;
	protected InventoryPlayer playerInventory_getter;

	public GuiVaultCracked(InventoryPlayer playerInventory, TileEntityBlockVaultCracked tile) 
	{
		super(new ContainerVaultCracked(playerInventory, tile));
		this.tile_getter = tile;
		this.playerInventory_getter = playerInventory;
		
	}
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomy.MODID ,"textures/gui/container/gui_vault.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
   
    public void initGui()
    {
        super.initGui();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
    }
    	
	@Override
	public void updateScreen() 
	{
		super.updateScreen();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		fontRenderer.drawString(new TextComponentTranslation(I18n.format("title.block_vault")).getFormattedText(), 8, 5, Color.DARK_GRAY.getRGB());
		fontRenderer.drawString(new TextComponentTranslation("Inventory").getFormattedText(), 8, 73, Color.DARK_GRAY.getRGB());
		
	}
				
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
	    super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
	       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); 
	       this.mc.getTextureManager().bindTexture(background); 
	       int k = (this.width - this.xSize) / 2; 
	       int l = (this.height - this.ySize) / 2;
	       this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize); 
	}
	
	
}
