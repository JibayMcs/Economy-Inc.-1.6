package fr.fifou.economy.blocks.tesr;

import org.lwjgl.opengl.GL11;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.models.ModelBills;
import fr.fifou.economy.blocks.models.ModelVault;
import fr.fifou.economy.blocks.models.ModelVault_PAD;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockBills;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityBillsSpecialRenderer extends TileEntitySpecialRenderer 
{

	private static ModelBills modelBlock = new ModelBills();
	public static ResourceLocation texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_0.png");

	
	public void renderTileEntityBillsAt(TileEntityBlockBills tile, double posX, double posY, double posZ, float partialTicks, int damageCount, float alpha) 
	{
		GlStateManager.pushMatrix();
		if(tile != null)
		{
			switch (tile.getDirection()) {
			case 0:
				GlStateManager.translate(posX + 0.125F, posY + 0.53F, posZ + 0.25F);
				break;
			case 1:
				GlStateManager.translate(posX + 0.75F, posY + 0.53F, posZ + 0.125F);
				break;
			case 2:
				GlStateManager.translate(posX + 0.875F, posY + 0.53F, posZ + 0.75F);
				break;
			case 3:
				GlStateManager.translate(posX + 0.25F, posY + 0.53F, posZ + 0.875F);
				break;
			default:
				break;
			}
		}
		GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90F * tile.getDirection(), 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.333F, 0.333F, 0.333F);
		checkBillRef(tile);
		this.bindTexture(texture);
		modelBlock.renderAll(tile);
		GlStateManager.popMatrix();
	}
 
	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) 
	{
		this.renderTileEntityBillsAt(((TileEntityBlockBills) te), x, y, z, partialTicks, destroyStage, alpha);
	}
	
	public void checkBillRef(TileEntityBlockBills tile)
	{
		
		switch (tile.getBill()) {
		case "item.item_oneb":
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_1.png");
			break;
		case "item.item_fiveb":
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_5.png");
			break;
		case "item.item_tenb":
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_10.png");
			break;
		case "item.item_twentyb":
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_20.png");
			break;
		case "item.item_fiftybe":
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_50.png");
			break;
		case "item.item_hundreedb":
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_100.png");
			break;
		case "item.item_twohundreedb":
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_200.png");
			break;
		case "item.item_fivehundreedb":
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_bills_500.png");
			break;
		default:
			break;
		}
	}
}