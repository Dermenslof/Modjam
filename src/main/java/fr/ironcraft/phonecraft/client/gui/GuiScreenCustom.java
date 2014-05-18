package fr.ironcraft.phonecraft.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.utils.CustomFont;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

/**
 * @author Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class GuiScreenCustom extends GuiScreen
{
	protected int phoneSize;
	protected int shift = 0;
	protected float transparency;
	
	private CustomFont font;
	
	/**
	 * @return Screen position coordinate (0) for X
	 */
	public int getScreenPosX()
	{
		return this.width - 106 + this.shift;
	}
	
	/**
	 * @return Screen position coordinate (0) for Y
	 */
	public int getScreenPosY()
	{
		return this.height - 183;
	}
	
	/**
	 * @return Screen Width
	 */
	public int getScreenWidth()
	{
		return (this.width - 14) - getScreenPosX();
	}
	
	/**
	 * @return Screen Height
	 */
	public int getScreenHeight()
	{
		return (this.height - 29) - getScreenPosY();
	}
	
	/**
	 * Draw the String in PhoneScreen
	 * 
	 * @param Text
	 * @param Position X
	 * @param Position Y
	 * @param Color
	 */
	protected void drawString(String par1Text, int par2PosX, int par3PosY, int par4Color)
	{
		this.font.drawString(this, par1Text, par2PosX, par3PosY, par4Color, this.transparency);
	}
	
	/**
	 * Draw the String in PhoneScreen
	 * 
	 * @param Text
	 * @param Position X
	 * @param Position Y
	 * @param Colo
	 * @param Transparency variation
	 */
	protected void drawString(String par1Text, int par2PosX, int par3PosY, int par4Color, float par5Transparency)
	{
		this.font.drawString(this, par1Text, par2PosX, par3PosY, par4Color, this.transparency + par5Transparency);
	}
	
	/**
	 * Draw rectangle with gradiant effect
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Color 2
	 */
	protected void drawAbsoluteGradientRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Color, int par6Color, float par7TransparencyVar)
	{
		this.drawGradientRect_(par1PosX, par2PosY, par3PosX2, par4PosY2, par5Color, par6Color, par7TransparencyVar);
	}
	
	/**
	 * Draw rectangle with gradiant effect in PhoneScreen
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Color 2
	 * @param Transparency variation
	 */
	protected void drawGradientRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Color, int par6Color, float par7TransparencyVar)
	{
		this.drawGradientRect_(getScreenPosX() + par1PosX + this.shift, getScreenPosY() + par2PosY, getScreenPosX() + par3PosX2 + this.shift, getScreenPosY() + par4PosY2, par5Color, par6Color, this.transparency + par7TransparencyVar);
	}
	
	private void drawGradientRect_(int par1, int par2, int par3, int par4, int par5, int par6, float trans)
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

	/**
	 * Draw rectangle with gradiant effect and round corners in PhoneScreen
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Color2
	 * @param Transparency variation
	 */
	protected void drawRoundedGradientRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Color, int par6Color, int par7Radius, float par8TransparencyVar)
	{
		this.drawRoundedGradientRect_(getScreenPosX() + par1PosX + this.shift, getScreenPosY() + par2PosY, getScreenPosX() + par3PosX2 + this.shift, getScreenPosY() + par4PosY2, par5Color, par6Color, par7Radius, this.transparency + par8TransparencyVar);
	}
	
	/**
	 * Draw rectangle with gradiant effect and round corners
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Color2
	 * @param Transparency
	 */
	protected void drawAbsoluteRoundedGradientRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Color, int par6Color, int par7Radius, float par8Transparency)
	{
		this.drawRoundedGradientRect_(par1PosX, par2PosY, par3PosX2, par4PosY2, par5Color, par6Color, par7Radius, par8Transparency);
	}
	
	private void drawRoundedGradientRect_(int x, int y, int x1, int y1, int color, int color2, int radius, float trans)
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
		drawAbsoluteRect(newX, newY, newX1, newY1, color, trans);
		drawAbsoluteRect(x, newY, newX, newY1, color, trans);
		drawAbsoluteRect(newX1, newY, x1, newY1, color, trans);
		drawAbsoluteRect(newX, y, newX1, newY, color2, trans);
		drawAbsoluteRect(newX, newY1, newX1, y1, color2, trans);
		drawAbsoluteQuarterCircle(newX+1,newY,radius,0,color, trans);
		drawAbsoluteQuarterCircle(newX1,newY,radius,1,color, trans);
		drawAbsoluteQuarterCircle(newX+1,newY1,radius,2,color2, trans);
		drawAbsoluteQuarterCircle(newX1,newY1,radius,3,color2, trans);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	
	/**
	 * Draw rectangle in PhoneScreen
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Color2
	 * @param Transparency variation
	 */
	protected void drawRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Color, float par6TransparencyVar)
	{
		this.drawRect_(getScreenPosX() + par1PosX + this.shift, getScreenPosY() + par2PosY, getScreenPosX() + par3PosX2 + this.shift, getScreenPosY() + par4PosY2, par5Color, this.transparency + par6TransparencyVar);
	}
	
	/**
	 * Draw rectangle
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Color2
	 * @param Transparency
	 */
	protected void drawAbsoluteRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Color, float par6TransparencyVar)
	{
		this.drawRect_(par1PosX, par2PosY, par3PosX2, par4PosY2, par5Color, par6TransparencyVar);
	}
	
	private void drawRect_(int par0, int par1, int par2, int par3, int par4, float trans)
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

	/**
	 * Draw rectangle with round corners in PhoneScreen
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Transparency
	 */
	public void drawRoundedRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Radius, int par6Color, float  par7Transparency)
	{
		this.drawRoundedRect_(getScreenPosX() + par1PosX + this.shift, getScreenPosY() + par2PosY, getScreenPosX() + par3PosX2 + this.shift, getScreenPosY() + par4PosY2, par5Radius, par6Color, this.transparency + par7Transparency);
	}
	
	/**
	 * Draw rectangle with round corners in PhoneScreen
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Transparency
	 */
	public void drawAbsoluteRoundedRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Radius, int par6Color, float  par7Transparency)
	{
		drawRoundedRect_(par1PosX, par2PosY, par3PosX2, par4PosY2, par5Radius, par6Color, par7Transparency);
	}
	
	private void drawRoundedRect_(int x, int y, int x1, int y1, int radius, int color, float trans)
	{
		int newX = Math.abs(x+radius);
		int newY = Math.abs(y+radius);
		int newX1 = Math.abs(x1-radius);
		int newY1 = Math.abs(y1-radius);
		drawAbsoluteRect(newX, newY, newX1, newY1, color, trans);
		drawAbsoluteRect(x, newY, newX, newY1, color, trans);
		drawAbsoluteRect(newX1, newY, x1, newY1, color, trans);
		drawAbsoluteRect(newX, y, newX1, newY, color, trans);
		drawAbsoluteRect(newX, newY1, newX1, y1, color, trans);
		drawAbsoluteQuarterCircle(newX,newY,radius,0,color, trans);
		drawAbsoluteQuarterCircle(newX1,newY,radius,1,color, trans);
		drawAbsoluteQuarterCircle(newX,newY1,radius,2,color, trans);
		drawAbsoluteQuarterCircle(newX1,newY1,radius,3,color, trans);
	}
	
	
	/**
	 * Draw a quarter of circle in PhoneScreen
	 * 
	 * @param Position X
	 * @param Position Y
	 * @param Radius
	 * @param Quarters (0, 1, 2, 3)
	 * @param Color
	 * @param Transparency variation
	 */
	protected void drawQuarterCircle(int par1X, int par2Y, int par3Radius, int par4Mode, int par5Color, float  par6TransparencyVar)
	{
		this.drawQuarterCircle_(getScreenPosX() + par1X + this.shift, getScreenPosY() + par2Y, par3Radius, par4Mode, par5Color, this.transparency + par6TransparencyVar);
	}
	
	/**
	 * Draw a quarter of circle in PhoneScreen
	 * 
	 * @param Position X
	 * @param Position Y
	 * @param Radius
	 * @param Quarters (0, 1, 2, 3)
	 * @param Color
	 * @param Transparency
	 */
	protected void drawAbsoluteQuarterCircle(int par1X, int par2Y, int par3Radius, int par4Mode, int par5Color, float  par6Transparency)
	{
		this.drawQuarterCircle_(par1X, par2Y, par3Radius, par4Mode, par5Color, par6Transparency);
	}
	
	private void drawQuarterCircle_(int x, int y, int radius, int mode, int color, float trans)
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
	
	/**
	 * Draw rectangle with gradiant effect in Phonecraft
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Color2
	 * @param Transparency
	 */
	public void drawGradientRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Radius, int par6Color)
	{
		this.drawGradientRect_(getScreenPosX() + par1PosX + this.shift, getScreenPosY() + par2PosY, getScreenPosX() + par3PosX2 + this.shift, getScreenPosY() + par4PosY2, par5Radius, par6Color);
	}
	
	/**
	 * Draw rectangle with gradiant effect
	 * 
	 * @param Position X1
	 * @param Position Y1
	 * @param Position X2
	 * @param Position Y2
	 * @param Color
	 * @param Color2
	 * @param Transparency
	 */
	public void drawAbsoluteGradientRect(int par1PosX, int par2PosY, int par3PosX2, int par4PosY2, int par5Radius, int par6Color)
	{
		this.drawGradientRect_(par1PosX, par2PosY, par3PosX2, par4PosY2, par5Radius, par6Color);
	}
	
    private void drawGradientRect_(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = (float)(par5 >> 24 & 255) / 255.0F;
        float f1 = (float)(par5 >> 16 & 255) / 255.0F;
        float f2 = (float)(par5 >> 8 & 255) / 255.0F;
        float f3 = (float)(par5 & 255) / 255.0F;
        float f4 = (float)(par6 >> 24 & 255) / 255.0F;
        float f5 = (float)(par6 >> 16 & 255) / 255.0F;
        float f6 = (float)(par6 >> 8 & 255) / 255.0F;
        float f7 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)par3, (double)par2, (double)this.zLevel);
        tessellator.addVertex((double)par1, (double)par2, (double)this.zLevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)par1, (double)par4, (double)this.zLevel);
        tessellator.addVertex((double)par3, (double)par4, (double)this.zLevel);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
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
	
	/**
	 * get the font used by Phonecraft
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @return CustomFont
	 */
	public CustomFont getFont()
	{
		return this.font;
	}

	/**
	 * set the font used by Phonecraft
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @param new font
	 * @return font set
	 */
	public CustomFont setFont(CustomFont font)
	{
		return this.font = font;
	}
}
