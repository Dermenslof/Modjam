package fr.ironcraft.phonecraft.client.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.client.KeyHandler;
import fr.ironcraft.phonecraft.utils.ImageLoader;

/**
 * @author Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class GuiPhoneImages extends GuiPhoneInGame
{
	protected int prevImage;
	protected List<String> photos;
	protected BufferedImage buffered;
	protected boolean menuPhoto;
	protected ImageLoader imgLoader;
	protected static int image;
	protected int texture;
	protected Map<Integer, String> appsList = new HashMap<Integer, String>();
	protected static boolean rotate;
	
	public GuiPhoneImages (Minecraft par1Minecraft)
	{
		super(par1Minecraft);
		this.imgLoader = new ImageLoader();
	}
	
    public void initGui()
    {
    	this.isFocused = true;
		this.screen = 4;
		this.menuPhoto = true;
		this.photos = getImagesAvaiable();
		this.prevImage = -1;
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
    	else if (par2 == KeyHandler.key_PhoneGUI.getKeyCode())
    	{
    		this.screen = -1;
    		this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc));
    	}
    }
    
    public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		if (!this.mc.inGameHasFocus)
		{
			switch(this.bouton)
			{
			case 0:
				System.out.println("test ploplo");
				this.app = -1;
				this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc, false));
				this.screen = 0;
				break;
			case 1:
				if(this.image != -1)
					this.mc.displayGuiScreen(new GuiPhoneEditImg(this.mc, new File(this.photos.get(this.image))));
				break;
			case 2:
				this.screen = 2;
				break;
			case 3:
				this.rotate = !this.rotate;
				break;
			case 4:
				try
				{
					String name = this.photos.get(this.image);
					File f = new File(this.mc.mcDataDir, Phonecraft.phoneFolder + "pictures/dcim/" + name);
					f.delete();
					this.photos = getImagesAvaiable();

					if(this.image >this.photos.size()-1)
						this.image--;
				}
				catch(Exception e)
				{
					this.image = 0;	
				}
				break;
			}
			//ecran images
			if(i >= this.width-106 && i <= this.width-14)
			{
				if(j >= this.height-184 && j <= this.height-29)
				{
					if(k == 1)
						this.menuPhoto = !this.menuPhoto;
				}
			}
		}
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
    	drawImages(par1, par2, par3);
    	onMouseOverPhone(par1, par2);
    }
    
	public void drawImages(int par1, int par2, float par3)
	{
		this.drawRect(this.width-106+this.shift, this.height-183, this.width-14+this.shift, this.height-29, 0xff000000, 1);
		boolean doPop = false;
		boolean noImg = false;
		GL11.glPushMatrix();
			try
			{
				GL11.glEnable(GL11.GL_BLEND);
				String name = this.photos.get(this.image);
				File f = new File(this.mc.mcDataDir, Phonecraft.phoneFolder + "pictures/dcim/" + name);
				if(f.exists())
				{
					int sizeX = 256;
					int sizeY = 256;
					if(this.prevImage != this.image)
					{
						this.prevImage = this.image;
						Image image = ImageIO.read(f);
						this.buffered = (BufferedImage) image;
						this.texture = this.imgLoader.setupTexture(this.buffered);
					}
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture);
					if(this.rotate)
					{
						GL11.glTranslatef(this.getScreenPosX() + this.getScreenSizeX() - 0.5F, this.getScreenPosY(), 0);
						GL11.glRotatef(+90F, 0, 0, 1);
						GL11.glScalef(0.602F, 0.35500256F, 1);
					}
					else
					{
						GL11.glTranslatef(this.getScreenPosX() + 0.6F, this.getScreenPosY() * 1.35F, 0);
						GL11.glScalef(0.354F, 0.21230769F, 1);
					}
					GL11.glColor4f(1,  1,  1,  this.transparency);
					this.drawTexturedModalRect(0, 0, 0, 0, sizeX, sizeY);
				}
				else
					noImg = true;
				doPop = true;
			}
			catch(Exception ex)
			{
				doPop = noImg = true;
			}
			if(noImg)
			{
				this.drawString("pas d'image", this.width-60 -(this.getFont().getStringWidth("pas d'image")/2), this.height/2-40, 0xffd2d2d2, this.transparency);
				this.image = -1;
			}
			if(doPop)
				GL11.glPopMatrix();
			if(this.menuPhoto)
			{
				//barre head
				if(this.photos.size() != 0)
				{
					GL11.glPushMatrix();
						drawRect(this.width-106+this.shift, this.height-28-155, this.width-106+this.shift+92, this.height-28-155+7, 0xff222222, this.transparency);
						GL11.glTranslatef(this.width-56-0.5F,  this.height-28-157,  0);
						GL11.glScalef(0.5F, 0.5F, 1);
						this.drawString(String.valueOf(this.image+1)+"/"+String.valueOf(this.photos.size()), (int) (0-(this.getFont().getStringWidth(String.valueOf(this.image+1)+"/"+String.valueOf(this.photos.size()))/2F)/0.5F), 0, 0xffd2d2d2, this.transparency);
						GL11.glPopMatrix();
				}
				//fond boutons
				
				//buttons back
				GL11.glPushMatrix();
				GL11.glColor4f(1,  1,  1,  this.transparency-1.0F);
				
				this.drawGradientRect(0, 135, 92, 155, 0x323232, 0x111111, -1.0F);
				
				int[] ic = {22, 0, 9, 15};
				for(int t=0; t<ic.length; t++) {
					this.drawGradientRect(1 + (t * 23), 136, 22 + (t * 23), 154, 0x626262, 0x424242, -1.0F);
					this.drawIcon(ic[t], 3 + (t * 23), 137, 1F);
				}

				GL11.glPopMatrix();
				
//				GL11.glPushMatrix();
//					this.drawGradientRect(0, 135, 92, 155, 0xff323232, 0xff111111);
//					this.drawGradientRect(1, 136, 22, 154, 0xff626262, 0xff424242);
//					this.drawGradientRect(82, 136, 45, 154, 0xff626262, 0xff424242);
//					this.drawGradientRect(59, 136, 68, 154, 0xff626262, 0xff424242);
//					this.drawGradientRect(36, 136, 91, 154, 0xff626262, 0xff424242);
//					//icons boutons
//					this.mc.renderEngine.bindTexture(texturePhone);
//					GL11.glEnable(GL11.GL_BLEND);
//					GL11.glColor4f(1,  1,  1, this.transparency);
//					GL11.glTranslatef(this.width-103.5F+this.shift, this.height-43, 0);
//					for(int t=0; t<4; t++)
//						this.drawTexturedModalRect(0+(t*23), 0, 110+(t+2)*14, 0, 15, 10);
//				GL11.glPopMatrix();
			}
			this.drawRect(this.width-106+this.shift, this.height-183, this.width-14+this.shift, this.height-29, 0xff000000, 1f-this.transparency);
	}
	
	public List<String> getImagesAvaiable()
	{
		List<String> list = new ArrayList<String>();
		File dir = new File(this.mc.mcDataDir, Phonecraft.phoneFolder + "pictures/dcim/");
		if (!dir.exists())
			dir.mkdirs();
		for(File f : dir.listFiles())
		{
			if(f.getPath().contains(".png"))
				list.add(f.getName());
		}
		return list;
	}
    
    private void onMouseOverPhone(int x, int y)
    {
		if(this.isTactile && !this.mouseIsDrag && this.clickX >= 0)
		{
			if(-this.clickX+this.releaseX >40)
				this.image--;
			else if(-this.clickX+this.releaseX <-40)
				this.image++;
			this.isTactile = false;
			this.clickX = this.releaseX = this.releaseY = this.clickY = -1;
		}
		if(this.image < 0)
			this.image = this.photos.size()-1;
		if(this.image > this.photos.size()-1)
			this.image = 0;
    	if(this.isFocused)
    	{
    		this.bouton = -1;
    		if(!this.animPhoto)
    		{
    			if(x >= this.width-106 && x <= this.width-14)
    			{
    				if(y >= this.height-191 && y <= this.height-29)
    				{
    					if(this.menuPhoto)
    					{
    						for(int t=0; t<4; t++)
    						{
    							if(x >= this.width-106+(t*23) && x <= this.width-84+(t*23) && y >= this.height-48 && y <= this.height-31)
    							{
    								GL11.glPushMatrix();
    									this.drawGradientRect(1 + (t*23), 136, 22 + (t*23), 154, 0xff626262, 0x55000000, -1.5F);
    								GL11.glPopMatrix();
    								this.bouton = t+1;
    							}
    						}
    					}
    				}
    			}
//    			super.o
//    			if(x >= this.width-71 && x <= this.width-51)
//    			{
//    				if(y >= this.height-19 && y <= this.height-13)
//    				{
//    					GL11.glPushMatrix();
//    						GL11.glTranslatef(0.5F, 1.22F, 0);
//    						GL11.glEnable(GL11.GL_BLEND);
//    						GL11.glColor4f(1F,  1F,  1F,  0.3F);
//    						this.mc.renderEngine.bindTexture(texturePhone);
//    						this.drawTexturedModalRect(this.width-72+this.shift, this.height-19, 0, 414/2+6, 50, 6);
//    						GL11.glDisable(GL11.GL_BLEND);
//    					GL11.glPopMatrix();
//    					this.bouton = 0;
//    				}
//    			}
    		}
    	}
    }
}
