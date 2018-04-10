package fr.fifou.economy.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.containers.ContainerVault;
import fr.fifou.economy.packets.PacketIsOpen;
import fr.fifou.economy.packets.PacketListNBT;
import fr.fifou.economy.packets.PacketsRegistery;
import io.netty.buffer.Unpooled;
import net.java.games.input.Component.Identifier.Key;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.server.FMLServerHandler;
import scala.swing.event.KeyPressed;

public class GuiVaultNew extends GuiContainer
{
    	private GuiTextField addPlayersToList;
    	protected TileEntityBlockVault tile_getter;
    	protected InventoryPlayer playerInventory_getter;

		public GuiVaultNew(InventoryPlayer playerInventory, TileEntityBlockVault tile) 
		{
			super(new ContainerVault(playerInventory, tile));
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
	        if(tile_getter.getOwnerS().equals(mc.player.getUniqueID().toString()) && !Minecraft.getMinecraft().isSingleplayer())
	        {
	        	this.buttonList.add(new GuiButtonExt(0, i + 161, j, 15, 15, TextFormatting.BOLD.toString() + TextFormatting.WHITE + "⚙"));
	        }
	    }
	    
		@Override
		public void onGuiClosed() 
		{
			super.onGuiClosed();
			if(tile_getter.getIsOpen())
			{
				tile_getter.setIsOpen(false);
				tile_getter.markDirty();
				PacketsRegistery.network.sendToServer(new PacketIsOpen(tile_getter.getPos().getX(), tile_getter.getPos().getY(), tile_getter.getPos().getZ(), false));
			}
		}
		
		@Override
		public void updateScreen() 
		{
			super.updateScreen();
		}
		
		@Override
		protected void actionPerformed(GuiButton button) throws IOException 
		{
			switch (button.id) {
			case 0:
				playerInventory_getter.player.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_SETTINGS, playerInventory_getter.player.world, tile_getter.getPos().getX(), tile_getter.getPos().getY(), tile_getter.getPos().getZ());
				break;

			}
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