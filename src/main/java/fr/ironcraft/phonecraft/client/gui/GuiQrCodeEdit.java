package fr.ironcraft.phonecraft.client.gui;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.lang.reflect.Field;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.common.Blocks.BlockQrCode;
import fr.ironcraft.phonecraft.common.tileentities.TileEntityQrCode;
import fr.ironcraft.phonecraft.packet.PacketPipeline;
import fr.ironcraft.phonecraft.packet.PacketQrCode;
import fr.ironcraft.phonecraft.utils.CustomFont;
import fr.ironcraft.phonecraft.utils.PostFile;
import fr.ironcraft.phonecraft.utils.Qrcode;
//import fr.ironcraft.phonecraft.utils.PostFile;
//import fr.ironcraft.phonecraft.utils.QrCodeThread;
//import fr.ironcraft.phonecraft.utils.Qrcode;
//import fr.ironcraft.phonecraft.utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C17PacketCustomPayload;

/**
 * @author Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class GuiQrCodeEdit extends GuiScreen
{
	private Minecraft mc;
	private TileEntityQrCode tile;
	private GuiTextField tName;
	private GuiTextField tType;
	private GuiTextField[] tDescription = new GuiTextField[6];
	private GuiCheckBox[] types = new GuiCheckBox[5];
	private boolean color;
	private Color forground = Color.BLACK;
	private Color background = Color.WHITE;
	private GuiCheckBox[] QrColors = new GuiCheckBox[24];
	private String[] colors = {"WHITE", "GRAY", "DARK_GRAY", "BLACK", "RED", "BLUE", "YELLOW", "CYAN", "PINK", "MAGENTA", "ORANGE", "GREEN"};
	private Qrcode decoder;
	
	public GuiQrCodeEdit(Minecraft par0, TileEntityQrCode par1)
	{
		this.mc = par0;
		this.tile = par1;
		this.decoder = new Qrcode();
	}
	
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		this.buttonList.add(new GuiButton(0, this.width - 130, this.height - 60, 50, 20, I18n.format("gui.qrcode.ok", new Object[0])));
		this.buttonList.add(new GuiButton(1, this.width - 77, this.height - 60, 50, 20, I18n.format("gui.qrcode.cancel", new Object[0])));
		this.tName = new GuiTextField(this.fontRendererObj, 39, 50, 200, 15);
		this.tName.setTextColor(0xffb2b2b2);
		this.tName.setMaxStringLength(32);
		for (int i=0; i<this.tDescription.length; i++)
		{
			this.tDescription[i] = new GuiTextField(this.fontRendererObj, 43, this.height - 135 + i*15, 200, 15);
			this.tDescription[i].setEnableBackgroundDrawing(false);
			this.tDescription[i].setTextColor(0xffb2b2b2);
			this.tDescription[i].setMaxStringLength(32);
		}
		int dec = 0;
		for (int i=0; i<this.types.length - 1; i++)
		{
			this.types[i] = new GuiCheckBox(2 + i, this.width - 118, 51 + i*20);
			this.buttonList.add(this.types[i]);
		}
		this.types[4] = new GuiCheckBox(2 + 4, this.width - 118, this.height - 90);
		this.buttonList.add(this.types[4]);
		this.types[0].checked = true;
		for (int i=0; i < 6; i++)
		{
			this.QrColors[i] = new GuiCheckBox(7 + i, 45 + i * 40, 51);
			this.buttonList.add(this.QrColors[i]);
		}
		for (int i=0; i < 6; i++)
		{
			this.QrColors[i + 6] = new GuiCheckBox(13 + i, 45 + i * 40, 51 + 20);
			this.buttonList.add(this.QrColors[i + 6]);
		}
		for (int i=0; i < 6; i++)
		{
			this.QrColors[i + 12] = new GuiCheckBox(19 + i, 45 + i * 40, 51 + 80);
			this.buttonList.add(this.QrColors[i + 12]);
		}
		for (int i=0; i < 6; i++)
		{
			this.QrColors[i + 18] = new GuiCheckBox(25 + i, 45 + i * 40, 51 + 100);
			this.buttonList.add(this.QrColors[i + 18]);
		}
		for (GuiCheckBox g : QrColors)
			g.visible = false;
		if (!this.tile.data.equals(""))
		{
			String test = this.tile.data;
			String[] infos = test.split("\n");
			for (int i=0; i<infos.length; i++)
				infos[i] = infos[i];
			this.tName.setText(infos[0]);
			for (int i=0; i<this.tDescription.length; i++)
				this.tDescription[i].setText(infos[i + 1]);
			for (int i=0; i<this.types.length - 1; i++)
			{
				this.types[i].checked = false;
				if (i == Integer.parseInt(infos[7]) - 1)
					this.types[i].checked = true;
			}
			if (infos[8].equals("1"))
				this.types[this.types.length - 1].checked = true;
			for (int i=0; i < this.colors.length; i++)
			{
				if (infos[infos.length - 2].equals(this.colors[i]))
					this.QrColors[i].checked = true;
				if (infos[infos.length - 1].equals(this.colors[i]))
					this.QrColors[i + 12].checked = true;
			}
		}
		else
		{
			this.QrColors[0].checked = true;
			this.QrColors[15].checked = true;
		}
	}
	
	public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
	
	public void updateScreen()
	{
		
	}
	
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	public void keyTyped(char par1, int par2)
	{
		for (int x=0; x<6; x++)
		{
			if (this.tDescription[x].isFocused())
			{
				int pos = this.tDescription[x].getCursorPosition();
				if (par2 == Keyboard.KEY_RETURN && x < 5 && this.tDescription[x].getText().length() != 0)
				{
					this.tDescription[x].setFocused(false);
					this.tDescription[x + 1].setFocused(true);
					if (pos < this.tDescription[x].getText().length())
					{
						this.tDescription[x + 1].setText(this.tDescription[x].getText().substring(pos));
						this.tDescription[x].setText(this.tDescription[x].getText().substring(0, pos));
						this.tDescription[x + 1].setCursorPositionZero();
					}
					return;
				}
				else if (par2 == Keyboard.KEY_DOWN && x < 5 && this.tDescription[x].getText().length() != 0)
				{
					this.tDescription[x].setFocused(false);
					this.tDescription[x + 1].setFocused(true);
					return;
				}
				else if (par2 == Keyboard.KEY_UP && x > 0)
				{
					this.tDescription[x].setFocused(false);
					this.tDescription[x - 1].setFocused(true);
					return;
				}
				else if (par2 == Keyboard.KEY_RIGHT && x < 5 && pos == this.tDescription[x].getText().length() && !this.tDescription[x].getText().equals(""))
				{
					this.tDescription[x].setFocused(false);
					this.tDescription[x + 1].setFocused(true);
					return;
				}
				else if (par2 == Keyboard.KEY_LEFT && x > 0 && pos == 0)
				{
					this.tDescription[x].setFocused(false);
					this.tDescription[x - 1].setFocused(true);
					this.tDescription[x - 1].setCursorPositionEnd();
					return;
				}
				else if (par2 == Keyboard.KEY_BACK && x > 0 && pos == 0)
				{
					this.tDescription[x].setFocused(false);
					this.tDescription[x - 1].setFocused(true);
					this.tDescription[x - 1].setCursorPositionEnd();
					int posnext = this.tDescription[x - 1].getCursorPosition();
					if (pos == 0 && this.tDescription[x].getText().length() != 0)
					{
						this.tDescription[x - 1].setText(this.tDescription[x - 1].getText() + this.tDescription[x].getText());
						this.tDescription[x].setText("");
						this.tDescription[x - 1].setCursorPosition(posnext);
					}
					return;
				}
				else if (this.tDescription[x].getText().length() == this.tDescription[x].getMaxStringLength() && x < 5)
				{
					if (par2 != Keyboard.KEY_LEFT && par2 != Keyboard.KEY_BACK && par2 != Keyboard.KEY_RETURN && par2 != Keyboard.KEY_UP && par2 != Keyboard.KEY_DOWN)
					{
						this.tDescription[x].setFocused(false);
						this.tDescription[x + 1].setFocused(true);
					}
				}
				this.tDescription[x].textboxKeyTyped(par1, par2);
				return;
			}
		}
		if (this.tName.isFocused())
			this.tName.textboxKeyTyped(par1, par2);
		else
			super.keyTyped(par1, par2);
	}

	public void handleMouseInput()
	{
		super.handleMouseInput();
	}

	public void mouseClicked(int i, int j, int k)
	{
		this.tName.setFocused(false);
		this.tName.mouseClicked(i, j, k);
		for (int y=0; y<this.tDescription.length; y++)
			this.tDescription[y].setFocused(false);
		for (int x=0; x<this.tDescription.length; x++)
				this.tDescription[x].mouseClicked(i, j, k);
		boolean focus = false;
		int nbfocus = -1;
		for (int x=0; x<this.tDescription.length; x++)
		{
			if (x != 0 && this.tDescription[x].isFocused())
			{
				focus = true;
				nbfocus = x;
				break;
			}
		}
		if (focus && nbfocus >= 0)
		{
			if (this.tDescription[0].getText().length() == 0)
			{
				for (int y=0; y<this.tDescription.length; y++)
					this.tDescription[y].setFocused(false);
				this.tDescription[0].setFocused(true);
			}
			else if (nbfocus > 1 && this.tDescription[nbfocus - 1].getText().length() == 0)
			{
				for (int y=0; y<this.tDescription.length; y++)
					this.tDescription[y].setFocused(false);
				while (this.tDescription[nbfocus - 1].getText().length() == 0 && nbfocus > 0)
				{
					this.tDescription[nbfocus - 1].setFocused(true);
					this.tDescription[nbfocus].setFocused(false);
					nbfocus--;
				}
			}
		}
		if (i >= 21 && i <= 90 && j >= 2 && j <= 20)
		{
			this.color = false;
			tName.setVisible(true);
			tName.setEnabled(true);
			for (GuiTextField g : tDescription)
			{
				g.setVisible(true);
				g.setEnabled(true);
			}
			for (GuiCheckBox g : types)
				g.visible = true;
			for (GuiCheckBox g : QrColors)
				g.visible = false;
		}
		else if (i >= 91 && i <= 160 && j >= 2 && j <= 20)
		{
			this.color = true;
			tName.setVisible(false);
			tName.setEnabled(false);
			for (GuiTextField g : tDescription)
			{
				g.setVisible(false);
				g.setEnabled(false);
			}
			for (GuiCheckBox g : types)
				g.visible = false;
			for (GuiCheckBox g : QrColors)
				g.visible = true;
			for (int l=12; l<24; l++)
				this.QrColors[l].visible = false;
			for(int l=0; l < 12; l++)
			{
				if (this.QrColors[l].checked)
				{
					hideColor(l);
					break;
				}
			}
		}
		super.mouseClicked(i, j, k);
	}
	
	private void hideColor(int slot)
	{
		Color a;
		try
		{
		    Field field = Color.class.getField(this.colors[slot]);
		    a = (Color)field.get(null);
		}
		catch (Exception e)
		{
			a = Color.WHITE;
		}
		double lumA = 0.299 * a.getRed() + 0.587 * a.getGreen() + 0.114 * a.getBlue();
		int l = 12;
		double lumB;
		for (String c : this.colors)
		{
			Color b;
			try
			{
			    Field field = Color.class.getField(c);
			    b = (Color)field.get(null);
			}
			catch (Exception e)
			{
				b = Color.WHITE;
			}
			lumB = 0.299 * b.getRed() + 0.587 * b.getGreen() + 0.114 * b.getBlue();
			System.out.println(c + ": " + lumB);
			if(Math.max(lumA, lumB) - Math.min(lumA, lumB) > 110)
				this.QrColors[l].visible = true;
			l++;
		}
	}
	
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
		{
			int wName = this.tile.getWorldObj().provider.dimensionId;
			int type = 0;
			int teleportation = this.types[4].checked ? 1 : 0;
			for (int i=0; i<types.length - 1; i++)
			{
				if (types[i].checked)
				{
					type = i + 1;
					break;
				}
			}
			for(GuiTextField g : this.tDescription)
			{
				if (g.getText().length() == 0)
					g.setText(" ");
			}
			String fg = " ", bg = " ";
			for (int i=0; i < 24; i++)
			{
				if (this.QrColors[i].checked)
				{
					if (i < 12)
						bg = this.colors[i];
					else
						fg = this.colors[i - 12];
				}
			}
			String data = 
					this.tName.getText() + "\n" +
					this.tDescription[0].getText() + "\n" +
					this.tDescription[1].getText() + "\n" +
					this.tDescription[2].getText() + "\n" +
					this.tDescription[3].getText() + "\n" +
					this.tDescription[4].getText() + "\n" +
					this.tDescription[5].getText() + "\n" +
					type + "\n" +
					teleportation + "\n" +
					this.tile.xCoord + "\n" +
					this.tile.yCoord + "\n" + 
					this.tile.zCoord + "\n" +
					bg + "\n" +
					fg;
			String name = this.tile.xCoord + "_" + this.tile.yCoord + "_" + this.tile.zCoord + ".png";
			File qrcode = this.decoder.encode(new File(this.mc.mcDataDir, "PhoneCraft/" + name), data, "png", bg, fg);
			new Thread(new PostFile(qrcode)).start();

            Side side = FMLCommonHandler.instance().getEffectiveSide();
            if (side == Side.CLIENT)
            	Phonecraft.packetPipeline.sendToServer(new PacketQrCode(wName, this.tile.xCoord, this.tile.yCoord, this.tile.zCoord, data));
			this.tile.data = data;
			this.tile.texture = Phonecraft.urlFiles + "/qrcodes/" + name;
		}
		if (guibutton.id < 2)
			this.mc.thePlayer.closeScreen();
		if (guibutton.id >= 2 && guibutton.id <= 5)
		{
			for (int i=0; i<4; i++)
				this.types[i].checked = false;
		}
		if (guibutton.id == 2)
			this.types[0].checked = true;
		else if (guibutton.id == 3)
			this.types[1].checked = true;
		else if (guibutton.id == 4)
			this.types[2].checked = true;
		else if (guibutton.id == 5)
			this.types[3].checked = true;
		else if (guibutton.id == 6)
			this.types[4].checked = !this.types[4].checked;
		if (guibutton.id >= 7 && guibutton.id <= 18)
		{
			for (int i=0; i<12; i++)
				this.QrColors[i].checked = false;
			for (int i=12; i<24; i++)
				this.QrColors[i].visible = false;
			hideColor(guibutton.id - 7);
		}
		if (guibutton.id >= 19 && guibutton.id <= 30)
		{
			for (int i=0; i<12; i++)
				this.QrColors[i + 12].checked = false;
		}
		if (guibutton.id >= 7)
			this.QrColors[guibutton.id - 7].checked = true;
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{

		this.drawRoundedRect(13, 20, this.width - 13, this.height - 30, 10, 0xff757575, 1.0F);
		this.drawRoundedRect(15, 22, this.width - 15, this.height - 32, 10, 0xff454545, 1.0F);
		
		this.drawRoundedRect(21, 2, 90, 15, 4, 0xff757575, 1.0F);
		this.drawRect(21, 10, 90, 20, 0xff757575);
		this.drawRoundedRect(24, 5, 87, 15, 4, 0xff454545, 1.0F);
		this.drawRect(24, 10, 87, this.color ? 20 : 22, 0xff454545);
		
		this.drawRoundedRect(91, 2, 160, 15, 4, 0xff757575, 1.0F);
		this.drawRect(91, 10, 160, 20, 0xff757575);
		this.drawRoundedRect(94, 5, 157, 15, 4, 0xff454545, 1.0F);
		this.drawRect(94, 10, 157, this.color ? 22 : 20, 0xff454545);
		
		this.drawString(this.fontRendererObj, "Infos", 32, 8, 0xffffffff);
		this.drawString(this.fontRendererObj, "Colors", 104, 8, 0xffffffff);
		
		if (!color)
		{
			this.drawRoundedRect(29, 27, 250, 72, 8, 0xff757575, 1.0F);
			this.drawRoundedRect(30, 28, 249, 71, 8, 0xff252525, 1.0F);
			this.drawHorizontalLine(29, 248, 43, 0xff757575);
			this.drawRoundedRect(this.width - 128, 27, this.width - 29, 126, 8, 0xff757575, 1.0F);
			this.drawRoundedRect(this.width - 127, 28, this.width - 30, 125, 8, 0xff252525, 1.0F);
			this.drawHorizontalLine(this.width - 127, this.width - 30, 43, 0xff757575);
			this.drawRoundedRect(this.width - 128, this.height - 100, this.width - 29, this.height - 74, 8, 0xff757575, 1.0F);
			this.drawRoundedRect(this.width - 127, this.height - 99, this.width - 30, this.height - 75, 8, 0xff252525, 1.0F);
			this.drawRoundedRect(29, this.height - 160, 250, this.height - 40, 8, 0xff757575, 1.0F);
			this.drawRoundedRect(30, this.height - 159, 249, this.height - 41, 8, 0xff252525, 1.0F);
			this.drawHorizontalLine(29, 248, this.height - 144, 0xff757575);
			this.drawRect(38, this.height - 138, 240, this.height - 47, 0xffa2a2a2);
			this.drawRect(39, this.height - 137, 239, this.height - 48, 0xff000000);
			try
			{
				this.tName.drawTextBox();
				for (int i=0; i<tDescription.length; i++)
					this.tDescription[i].drawTextBox();
			}
			catch(Exception ex){}
			this.drawString(this.fontRendererObj, I18n.format("gui.qrcode.title", new Object[0]) + " :", 39, 32, 0xffffffff);
			this.drawString(this.fontRendererObj, I18n.format("gui.qrcode.description", new Object[0]) + " :", 39, this.height - 155, 0xffffffff);
			this.drawString(this.fontRendererObj, I18n.format("gui.qrcode.types", new Object[0]) + " :", this.width - 115, 32, 0xffffffff);
			this.drawString(this.fontRendererObj, I18n.format("gui.qrcode.info", new Object[0]), this.width - 101, 50, 0xffb2b2b2);
			this.drawString(this.fontRendererObj, I18n.format("gui.qrcode.event", new Object[0]), this.width - 101, 70, 0xffb2b2b2);
			this.drawString(this.fontRendererObj, "...", this.width - 101, 90, 0xffb2b2b2);
			this.drawString(this.fontRendererObj, I18n.format("gui.qrcode.bonus", new Object[0]), this.width - 101, 110, 0xffb2b2b2);
			this.drawString(this.fontRendererObj, I18n.format("gui.qrcode.teleportation", new Object[0]), this.width - 101, this.height - 91, 0xffb2b2b2);
		}
		else
		{
			this.drawRoundedRect(29, 27, 290, 85, 8, 0xff757575, 1.0F);
			this.drawRoundedRect(30, 28, 289, 84, 8, 0xff252525, 1.0F);
			this.drawHorizontalLine(29, 288, 43, 0xff757575);
			this.drawRoundedRect(29, 27 + 80, 290, 85 + 80, 8, 0xff757575, 1.0F);
			this.drawRoundedRect(30, 28 + 80, 289, 84 + 80, 8, 0xff252525, 1.0F);
			this.drawHorizontalLine(29, 288, 43 + 80, 0xff757575);
			this.drawString(this.fontRendererObj, "Background :", 38, 32, 0xffffffff);
			this.drawString(this.fontRendererObj, "Forground :", 38, 112, 0xffffffff);
			for(int i=0; i < 6; i++)
			{
				for(int j=0; j < 2; j++)
					this.drawRect(60 + i * 40, 48 + j * 20, 72 + i * 40, 60 + j * 20, 0xff757575);
			}
			for(int i=0; i < 6; i++)
			{
				for(int j=0; j < 2; j++)
					this.drawRect(60 + i * 40, 48 + j * 20 + 80, 72 + i * 40, 60 + j * 20 + 80, 0xff757575);
			}
			this.drawRect(61, 49, 71, 59, 0xffffffff);
			this.drawRect(61, 49 + 80, 71, 59 + 80, 0xffffffff);
			int i = 1;
			this.drawRect(61 + 40 * i, 49, 71 + 40 * i, 59, 0xffa2a2a2);
			this.drawRect(61 + 40 * i, 49 + 80, 71 + 40 * i, 59 + 80, 0xffa2a2a2);
			i++;
			this.drawRect(61 + 40 * i, 49, 71 + 40 * i, 59, 0xff424242);
			this.drawRect(61 + 40 * i, 49 + 80, 71 + 40 * i, 59 + 80, 0xff424242);
			i++;
			this.drawRect(61 + 40 * i, 49, 71 + 40 * i, 59, 0xff000000);
			this.drawRect(61 + 40 * i, 49 + 80, 71 + 40 * i, 59 + 80, 0xff000000);
			i++;
			this.drawRect(61 + 40 * i, 49, 71 + 40 * i, 59, 0xffff0000);
			this.drawRect(61 + 40 * i, 49 + 80, 71 + 40 * i, 59 + 80, 0xffff0000);
			i++;
			this.drawRect(61 + 40 * i, 49, 71 + 40 * i, 59, 0xff0000ff);
			this.drawRect(61 + 40 * i, 49 + 80, 71 + 40 * i, 59 + 80, 0xff0000ff);
			i++;
			this.drawRect(61, 69, 71, 79, 0xffffff00);
			this.drawRect(61, 69 + 80, 71, 79 + 80, 0xffffff00);
			i = 1;
			this.drawRect(61 + 40 * i, 69, 71 + 40 * i, 79, 0xff00ffff);
			this.drawRect(61 + 40 * i, 69 + 80, 71 + 40 * i, 79 + 80, 0xff00ffff);
			i++;
			this.drawRect(61 + 40 * i, 69, 71 + 40 * i, 79, 0xffffaaff);
			this.drawRect(61 + 40 * i, 69 + 80, 71 + 40 * i, 79 + 80, 0xffffaaff);
			i++;
			this.drawRect(61 + 40 * i, 69, 71 + 40 * i, 79, 0xffaa00aa);
			this.drawRect(61 + 40 * i, 69 + 80, 71 + 40 * i, 79 + 80, 0xffaa00aa);
			i++;
			this.drawRect(61 + 40 * i, 69, 71 + 40 * i, 79, 0xffffaa00);
			this.drawRect(61 + 40 * i, 69 + 80, 71 + 40 * i, 79 + 80, 0xffffaa00);
			i++;
			this.drawRect(61 + 40 * i, 69, 71 + 40 * i, 79, 0xff00ff00);
			this.drawRect(61 + 40 * i, 69 + 80, 71 + 40 * i, 79 + 80, 0xff00ff00);
			i++;
		}
		super.drawScreen(par1, par2, par3);
	}
	
	protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6, float trans)
	{
		float var7 = (float)(par5 >> 24 & 255) / 255.0F;
		float var8 = (float)(par5 >> 16 & 255) / 255.0F;
		float var9 = (float)(par5 >> 8 & 255) / 255.0F;
		float var10 = (float)(par5 & 255) / 255.0F;
		float var11 = (float)(par6 >> 24 & 255) / 255.0F;
		float var12 = (float)(par6 >> 16 & 255) / 255.0F;
		float var13 = (float)(par6 >> 8 & 255) / 255.0F;
		float var14 = (float)(par6 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, trans);
		var15.addVertex((double)par3, (double)par2, (double)this.zLevel);
		var15.addVertex((double)par1, (double)par2, (double)this.zLevel);
		var15.setColorRGBA_F(var12, var13, var14, trans);
		var15.addVertex((double)par1, (double)par4, (double)this.zLevel);
		var15.addVertex((double)par3, (double)par4, (double)this.zLevel);
		var15.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	protected void drawRoundedGradientRect(int x, int y, int x1, int y1, int color, int color2, int radius, float trans)
	{
		int newX = Math.abs(x+radius);
		int newY = Math.abs(y+radius);
		int newX1 = Math.abs(x1-radius);
		int newY1 = Math.abs(y1-radius);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		drawRect(newX, newY, newX1, newY1, color, trans);
		drawRect(x, newY, newX, newY1, color, trans);
		drawRect(newX1, newY, x1, newY1, color, trans);
		drawRect(newX, y, newX1, newY, color2, trans);
		drawRect(newX, newY1, newX1, y1, color2, trans);
		drawQuarterCircle(newX+1,newY,radius,0,color, trans);
		drawQuarterCircle(newX1,newY,radius,1,color, trans);
		drawQuarterCircle(newX+1,newY1,radius,2,color2, trans);
		drawQuarterCircle(newX1,newY1,radius,3,color2, trans);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	protected void drawRect(int par0, int par1, int par2, int par3, int par4, float trans)
	{
		int var5;
		if (par0 < par2)
		{
			var5 = par0;
			par0 = par2;
			par2 = var5;
		}
		if (par1 < par3)
		{
			var5 = par1;
			par1 = par3;
			par3 = var5;
		}
		float var6 = (float)(par4 >> 16 & 255) / 255.0F;
		float var7 = (float)(par4 >> 8 & 255) / 255.0F;
		float var8 = (float)(par4 & 255) / 255.0F;
		Tessellator var9 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(var6, var7, var8, trans);
		var9.startDrawingQuads();
		var9.addVertex((double)par0, (double)par3, 0.0D);
		var9.addVertex((double)par2, (double)par3, 0.0D);
		var9.addVertex((double)par2, (double)par1, 0.0D);
		var9.addVertex((double)par0, (double)par1, 0.0D);
		var9.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void drawRoundedRect(int x, int y, int x1, int y1, int radius, int color, float trans)
	{
		int newX = Math.abs(x+radius);
		int newY = Math.abs(y+radius);
		int newX1 = Math.abs(x1-radius);
		int newY1 = Math.abs(y1-radius);
		drawRect(newX, newY, newX1, newY1, color, trans);
		drawRect(x, newY, newX, newY1, color, trans);
		drawRect(newX1, newY, x1, newY1, color, trans);
		drawRect(newX, y, newX1, newY, color, trans);
		drawRect(newX, newY1, newX1, y1, color, trans);
		drawQuarterCircle(newX,newY,radius,0,color, trans);
		drawQuarterCircle(newX1,newY,radius,1,color, trans);
		drawQuarterCircle(newX,newY1,radius,2,color, trans);
		drawQuarterCircle(newX1,newY1,radius,3,color, trans);
	}

	public void drawQuarterCircle(int x, int y, int radius, int mode, int color, float trans)
	{
		float var6 = (float)(color >> 16 & 255) / 255.0F;
		float var7 = (float)(color >> 8 & 255) / 255.0F;
		float var8 = (float)(color & 255) / 255.0F;
		disableDefaults();
		GL11.glColor4d(var6, var7, var8, trans);
		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex2d(x, y);
		int start = 0;
		int xradius = radius;
		int yradius = radius;
		switch (mode)
		{
		case 0:
			xradius = yradius = -radius;
			break;
		case 1:
			start = 90;
			break;
		case 2:
			start = 90;
			xradius = yradius = -radius;
			break;
		case 3:
		}
		for (int i = start; i <= start + 90; i++)
		{
			double theta_radian = i*Math.PI / 180D;
			GL11.glVertex2d(x + Math.sin(theta_radian) * xradius, y + Math.cos(theta_radian) * yradius);
		}
		GL11.glEnd();
		enableDefaults();
	}

	public void setupOverlayRendering()
	{
		GL11.glClear(256);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, this.width, this.height, 0.0D, 1000D, 3000D);
		GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000F);
	}
	
	public void disableDefaults()
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public void enableDefaults()
	{
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}