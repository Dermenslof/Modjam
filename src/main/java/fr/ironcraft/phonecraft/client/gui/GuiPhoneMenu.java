package fr.ironcraft.phonecraft.client.gui;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.api.PhoneApps;
import fr.ironcraft.phonecraft.client.AppRegistry;
import fr.ironcraft.phonecraft.utils.TextUtils;

/**
 * @author Dermenslof, DrenBx
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
			case 1:
				this.isCamera = true;
				this.mc.displayGuiScreen(new GuiPhoneCamera(this.mc));
				break;
			case 2:
				this.screen = 2;
				this.mc.displayGuiScreen(new GuiPhoneMessages(this.mc));
				break;
			case 3:
				this.screen = 3;
				this.mc.displayGuiScreen(new GuiPhoneContacts(this.mc));
				break;
			case 4:
				this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc, false));
				this.screen = 0;
				break;
			}
			if(this.app > 0)
			{
				System.out.println("APP ID : " + app);
				try
				{
					GuiPhoneInGame appGui = AppRegistry.getAppGuiById(app - 1);
					if(appGui != null)
						this.mc.displayGuiScreen(appGui);
				}
				catch(NullPointerException e)
				{
					System.out.println("NULL POINTER FOR APP ID : " + app);
				}
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
		this.drawGradientRect(0, 0, 92, 154, 0x553388ee, 0x22000000, this.transparency > 0.3F ? -0.7F : 0);
		GL11.glPopMatrix();

		//Horloge mainMenu
//		GL11.glColor4f(1,  1,  1,  this.transparency);
//		this.drawIcon(12, 2, 2, 3F);
		drawQuarterCircle(25, 25, 20, 0, 0xFFFFFF, -1.5F);
		drawQuarterCircle(25, 25, 20, 1, 0xFFFFFF, -1.5F);
		drawQuarterCircle(25, 25, 20, 2, 0xFFFFFF, -1.5F);
		drawQuarterCircle(25, 25, 20, 3, 0xFFFFFF, -1.5F);
		Date d = new Date();
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1,  1,  1,  this.transparency);
		this.drawIcon(13, 26, 26, 0.5F, d.getMinutes() * 6 + 180);
//		this.mc.renderEngine.bindTexture(this.getTextureIcons());
//		GL11.glTranslatef(this.width - 80 + this.shift, this.height - 157, 0);
//		GL11.glRotatef(d.getMinutes() * 6 + 180,  0,  0,  1);
//		this.drawTexturedModalRect(-1, 0, 156, 79, 2, 15);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1,  1,  1,  this.transparency);
		this.drawIcon(13, 26, 26, 0.65F, d.getHours() * 30 + 180);
		
//		this.mc.renderEngine.bindTexture(this.getTextureIcons());
//		GL11.glTranslatef(this.width - 80 + this.shift, this.height - 157, 0);
//		GL11.glRotatef(d.getHours() * 30 + 180,  0,  0,  1);
//		this.drawTexturedModalRect(-1, 0, 156, 79, 2, 10);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1,  0,  0,  this.transparency);

//		this.mc.renderEngine.bindTexture(this.getTextureIcons());
//		GL11.glTranslatef(this.width - 80 + this.shift, this.height - 157, 0);
//		GL11.glRotatef(d.getSeconds() * 6 + 180,  0, 0, 1);
//		this.drawTexturedModalRect(-1, 0, 156, 79, 2, 15);
		this.drawIcon(13, 26, 26, 0.7F, d.getSeconds() * 6 + 180);
		GL11.glPopMatrix();

		//------------------------------------------
		
		GL11.glPushMatrix();
		Calendar cal = Calendar.getInstance();
		String Day = TextUtils.getLanguage("day." + cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toLowerCase());
		if (Day.charAt(0) >= 'a')
			Day = "" + (char)(Day.charAt(0) - 32) + Day.substring(1);

		GL11.glTranslatef(this.width - 30 + this.shift, this.height - 170, 0);
		GL11.glScalef(0.7F,  0.7F,  1);
		this.drawString(Day, - (int)((this.getFont().getStringWidth(Day) / 2) / 0.7F + 2), 0, 0xffd2d2d2, this.transparency);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(this.width - 30 + this.shift, this.height - 155, 0);
		GL11.glScalef(0.5F,  0.5F,  1);
		this.drawString(cal.get(Calendar.DAY_OF_MONTH)
				+ "/" + (cal.get(Calendar.MONTH) + 1)
				+ "/" + cal.get(Calendar.YEAR), - (int)((this.getFont().getStringWidth(cal.get(Calendar.DAY_OF_MONTH)
						+ "/" + (cal.get(Calendar.MONTH) + 1)
						+ "/" + cal.get(Calendar.YEAR)) / 2) / 0.7F + 2), 0, 0xffd2d2d2, this.transparency);
		GL11.glPopMatrix();

		if(this.transparency >= 1F)
		{
			//buttons back
			GL11.glPushMatrix();
			GL11.glColor4f(1,  1,  1,  this.transparency-1.0F);
			
			this.drawGradientRect(0, 135, 92, 155, 0x323232, 0x111111, -1.0F);
			
			int[] ic = {48, 18, 16, 8};
			for(int t=0; t<ic.length; t++) {
				this.drawGradientRect(1 + (t * 23), 136, 22 + (t * 23), 153, 0x626262, 0x424242, -1.0F);
				this.drawIcon(ic[t], 3 + (t * 23), 137, 1F);
			}

			GL11.glPopMatrix();

		}
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
		this.mc.renderEngine.bindTexture(this.getTextureIcons());
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
		onOverApps();
		int t = (this.page * 16);
		for(int line = 0; line < 4; line++)
		{
			for(int col = 0; col < 4; col++)
			{
				t++;
				try
				{
					String title = null;
					PhoneApps app = AppRegistry.getAppById(t - 1);
					if(app == null)
						return;
					title = app.appname();

					if(title == null)
						return;
					GL11.glPushMatrix();
					this.mc.renderEngine.bindTexture(texturePhone);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glColor4f(1,  1,  1, this.transparency/2);
					this.drawTexturedModalRect(this.width - 103 + (col * 23)+this.shift, this.height - 171 + 10 + (line * 30), 110 +((t - 1) % 16) * 14, 0, 14, 10);
					GL11.glPushMatrix();
					GL11.glScalef(0.5F, 0.5F, 1);
					GL11.glTranslatef((this.width - 103 + this.shift + (col % 16) * 23 + 9) / 0.5F, (this.height - 161 + 10 + (line * 30)) / 0.5F, 0);
					this.drawString(title, 0-(this.getFont().getStringWidth(title)/2), 0, 0xffd2d2d2, this.transparency);    				
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
		if(this.mouseX >= this.width - 106 && this.mouseX <= this.width - 14)
		{
			if(this.mouseY >= this.height - 191 && this.mouseY <= this.height - 29)
			{
				int t = (this.page*16);
				for(int line = 0; line < 4; line++)
				{
					for(int col = 0; col < 4; col++)
					{
						t++;
						try
						{
							String title = null;
							PhoneApps app = AppRegistry.getAppById(t - 1);
							if(app == null)
								return;
							title = app.appname();
							if(title == null)
								return;
							if(this.mouseX >= this.width - 103 + (col * 23) - 1 && this.mouseX <= this.width - 103 + (col * 23) + 19)
							{
								if(this.mouseY >= this.height - 171 + 10 + (line * 30) - 2 && this.mouseY <= this.height - 171 + 10 + (line * 30) + 20)
								{
									this.drawRect(this.width - 103 + (col * 23) + this.shift - 1, this.height - 171 + 10 + (line * 30) - 2, this.width - 103 + (col * 23) + this.shift + 19, this.height - 171 + 10 + (line * 30) + 20, 0x55d2d2d2);
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
						for(int t = 0; t < 4; t++)
						{
							if(x >= this.width - 106 + (t * 23) && x <= this.width - 84 + (t * 23) && y >= this.height - 48 && y <= this.height - 31)
							{
								GL11.glPushMatrix();
								this.drawGradientRect(1 + (t * 23), 136, 22 + (t * 23), 153, 0xff626262, 0x55000000, -1.6F);
								GL11.glPopMatrix();
								this.bouton = t + 1;
							}
						}
					}
				}
			}
//			if(x >= this.width - 71 && x <= this.width - 51)
//			{
//				if(y >= this.height - 19 && y <= this.height - 13)
//				{
//					GL11.glPushMatrix();
//					GL11.glTranslatef(0.5F, 1.22F, 0);
//					GL11.glEnable(GL11.GL_BLEND);
//					GL11.glColor4f(1F,  1F,  1F,  this.transparency - 0.7F);
//					this.mc.renderEngine.bindTexture(this.getTextureIcons());
//					this.drawTexturedModalRect(this.width - 72 + this.shift, this.height - 19, 0, 414 / 2 + 6, 50, 6);
//					GL11.glDisable(GL11.GL_BLEND);
//					GL11.glPopMatrix();
//					this.bouton = 0;
//				}
//			}
		}
	}
}