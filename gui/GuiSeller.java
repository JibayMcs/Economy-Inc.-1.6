package fr.fifou.economy.gui;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.server.permission.PermissionAPI;

import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.containers.ContainerSeller;
import fr.fifou.economy.packets.PacketCardChange;
import fr.fifou.economy.packets.PacketSellerCreated;
import fr.fifou.economy.packets.PacketsRegistery;

public class GuiSeller extends GuiContainer
{
	private TileEntityBlockSeller tile;
	
	public GuiSeller(InventoryPlayer playerInventory, TileEntityBlockSeller tile) 
	{
		super(new ContainerSeller(playerInventory, tile));
		this.tile = tile;
		
	}
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomy.MODID ,"textures/gui/container/gui_seller.png");
	protected int xSize = 176;
	protected int ySize = 168;
	protected int guiLeft;
	protected int guiTop;
	
	private GuiButtonExt validate;
	private GuiButtonExt one;
	private GuiButtonExt five;
	private GuiButtonExt ten;
	private GuiButtonExt twenty;
	private GuiButtonExt fifty;
	private GuiButtonExt hundreed;
	private GuiButtonExt twoHundreed;
	private GuiButtonExt fiveHundreed;
	private GuiButtonExt unlimitedStack;
	
	private static double cost;
	private boolean admin = false;
	
	
	
	@Override
	public void updateScreen() 
	{
		super.updateScreen();
		
	}
	

	
	@Override
	public void initGui() 
	{
		super.initGui();
		World worldIn = mc.world;
		EntityPlayer player = mc.player;
		if(tile.getCreated() == false)
		{
			this.buttonList.add(this.validate = new GuiButtonExt(1, width / 2 + 26, height / 2 + 83, 55, 20, I18n.format("title.validate")));
			this.buttonList.add(this.one = new GuiButtonExt(2, width / 2 - 122, height / 2 - 75, 35, 20, "1"));
			this.buttonList.add(this.five = new GuiButtonExt(3, width / 2 - 122, height / 2 - 56, 35, 20, "5"));
			this.buttonList.add(this.ten = new GuiButtonExt(4, width / 2- 122, height / 2 - 37, 35, 20, "10"));
			this.buttonList.add(this.twenty = new GuiButtonExt(5, width / 2 - 122, height / 2 - 18, 35, 20, "20"));
			this.buttonList.add(this.fifty = new GuiButtonExt(6, width / 2 - 122, height / 2 + 1, 35, 20, "50"));
			this.buttonList.add(this.hundreed = new GuiButtonExt(7, width / 2 - 122, height / 2 + 20, 35, 20, "100"));
			this.buttonList.add(this.twoHundreed = new GuiButtonExt(8, width / 2 - 122, height / 2 + 39, 35, 20, "200"));
			this.buttonList.add(this.fiveHundreed = new GuiButtonExt(9, width / 2 - 122, height / 2 + 58, 35, 20, "500"));
			if(player.isCreative() == true)
			{
				this.buttonList.add(this.unlimitedStack = new GuiButtonExt(9, width /2 + 2, height / 2 - 96, 80, 13, I18n.format("title.unlimited")));
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{
		super.actionPerformed(button);
		World worldIn = mc.world;
		EntityPlayer playerIn = mc.player;
		if(tile != null)
		{
			if(button == this.unlimitedStack)
			{
				if(this.admin == false)
				{
					this.admin = true;
					tile.setAdmin(true);
					playerIn.sendMessage(new TextComponentString(I18n.format("title.unlimitedStack")));
				}
				else if(this.admin == true)
				{
					this.admin = false;
					tile.setAdmin(false);
					playerIn.sendMessage(new TextComponentString(I18n.format("title.limitedStack")));
				}
			}
			if(button == this.one)
			{
				tile.setCost(1);
				this.cost = 1;
			}
			else if(button == this.five)
			{
				tile.setCost(5);
				this.cost = 5;
			}
			else if(button == this.ten)
			{
				tile.setCost(10);
				this.cost = 10;
			}
			else if(button == this.twenty)
			{
				tile.setCost(20);
				this.cost = 20;
			}
			else if(button == this.fifty)
			{
				tile.setCost(50);
				this.cost = 50;
			}
			else if(button == this.hundreed)
			{
				tile.setCost(100);
				this.cost = 100;
			}
			else if(button == this.twoHundreed)
			{
				tile.setCost(200);
				this.cost = 200;
			}
			else if(button == this.fiveHundreed)
			{
				tile.setCost(500);
				this.cost = 500;
			}
			else if(button == this.validate)
			{
				if(!(tile.getCost() == 0)) // IF TILE HAS NOT A COST OF 0 THEN WE PASS TO THE OTHER
				{
					if(tile.getStackInSlot(0) != ItemStack.EMPTY) // IF SLOT 0 IS NOT BLOCKS.AIR, WE PASS
					{
						if(this.admin == false) //ADMIN HASN'T SET : UNLIMITED STACK
						{
							tile.setAdmin(false);
							tile.setCreated(true); // CLIENT SET CREATED AT TRUE
							final int x = tile.getPos().getX(); // GET X
							final int y = tile.getPos().getY(); // GET Y
							final int z = tile.getPos().getZ(); // GET Z
							int amount = tile.getStackInSlot(0).getCount(); // GET COUNT IN TILE THANKS TO STACK IN SLOT
							String name = tile.getStackInSlot(0).getDisplayName(); // GET ITEM NAME IN TILE THANKS TO STACK IN SLOT
							tile.setAmount(amount); //CLIENT SET AMOUNT
							tile.setItem(name); // CLIENT SET ITEM NAME
							playerIn.closeScreen(); // CLOSE SCREEN
							PacketsRegistery.network.sendToServer(new PacketSellerCreated(true, this.cost, name, amount, x, y, z, false)); // SEND SERVER PACKET FOR CREATED, COST, NAME, AMOUNT, X,Y,Z ARE TILE COORDINATES
							tile.markDirty();
						}
						else if(this.admin == true) //ADMIN HAS SET : UNLIMITED STACK
						{
							tile.setAdmin(true);
							tile.setCreated(true); // CLIENT SET CREATED AT TRUE
							final int x = tile.getPos().getX(); // GET X
							final int y = tile.getPos().getY(); // GET Y
							final int z = tile.getPos().getZ(); // GET Z
							int amount = tile.getStackInSlot(0).getCount(); // GET COUNT IN TILE THANKS TO STACK IN SLOT
							String name = tile.getStackInSlot(0).getDisplayName(); // GET ITEM NAME IN TILE THANKS TO STACK IN SLOT
							tile.setAmount(amount); //CLIENT SET AMOUNT
							tile.setItem(name); // CLIENT SET ITEM NAME
							playerIn.closeScreen(); // CLOSE SCREEN
							PacketsRegistery.network.sendToServer(new PacketSellerCreated(true, this.cost, name, amount, x, y, z, true)); // SEND SERVER PACKET FOR CREATED, COST, NAME, AMOUNT, X,Y,Z ARE TILE COORDINATES
							tile.markDirty();
						}
						
					}
					else // PROVIDE PLAYER TO SELL AIR
					{
						playerIn.sendMessage(new TextComponentString(I18n.format("title.sellAir")));	
					}
				}
				else // IT MEANS THAT PLAYER HAS NOT SELECTED A COST
				{
					playerIn.sendMessage(new TextComponentString(I18n.format("title.noCost")));
				}
			}
		}
		
	}
	
	@Override
	public boolean doesGuiPauseGame() 
	{
		return false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		fontRenderer.drawString(new TextComponentTranslation(I18n.format("title.block_seller")).getFormattedText(), 8, 5, Color.DARK_GRAY.getRGB());
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