package fr.fifou.economy.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.containers.ContainerChanger;
import fr.fifou.economy.containers.ContainerVault;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiChanger extends GuiContainer {

	public GuiChanger(InventoryPlayer playerInventory, TileEntityBlockChanger tile) 
	{
		super(new ContainerChanger(playerInventory, tile));
	}

	private TileEntityBlockChanger tile;
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomy.MODID ,"textures/gui/container/gui_changer.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
   
	
	@Override
	public void updateScreen() 
	{
		super.updateScreen();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		fontRenderer.drawString(new TextComponentTranslation(I18n.format("title.block_changer")).getFormattedText(), (this.xSize / 2) - (this.fontRenderer.getStringWidth(I18n.format("title.block_changer")) / 2), 4, Color.DARK_GRAY.getRGB());
		fontRenderer.drawString(new TextComponentTranslation("Inventory").getFormattedText(), 7, 72, Color.DARK_GRAY.getRGB());
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
