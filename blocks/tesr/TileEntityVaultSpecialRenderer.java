package fr.fifou.economy.blocks.tesr;

import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;

import org.lwjgl.opengl.GL11;

import com.leviathanstudio.craftstudio.client.model.ModelCraftStudio;
import com.leviathanstudio.craftstudio.client.util.MathHelper;
import com.leviathanstudio.craftstudio.common.animation.simpleImpl.CSTileEntitySpecialRenderer;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.models.ModelVault;
import fr.fifou.economy.blocks.models.ModelVault_PAD;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityVaultSpecialRenderer<T extends TileEntityBlockVault> extends TileEntitySpecialRenderer<T>
{
	
    public static final FloatBuffer ROTATION_CORRECTOR;
    
    static {
        Matrix4f mat = new Matrix4f();
        mat.set(MathHelper.quatFromEuler(180, 0, 0));
        ROTATION_CORRECTOR = MathHelper.makeFloatBuffer(mat);
    }
    
	private static ModelCraftStudio modelBlock;
	public static ResourceLocation texture;
	
	public TileEntityVaultSpecialRenderer() 
	{
		this.modelBlock = new ModelCraftStudio(ModEconomy.MODID, "model_vault", 256, 128);
	}
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) 
	{
		checkTextures(te);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 0.750, z + 0.5);
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.multMatrix(CSTileEntitySpecialRenderer.ROTATION_CORRECTOR);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
		GlStateManager.rotate(-90F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(90F * te.getDirection(), 0.0F, 1.0F, 0.0F);
        bindTexture(this.texture);
        this.modelBlock.render(te);
        GlStateManager.popMatrix();
	}
	
 
	@Override
	public boolean isGlobalRenderer(T te) 
	{
		return true;
	}
	
	public void checkTextures(T te)
	{
		if(te.hasItems())
		{
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_vault.png");
		}
		else
		{
			texture = new ResourceLocation(ModEconomy.MODID, "textures/blocks_models/block_vault_withoutgold.png");	
		}	
	}
	
}
