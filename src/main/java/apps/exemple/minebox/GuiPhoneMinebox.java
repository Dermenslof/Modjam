package apps.exemple.minebox;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneInGame;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneMenu;
import fr.ironcraft.phonecraft.utils.ImageLoader;

@SideOnly(Side.CLIENT)
public class GuiPhoneMinebox extends GuiPhoneInGame
{
	public BufferedImage albumImg = null;
	public BufferedImage prevAlbumImg = null;
	public String[] infos;
	public long delay;
	public long totalTime;
	public long backTime;
	public long tmp;
	public long start;

	private Thread json;
	private int texture;
	private int textureBack;
	private int textureLogo;
	private int textureUnknow;
	private int textureIcons;

	public static Minecraft mc = Minecraft.getMinecraft();
	private ImageLoader imageLoader;

	public GuiPhoneMinebox()
	{
		super(mc);
		this.imageLoader = new ImageLoader();
	}

	public void initGui()
	{
		super.initGui();
		this.textureBack = this.imageLoader.setupTexture("apps/exemple/minebox:textures/mbrBack.png");
		this.textureLogo = this.imageLoader.setupTexture("apps/exemple/minebox:textures/mbrLogo.png");
		this.textureUnknow = this.imageLoader.setupTexture("apps/exemple/minebox:textures/unknow.png");
		//this.textureIcons = this.imageLoader.setupTexture("apps/exemple/minebox:textures/icons.png");
		AppMinebox.sound = StreamSoundThread.getThread();
		if(!StreamSoundThread.isStart())
			AppMinebox.sound.start();
	}

	public void updateScreen()
	{
		long time = System.currentTimeMillis();
		if (backTime > 0)
			backTime = tmp - (time - start);
		if (this.infos != null)
		{
			if (time >= delay)
				getInfos();
		}
		else
			getInfos();
		if(AppMinebox.sound != null)
			AppMinebox.sound.updateSoundVolume();
		super.updateScreen();
	}

	public void mouseClicked(int i, int j, int k)
	{
		if (!this.mc.inGameHasFocus)
		{
			switch (this.bouton)
			{
			case 0:
				this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc));
				break;
			case 1:
				AppMinebox.sound.volume -= 0.05F;
				break;
			case 2:
				AppMinebox.sound.volume += 0.05F;
				break;
			case 4:
				AppMinebox.sound.interrupt();
				this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc));
				break;
			case 3:
				getInfos();
				break;
			}
		}
		super.mouseClicked(i, j, k);
	}

	public void handleMouseInput()
	{
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		super.handleMouseInput();
		if (Mouse.getEventButtonState())
			this.mouseClicked(x, y, Mouse.getEventButton());
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		boolean pub = false;
		boolean info = infos != null && infos.length > 0;
		if (infos != null)
			pub = !infos[2].toLowerCase().contains("adver") && !infos[2].toLowerCase().contains("mineboxradio") && !infos[1].startsWith("#");
		super.drawScreen(par1, par2, par3);
		drawBackground();
		//infos
		GL11.glPushMatrix();
		GL11.glScalef(0.5F,  0.5F,  1);
		if(info)
		{
			if (pub)
				drawInfos();
			else
			{
				GL11.glScalef(2F, 2F,  1);
				this.drawString("Pub", this.width - 71  + this.shift, this.height - 152,  0xd2d2d2, this.transparency);
			}
		}
		else
			drawWaiting();
		GL11.glPopMatrix();
		if (info && pub)
			drawTimeLine();
		drawButtons();
		onMouseOverPhone(par1, par2);
	}

	private void drawBackground()
	{
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, this.transparency);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glScalef(0.61F,  0.61F,  1);
		GL11.glTranslatef(-0.2F, 0F, 0);
		//background
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureBack);
		this.drawTexturedModalRect((int)((this.width - 105)/0.61F), (int)((this.height - 183)/0.61F), 0, 0, 150, 256);
		//logo
		GL11.glScalef(0.61F,  0.61F,  1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureLogo);
		this.drawTexturedModalRect((int)(((this.width - 106)/0.61F)/0.61F), (int)(((this.height - 183)/0.61F)/0.61F), 0, 0, 256, 256);
		GL11.glPopMatrix();
		//cadres
		this.drawRect(this.width - 100, this.height - 155, this.width - 20, this.height - 128, 0x0, this.transparency - 1.75F);
	}

	private void drawInfos()
	{
		GL11.glPushMatrix();
		GL11.glScalef(2F, 2F,  1);
		this.drawRect(this.width - 84, this.height - 122, this.width - 35, this.height - 74, 0x00, this.transparency - 1.4F);
		this.drawRoundedRect(this.width - 104, this.height - 67, this.width - 16, this.height - 50, 3, 0x00, this.transparency - 1.5F);
		GL11.glPopMatrix();
		String[] names = infos[2].split(" - ");
		this.drawString("Artiste(s):" , (int)((this.width - 98  + this.shift)/0.5F), (int) ((this.height - 149)/0.5F),  0xd2d2d2, this.transparency);
		for(int i=0; i<names.length; i++)
			this.drawString(names[i] , (int)((this.width - 74  + this.shift)/0.5F), (int) ((this.height - 149 + 5*i)/0.5F),  0xd2d2d2, this.transparency);
		this.drawString(infos[1] , (int)((this.width - 98 + this.shift)/0.5F), (int)((this.height-156)/0.5F),  0xd2d2d2, this.transparency);
		drawJacket();
	}

	private void drawJacket()
	{
		if (this.albumImg != null)
		{
			if (this.prevAlbumImg != this.albumImg)
			{
				this.prevAlbumImg = this.albumImg;
				this.texture = ClientProxy.imageLoader.setupTexture(albumImg);
			}
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture);
		}
		else
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureUnknow);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, this.transparency);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glScalef(0.3F, 0.3F, 0.3F);
		this.drawTexturedModalRect((int)(((this.width - 79 + this.shift)/0.3F)/0.5F), (int) (((this.height - 117)/0.3F)/0.5F), 0, 0, 256, 256);
		GL11.glPopMatrix();
	}

	private void drawWaiting()
	{
		GL11.glScalef(2F, 2F,  1);
		this.drawRect(this.width - 84, this.height - 122, this.width - 35, this.height - 74, 0x00, this.transparency - 1.4F);
		if (mc.getLanguageManager().getCurrentLanguage().getLanguageCode().startsWith("fr"))
			this.drawString("Chargement", this.width - 88  + this.shift, this.height - 147,  0xd2d2d2);
		else
			this.getFont().drawString(this, "Loading", this.width - 80  + this.shift, this.height - 147,  0xd2d2d2);
		long t = System.currentTimeMillis() % 1000 / 100;
		//		System.out.println(t);
		if (t < 2)
			this.drawString("", this.width - 64 + this.shift, this.height - 105,  0xd2d2d2);
		else if (t < 4)
			this.drawString(".", this.width - 64 + this.shift, this.height - 105,  0xd2d2d2);
		else if (t < 6)
			this.drawString("..", this.width - 64 + this.shift, this.height - 105,  0xd2d2d2);
		else if (t < 8)
			this.drawString("...", this.width - 64 + this.shift, this.height - 105,  0xd2d2d2);
		else
			this.drawString("....", this.width - 64  + this.shift, this.height - 105,  0xd2d2d2);
	}

	private void drawTimeLine()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, this.transparency);
		long last = totalTime - backTime;
		double percent = totalTime < 1 ? 0 : (((last)*100D)/totalTime);
		int current = (int)((80D/100D)*percent);
		this.drawRect(this.width - 100, this.height - 58, this.width - 20, this.height - 57, 0x232323, this.transparency);
		this.drawRect(this.width - 100, this.height - 58, this.width - 100 + current, this.height - 57, 0xa21122, this.transparency);
		this.drawRoundedRect(this.width - 101 + current, this.height - 60, this.width - 99 + current, this.height - 55, 1, 0xffffff, this.transparency);
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 1);
		int minutes = (int)(last/60);
		int seconds = (int)((last -((last/1000)-minutes))/1000) % 60;
		this.getFont().drawString(this, "" + (minutes/1000) + ":" + (seconds < 10 ? "0" + seconds : (seconds == 60 ? "00" : "" + seconds)), (int)((this.width - 100 + this.shift)/0.5F), (int)((this.height - 68)/0.5F), 0xffffff, this.transparency);
		GL11.glPopMatrix();
	}

	private void drawButtons()
	{
		GL11.glPushMatrix();

		this.drawGradientRect(106, 48, 14, 28, 0xff323232, 0xff111111);

		int[] ic = {39, 38, 21, 36};
		for(int t=0; t<ic.length; t++) {
			this.drawGradientRect(105 - (t * 23), 47, 84 - (t * 23), 29, 0x626262, 0x424242, -1.0F);
			this.drawIcon(ic[t], 3 + (t * 23), 137, 1F);
		}

		GL11.glPopMatrix();
	}
	
	private void onMouseOverPhone(int x, int y)
	{
		if (this.getFocus())
		{
			this.bouton = -1;
//			//Main button (move to super class)
//			if (x >= this.width - 71 && x <= this.width - 51)
//			{
//				if (y >= this.height - 19 && y <= this.height - 13)
//				{
//					GL11.glPushMatrix();
//					GL11.glTranslatef(0.5F, 1.22F, 0);
//					GL11.glEnable(GL11.GL_BLEND);
//					GL11.glColor4f(1F, 1F, 1F, 0.3F);
//					this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/phone1.png"));
//					this.drawTexturedModalRect(this.width - 72 + this.shift, this.height - 19, 0, 414 / 2 + 6, 50, 6);
//					GL11.glDisable(GL11.GL_BLEND);
//					GL11.glPopMatrix();
//					this.bouton = 0;
//				}
//			}
			if(x >= this.width-106 && x <= this.width-14)
			{
				if(y >= this.height-191 && y <= this.height-29)
				{
					for(int t=0; t<4; t++)
					{
						if(x >= this.width-106+(t*23) && x <= this.width-84+(t*23) && y >= this.height-48 && y <= this.height-31)
						{
							GL11.glPushMatrix();
							this.drawGradientRect(105 - (t * 23), 47, 84 - (t*23), 29, 0xff626262, 0x55000000, -1.6F);
							GL11.glPopMatrix();
							this.bouton = t + 1;
						}
					}
				}
			}
		}
	}

	private void getInfos()
	{
		json = new Thread(new MbrThread(this));
		try
		{
			json.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}