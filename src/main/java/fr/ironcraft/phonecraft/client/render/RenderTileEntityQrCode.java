package fr.ironcraft.phonecraft.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.common.tileentities.TileEntityQrCode;
import fr.ironcraft.phonecraft.utils.QrCodeThread;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @authors Dermenslof, DrenBx
 */
public class RenderTileEntityQrCode extends TileEntitySpecialRenderer
{
	private static ResourceLocation texture = new ResourceLocation("phonecraft:textures/gui/icons/unknow.png");

	public void renderTileEntityAt(TileEntityQrCode par1TileEntityCR, double par2, double par4, double par6, float par8)
	{
		int var9;
		if (!par1TileEntityCR.hasWorldObj())
			var9 = 0;
		else
			var9 = par1TileEntityCR.getBlockMetadata();
		if (par1TileEntityCR.texture != null)
		{
			if (par1TileEntityCR.textureID < 0 && !par1TileEntityCR.data.equals(""))
				new Thread(new QrCodeThread(par1TileEntityCR)).start();
			if(par1TileEntityCR.img != null && par1TileEntityCR.textureID < 0)
				par1TileEntityCR.textureID = ClientProxy.imageLoader.setupTexture(par1TileEntityCR.img);
			else if (par1TileEntityCR.textureID > 0)
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1TileEntityCR.textureID);
			else
				this.bindTexture(texture);
		}
		else
			this.bindTexture(texture);
		GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslatef((float)par2, (float)par4, (float)par6);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			short var11 = 0;
			if (var9 == 2)
			{
				GL11.glRotatef((float)90, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef((float)180, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-1, -1.99F, -1);
			}
			else if (var9 == 3)
			{
				GL11.glRotatef((float)90, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(1, 0.01F, -1);
			}
			else if (var9 == 4)
			{
				GL11.glRotatef((float)90, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef((float)90, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(1, -1.99F, -1);
			}
			else if (var9 == 5)
			{
				GL11.glRotatef((float)90, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef((float)-90, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-1,0.01F, -1);
			}
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-1.0, 0, -1.0, 0, 0);
			tessellator.addVertexWithUV(-1.0, 0, 1.0, 0, 1);
			tessellator.addVertexWithUV(1.0, 0, 1.0, 1, 1);
			tessellator.addVertexWithUV(1.0, 0, -1.0, 1, 0);
			tessellator.draw();
			GL11.glRotatef((float)-180, 0.0F, 0.0F, 1.0F);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-1.0, 0, -1.0, 0, 0);
			tessellator.addVertexWithUV(-1.0, 0, 1.0, 0, 1);
			tessellator.addVertexWithUV(1.0, 0, 1.0, 1, 1);
			tessellator.addVertexWithUV(1.0, 0, -1.0, 1, 0);
			tessellator.draw();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityAt((TileEntityQrCode)par1TileEntity, par2, par4, par6, par8);
	}
}
