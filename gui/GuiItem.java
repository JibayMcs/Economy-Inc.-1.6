package fr.fifou.economy.gui;

import java.io.IOException;
import java.util.Objects;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.packets.PacketCardChange;
import fr.fifou.economy.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSelectString;

public class GuiItem extends GuiScreen
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomy.MODID ,"textures/gui/screen/gui_item.png");
	private GuiButtonExt oneB;
	private GuiButtonExt fiveB;
	private GuiButtonExt TenB;
	private GuiButtonExt TwentyB;
	private GuiButtonExt FiftyB;
	private GuiButtonExt HundreedB;
	private GuiButtonExt TwoHundreedB;
	private GuiButtonExt FiveHundreedB;
	
	private GuiButtonExt oneBMinus;
	private GuiButtonExt fiveBMinus;
	private GuiButtonExt TenBMinus;
	private GuiButtonExt TwentyBMinus;
	private GuiButtonExt FiftyBMinus;
	private GuiButtonExt HundreedBMinus;
	private GuiButtonExt TwoHundreedBMinus;
	private GuiButtonExt FiveHundreedBMinus;
	
	private GuiButtonExt upgrade;
	
	private double funds_s;
	private String owner = Minecraft.getMinecraft().player.getName();
	
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	
	
	@Override
	public void initGui()
	{
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        
      //On ouvre la matrice de rendu
        GlStateManager.pushMatrix();
        //On place le modèle 3D aux position X,Y,Z du blocs
        GlStateManager.translate(this.width / 2, this.height / 2, 0);
        //On retourne le bloc pour le mettre à l'endroit
        GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
        //On ferme la matrice de rendu
        GlStateManager.popMatrix();
        
        this.buttonList.add(this.oneB = new GuiButtonExt(0, width / 2 + 90, height / 2 - 55, 30, 20, TextFormatting.GREEN + "+1"));
		this.buttonList.add(this.fiveB = new GuiButtonExt(1, width / 2 - 120, height / 2 , 30, 20, TextFormatting.GREEN + "+5"));
		this.buttonList.add(this.TenB = new GuiButtonExt(2, width / 2 - 85, height / 2 , 30, 20, TextFormatting.GREEN + "+10"));
		this.buttonList.add(this.TwentyB = new GuiButtonExt(3, width /2 - 50, height / 2 , 30, 20, TextFormatting.GREEN + "+20"));
		this.buttonList.add(this.FiftyB = new GuiButtonExt(4, width / 2 - 15, height / 2 , 30, 20, TextFormatting.GREEN + "+50"));
		this.buttonList.add(this.HundreedB = new GuiButtonExt(5, width / 2 + 20, height / 2 , 30, 20, TextFormatting.GREEN + "+100"));
		this.buttonList.add(this.TwoHundreedB = new GuiButtonExt(6, width / 2 + 55, height / 2 , 30, 20, TextFormatting.GREEN + "+200"));
		this.buttonList.add(this.FiveHundreedB = new GuiButtonExt(7, width / 2 + 90, height / 2 , 30, 20, TextFormatting.GREEN + "+500"));
		
		this.buttonList.add(this.oneBMinus = new GuiButtonExt(8, width / 2 + 90, height / 2 - 25, 30, 20,TextFormatting.RED +  "-1"));
		this.buttonList.add(this.fiveBMinus = new GuiButtonExt(9, width / 2 - 120, height / 2 + 30, 30, 20,TextFormatting.RED +  "-5"));
		this.buttonList.add(this.TenBMinus = new GuiButtonExt(10, width / 2 - 85, height / 2 + 30, 30, 20, TextFormatting.RED + "-10"));
		this.buttonList.add(this.TwentyBMinus = new GuiButtonExt(11, width /2 - 50, height / 2 + 30, 30, 20, TextFormatting.RED + "-20"));
		this.buttonList.add(this.FiftyBMinus = new GuiButtonExt(12, width / 2 - 15, height / 2 + 30, 30, 20, TextFormatting.RED + "-50"));
		this.buttonList.add(this.HundreedBMinus = new GuiButtonExt(13, width / 2 + 20, height / 2 + 30, 30, 20, TextFormatting.RED + "-100"));
		this.buttonList.add(this.TwoHundreedBMinus = new GuiButtonExt(14, width / 2 + 55, height / 2 + 30, 30, 20, TextFormatting.RED + "-200"));
		this.buttonList.add(this.FiveHundreedBMinus = new GuiButtonExt(15, width / 2 + 90, height / 2 + 30, 30, 20, TextFormatting.RED + "-500"));
	}
	
	@Override
	public void updateScreen()
	{
		
		super.updateScreen();
		EntityPlayer playerIn =  mc.player;
		ItemStack stack = mc.player.getHeldItemMainhand();
		this.funds_s = playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney();
	}

	
	@Override
	  public boolean doesGuiPauseGame()
	    {
	        return false;
	    }
	
	
	protected void actionPerformed(GuiButton button) throws IOException
	{
		EntityPlayer player = mc.player;
		World worldIn = player.world;
		double funds;
		
		//TAKE BACK FUNDS IN ACCOUNT TO ADD IT IN INVENTORY
		if(button == this.oneB)
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(-1));
		}
		if (button == this.fiveB) 
		{	
			PacketsRegistery.network.sendToServer(new PacketCardChange(-5));
		}
		if (button == this.TenB) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(-10));
		}
		if (button == this.TwentyB) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(-20));

		}
		if (button == this.FiftyB) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(-50));
		}
		
		if (button == this.HundreedB) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(-100));
		}
		
		if (button == this.TwoHundreedB) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(-200));
		}
		
		if (button == this.FiveHundreedB) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(-500));
		}
		
		// MINUS
		if(button == this.oneBMinus)
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(1));
		}
		if (button == this.fiveBMinus) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(5));
		}
		if (button == this.TenBMinus) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(10));
		}
		
		if (button == this.TwentyBMinus) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(20));
		}
		if (button == this.FiftyBMinus) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(50));
		}
		if (button == this.HundreedBMinus) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(100));
		}
		if (button == this.TwoHundreedBMinus) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(200));
		}
		if (button == this.FiveHundreedBMinus) 
		{
			PacketsRegistery.network.sendToServer(new PacketCardChange(500));
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
        drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - mouseX, (float)(j + 75 - 50) - mouseY, this.mc.player);
		this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.ownerCard")+ ": " + owner, (this.width / 2) - 75, (this.height / 2)- 55, 0x000);
		this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.fundsCard")+ ": " + String.valueOf(funds_s), (this.width / 2) - 75, (this.height / 2)- 45, 0x000);

		super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Draws an entity on the screen looking forward.
     */
    public static void drawEntityOnScreen(int posX, int posY, float scale, float mouseX, float mouseY, EntityLivingBase ent)
    {   
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX - 25, (float)posY - 22.5, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.scale(0.75F, 0.75F, 0.75F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
        float f5 = -25.0F;
        ent.renderYawOffset = f5;
        ent.rotationYaw = f5;
        ent.rotationPitch = 0.0F;
        ent.rotationYawHead = 0.0F;
        ent.prevRotationYawHead = 0.0F;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
