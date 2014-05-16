package fr.ironcraft.phonecraft.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
public class GuiScreenCustom extends GuiScreen {
	
	protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6, float trans)
	{
		float var7 = (float)(par5 >> 24 & 255) / 255.0F;
		float var8 = (float)(par5 >> 16 & 255) / 255.0F;
		float var9 = (float)(par5 >> 8 & 255) / 255.0F;
		float var10 = (float)(par5 & 255) / 255.0F;
		float var11 = (float)(par6 >> 24 & 255) / 255.0F;
		float var12 = (float)(par6 >> 16 & 255) / 255.0F;
		float var13 = (float)(par6 >> 8 & 255) / 255.0F;
		float var14 = (float)(par6 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, trans);
		var15.addVertex((double)par3, (double)par2, (double)this.zLevel);
		var15.addVertex((double)par1, (double)par2, (double)this.zLevel);
		var15.setColorRGBA_F(var12, var13, var14, trans);
		var15.addVertex((double)par1, (double)par4, (double)this.zLevel);
		var15.addVertex((double)par3, (double)par4, (double)this.zLevel);
		var15.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	protected void drawRoundedGradientRect(int x, int y, int x1, int y1, int color, int color2, int radius, float trans)
	{
		int newX = Math.abs(x+radius);
		int newY = Math.abs(y+radius);
		int newX1 = Math.abs(x1-radius);
		int newY1 = Math.abs(y1-radius);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		drawRect(newX, newY, newX1, newY1, color, trans);
		drawRect(x, newY, newX, newY1, color, trans);
		drawRect(newX1, newY, x1, newY1, color, trans);
		drawRect(newX, y, newX1, newY, color2, trans);
		drawRect(newX, newY1, newX1, y1, color2, trans);
		drawQuarterCircle(newX+1,newY,radius,0,color, trans);
		drawQuarterCircle(newX1,newY,radius,1,color, trans);
		drawQuarterCircle(newX+1,newY1,radius,2,color2, trans);
		drawQuarterCircle(newX1,newY1,radius,3,color2, trans);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	protected void drawRect(int par0, int par1, int par2, int par3, int par4, float trans)
	{
		int var5;
		if (par0 < par2)
		{
			var5 = par0;
			par0 = par2;
			par2 = var5;
		}
		if (par1 < par3)
		{
			var5 = par1;
			par1 = par3;
			par3 = var5;
		}
		float var6 = (float)(par4 >> 16 & 255) / 255.0F;
		float var7 = (float)(par4 >> 8 & 255) / 255.0F;
		float var8 = (float)(par4 & 255) / 255.0F;
		Tessellator var9 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(var6, var7, var8, trans);
		var9.startDrawingQuads();
		var9.addVertex((double)par0, (double)par3, 0.0D);
		var9.addVertex((double)par2, (double)par3, 0.0D);
		var9.addVertex((double)par2, (double)par1, 0.0D);
		var9.addVertex((double)par0, (double)par1, 0.0D);
		var9.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void drawRoundedRect(int x, int y, int x1, int y1, int radius, int color, float trans)
	{
		int newX = Math.abs(x+radius);
		int newY = Math.abs(y+radius);
		int newX1 = Math.abs(x1-radius);
		int newY1 = Math.abs(y1-radius);
		drawRect(newX, newY, newX1, newY1, color, trans);
		drawRect(x, newY, newX, newY1, color, trans);
		drawRect(newX1, newY, x1, newY1, color, trans);
		drawRect(newX, y, newX1, newY, color, trans);
		drawRect(newX, newY1, newX1, y1, color, trans);
		drawQuarterCircle(newX,newY,radius,0,color, trans);
		drawQuarterCircle(newX1,newY,radius,1,color, trans);
		drawQuarterCircle(newX,newY1,radius,2,color, trans);
		drawQuarterCircle(newX1,newY1,radius,3,color, trans);
	}

	public void drawQuarterCircle(int x, int y, int radius, int mode, int color, float trans)
	{
		float var6 = (float)(color >> 16 & 255) / 255.0F;
		float var7 = (float)(color >> 8 & 255) / 255.0F;
		float var8 = (float)(color & 255) / 255.0F;
		disableDefaults();
		GL11.glColor4d(var6, var7, var8, trans);
		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex2d(x, y);
		int start = 0;
		int xradius = radius;
		int yradius = radius;
		switch (mode)
		{
		case 0:
			xradius = yradius = -radius;
			break;
		case 1:
			start = 90;
			break;
		case 2:
			start = 90;
			xradius = yradius = -radius;
			break;
		case 3:
		}
		for (int i = start; i <= start + 90; i++)
		{
			double theta_radian = i * Math.PI / 180D;
			GL11.glVertex2d(x + Math.sin(theta_radian) * xradius, y + Math.cos(theta_radian) * yradius);
		}
		GL11.glEnd();
		enableDefaults();
	}
	
	public void setupOverlayRendering()
	{
		GL11.glClear(256);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, this.width, this.height, 0.0D, 1000D, 3000D);
		GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000F);
	}

	public void disableDefaults()
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public void enableDefaults()
	{
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
