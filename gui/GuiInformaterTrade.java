package fr.fifou.economy.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.containers.ContainerInformaterTrade;
import fr.fifou.economy.containers.ContainerVault2by2;
import fr.fifou.economy.entity.EntityInformater;
import fr.fifou.economy.items.ItemHundreedb;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.packets.PacketInformaterTrading;
import fr.fifou.economy.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiInformaterTrade extends GuiContainer
{
	protected InventoryPlayer playerInventory_getter;
	protected int safeCode;
	
	public GuiInformaterTrade(InventoryPlayer playerInventory, int code) 
	{
		super(new ContainerInformaterTrade(playerInventory));
		this.playerInventory_getter = playerInventory;
		this.safeCode = code;
	}
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomy.MODID ,"textures/gui/container/gui_infor_trader.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
	private GuiButtonExt validate;

	
	@Override
	public void initGui() {
		
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.buttonList.add(this.validate = new GuiButtonExt(1, width / 2 + 26, height / 2 - 20, 50, 18, I18n.format("title.accept")));

	}
	
	@Override
	public void updateScreen() 
	{
		super.updateScreen();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{	
		if(button == this.validate)
		{
			EntityPlayer player = mc.player;
			World world = player.world;
			int totalcount = 0;

			for(int i = 0; i < player.inventory.getSizeInventory(); i++)
			{
				if(player.inventory.getStackInSlot(i).getItem() instanceof ItemHundreedb)
				{
					totalcount++;
					if(!(totalcount > 1))
					{
						PacketsRegistery.network.sendToServer(new PacketInformaterTrading(i, this.safeCode));
						player.inventory.getStackInSlot(i).setCount(player.inventory.getStackInSlot(i).getCount() - 1);
						player.inventory.addItemStackToInventory(new ItemStack(Items.PAPER).setStackDisplayName("Code : " + String.valueOf(this.safeCode)));
					}
				}
			}
		}
	}

				
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
	    super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
		this.fontRenderer.drawString(I18n.format("title.stranger"), (this.width / 2 - 81), (this.height / 2 - 75), 0x000);
		this.fontRenderer.drawString(I18n.format("title.informations"), (this.width / 2 - 81), (this.height / 2 - 60), 0x000);
		this.fontRenderer.drawString(I18n.format("title.seevault"), (this.width / 2 - 81), (this.height / 2 - 50), 0x000);
		this.fontRenderer.drawString(I18n.format("title.codevault"), (this.width / 2 - 81), (this.height / 2 - 40), 0x000);
		this.fontRenderer.drawString(I18n.format("title.onlyhundred"), (this.width / 2 - 81), (this.height / 2 - 30), 0x000);
		this.fontRenderer.drawString(I18n.format("title.deal"), (this.width / 2 - 81), (this.height / 2 - 15), 0x000);

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
	
	@Override
	public void onGuiClosed() 
	{
		super.onGuiClosed();
	}
	
}