package fr.fifou.economy.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.packets.PacketCardChange;
import fr.fifou.economy.packets.PacketListNBT;
import fr.fifou.economy.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiVaultSettings extends GuiScreen
{
	private static final ResourceLocation background = new ResourceLocation(ModEconomy.MODID ,"textures/gui/screen/gui_item.png");
	protected int xSize = 256;
	protected int ySize = 124;
	protected int guiLeft;
	protected int guiTop;
	private GuiTextField addPlayersToList;
	private TileEntityBlockVault tile;
    protected List<GuiButton> properButtonList = Lists.<GuiButton>newArrayList();
    protected List<GuiLabel> properLabelList = Lists.<GuiLabel>newArrayList();
    protected GuiButton properSelectedButton;

	
	public GuiVaultSettings(TileEntityBlockVault tile) 
	{
		this.tile = tile;
	}
	
	@Override
	public void initGui()
	{ 
        int k = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        Keyboard.enableRepeatEvents(true);
        this.addPlayersToList = new GuiTextField(0, this.fontRenderer, k + 50, j + 110, 155, 12);
        this.addPlayersToList.setMaxStringLength(35);
        this.addPlayersToList.setEnabled(false);
        this.addPlayersToList.setText("Add other players.");    

	}
	
	@Override
	public void updateScreen() 
	{
    	this.properButtonList.clear();
        for(int i = 0; i < tile.getOthers().size(); i++)
        {
        	this.properButtonList.add(new GuiButtonExt(i, (width / 2) + 35, ((height /2) - 55) + i * 20, 40, 13, TextFormatting.DARK_RED + "✖"));
        }
		if(tile.getMax() == 5)
		{
			this.addPlayersToList.setEnabled(false);
			this.addPlayersToList.setText("Max players allowed reached");
		}
	}
		
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
		 // added
        this.mc.getTextureManager().bindTexture(background);
        int k = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, j, 0, 0, this.xSize, this.ySize);
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    GlStateManager.disableLighting();
	    GlStateManager.disableBlend();
	    if(!Minecraft.getMinecraft().isSingleplayer())
	    {
		    if(tile.getOwnerS().equals(Minecraft.getMinecraft().player.getUniqueID().toString()))
		    {
		    	this.addPlayersToList.drawTextBox();
		    }
	    }
        for(int i = 0; i < tile.getOthers().size(); i++)
        {
        	this.fontRenderer.drawString(tile.getOthers().get(i).toString(), (width / 2) - 70, ((height /2) - 52) + i * 20, 0x00);
        }
        for (int l = 0; l < this.properButtonList.size(); ++l)
        {
            ((GuiButton)this.properButtonList.get(l)).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
        for (int m = 0; m < this.properLabelList.size(); ++m)
        {
            ((GuiLabel)this.properLabelList.get(m)).drawLabel(this.mc, mouseX, mouseY);
        }
	}
	
	@Override
	public void onGuiClosed() 
	{
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
		 if(keyCode == 1)
	        {
	            super.keyTyped(typedChar, keyCode);
	        }
	        else if(keyCode == 28 || keyCode == 156)
	        {	
	        	if(tile.getMax() < 5)
	        	{
			        this.addPlayerToTileEntity();
			        this.addPlayersToList.setText("");			        
	        	}
	        	else
	        	{
	        		this.addPlayersToList.setText("Max players allowed reached ");
	        	}
        	}
        	else
        	{
	            this.addPlayersToList.textboxKeyTyped(typedChar, keyCode);
        	}
    }
    
    private void addPlayerToTileEntity()
    {
        String s = this.addPlayersToList.getText();
        EntityPlayer playerAdd = Minecraft.getMinecraft().world.getPlayerEntityByName(s);
        if(playerAdd != null)
        {
	        String playerAddUUID = playerAdd.getName();
	        PacketsRegistery.network.sendToServer(new PacketListNBT(playerAddUUID, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), false, "add", 0)); 
        }
    }
    
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{
		switch (this.properSelectedButton.id) {
		case 0:
			PacketsRegistery.network.sendToServer(new PacketListNBT("", tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), false, "remove", 0));
	        break;
		case 1:
			PacketsRegistery.network.sendToServer(new PacketListNBT("", tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), false, "remove", 1));
	        break;
		case 2:
			PacketsRegistery.network.sendToServer(new PacketListNBT("", tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), false, "remove", 2));
	        break;
		case 3:
			PacketsRegistery.network.sendToServer(new PacketListNBT("", tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), false, "remove", 3));
	        break;
		case 4:
			PacketsRegistery.network.sendToServer(new PacketListNBT("", tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), false, "remove", 4));
	        break;
		}
	}
	
	@Override
	  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	    {
	        super.mouseClicked(mouseX, mouseY, mouseButton);
	        if(this.addPlayersToList.mouseClicked(mouseX, mouseY, mouseButton))
	        {
	        	this.addPlayersToList.setText("");
	        	this.addPlayersToList.setEnabled(true);
	        }   
	        if (mouseButton == 0)
	        {
	            for (int i = 0; i < this.properButtonList.size(); ++i)
	            {
	                GuiButton guibutton = this.properButtonList.get(i);
	                
	                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
	                {
	                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.properButtonList);
	                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
	                        break;
	                    guibutton = event.getButton();
	                    this.properSelectedButton = guibutton;
	                    guibutton.playPressSound(this.mc.getSoundHandler());
	                    this.actionPerformed(guibutton);
	                    if (this.equals(this.mc.currentScreen))
	                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), this.properButtonList));
	                }
	            }
	        }
	    }
	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (this.properSelectedButton != null && state == 0)
        {
            this.properSelectedButton.mouseReleased(mouseX, mouseY);
            this.properSelectedButton = null;
        }
    }
    
	@Override
	  public boolean doesGuiPauseGame()
	    {
	        return false;
	    }

}