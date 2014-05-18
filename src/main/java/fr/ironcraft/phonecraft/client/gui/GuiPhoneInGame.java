package fr.ironcraft.phonecraft.client.gui;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.KeyHandler;
import fr.ironcraft.phonecraft.utils.CustomFont;
import fr.ironcraft.phonecraft.utils.TextureUtils;

/**
 * @authors Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class GuiPhoneInGame extends GuiScreenCustom
{
	protected Minecraft mc;

	//protected int shift = 0;
	protected boolean isFocused;
	protected boolean isOpen = true;
	protected boolean isAnimated = false;
	protected boolean isHome = false;
	protected boolean isFullscreen = false;
	protected boolean isCamera;
	protected boolean shootCamera;

	public boolean hideGui;
	public boolean hidePhone;
	protected boolean animPhoto;
	protected float angle;
	protected int changePoint;
	protected float scale = 1;

	protected static ResourceLocation texturePhone;
	private static ResourceLocation textureIcons;

	private int screenWidth;
	private int screenHeight;

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

	public GuiPhoneInGame(Minecraft par1Minecraft)
	{
		this(par1Minecraft, "coque", "icons/default");
	}

	public GuiPhoneInGame(Minecraft par1Minecraft,  String phoneType)
	{
		this(par1Minecraft, phoneType, "icons/default");
	}

	public GuiPhoneInGame(Minecraft par1Minecraft, String phoneType, String iconsType)
	{
		setTexturePhone(phoneType);
		setTextureIcons(iconsType);
		this.mc = par1Minecraft;
	}

	@Override
	public void initGui()
	{
		this.hidePhone = this.screen == 1 ? true : false;
		Keyboard.enableRepeatEvents(true);
		this.setFont(ClientProxy.fonts.timenewRoman);
		//		this.isFocused = false;
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
		this.drawBackground();
		if (!hidePhone)
			this.drawGradientRect(0, -10, 92, 154, 0xFF252525, 0xFF101010, 1.0F);

		this.drawForground();

		this.onMouseOverPhone(par1, par2);
	}

	private void drawForground()
	{	
		if(this.screen == 1)
			return;
		if (!this.isAnimated) {
			//Wallpaper
			/*GL11.glPushMatrix();
		this.mc.renderEngine.bindTexture(Wallpaper);
		this.drawTexturedModalRect(this.width - 106 + this.shift, this.height - 193, 0, 0, 92, 154);
		GL11.glPopMatrix();*/

			this.drawRect(0, -10, 92, 0, 0xFFFFFF, -1.9F);
			//time (real)
			Calendar cal = new GregorianCalendar();
			int hour = cal.get(Calendar.HOUR);
			String h = cal.get(Calendar.AM_PM) == 0 ? "" + hour : "" + (hour + 12);
			h = hour < 10 && cal.get(Calendar.AM_PM) == 0 ? "0" + h : h;
			int minute = cal.get(Calendar.MINUTE);
			String m =  minute < 10 ? "0" + minute : "" + minute;

			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 1F);
			GL11.glTranslatef((this.width - 29.5F + this.shift) / 0.5F, (this.height - 192.5F) / 0.5F - 32, 0.5F);
			this.drawString(h, 0, 0, 0xd2d2d2, 0.3F);
			this.drawString(":", 12 , -1, 0xd2d2d2, 0.3F);
			this.drawString( m, 16, 0, 0xd2d2d2, 0.3F);
			GL11.glPopMatrix();

			//reseau
			GL11.glPushMatrix();
			GL11.glTranslatef(this.width - 105F + this.shift, this.height - 208, 0);
			GL11.glScalef(0.5F, 0.5F, 1);
			this.drawString("IC telecom", 0 + 1, 0, 0xd2d2d2, 0.3F);
			GL11.glPopMatrix();
		}

		this.drawAbsoluteRect(this.getScreenPosX(), this.getScreenPosY() - 10, this.getScreenPosX() + this.getScreenWidth() + this.getShift(), this.getScreenPosY() + this.getScreenHeight(), 0xff252525, this.screen == 4 ? 0 : this.screen == -1 ? this.transparency : 1F - this.transparency);
	}

	private void drawBackground()
	{
		if(this.screen == 1)
			return;
		//image telephone
		GL11.glColor4f(1,  1,  1,  3 - this.scale);
		GL11.glTranslatef(this.width / 2, this.height / 2, 0);
		GL11.glRotatef(this.angle, 0, 0, 1);
		GL11.glScalef(this.scale, this.scale, 1);
		GL11.glTranslatef(-this.width / 2 - this.changePoint / 1.38F, -this.height / 2-this.changePoint / 15, 0);
		
		//Texture
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, 0F, 0);
		this.mc.renderEngine.bindTexture(this.getTexturePhone());
		this.drawTexturedModalRect(this.width - 110 + this.shift, this.height - 235, 0, 0, 110, 250);
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

	/* Change focus (inGameGUI / PhoneGUI) */
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

	/*  player focused into this GUI */
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

	protected void onMouseOverPhone(int x, int y)
	{
		if(this.isFocused)
		{
			if(x >= this.width - 71 && x <= this.width - 51)
			{
				if(y >= this.height - 35 && y <= this.height - 20)	
				{
					GL11.glPushMatrix();
//					GL11.glTranslatef(0.5F, 1.22F, 0);
//					GL11.glEnable(GL11.GL_BLEND);
//					GL11.glColor4f(1F,  1F,  1F,  this.transparency - 0.7F);
//					this.mc.renderEngine.bindTexture(this.getTextureIcons());
//					this.drawTexturedModalRect(this.width - 72 + this.shift, this.height - 19, 0, 414 / 2 + 6, 50, 6);
//					GL11.glDisable(GL11.GL_BLEND);
					
					GL11.glColor4f(1,  1,  1,  this.transparency);
					this.drawIcon(4, 36, 145, 1.2F);
					
					GL11.glPopMatrix();
					this.bouton = 0;
				}
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	/**
	 * get shift
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @return value of shift
	 */
	public int getShift()
	{
		return this.shift;
	}
	
	/**
	 * get if mouse is grab on Minecraft or not
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @return true if mouse isn't grab on Minecraft
	 */
	public boolean getFocus()
	{
		return this.isFocused;
	}

	/**
	 * get the main Phonecraft texture
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @return ResourceLocation texture
	 */
	public ResourceLocation getTexturePhone()
	{
		return texturePhone;
	}

	/**
	 * get the main Phonecraft icons
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @return ResourceLocation texture
	 */
	public ResourceLocation getTextureIcons()
	{
		return textureIcons;
	}

	//	protected void drawStringOnScreen(int x, int y, String str, int color, float transparency)
	//	{
	//		this.drawString(str, this.width - 106 + x, this.height - 193 + y, color, transparency);
	//	}
	//	
	//	protected void drawImageOnScreen(ResourceLocation img, int x, int y, int imgX, int imgY, int sizeX, int sizeY)
	//	{
	//		this.mc.renderEngine.bindTexture(img);
	//		this.drawTexturedModalRect(this.width - 106 + x,  this.height - 183 + y,  imgX,  imgY,  sizeX,  sizeY);
	//	}

	/**
	 * set Phonecraft default icon with an position ID (like terrain.png, R.I.P)
	 *"/!\" You must use GL11.glColor4f before use this function
	 * 
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @param Position of texture on the png
	 * @param Position X on phone screen
	 * @param Position Y on phone screen
	 * @param Define the dimension size
	 * 
	 */
	public void drawIcon(int textureId, int posX, int posY, float textureSize)
	{
		this.drawIcon(textureId, posX, posY, textureSize, 0);
	}

	public void drawIcon(int textureId, int posX, int posY, float textureSize, float rotation)
	{
		int iconSize = 16;
		int iconPerLine = 16;
		int iconPerCol = 256;
		int iconPosX = (textureId % iconPerLine) * iconSize;
		int iconPosY = ((textureId / iconPerLine) * iconSize) % iconPerCol;

		GL11.glPushMatrix();
		GL11.glScalef(textureSize, textureSize, 1.0F);
		this.mc.renderEngine.bindTexture(this.getTextureIcons());
		GL11.glTranslatef((this.width - 105.5F + posX + this.shift) / textureSize, (this.height - 183 + posY) / textureSize, 0.5F);
		GL11.glRotatef(rotation, 0, 0, 1);
		this.drawTexturedModalRect(0, 0, iconPosX, iconPosY, iconSize, iconSize);
		GL11.glPopMatrix();
	}

	/**
	 * set the main texture used by Phonecraft
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @param filename
	 * @return new phone texture
	 */
	public ResourceLocation setTexturePhone(String phoneType)
	{
		return this.texturePhone = new ResourceLocation(TextureUtils.getTextureNameForGui(phoneType));
	}

	/**
	 * set the main icons used by Phonecraft
	 * 
	 * @author Dren
	 * @since Phonecraft 1.0 for Minecraft 1.7.2
	 * @param filenam or domain:filename
	 * @return new icons
	 */
	public ResourceLocation setTextureIcons(String iconsType)
	{
		return this.textureIcons = new ResourceLocation(TextureUtils.getTextureNameForGui(iconsType));
	}
}
