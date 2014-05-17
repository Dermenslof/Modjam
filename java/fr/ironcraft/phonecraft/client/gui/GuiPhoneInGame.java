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

	protected int shift = 0;
	protected boolean isFocused;
	protected boolean isOpen = true;
	protected boolean isAnimated = false;
	protected boolean isHome = false;
	protected boolean isFullscreen = false;
	protected boolean isCamera;
	protected boolean shootCamera;
	protected float transparency;
	
	public boolean hideGui;
	protected boolean animPhoto;
	protected float angle;
	protected int changePoint;
	protected float scale = 1;
	
	protected static ResourceLocation texturePhone;
	private static ResourceLocation textureIcons;
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

	public GuiPhoneInGame(Minecraft par1Minecraft)
	{
		this(par1Minecraft, "phone", "icons/default");
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
//		drawBackground();
		this.drawBackground();
//		this.drawRect(this.width - 106 + this.shift, this.height - 193, this.width - 14 + this.shift, this.height - 39, 0xFF000000);
		this.drawForground();
	}

	private void drawForground()
	{	
		if(this.screen == 1)
			return;
		//Wallpaper
		/*GL11.glPushMatrix();
		this.mc.renderEngine.bindTexture(Wallpaper);
		this.drawTexturedModalRect(this.width - 106 + this.shift, this.height - 193, 0, 0, 92, 154);
		GL11.glPopMatrix();*/

		this.drawRect(this.width - 106 + this.shift, this.height - 183, this.width - 14 + this.shift, this.height - 193, 0x22FFFFFF);
		//time (real)
		Calendar cal = new GregorianCalendar();
		int hour = cal.get(Calendar.HOUR);
		String h = cal.get(Calendar.AM_PM) == 0 ? "" + hour : "" + (hour + 12);
		int minute = cal.get(Calendar.MINUTE);
		String m =  minute < 10 ? "0" + minute : "" + minute;
		
		GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 1F);
			GL11.glTranslatef((this.width - 29.5F + this.shift) / 0.5F, (this.height - 192.5F) / 0.5F, 0.5F);
			font.drawString(this, h, 0, 0, 0xd2d2d2, 0.3F);
			font.drawString(this, ":", 12 , -1, 0xd2d2d2, 0.3F);
			font.drawString(this, m, 16, 0, 0xd2d2d2, 0.3F);
		GL11.glPopMatrix();
		
		//reseau
		GL11.glPushMatrix();
			GL11.glTranslatef(this.width - 105F + this.shift, this.height - 192, 0);
			GL11.glScalef(0.5F, 0.5F, 1);
			font.drawString(this, "IC telecom", 0 + 1, 0, 0xd2d2d2, 0.3F);
		GL11.glPopMatrix();
		this.drawRect(this.width-106+this.shift, this.height-183, this.width-14+this.shift, this.height-29, 0xff000000, this.screen == 4 ? 0 : this.screen == -1 ? this.transparency : 1f-this.transparency);
	}
	
	private void drawBackground()
	{
		if(this.screen == 1)
			return;
		//image telephone
		GL11.glColor4f(1,  1,  1,  3-this.scale);
		GL11.glTranslatef(this.width/2, this.height/2, 0);
		GL11.glRotatef(this.angle, 0, 0, 1);
		GL11.glScalef(this.scale, this.scale, 1);
		GL11.glTranslatef(-this.width/2-this.changePoint/1.38F, -this.height/2-this.changePoint/15, 0);
		//Texture
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, 0F, 0);
		this.mc.renderEngine.bindTexture(this.getTexturePhone());
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
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	public CustomFont getFont()
	{
		return this.font;
	}
	
	public CustomFont setFont(CustomFont font)
	{
		return this.font = font;
	}
	
	public ResourceLocation getTexturePhone()
	{
		return texturePhone;
	}
	
	public ResourceLocation getTextureIcons()
	{
		return textureIcons;
	}
	
	public ResourceLocation setTexturePhone(String phoneType)
	{
		return this.texturePhone = new ResourceLocation(TextureUtils.getTextureNameForGui(phoneType));
	}
	
	public ResourceLocation setTextureIcons(String iconsType)
	{
		return this.textureIcons = new ResourceLocation(TextureUtils.getTextureNameForGui(iconsType));
	}
}
