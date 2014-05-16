package fr.ironcraft.phonecraft.client.gui;

import java.util.Date;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.KeyHandler;
import fr.ironcraft.phonecraft.utils.CustomFont;
import fr.ironcraft.phonecraft.utils.TextureUtils;

public class GuiPhoneInGame  extends GuiScreen {

	protected static Minecraft mc;

	protected int shift = 0;
	private boolean isFocused;
	protected boolean isOpen = true;
	protected boolean isAnimated = false;
	protected boolean isHome = false;
	protected boolean isFullscreen = false;
	protected float transparency;
	
	public static ResourceLocation texturePhone;
	protected CustomFont font;

	/* For mouse gestion */
	private int clickX;
	private int clickY;
	private int releaseX;
	private int releaseY;
	private boolean isTactile;
	private boolean mouseIsDrag;

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
		try {
			this.font = new CustomFont(this.mc, "TimesNewRoman", 10);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.isFocused = true;
	}
	
	@Override
	public void updateScreen()
	{
		this.setFocus();
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		this.setMovement();
		super.drawScreen(par1, par2, par3);
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
		this.drawRect(this.width - 106 + this.shift, this.height - 39, this.width - 14 + this.shift, this.height - 54, 0x22FFFFFF);
		
		//time (real)
		Date d = new Date();
		String h = d.getHours() < 10 ? "0" + String.valueOf(d.getHours()) : String.valueOf(d.getHours());
		String m =  d.getMinutes() < 10 ? "0" + String.valueOf(d.getMinutes()) : String.valueOf(d.getMinutes());
		
		GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 1F);
			GL11.glTranslatef((this.width - 28.5F + this.shift) / 0.5F, (this.height - 192.5F) / 0.5F, 0);
			font.drawString(this, h, 0, 0, 0xd2d2d2, 0.3F);
			font.drawString(this, ":", 12, -1, 0xd2d2d2, 0.3F);
			font.drawString(this, m, 16, 0, 0xd2d2d2, 0.3F);
		GL11.glPopMatrix();
		
		//reseau
		GL11.glPushMatrix();
			GL11.glTranslatef(this.width - 105F + this.shift, this.height - 192, 0);
			GL11.glScalef(0.5F, 0.5F, 1);
			font.drawString(this, "IC telecom", 0, 0, 0xd2d2d2, 0.3F);
		GL11.glPopMatrix();
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

			if (!this.isOpen && !this.isAnimated)
				this.mc.displayGuiScreen(new GuiPhoneAnimation(mc, true));
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
	
	public int getShift()
	{
		return this.shift;
	}
}
