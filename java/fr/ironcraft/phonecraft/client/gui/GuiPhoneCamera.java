package fr.ironcraft.phonecraft.client.gui;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.client.KeyHandler;
import fr.ironcraft.phonecraft.common.Blocks.ICBlocks;
import fr.ironcraft.phonecraft.utils.CameraScreenshot;
import fr.ironcraft.phonecraft.utils.TaskFlash;
import fr.ironcraft.phonecraft.utils.TextureUtils;

/**
 * @author Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class GuiPhoneCamera extends GuiPhoneInGame
{
	public static boolean isQrCode;
	public static List<int[]> lastBlocks = new ArrayList<int[]>();
	public static boolean isFlash;
	public static ResourceLocation textureFlash = new ResourceLocation(TextureUtils.getTextureNameForGui("icons/flash"));
	public static ResourceLocation textureqrCode = new ResourceLocation("phonecraft:textures/blocks/qrCode.png");

	public GuiPhoneCamera (Minecraft par1Minecraft)
	{
		super(par1Minecraft);
	}
	
	public GuiPhoneCamera(Minecraft par1Minecraft, boolean anim)
	{
		super(par1Minecraft);
		if (!anim)
		{
			this.scale = 2.8F;
			this.screen = 1;
			this.angle = -90;
			this.mc.gameSettings.hideGUI = true;
		}
	}

	public void initGui()
	{
		this.animPhoto = true;
		super.initGui();
	}

	public void updateScreen()
	{
		super.updateScreen();
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}

	public void keyTyped(char par1, int par2)
	{
		if (par2 == 1)
		{
			this.mc.gameSettings.fovSetting = 0;
			this.mc.gameSettings.hideGUI = false;
			this.mc.displayGuiScreen(new GuiIngameMenu());
		}
	}

	public void mouseClicked(int i, int j, int k)
	{
		if(this.screen == 1 && !this.hideGui && !this.isFocused && k == 0)
		{
			if (isFlash)
				this.setBlockFlash();
			this.hideGui = true;
			this.shootCamera = true;
		}
		else
		{
			if (k == 0)
			{
				if (i >= this.width - 28 && i <= this.width - 12)
				{
					if (j >= 10 && j <= 40)
						isQrCode = !isQrCode;
				}
				else if (i >= 12 && i <= 28)
				{
					if (j >= 10 && j <= 40)
						isFlash = !isFlash;
				}
			}
		}
	}

	public void handleMouseInput()
	{
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		if(Mouse.getEventButtonState())
			this.mouseClicked(x, y, Mouse.getEventButton());
		int w = Mouse.getDWheel();
		if (w != 0 && !this.isFocused)
		{
			if (w > 0)
				this.mc.gameSettings.fovSetting -= 0.1F;
			else if (w < 0)
				this.mc.gameSettings.fovSetting += 0.1F;
			if (this.mc.gameSettings.fovSetting > 0F)
				this.mc.gameSettings.fovSetting = 0F;
			else if (this.mc.gameSettings.fovSetting < -1.6F)
				this.mc.gameSettings.fovSetting = -1.6F;
		}
		super.handleMouseInput();
	}

	private void drawBackground()
	{
		GL11.glPushMatrix();
		//timing animation photo
		if(this.animPhoto)
		{
			this.screen = -1;
			this.changePoint += 10;
			if(this.changePoint >= this.width / 2)
			{
				this.changePoint = this.width / 2;
				this.scale += 0.1F;
				if(this.scale >= 2.8F)
				{
					this.scale = 2.8F;
					this.screen = 1;
					this.mc.gameSettings.hideGUI = true;
				}
			}
			this.angle -= 4;
			if(this.angle <= -90)
				this.angle = -90;
		}
		else
		{
			this.scale -= 0.1F;
			if(this.scale <= 1F)
			{
				this.scale = 1F;
				if(this.changePoint > 0)
					this.changePoint -= 10;
				if(this.changePoint <= 0)
					this.changePoint = 0;
				if(this.angle < 0)
					this.angle += 4;
				if(this.angle >= 0)
				{
					this.angle = 0;
					this.mc.gameSettings.hideGUI = false;
					this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc));
				}
			}
		}
		GL11.glPopMatrix();
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		if(this.screen != 1 )
			drawBackground();
		else
			drawCamera(par1, par2, par3);
	}

	private void drawCamera(int par1, int par2, float par3)
	{
		if(Keyboard.isKeyDown(KeyHandler.key_PhoneGUI.getKeyCode()))
		{
			this.mc.gameSettings.fovSetting = 0;
			this.screen = -1;
			this.animPhoto = false;
			this.isCamera = false;
			this.changePoint = this.width/2;
			this.scale = 2.8F;
			this.angle = -90;
		}
		if(!this.hideGui)
		{
			if (lastBlocks.size() != 0)
			{
				for (int i=0; i<lastBlocks.size(); i++)
				{
					int[] loc = lastBlocks.get(i);
					this.mc.thePlayer.worldObj.setBlock(loc[0], loc[1], loc[2], Blocks.air, 0, 2);
				}
				lastBlocks.clear();
			}
			if (!isQrCode)
			{
				GL11.glPushMatrix();
				this.mc.renderEngine.bindTexture(this.getTexturePhone());
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glColor4f(1,  1,  1,  1F);
				this.drawTexturedModalRect(this.width/2 - 35, this.height/2 - 23, 110, 25, 100, 50);
				GL11.glPopMatrix();
			}
			else
			{
				this.drawHorizontalLine(this.width / 2 - 100, this.width / 2 - 80, this.height / 2 - 100, 0xffffffff);
				this.drawHorizontalLine(this.width / 2 + 100, this.width / 2 + 80, this.height / 2 - 100, 0xffffffff);
				this.drawHorizontalLine(this.width / 2 - 100, this.width / 2 - 80, this.height / 2 + 100, 0xffffffff);
				this.drawHorizontalLine(this.width / 2 + 100, this.width / 2 + 80, this.height / 2 + 100, 0xffffffff);

				this.drawVerticalLine(this.width / 2 - 100, this.height / 2 - 100, this.height / 2 - 80, 0xffffffff);
				this.drawVerticalLine(this.width / 2 + 100, this.height / 2 - 100, this.height / 2 - 80, 0xffffffff);
				this.drawVerticalLine(this.width / 2 - 100, this.height / 2 + 100, this.height / 2 + 80, 0xffffffff);
				this.drawVerticalLine(this.width / 2 + 100, this.height / 2 + 100, this.height / 2 + 80, 0xffffffff);
			}
			GL11.glPushMatrix();
			double percentZoom = -this.mc.gameSettings.fovSetting * 100 / 1.6;
			GL11.glColor4f(1,  1,  1,  1F);
			this.drawRoundedRect(this.width - 30, this.height - 115, this.width - 10, this.height - 5, 5, 0x00000000, 0.8F);
			this.drawRect(this.width - 21, this.height - 110, this.width - 19, this.height - 10, 0x00757575, 0.8F);
			this.drawRect(this.width - 25, this.height - 12 - (int)percentZoom, this.width - 15, this.height - 8 - (int)percentZoom, 0x00b2b2b2, 1F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			int qrcode = isQrCode ? 15 : 0;
			this.drawRoundedRect(this.width - 30, 5, this.width - 10, 45, 5, 0x00000000, 0.8F);
			this.drawRect(this.width - 28, 10, this.width - 12, 40, 0x00757575, 0.15F);
			this.drawRect(this.width - 28, 10 + qrcode, this.width - 12, 25 + qrcode, 0x00b2b2b2, 1F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			this.mc.renderEngine.bindTexture(textureFlash);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			if (isQrCode)
			{
				GL11.glScalef(0.0565F, 0.0565F, 0.0565F);
				this.mc.renderEngine.bindTexture(textureqrCode);
				this.drawTexturedModalRect((int)((this.width - 27)/0.0565F), (int)(25/0.0565F), 0, 0, 256, 256);
			}
			else
			{
				this.mc.renderEngine.bindTexture(texturePhone);
				this.drawTexturedModalRect((int)((this.width - 27)), (int)(12), 112, 15, 12, 12);
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			int flash = isFlash ? 15 : 0;
			this.drawRoundedRect(10, 5, 30, 45, 5, 0x00000000, 0.8F);
			this.drawRect(12, 10, 28, 40, 0x00757575, 0.15F);
			this.drawRect(12, 10 + flash, 28, 25 + flash, 0x00b2b2b2, 1F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			this.mc.renderEngine.bindTexture(textureFlash);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glScalef(0.05F, 0.05F, 0.05F);
			if (isFlash)
			{
				GL11.glColor4f(1F, 1F, 1F, 1F);
				this.drawTexturedModalRect((int)(13/0.05F), (int)(26/0.05F), 0, 0, 256, 256);
			}
			else
			{
				GL11.glColor4f(0.3F, 0F, 0F, 1F);
				this.drawTexturedModalRect((int)(13/0.05F), (int)(11/0.05F), 0, 0, 256, 256);
			}
			GL11.glPopMatrix();
		}
		else
		{
			if(this.shootCamera)
			{
				this.shootCamera = false;
				IntBuffer buffer = null;
				int[] list = null;
				int var2 = this.mc.displayWidth * this.mc.displayHeight;
				if (buffer == null || buffer.capacity() < var2)
				{
					buffer = BufferUtils.createIntBuffer(var2);
					list = new int[var2];
				}
				GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
				GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
				buffer.clear();
				GL11.glReadPixels(0, 0, this.mc.displayWidth, this.mc.displayHeight, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, buffer);
				buffer.get(list);
				new Thread(new CameraScreenshot(this.mc, this, buffer, list, isQrCode)).start();
				EntityPlayer p = this.mc.thePlayer;
				if (p.worldObj.isRemote)
					p.playSound("phonecraft:clicPhoto", 0.75F, 6F);
				else
					p.worldObj.playSoundAtEntity(p, "phonecraft:clicPhoto", 0.75F, 6F);
				if (isFlash)
					this.mc.displayGuiScreen(new TaskFlash(this.mc, 200));
			}
		}
	}

	private void setBlockFlash()
	{	
		int[] loc = {(int)this.mc.thePlayer.posX , (int)this.mc.thePlayer.posY - 1, (int)this.mc.thePlayer.posZ};
		this.lastBlocks.add(loc);
		this.mc.theWorld.setBlock(loc[0], loc[1], loc[2], ICBlocks.flash, 0, 2);;
	}
}
