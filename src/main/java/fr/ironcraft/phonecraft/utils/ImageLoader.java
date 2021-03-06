package fr.ironcraft.phonecraft.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.client.ClientProxy;

/**
 * @authors Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class ImageLoader
{
	private ByteBuffer imageData = GLAllocation.createDirectByteBuffer(16777216);

	private BufferedImage prevImage;

	public int setupTexture(BufferedImage par1BufferedImage)
	{
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		int x = par1BufferedImage.getWidth();
		int y = par1BufferedImage.getHeight();
		if(this.prevImage != par1BufferedImage)
		{
			int[] var5 = new int[y * x];
			byte[] var6 = new byte[x * y * 4];
			par1BufferedImage.getRGB(0, 0, x, y, var5, 0, x);
			for (int var7 = 0; var7 < var5.length; ++var7)
			{
				int var8 = var5[var7] >> 24 & 255;
				int var9 = var5[var7] >> 16 & 255;
				int var10 = var5[var7] >> 8 & 255;
				int var11 = var5[var7] & 255;
				var6[var7 * 4 + 0] = (byte)var9;
				var6[var7 * 4 + 1] = (byte)var10;
				var6[var7 * 4 + 2] = (byte)var11;
				var6[var7 * 4 + 3] = (byte)var8;
			}
			this.imageData.clear();
			this.imageData.put(var6);
			this.imageData.position(0).limit(var6.length);
		}
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, x, y, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
		this.prevImage = par1BufferedImage;
		return(textureID);
	}

	public int setupTexture(String textureLocation) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		String[] path = textureLocation.split(":");
		File file = new File(mc.mcDataDir, "PhoneCraft/apps/resources/" + path[0] + "/" + path[1]);
		BufferedImage img = null;
		try {
			img = (BufferedImage)ImageIO.read(file);
		}
		catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return setupTexture(img);
	}
	
	public int setupTextureApp(String textureLocation) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		String[] path = textureLocation.split(":");
		File file = new File(mc.mcDataDir, "PhoneCraft/apps/resources/" + path[0] + "/" + path[1]);
		BufferedImage img = null;
		try {
			img = (BufferedImage)ImageIO.read(file);
		}
		catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return setupTexture(img);
	}
}