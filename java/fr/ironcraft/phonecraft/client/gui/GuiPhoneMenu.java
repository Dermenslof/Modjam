package fr.ironcraft.phonecraft.client.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

/**
 * @author Dermenslof
 */
@SideOnly(Side.CLIENT)
public class GuiPhoneMenu extends GuiPhoneInGame
{
	private Object init;
	public static boolean isMap;

	public GuiPhoneMenu(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
		this.transparency = 0F;
		if(this.init == null)
		{
			this.isHome = true;
			this.init = this;
		}
	}

	public GuiPhoneMenu(Minecraft par1Minecraft, boolean home)
	{
		super(par1Minecraft);
		this.transparency = -0.5F;
		this.isHome = home;
	}

	public void initGui()
	{
		this.screen = 0;
		this.clickX = this.clickY = this.releaseX = this.releaseY = -1;
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
		super.keyTyped(par1, par2);
	}

	public void mouseClicked(int i, int j, int k)
	{
		if (!this.mc.inGameHasFocus)
		{
			switch(this.bouton)
			{
			case 0:
				this.app = -1;
				this.screen = 0;
				if(!this.isHome)
				{
					this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc));
					this.isHome = true;
				}
				break;
//			case 1:
//				this.isCamera = true;
//				this.mc.displayGuiScreen(new GuiPhoneCamera(this.mc));
//				break;
//			case 2:
//				this.screen = 2;
//				this.mc.displayGuiScreen(new GuiPhoneMessages(this.mc));
//				break;
//			case 3:
//				this.screen = 3;
//				this.mc.displayGuiScreen(new GuiPhoneContacts(this.mc));
//				break;
//			case 4:
//				this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc, false));
//				this.screen = 0;
//				break;
			}
			if(this.app > 0)
			{
//				System.out.println("APP ID : " + app);
//				try
//				{
//					GuiPhoneInGame appGui = AppRegistry.getAppGuiById(app - 1);
//					if(appGui != null)
//						this.mc.displayGuiScreen(appGui);
//				}
//				catch(NullPointerException e)
//				{
//					System.out.println("NULL POINTER FOR APP ID : " + app);
//				}
			}
		}
		super.mouseClicked(i, j, k);
	}

	public void handleMouseInput()
	{
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		super.handleMouseInput();
		if(Mouse.getEventButtonState())
			this.mouseClicked(x, y, Mouse.getEventButton());
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		this.drawMainMenu(par1, par2, par3);
		this.onMouseOverPhone(par1, par2);
	}

	private void drawHome(int par1, int par2, float par3)
	{
		GL11.glPushMatrix();
			GL11.glTranslatef(0F, + 0.5F, 0);
			this.drawGradientRect(this.width - 106 + this.shift, this.height - 184, this.width - 14 + this.shift, this.height - 29, 0x553388ee, 0x22000000,  this.transparency > 0.3F ? 0.3F : this.transparency);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1,  1,  1,  this.transparency);
			this.mc.renderEngine.bindTexture(texturePhone);
			this.drawTexturedModalRect(this.width - 101 + this.shift, this.height - 178, 113, 78, 42, 42);
			Date d = new Date();
			GL11.glTranslatef(this.width - 80 + this.shift, this.height - 157, 0);
			GL11.glRotatef(d.getMinutes() * 6 + 180,  0,  0,  1);
			this.drawTexturedModalRect(-1, 0, 156, 79, 2, 15);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			int day = cal.get(Calendar.DAY_OF_WEEK);
			String Day = "";
			switch(day)
			{
			case 1:
				Day = TextUtils.getLanguage("day.sunday");
				break;
			case 2:
				Day = TextUtils.getLanguage("day.monday");
				break;
			case 3:
				Day = TextUtils.getLanguage("day.tuesday");
				break;
			case 4:
				Day = TextUtils.getLanguage("day.wednesday");
				break;
			case 5:
				Day = TextUtils.getLanguage("day.thursday");
				break;
			case 6:
				Day = TextUtils.getLanguage("day.friday");
				break;
			case 7:
				Day = TextUtils.getLanguage("day.saturday");
				break;
			}
			GL11.glTranslatef(this.width - 30 + this.shift, this.height - 170, 0);
			GL11.glScalef(0.7F,  0.7F,  1);
			this.font.drawString(this, Day, - (int)((this.font.getStringWidth(Day) / 2) / 0.7F + 2), 0, 0xffd2d2d2, this.transparency);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
			GL11.glTranslatef(this.width - 30 + this.shift, this.height - 155, 0);
			GL11.glScalef(0.5F,  0.5F,  1);
			this.font.drawString(this, String.valueOf(d.getDay()) + "/" + String.valueOf(d.getMonth()) + "/" + String.valueOf(d.getYear()+1900), - (int)((this.font.getStringWidth(String.valueOf(d.getDay()) + "/" + String.valueOf(d.getMonth()) + "/" + String.valueOf(d.getYear() + 1900)) / 2) / 0.7F + 2), 0, 0xffd2d2d2, this.transparency);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1,  1,  1,  this.transparency);
			this.mc.renderEngine.bindTexture(texturePhone);
			GL11.glTranslatef(this.width - 80 + this.shift, this.height - 157, 0);
			GL11.glRotatef(d.getHours() * 30 + 180,  0,  0,  1);
			this.drawTexturedModalRect(-1, 0, 156, 79, 2, 10);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1,  0,  0,  this.transparency);
			this.mc.renderEngine.bindTexture(texturePhone);
			GL11.glTranslatef(this.width - 80 + this.shift, this.height - 157, 0);
			GL11.glRotatef(d.getSeconds() * 6 + 180,  0,  0,  1);
			this.drawTexturedModalRect(-1, 0, 156, 79, 2, 15);
		GL11.glPopMatrix();
		if(this.transparency >= 1F)
		{
			//fond boutons
			GL11.glPushMatrix();
				GL11.glColor4f(1,  1,  1,  this.transparency-1.0F);
				this.drawGradientRect(this.width - 106 + this.shift, this.height - 48, this.width - 14 + this.shift, this.height - 28, 0x323232, 0x111111, this.transparency - 1.0F);
				this.drawGradientRect(this.width - 105 + this.shift, this.height - 47, this.width - 84 + this.shift, this.height - 29, 0x626262, 0x424242, this.transparency - 1.0F);
				this.drawGradientRect(this.width - 82 + this.shift, this.height - 47, this.width - 61 + this.shift, this.height - 29, 0x626262, 0x424242, this.transparency - 1.0F);
				this.drawGradientRect(this.width - 59 + this.shift, this.height - 47, this.width - 38 + this.shift, this.height - 29, 0x626262, 0x424242, this.transparency - 1.0F);
				this.drawGradientRect(this.width - 36 + this.shift, this.height - 47, this.width - 15 + this.shift, this.height - 29, 0x626262, 0x424242, this.transparency - 1.0F);
				//icons boutons
				this.mc.renderEngine.bindTexture(texturePhone);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glColor4f(1,  1,  1,  (this.transparency - 1.0F));
				GL11.glTranslatef(this.width - 104 + this.shift, this.height - 44, 0);
				GL11.glScalef(1.1F, 1.1F, 1);
				for(int t=0; t<4; t++)
					this.drawTexturedModalRect(0 + (t * 21), 0, 110 + t * 14, 15, 15, 10);
			GL11.glPopMatrix();
		}
		this.drawRect(this.width - 106 + this.shift, this.height - 183, this.width - 14 + this.shift, this.height - 29, 0xff000000, 1F - this.transparency / 2);
	}

	private void drawMainMenu(int par1, int par2, float par3)
	{
		if(this.screen == -1)
			return;
		if(this.isHome)
		{
			drawHome(par1, par2, par3);
			return;
		}
		//pagination
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1,  1,  1,  this.transparency);
		GL11.glTranslatef(-0.5F, 0, 0);
		this.mc.renderEngine.bindTexture(texturePhone);
		for(int t=0; t<3; t++)
			this.drawTexturedModalRect(this.width - 72 + this.shift + (t * 10), this.height - 181, 44, 227, 3, 3);
		//page actuelle
		GL11.glTranslatef(-0.25F, -0.5F, 0);
		this.drawTexturedModalRect(this.width - 73 + this.shift + (this.page * 10), this.height - 186, 0, 230, 15, 15);
		GL11.glPopMatrix();
		drawPage();
	}

	private void drawPage()
	{
		//System.out.println("PAGE");
		onOverApps();
		int t = (this.page*16);
		for(int h=0; h<4; h++)
		{
			for(int q=0; q<4; q++)
			{
				t++;
				try
				{
					String title = null;
//					Application app = AppRegistry.getAppById(t - 1);
//					if(app == null)
//					{
//						return;
//					}
//
//					title = app.appname();


					if(title == null)
					{
						//System.err.println("Problème app " + t);
						return;
					}
					GL11.glPushMatrix();
						this.mc.renderEngine.bindTexture(texturePhone);
						GL11.glEnable(GL11.GL_BLEND);
						GL11.glColor4f(1,  1,  1, this.transparency/2);
						this.drawTexturedModalRect(this.width-103+(q*23)+this.shift, this.height-171+10+(h*30), 110+((t-1)%16)*14, 0, 14, 10);
						GL11.glPushMatrix();
							GL11.glScalef(0.5F, 0.5F, 1);
							GL11.glTranslatef((this.width-103+this.shift+(q%16)*23+9)/0.5F, (this.height-161+10+(h*30))/0.5F, 0);
							this.font.drawString(this, title, 0-(this.font.getStringWidth(title)/2), 0, 0xffd2d2d2, this.transparency);    				
						GL11.glPopMatrix();
					GL11.glPopMatrix();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return;
				}
			}
		}
	}

	private void onOverApps()
	{
		this.app = -1;
		if(!this.isFocused)
			return;
		if(this.mouseX >= this.width-106 && this.mouseX <= this.width-14)
		{
			if(this.mouseY >= this.height-191 && this.mouseY <= this.height-29)
			{
				int t = (this.page*16);
				for(int h=0; h<4; h++)
				{
					for(int q=0; q<4; q++)
					{
						t++;
						try
						{
							String title = null;
//							Application app = AppRegistry.getAppById(t - 1);
//							if(app == null)
//							{
//								return;
//							}
//
//							title = app.appname();


							if(title == null)
							{
								//System.err.println("Probl�me app " + t);
								return;
							}

							if(this.mouseX >= this.width-103+(q*23)-1 && this.mouseX <= this.width-103+(q*23)+19)
							{
								if(this.mouseY >= this.height-171+10+(h*30)-2 && this.mouseY <= this.height-171+10+(h*30)+20)
								{
									this.drawRect(this.width-103+(q*23)+this.shift-1, this.height-171+10+(h*30)-2, this.width-103+(q*23)+this.shift+19, this.height-171+10+(h*30)+20, 0x55d2d2d2);
									this.app = t;
								}
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							return;
						}
					}
				}
			}
		}
	}

	private void onMouseOverPhone(int x, int y)
	{
		if(this.isTactile && !this.mouseIsDrag && this.clickX >= 0)
		{
			if(-this.clickX+this.releaseX >40)
				this.page--;
			else if(-this.clickX+this.releaseX <-40)
				this.page++;
			this.isTactile = false;
			this.clickX = this.releaseX = this.releaseY = this.clickY = -1;
		}
		if(this.page < 0)
			this.page = 2;
		if(this.page > 2)
			this.page = 0;
		if(this.isFocused)
		{
			this.bouton = -1;
			if(x >= this.width - 106 && x <= this.width - 14)
			{
				if(y >= this.height - 191 && y <= this.height - 29)
				{
					if((this.screen == 0 && this.isHome))
					{
						for(int t=0; t<4; t++)
						{
							if(x >= this.width - 106 + (t * 23) && x <= this.width - 84 + (t * 23) && y >= this.height - 48 && y <= this.height - 31)
							{
								GL11.glPushMatrix();
								this.drawGradientRect(this.width - 105 + this.shift + (t * 23), this.height - 47, this.width - 84 + this.shift + (t * 23), this.height - 29, 0xff626262, 0x55000000, this.transparency-1.6F);
								GL11.glPopMatrix();
								this.bouton = t + 1;
							}
						}
					}
				}
			}
			if(x >= this.width - 71 && x <= this.width - 51)
			{
				if(y >= this.height - 19 && y <= this.height - 13)
				{
					GL11.glPushMatrix();
					GL11.glTranslatef(0.5F, 1.22F, 0);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glColor4f(1F,  1F,  1F,  this.transparency - 0.7F);
					this.mc.renderEngine.bindTexture(texturePhone);
					this.drawTexturedModalRect(this.width - 72 + this.shift, this.height - 19, 0, 414 / 2 + 6, 50, 6);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					this.bouton = 0;
				}
			}
		}
	}
}