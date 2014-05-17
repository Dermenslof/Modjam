package fr.ironcraft.phonecraft.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import fr.ironcraft.phonecraft.client.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * @authors Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class CustomFont
{
	private int texID;
	private int[] xPos;
	private int[] yPos;
	private int startChar;
	private int endChar;
	private FontMetrics metrics;

	public CustomFont(Minecraft mc, Object font, int size)
	{
		this(mc, font, size, 0, 512);
	}

	public CustomFont(Minecraft mc, Object font, int size, int startChar, int endChar)
	{
		this.startChar = startChar;
		this.endChar = endChar;
		xPos = new int[endChar-startChar];
		yPos = new int[endChar-startChar];

		BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		try
		{
			if (font instanceof String)
			{
				String fontName = (String)font;
				if (fontName.contains("/"))
					g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontName)).deriveFont((float)size));
				else
					g.setFont(new Font(fontName, 0, size));
			}
			else if (font instanceof InputStream)
				g.setFont(Font.createFont(Font.TRUETYPE_FONT, (InputStream)font).deriveFont((float)size));
			else if (font instanceof File)
				g.setFont(Font.createFont(Font.TRUETYPE_FONT, (File)font).deriveFont((float)size));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		g.setColor(new Color(255, 255, 255, 0));
		g.fillRect(0, 0, 256, 256);
		g.setColor(Color.white);
		metrics = g.getFontMetrics();
		int x = 2;
		int y = 2;
		for (int i=startChar; i<endChar; i++)
		{
			g.drawString(""+((char)i), x, y + g.getFontMetrics().getAscent());
			xPos[i-startChar] = x;
			yPos[i-startChar] = y - metrics.getMaxDescent();
			x += metrics.stringWidth(""+(char)i) + 2;
			if (x >= 250-metrics.getMaxAdvance())
			{
				x = 2;
				y += metrics.getMaxAscent() + metrics.getMaxDescent() + size / 2;
			}
		}
		texID = ClientProxy.imageLoader.setupTexture(img);
	}

	public void drawStringS(Gui gui, String text, int x, int y, int color)
	{
		int l = color;
		int shade = (color & 0xfcfcfc) >> 2;
		shade += l;
		drawString(gui, text, x+1, y+1, shade);
		drawString(gui, text, x, y, color);
	}

	public void drawString(Gui gui, String text, int x, int y, int color)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, texID);
		float red = (float)(color >> 16 & 0xff) / 255F;
		float green = (float)(color >> 8 & 0xff) / 255F;
		float blue = (float)(color & 0xff) / 255F;
		float alpha = (float)(color >> 24 & 0xff) / 255F;
		GL11.glColor4f(red, green, blue, alpha);
		int startX = x;
		for (int i=0; i<text.length(); i++)
		{
			char c = text.charAt(i);
			if (c == '\\')
			{
				char type = text.charAt(i+1);
				if (type == 'n')
				{
					y += metrics.getAscent() + 2;
					x = startX;
				}
				i++;
				continue;
			}
			drawChar(gui, c, x, y);
			x += metrics.getStringBounds(""+c, null).getWidth();
		}
	}

	public void drawString(Gui gui, String text, int x, int y, int rvb, float trans)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, texID);
		float red = (float)(rvb >> 16 & 0xff) / 255F;
		float green = (float)(rvb >> 8 & 0xff) / 255F;
		float blue = (float)(rvb & 0xff) / 255F;
		GL11.glColor4f(red, green, blue, trans);
		int startX = x;
		for (int i=0; i<text.length(); i++)
		{
			char c = text.charAt(i);
			if (c == '\\')
			{
				char type = text.charAt(i+1);
				if (type == 'n')
				{
					y += metrics.getAscent() + 2;
					x = startX;
				}
				i++;
				continue;
			}
			drawChar(gui, c, x, y);
			x += metrics.getStringBounds(""+c, null).getWidth();
		}
	}

	public FontMetrics getMetrics()
	{
		return metrics;
	}

	public int getStringWidth(String text)
	{
		return (int)getBounds(text).getWidth();
	}

	public int getStringHeight(String text)
	{
		return (int)getBounds(text).getHeight();
	}

	private Rectangle getBounds(String text)
	{
		int w = 0;
		int h = 0;
		int tw = 0;
		for (int i=0; i<text.length(); i++)
		{
			char c = text.charAt(i);
			if (c == '\\')
			{
				char type = text.charAt(i+1);

				if (type == 'n')
				{
					h += metrics.getAscent() + 2;
					if (tw > w)
						w = tw;
					tw = 0;
				}
				i++;
				continue;
			}
			tw += metrics.stringWidth(""+c);
		}
		if (tw > w)
			w = tw;
		h += metrics.getAscent();
		return new Rectangle(0, 0, w, h);
	}

	private void drawChar(Gui gui, char c, int x, int y)
	{
		Rectangle2D bounds = metrics.getStringBounds(""+c, null);
		try
		{
			gui.drawTexturedModalRect(x, y, xPos[c-startChar], yPos[c-startChar], (int)bounds.getWidth(), (int)bounds.getHeight() + metrics.getMaxDescent());
		}
		catch (Exception e)
		{
			c = '?'; 
			gui.drawTexturedModalRect(x, y, xPos[c-startChar], yPos[c-startChar], (int)bounds.getWidth(), (int)bounds.getHeight() + metrics.getMaxDescent());
		}
	}
}
