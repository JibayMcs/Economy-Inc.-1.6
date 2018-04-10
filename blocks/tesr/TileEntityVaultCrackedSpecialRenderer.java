package fr.fifou.economy.blocks.tesr;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.models.ModelVault;
import fr.fifou.economy.blocks.models.ModelVault_PAD;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityVaultCrackedSpecialRenderer  extends TileEntitySpecialRenderer 
{

	private static ModelVault modelBlock = new ModelVault();
	private static ModelVault_PAD modelBlock_add = new ModelVault_PAD();
	public static ResourceLocation texture;
	
	public void renderTileEntityVaultCrackedAt(TileEntityBlockVaultCracked tile, double posX, double posY, double posZ, float partialTicks, int damageCount, float alpha) 
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(posX + 0.5F, posY + 0.75F, posZ + 0.5F);
		GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(-90F, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlStateManager.rotate(90F * tile.getDirection(), 0.0F, 1.0F, 0.0F);
		if(tile.hasItems())
		{
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_vault.png");
		}
		else
		{
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_vault_withoutgold.png");	
		}
		this.bindTexture(texture);
		modelBlock.renderAll();
		GlStateManager.scale(0.5, 0.5, 0.35);
		GlStateManager.translate(-0.93, 0.8, 0.8);
		modelBlock_add.renderAll();
		GlStateManager.popMatrix();
	}
 
	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) 
	{
		this.renderTileEntityVaultCrackedAt(((TileEntityBlockVaultCracked) te), x, y, z, partialTicks, destroyStage, alpha);
	}
	
}
