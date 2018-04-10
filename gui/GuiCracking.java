package fr.fifou.economy.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.containers.ContainerVaultCracked;
import fr.fifou.economy.items.ItemCreditcard;
import fr.fifou.economy.packets.PacketCardChangeSeller;
import fr.fifou.economy.packets.PacketOpenCracked;
import fr.fifou.economy.packets.PacketSellerFundsTotal;
import fr.fifou.economy.packets.PacketsRegistery;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import scala.reflect.internal.Trees.This;

public class GuiCracking extends GuiScreen
{
	private TileEntityBlockVaultCracked tile;
	
	public GuiCracking(TileEntityBlockVaultCracked tile) 
	{
		this.tile = tile;
	}
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomy.MODID ,"textures/gui/screen/gui_cracking.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int xSizeButton = 20;
	protected int ySizeButton = 20;
	protected int guiLeft;
	protected int guiTop;
	
	private int counter = 0;
	private int ic_0 = 0;
	private int ic_1 = 0;
	private int ic_2 = 0;
	private int ic_3 = 0;
	
	private int pass_0 = 0;
	private int pass_1 = 0;
	private int pass_2 = 0;
	private int pass_3 = 0;
	
	private String pass_full = "";

	
	protected GuiButtonExt button_0;
	protected GuiButtonExt button_1;	
	protected GuiButtonExt button_2;	
	protected GuiButtonExt button_3;	
	protected GuiButtonExt button_4;	
	protected GuiButtonExt button_5;	
	protected GuiButtonExt button_6;	
	protected GuiButtonExt button_7;	
	protected GuiButtonExt button_8;	
	protected GuiButtonExt button_9;
	protected GuiButtonExt button_10;
	protected GuiButtonExt button_11;

	
	

	
	@Override
	public void updateScreen() 
	{
		super.updateScreen();
	}
	
	@Override
	public void initGui() 
	{
		this.guiLeft = (this.width - this.xSize) / 2;
	    this.guiTop = (this.height - this.ySize) / 2;
	    this.buttonList.add(this.button_0 = new GuiButtonExt(0, width / 2 - 10, height / 2 + 50, 20, 20, "0"));
	    this.buttonList.add(this.button_1 = new GuiButtonExt(1, width / 2 - 35, height / 2 + 25, 20, 20, "1"));
	    this.buttonList.add(this.button_2 = new GuiButtonExt(2, width / 2 - 10, height / 2 + 25, 20, 20, "2"));
	    this.buttonList.add(this.button_3 = new GuiButtonExt(3, width / 2 + 15, height / 2 + 25, 20, 20, "3"));
	    this.buttonList.add(this.button_4 = new GuiButtonExt(4, width / 2 - 35, height / 2 , 20, 20, "4"));
	    this.buttonList.add(this.button_5 = new GuiButtonExt(5, width / 2 - 10, height / 2, 20, 20, "5"));
	    this.buttonList.add(this.button_6 = new GuiButtonExt(6, width / 2 + 15, height / 2 , 20, 20, "6"));
	    this.buttonList.add(this.button_7 = new GuiButtonExt(7, width / 2 - 35, height / 2 - 25, 20, 20, "7"));
	    this.buttonList.add(this.button_8 = new GuiButtonExt(8, width / 2 - 10, height / 2 - 25, 20, 20, "8"));
	    this.buttonList.add(this.button_9 = new GuiButtonExt(9, width / 2 + 15, height / 2 - 25, 20, 20, "9"));
	    
	    this.buttonList.add(this.button_10 = new GuiButtonExt(10, width / 2 - 35, height / 2 + 50, 20, 20, "X"));
	    this.buttonList.add(this.button_11 = new GuiButtonExt(11, width / 2 + 15, height / 2 + 50, 20, 20, "V"));


	}
	
	
	@Override
	public boolean doesGuiPauseGame() 
	{
		return false;
	}
	
	 
		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks)
		{
			this.drawDefaultBackground();
			// added
	        this.mc.getTextureManager().bindTexture(background);
	        int i = this.guiLeft;
	        int j = this.guiTop;
	        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
			super.drawScreen(mouseX, mouseY, partialTicks);
			drawImageInGui();
	    }
		
		@Override
		protected void actionPerformed(GuiButton button) throws IOException 
		{
			super.actionPerformed(button);
			switch (button.id) {
			case 0:
				countCheck(0);
				break;
			case 1:
				countCheck(1);
				break;
			case 2:
				countCheck(2);
				break;
			case 3:
				countCheck(3);
				break;
			case 4:
				countCheck(4);
				break;
			case 5:
				countCheck(5);
				break;
			case 6:
				countCheck(6);
				break;
			case 7:
				countCheck(7);
				break;
			case 8:
				countCheck(8);
				break;
			case 9:
				countCheck(9);
				break;
			case 10 :
				mc.player.openGui(ModEconomy.instance, GuiHandler.BLOCK_VAULT_CRACKING, mc.world, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
				break;
			case 11 : 
				this.pass_full = String.valueOf(pass_0) + String.valueOf(pass_1) + String.valueOf(pass_2) + String.valueOf(pass_3);
				if(tile.getPassword().equals(this.pass_full))
				{
					PacketsRegistery.network.sendToServer(new PacketOpenCracked(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), true));
				}
				else
				{
					mc.player.closeScreen();
				}
			default:
				break;
			}
		}
		
		private void countCheck(int i) 
		{
			if(this.counter == 0)
			{
				this.ic_0 = (20 * i);
				this.counter = 1;
				this.pass_0 = i;
			}
			else if(this.counter == 1)
			{
				this.ic_1 = (20 * i);
				this.counter = 2;
				this.pass_1 = i;

			}
			else if(this.counter == 2)				
			{
				this.ic_2 = (20 * i);
				this.counter = 3;
				this.pass_2 = i;

			}
			else if(this.counter == 3)
			{
				this.ic_3 = (20 * i);
				this.counter = 4;
				this.pass_3 = i;
			}


		}
		
		public void drawImageInGui() 
		{
	        int i = this.guiLeft + 46;
	        int j = this.guiTop + 27;
            mc.getTextureManager().bindTexture(background);
            this.drawTexturedModalRect(i + (21 * 0), j , 236, 0  + this.ic_0, this.xSizeButton, this.ySizeButton);
            this.drawTexturedModalRect(i + (21 * 1), j , 236, 0  + this.ic_1, this.xSizeButton, this.ySizeButton);
            this.drawTexturedModalRect(i + (21 * 2), j , 236, 0  + this.ic_2, this.xSizeButton, this.ySizeButton);
            this.drawTexturedModalRect(i + (21 * 3), j , 236, 0  + this.ic_3, this.xSizeButton, this.ySizeButton);
		}
		
		@Override
		public void onGuiClosed()
		{
			super.onGuiClosed();
			this.counter = 0;
		}
		

}
