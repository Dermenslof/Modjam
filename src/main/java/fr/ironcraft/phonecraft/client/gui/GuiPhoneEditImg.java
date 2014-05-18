package fr.ironcraft.phonecraft.client.gui;

import java.io.File;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.client.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

/**
 * @author Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class GuiPhoneEditImg extends GuiPhoneInGame
{
	private GuiTextField textField;
	private boolean editImg;
	private File file;

	public GuiPhoneEditImg(Minecraft par1Minecraft, File file)
	{
		super(par1Minecraft);
		this.file = file;
	}

	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.textField = new GuiTextField(this.fontRendererObj, this.width - 104 + this.shift, this.height - 158, 88, 63);
		this.textField.setEnableBackgroundDrawing(false);
		this.textField.setTextColor(0xd2d2d2);
		this.textField.setVisible(true);
		this.textField.setText(this.file.getName().replace(".png", ""));
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
			this.mc.displayGuiScreen(new GuiIngameMenu());
		else if (par2 == KeyHandler.key_PhoneGUI.getKeyCode())
		{
			this.app = 0;
			this.mc.displayGuiScreen(new GuiPhoneImages(this.mc));
		}
		else if(par2 == Keyboard.KEY_RETURN && !this.textField.getText().equals(this.file.getName()))
			this.file.renameTo(new File(Phonecraft.phoneFolder + "pictures/dcim/"+this.textField.getText()+".png"));
		this.textField.textboxKeyTyped(par1, par2);
	}

	public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		if (!this.mc.inGameHasFocus)
		{
			this.textField.setFocused(false);
			this.textField.mouseClicked(i, j, k);
			switch(this.bouton)
			{
			case 0:
				this.app = 0;
				this.mc.displayGuiScreen(new GuiPhoneImages(this.mc));
				break;
			case 1:
				System.out.println(this.bouton);
				break;
			case 2:
				System.out.println(this.bouton);
				break;
			case 3:
				System.out.println(this.bouton);
				break;
			case 4:
				System.out.println(this.bouton);
				break;
			case 5:
				System.out.println(this.bouton);
				break;
			case 6:
				System.out.println(this.bouton);
				break;
			case 7:
				System.out.println(this.bouton);
				break;
			case 8:
				System.out.println(this.bouton);
				break;
			case 9:
				System.out.println(this.bouton);
				break;
			case 10:
				System.out.println(this.bouton);
				break;
			case 11:
				System.out.println(this.bouton);
				break;
			case 12:
				System.out.println(this.bouton);
				break;
			case 13:
				System.out.println(this.bouton);
				if(!this.textField.getText().equals(this.file.getName()))
					this.file.renameTo(new File(Phonecraft.phoneFolder + "pictures/dcim/"+this.textField.getText()+".png"));
				break;
			case 14:
				this.app = 0;
				this.mc.displayGuiScreen(new GuiPhoneImages(this.mc));
				break;
			case 15:
				this.textField.setText(this.textField.getText().substring(0, this.textField.getText().length()-1));
				break;
			}
		}
	}

	public void handleMouseInput()
	{
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		if(Mouse.getEventButtonState())
			this.mouseClicked(x, y, Mouse.getEventButton());
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		this.textField.setFocused(true);
		super.drawScreen(par1, par2, par3);
		this.drawRect(this.width - 106 + this.shift, this.height - 183, this.width - 14 + this.shift, this.height - 29, 0xff000000, 1);
		GL11.glPushMatrix();
			GL11.glTranslatef(-0.2F,  0,  0);
			GL11.glScalef(1.001F,  1,  1);
			this.drawRect(this.width - 106, this.height - 110, this.width - 14, this.height - 28, 0xff424242, this.transparency);
			this.drawGradientRect(0, 73, 92, 155, 0xff424242, 0xff222222);
			GL11.glTranslatef(0.9F,  0,  0);
			int num = 1;
			for(int y=0; y<5; y++)
			{
				for(int x=0; x<3; x++)
				{
					if(num == 13)
					{
						this.drawRoundedRect(2 + (x * 30), 78 + (y * 15), 2 + 26 + (x * 30), 78 + 12 + (y * 15), 2, 0xff007700, 0);
						this.drawGradientRect(2 + (x * 30), 78 + (y * 15) + 7, 2 + 26 + (x * 30), 78 + 12 + (y * 15), 0xff007700, 0xaa222222);
					}
					else if(num == 14)
					{
						this.drawRoundedRect(2 + (x * 30), 78 + (y * 15), 2 + 26 + (x * 30), 78 + 12 + (y * 15), 2, 0xff770000, 0);
						this.drawGradientRect(2 + (x * 30), 78 + (y * 15) + 7, 2 + 26 + (x * 30), 78 + 12 + (y * 15), 0xff770000, 0xaa222222);
					}
					else
					{
						this.drawRoundedRect(2 + (x * 30), 78 + (y * 15), 2 + 26 + (x * 30), 78 + 12 + (y * 15), 2, 0xffd2d2d2, 0);
						this.drawGradientRect(2 + (x * 30), 78 + (y * 15) + 7, 2 + 26 + (x * 30), 78 + 12 + (y * 15), 0x55d2d2d2, 0xaa222222);
					}
					num++;
				}
			}
		GL11.glPopMatrix();
		onMouseOverPhone(par1, par2);
		num = 1;
		for(int y=0; y<3; y++)
		{
			for(int x=0; x<3; x++)
			{
				this.drawString(String.valueOf(num), this.width - 102 + (x * 30), this.height - 110 + (y * 15), 0xff000000, this.transparency);
				num++;
			}
		}
		for(int x=0; x<3; x++)
		{
			if(x == 0)
				this.drawString("#", this.width - 102 + (x * 30), this.height - 110 + (3 * 15), 0xff000000, this.transparency);
			else if(x == 1)
				this.drawString("0", this.width - 102 + (x * 30), this.height - 110 + (3 * 15), 0xff000000, this.transparency);
			else if(x == 2)
				this.drawString("*", this.width - 102 + (x * 30), this.height - 110 + (3 * 15), 0xff000000, this.transparency);
		}
		for(int x=0; x<3; x++)
		{
			if(x == 0)
				this.drawString("", this.width - 102 + (x * 30), this.height - 95 + (3 * 15), 0xff000000, this.transparency);
			else if(x == 1)
				this.drawString("", this.width - 102 + (x * 30), this.height - 95 + (3 * 15), 0xff000000, this.transparency);
			else if(x == 2)
				this.drawString("", this.width - 102 + (x * 30), this.height - 95 + (3 * 15), 0xff000000, this.transparency);
		}
		num = 1;
		for(int y=0; y<3; y++)
		{
			for(int x=0; x<3; x++)
			{
				GL11.glPushMatrix();
					GL11.glTranslatef(this.width - 92.5F+(x * 30),  this.height - 104 + (y * 15),  0);
					GL11.glScalef(0.5F,  0.5F,  1);
					switch(num)
					{
					case 2:
						this.drawString("ABC", 5, 0, 0xff434343, this.transparency);
						break;
					case 3:
						this.drawString("DEF", 5, 0, 0xff434343, this.transparency);
						break;
					case 4:
						this.drawString("GHI", 7, 0, 0xff434343, this.transparency);
						break;
					case 5:
						this.drawString("JKL", 7, 0, 0xff434343, this.transparency);
						break;
					case 6:
						this.drawString("MNO", 3, 0, 0xff434343, this.transparency);
						break;
					case 7:
						this.drawString("PQRS", 0, 0, 0xff434343, this.transparency);
						break;
					case 8:
						this.drawString("TUV", 5, 0, 0xff434343, this.transparency);
						break;
					case 9:
						this.drawString("WXYZ", 0, 0, 0xff434343, this.transparency);
						break;
					}
				GL11.glPopMatrix();
				num++;
			}
		}
		this.drawRect(this.width - 106+this.shift, this.height - 183, this.width - 14 + this.shift, this.height - 29, 0xff000000, 1F - this.transparency);
		this.drawString("nom de l'image:", this.width - 104 + this.shift, this.height - 185, 0xffd2d2d2, this.transparency);
		this.textField.drawTextBox();
	}

	private void onMouseOverPhone(int x, int y)
	{
		if(this.isFocused)
		{
			this.bouton = -1;
			if(x >= this.width - 106 && x <= this.width - 14)
			{
				if(y >= this.height - 191 && y <= this.height - 29)
				{
					int num = 1;
					for(int Y=0; Y<5; Y++)
					{
						for(int X=0; X<3; X++)
						{
							if(x >= this.width - 103 + (X * 30) && x <= this.width - 104 + 26 + (X * 30) && y >= this.height - 105+(Y * 15) && y <= this.height - 105 + 12 + (Y * 15)){
								GL11.glPushMatrix();
									GL11.glTranslatef(0.9F,  0,  0);
									this.drawRoundedRect(2 + (X * 30), 78 + (Y * 15), 2 + 26 + (X * 30), 78 + 12 + (Y * 15), 2, 0x88000000, -0.8F);
								GL11.glPopMatrix();
								this.bouton = num;
							}
							num++;
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
						GL11.glColor4f(1F,  1F,  1F,  0.3F);
						this.mc.renderEngine.bindTexture(texturePhone);
						this.drawTexturedModalRect(this.width - 72 + this.shift, this.height - 19, 0, 414/2 + 6, 50, 6);
						GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					this.bouton = 0;
				}
			}
		}
	}    
}
