package fr.ironcraft.phonecraft.client.gui;

import java.util.Date;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.KeyHandler;
import fr.ironcraft.phonecraft.utils.CustomFont;
import fr.ironcraft.phonecraft.utils.SoundUtils;
import fr.ironcraft.phonecraft.utils.TextureUtils;

public class GuiPhoneInGame  extends GuiScreen {

	protected static Minecraft mc;

	protected int shift = 0;
	protected boolean isFocused;
	protected boolean isOpen = true;
	protected boolean isAnimated = false;
	protected boolean isHome = false;
	protected boolean isFullscreen = false;
	protected boolean isCamera;
	protected float transparency;
	
	public static ResourceLocation texturePhone;
	protected CustomFont font;

	/* For mouse gestion */
	protected int clickX;
	protected int clickY;
	protected int releaseX;
	protected int releaseY;
	protected int mouseX;
	protected int mouseY;
	protected boolean isTactile;
	protected boolean mouseIsDrag;
	protected int screen;
	protected int bouton;
	protected int app;
	protected static int page;

	public GuiPhoneInGame (Minecraft par1Minecraft)
	{
		this(par1Minecraft, "phone");
	}

	public GuiPhoneInGame (Minecraft par1Minecraft, String phoneType)
	{
		this.texturePhone = new ResourceLocation(TextureUtils.getTextureNameForGui(phoneType));
		this.mc = par1Minecraft;
		mc.thePlayer.triggerAchievement(ClientProxy.achievements.openPhone);
	}

	@Override
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		this.font = ClientProxy.fonts.timenewRoman;
		this.isFocused = true;
	}
	
	@Override
	public void updateScreen()
	{
		this.setFocus();
		if (this.isOpen && !this.isAnimated)
		{
			if (this.transparency < 2.0F)
				this.transparency += 0.4F;
		}
		else if (!this.isOpen && !this.isAnimated && this.transparency > -0.5F)
			this.transparency -= 0.4F;
		if (!this.isOpen && !this.isAnimated && this.transparency <= -0.5F)
			this.mc.displayGuiScreen(new GuiPhoneAnimation(mc, true));
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		this.setMovement();
		this.mouseX = par1;
		this.mouseY = par2;
		drawBackground();
		this.drawBackground();
		this.drawRect(this.width - 106 + this.shift, this.height - 193, this.width - 14 + this.shift, this.height - 39, 0xFF000000);
		this.drawForground();
	}

	private void drawForground()
	{	
		//Wallpaper
		/*GL11.glPushMatrix();
		this.mc.renderEngine.bindTexture(Wallpaper);
		this.drawTexturedModalRect(this.width - 106 + this.shift, this.height - 193, 0, 0, 92, 154);
		GL11.glPopMatrix();*/

		this.drawRect(this.width - 106 + this.shift, this.height - 183, this.width - 14 + this.shift, this.height - 193, 0x22FFFFFF);
		//time (real)
		Date d = new Date();
		String h = d.getHours() < 10 ? "0" + String.valueOf(d.getHours()) : String.valueOf(d.getHours());
		String m =  d.getMinutes() < 10 ? "0" + String.valueOf(d.getMinutes()) : String.valueOf(d.getMinutes());
		
		GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 1F);
			GL11.glTranslatef((this.width - 28.5F + this.shift) / 0.5F, (this.height - 192.5F) / 0.5F, 0.5F);
			font.drawString(this, h, 0, 0, 0xd2d2d2, 0.3F);
			font.drawString(this, ":", 12, -1, 0xd2d2d2, 0.3F);
			font.drawString(this, m, 16, 0, 0xd2d2d2, 0.3F);
		GL11.glPopMatrix();
		
		//reseau
		GL11.glPushMatrix();
			GL11.glTranslatef(this.width - 105F + this.shift, this.height - 192, 0);
			GL11.glScalef(0.5F, 0.5F, 1);
			font.drawString(this, "IC", 0, 0, 0xd2d2d2, 0.3F);
			font.drawString(this, "telecom", 0, 0 + 1, 0xd2d2d2, 0.3F);
		GL11.glPopMatrix();
		this.drawRect(this.width-106+this.shift, this.height-183, this.width-14+this.shift, this.height-29, 0xff000000, this.screen == 4 ? 0 : this.screen == -1 ? this.transparency : 1f-this.transparency);
	}
	
	private void drawBackground()
	{
		//Texture
		GL11.glPushMatrix();
		this.mc.renderEngine.bindTexture(texturePhone);
		this.drawTexturedModalRect(this.width - 110 + this.shift, this.height - 210, 0, 0, 100, 200);
		GL11.glPopMatrix();
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (par2 == 1)
			this.mc.displayGuiScreen(new GuiIngameMenu());
//		else if (par2 == KeyHandler.key_PhoneFocus.getKeyCode())
//			this.isFocused = !this.isFocused;
		else if (par2 == KeyHandler.key_PhoneGUI.getKeyCode()) {
			this.isOpen = !this.isOpen;
		}
	}

	/**
	 * Change focus (inGameGUI / PhoneGUI)
	 */
	private void setFocus()
	{
//		if (!...controlFocus)
//			return;
		if(Keyboard.isKeyDown(KeyHandler.key_PhoneFocus.getKeyCode()))
		{
			if(!this.isFocused)
			{
				this.mc.inGameHasFocus = false;
				this.mc.mouseHelper.ungrabMouseCursor();
				this.isFocused = true;
			}
		}
		else
		{
			this.mc.inGameHasFocus = true;
			this.mc.mouseHelper.grabMouseCursor();
			this.isFocused = false;
		}
	}

	/**
	 * Move player focused into this GUI
	 */
	private void setMovement()
	{
		float dir = 180;
		float power = 0;

		boolean up = Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode());
		boolean down = Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode());
		boolean jump = Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
		boolean left = Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode());
		boolean right = Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode());

		if(jump && this.mc.thePlayer.onGround)
			this.mc.thePlayer.motionY = 0.4F;

		if (left == right) {
			dir = 180;
			power = up && !down ? -1 : (down && !up ? 1 : 0);
		}
		else {
			if (up == down) {
				dir = -90;
				power = left && !right ? 1 : (right && !left ? -1 : 0);
			}
			else {
				if (up && !down && left && !right) {
					dir = -45;
					power = 1;
				}
				else if (up && !down && !left && right) {
					dir = 45;
					power = 1;
				}
				if (!up && down && left && !right) {
					dir = 45;
					power = -1;
				}
				else if (!up && down && !left && right) {
					dir = -45;
					power = -1;
				}
			}
		}
		this.mc.thePlayer.motionZ = (double)(MathHelper.cos((this.mc.thePlayer.rotationYaw + dir) / 180.0F * (float)Math.PI) * power * 0.20F);
		this.mc.thePlayer.motionX = (double)(-MathHelper.sin((this.mc.thePlayer.rotationYaw + dir) / 180.0F * (float)Math.PI) * power * 0.20F);
	}
	
	@Override
	public void handleMouseInput()
	{
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		
		//Tactile slide gestion
		if (Mouse.isButtonDown(0) && this.isFocused)
		{
			if(x >= this.width - 106 && x <= this.width - 14)
			{
				if(y >= this.height - 193 && y <= this.height - 39)
				{
					if(!this.mouseIsDrag)
					{
						this.clickX = x;
						this.clickY = y;
						this.mouseIsDrag = true;
					}
					else
					{
						if(this.clickX != x || this.clickY != y)
							this.isTactile = true;
						this.releaseX = x;
						this.releaseY = y;
					}
				}
				else
					this.mouseIsDrag = false;
			}
			else
				this.mouseIsDrag = false;
		}
		else
			this.mouseIsDrag = false;
	}

	public CustomFont getFont()
	{
		return this.font;
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
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
			double theta_radian = i*Math.PI / 180D;
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
