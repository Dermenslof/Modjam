package fr.ironcraft.phonecraft.client.gui;

import java.util.Date;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.KeyHandler;
import fr.ironcraft.phonecraft.utils.CustomFont;
import fr.ironcraft.phonecraft.utils.TextureUtils;

public class GuiPhoneInGame  extends GuiScreen {

	protected static Minecraft mc;

	protected int shift = 0;
	private boolean isFocused;
	protected boolean focus;
	protected boolean isOpen = true;
	protected boolean isAnimated = false;
	protected boolean isHome = false;
	protected float transparency;
	
	public static ResourceLocation texturePhone;
	protected CustomFont font;

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
		//useless for now
		//this.isFocused = true;
		
		Keyboard.enableRepeatEvents(true);
		try
		{
			this.font = new CustomFont(this.mc, "TimesNewRoman", 10);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		this.focus = true;
	}
	
	@Override
	public void updateScreen()
	{
//		this.setFocus();
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		this.setMovement();
		super.drawScreen(par1, par2, par3);
		this.drawBackground();
		this.drawRect(this.width - 106 + this.shift, this.height - 193, this.width - 14 + this.shift, this.height - 39, 0xff000000);
	}

	private void drawBackground()
	{
		//Texture
		GL11.glPushMatrix();
		this.mc.renderEngine.bindTexture(texturePhone);
		this.drawTexturedModalRect(this.width - 110 + this.shift, this.height - 210, 0, 0, 100, 200);
		GL11.glPopMatrix();
		
		//time (real)
		String h = "10";
		String m =  "21";
		
		GL11.glPushMatrix();
			//GL11.glScalef(0.5F, 0.5F, 1);
			//GL11.glTranslatef((this.width-28.5F+this.shift)/0.5F, (this.height-192.5F)/0.5F, 0);
			font.drawString(this, h, 0, 0, 0xd2d2d2, 1.0F);
			font.drawString(this, ":", 12, -1, 0xd2d2d2, 1.0F);
			font.drawString(this, m, 16, 0, 0xd2d2d2, 1.0F);
		GL11.glPopMatrix();
		
		//reseau
		GL11.glPushMatrix();
			GL11.glTranslatef(this.width-105F+this.shift, this.height-192, 0);
			GL11.glScalef(0.5F, 0.5F, 1);
			font.drawString(this, "ICtelecom", 0, 0, 0xd2d2d2, 1.0F);
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
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
