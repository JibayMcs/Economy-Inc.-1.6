package fr.fifou.economy.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.containers.ContainerSeller;
import fr.fifou.economy.items.ItemCreditcard;
import fr.fifou.economy.packets.PacketCardChange;
import fr.fifou.economy.packets.PacketCardChangeAdmin;
import fr.fifou.economy.packets.PacketCardChangeSeller;
import fr.fifou.economy.packets.PacketSellerCreated;
import fr.fifou.economy.packets.PacketSellerFundsTotal;
import fr.fifou.economy.packets.PacketsRegistery;

public class GuiSellerBuy extends GuiScreen
{
	private TileEntityBlockSeller tile;
	
	public GuiSellerBuy(TileEntityBlockSeller tile) 
	{
		this.tile = tile;
	}
	
	private static final ResourceLocation background = new ResourceLocation(ModEconomy.MODID ,"textures/gui/screen/gui_item.png");
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	
	private GuiButtonExt slot1;
	private GuiButtonExt takeFunds;
	private String owner = "";
	private String itemName = "";
	private double cost;
	private int amount;
	private double fundsTotalRecovery;
	private int sizeInventoryCheckCard;
	
	@Override
	public void updateScreen() 
	{
		super.updateScreen();
		amount = tile.getAmount();
		fundsTotalRecovery = tile.getFundsTotal();	
		tile.setFundsTotal(fundsTotalRecovery);
		tile.setAmount(amount);
		tile.markDirty();
	}
	
	@Override
	public void initGui() 
	{
		this.guiLeft = (this.width - this.xSize) / 2;
	    this.guiTop = (this.height - this.ySize) / 2;
		World worldIn = mc.world;
		EntityPlayer player = mc.player;
		if(tile != null)
		{
			this.owner = tile.getOwnerName();
			this.itemName = tile.getItem();
			this.cost = tile.getCost();
			this.buttonList.add(this.slot1 = new GuiButtonExt(1, width / 2 - 50, height / 2 + 27, 100, 20, I18n.format("title.buy")));
			String sellerOwner = tile.getOwner();
			String worldPlayer = player.getUniqueID().toString();
			if(sellerOwner.equals(worldPlayer))
			{
				this.buttonList.add(this.takeFunds = new GuiButtonExt(2, width / 2 + 20, height / 2 - 74, 100, 13, I18n.format("title.recover")));
			}
			
		}
	}
	
	
	@Override
	public boolean doesGuiPauseGame() 
	{
		return false;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{
		super.actionPerformed(button);
		
		World worldIn = mc.world;
		EntityPlayer player = mc.player;
		if(tile != null) // WE CHECK IF TILE IS NOT NULL FOR AVOID CRASH
		{	
			if(button == this.slot1) //IF PLAYER BUY
			{
				if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getLinked())
				{
					if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= tile.getCost()) //IF FUNDS IN CARD ARE SUPERIOR OR EQUAL TO THE COST WE PASS
						{
								if(tile.getAmount() >= 1)
								{
									boolean admin = tile.getAdmin();
									if(admin == false)
									{
										double fundTotal = tile.getFundsTotal(); // WE GET THE TOTAL FUNDS
										tile.setFundsTotal(fundTotal + tile.getCost()); // CLIENT ADD TOTAL FUNDS + THE COST OF THE ITEM
										final int x = tile.getPos().getX(); // GET X COORDINATES
										final int y = tile.getPos().getY(); // GET Y COORDINATES
										final int z = tile.getPos().getZ(); // GET Z COORDINATES
										final double cost = tile.getCost(); // GET COST OF THE TILE ENTITY
										int amount = tile.getAmount(); // GET AMOUNT OF THE TILE ENTITY
										tile.setAmount(amount -1); // CLIENT SET AMOUNT MINUS ONE EACH TIME HE BUY
										PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal((fundTotal + tile.getCost()), x,y,z, amount, false)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
										PacketsRegistery.network.sendToServer(new PacketCardChangeSeller(cost)); // SENDING ANOTHER PACKET TO UPDATE CLIENT'S CARD IN SERVER KNOWLEDGE
										tile.markDirty();
									}
									else if(admin == true)
									{
										double fundTotal = tile.getFundsTotal(); // WE GET THE TOTAL FUNDS
										tile.setFundsTotal(fundTotal + tile.getCost()); // CLIENT ADD TOTAL FUNDS + THE COST OF THE ITEM
										final int x = tile.getPos().getX(); // GET X COORDINATES
										final int y = tile.getPos().getY(); // GET Y COORDINATES
										final int z = tile.getPos().getZ(); // GET Z COORDINATES
										final double cost = tile.getCost(); // GET COST OF THE TILE ENTITY
										int amount = tile.getAmount(); // GET AMOUNT OF THE TILE ENTITY
										tile.setAmount(amount); // CLIENT SET AMOUNT MINUS ONE EACH TIME HE BUY
										PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal((fundTotal + tile.getCost()), x,y,z, amount, false)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
										PacketsRegistery.network.sendToServer(new PacketCardChangeSeller(cost)); // SENDING ANOTHER PACKET TO UPDATE CLIENT'S CARD IN SERVER KNOWLEDGE
										tile.markDirty();
									}
								}
						}
						else // ELSE WE SEND HIM A MESSAGE TO TELL THEY DON'T HAVE ENOUGH FUNDS
						{
							player.sendMessage(new TextComponentString(I18n.format("title.noEnoughFunds")));
						}	
				}
				else // ELSE HE DON'T HAVE THE UPGRADE AND WE CHECK FOR THE CARD
				{
					for(int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if(player.inventory.getStackInSlot(i).getItem() instanceof ItemCreditcard)
						{
							ItemStack creditCard = player.inventory.getStackInSlot(i);
							if(player.getUniqueID().toString().equals(creditCard.getTagCompound().getString("OwnerUUID")))
							{
								if(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() >= tile.getCost())
								{
									if(tile.getAmount() >= 1)
									{
										boolean admin = tile.getAdmin();
										if(admin == false)
										{
											double fundTotal = tile.getFundsTotal(); // WE GET THE TOTAL FUNDS
											tile.setFundsTotal(fundTotal + tile.getCost()); // CLIENT ADD TOTAL FUNDS + THE COST OF THE ITEM
											final int x = tile.getPos().getX(); // GET X COORDINATES
											final int y = tile.getPos().getY(); // GET Y COORDINATES
											final int z = tile.getPos().getZ(); // GET Z COORDINATES
											final double cost = tile.getCost(); // GET COST OF THE TILE ENTITY
											int amount = tile.getAmount(); // GET AMOUNT OF THE TILE ENTITY
											tile.setAmount(amount -1); // CLIENT SET AMOUNT MINUS ONE EACH TIME HE BUY
											PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal((fundTotal + tile.getCost()), x,y,z, amount, false)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
											PacketsRegistery.network.sendToServer(new PacketCardChangeSeller(cost)); // SENDING ANOTHER PACKET TO UPDATE CLIENT'S CARD IN SERVER KNOWLEDGE
											tile.markDirty();
										}
										else if(admin == true)
										{
											double fundTotal = tile.getFundsTotal(); // WE GET THE TOTAL FUNDS
											tile.setFundsTotal(fundTotal + tile.getCost()); // CLIENT ADD TOTAL FUNDS + THE COST OF THE ITEM
											final int x = tile.getPos().getX(); // GET X COORDINATES
											final int y = tile.getPos().getY(); // GET Y COORDINATES
											final int z = tile.getPos().getZ(); // GET Z COORDINATES
											final double cost = tile.getCost(); // GET COST OF THE TILE ENTITY
											int amount = tile.getAmount(); // GET AMOUNT OF THE TILE ENTITY
											tile.setAmount(amount); // CLIENT SET AMOUNT MINUS ONE EACH TIME HE BUY
											PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal((fundTotal + tile.getCost()), x,y,z, amount, false)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
											PacketsRegistery.network.sendToServer(new PacketCardChangeSeller(cost)); // SENDING ANOTHER PACKET TO UPDATE CLIENT'S CARD IN SERVER KNOWLEDGE
											tile.markDirty();
										}
									}
								}
								else
								{
									player.sendMessage(new TextComponentString(I18n.format("title.noEnoughFunds")));
								}
							}
							else
							{
								player.sendMessage(new TextComponentString(I18n.format("title.noSameOwner")));
							}
						}
						else if(!(player.inventory.getStackInSlot(i).getItem() instanceof ItemCreditcard))
						{
							this.sizeInventoryCheckCard = this.sizeInventoryCheckCard + 1;
							if(this.sizeInventoryCheckCard == player.inventory.getSizeInventory())
							{
								if(!(tile.getAmount() == 0))
								{
									player.sendMessage(new TextComponentString(I18n.format("title.noCardFoundAndNoLink")));
								}
								else if(tile.getAmount() == 0)
								{
									player.sendMessage(new TextComponentString(I18n.format("title.noMoreQuantity")));
								}
								this.sizeInventoryCheckCard = 0;
							}
							if(i == player.inventory.getSizeInventory())
							{
								this.sizeInventoryCheckCard = 0;
							}
						}
					}
				}
			}
			else if(button == this.takeFunds)
			{
				final int x = tile.getPos().getX(); // GET X COORDINATES
				final int y = tile.getPos().getY(); // GET Y COORDINATES
				final int z = tile.getPos().getZ(); // GET Z COORDINATES
				tile.setFundsTotal(0);
				tile.markDirty();
				PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal(fundsTotalRecovery, x,y,z, amount, true)); //SENDING PACKET TO LET SERVER KNOW CHANGES WITH TOTAL FUNDS, COORDINATES AND AMOUNT
			}
					
		}
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
			this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.seller") + owner, (this.width / 2 - 120), (this.height / 2 - 50), 0x000);
			this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.item") + itemName, (this.width / 2 - 120), (this.height / 2 - 40), 0x000);
			this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.cost") + cost, (this.width / 2 - 120), (this.height / 2 - 30), 0x000);
			this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.amount") + amount, (this.width / 2 - 120), (this.height / 2 - 20), 0x000);
			this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.fundsToRecover") + fundsTotalRecovery, (this.width / 2 - 120), (this.height / 2 - 10), 0x000);
			super.drawScreen(mouseX, mouseY, partialTicks);
	        drawImageInGui();

	    }

		public void drawImageInGui() 
		{
	        int i = this.guiLeft;
	        int j = this.guiTop;
	        GL11.glPushMatrix();
			GlStateManager.enableRescaleNormal();
		    RenderHelper.enableGUIStandardItemLighting();
		    GL11.glScaled(2, 2, 2);
		    ItemStack stack = new ItemStack(Blocks.BARRIER,1 , 0);
		    if(!(tile.getAmount() == 0))
		    {
			    stack = new ItemStack(tile.getStackInSlot(0).getItem(),1 , tile.getStackInSlot(0).getMetadata());
		    }
		    this.itemRender.renderItemIntoGUI(stack, (i / 2) + 105 , (j /2) + 5);
		    RenderHelper.disableStandardItemLighting();
		    GlStateManager.disableRescaleNormal();
		    GL11.glPopMatrix();   
		}
}