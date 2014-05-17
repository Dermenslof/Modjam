package fr.ironcraft.phonecraft.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneInGame;

/**
 * @author Dermenslof
 */
@SideOnly(Side.CLIENT)
public class CameraScreenshot implements Runnable
{
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	private IntBuffer buffer;
	private int[] list;
	private GuiPhoneInGame gui;
	private Minecraft mc;
	private boolean isQrCode;
//	private Qrcode decoder;

	public CameraScreenshot(Minecraft mc, GuiPhoneInGame gui, IntBuffer buf, int[] li, boolean isQrCode)
	{
		this.mc = mc;
		this.gui = gui;
		this.isQrCode = isQrCode;
		this.buffer = buf;
		this.list = li;
//		this.decoder = new Qrcode();
	}

	@Override
	public void run()
	{
		try
		{
			File var1 = new File(this.mc.mcDataDir, Phonecraft.phoneFolder + "pictures/dcim/");
			var1.mkdirs();

			copyList(this.list, this.mc.displayWidth, this.mc.displayHeight);
			BufferedImage var3 = new BufferedImage(this.mc.displayWidth, this.mc.displayHeight, 1);
			var3.setRGB(0, 0, this.mc.displayWidth, this.mc.displayHeight, this.list, 0, this.mc.displayWidth);
			File var4 = setFileName(var1);
			ImageIO.write(var3, "png", var4);
//			if (this.isQrCode)
//				this.decoder.decode(var4);
			gui.hideGui = false;
		}
		catch (Exception var8)
		{
			var8.printStackTrace();
		}
	}

	private File setFileName(File par0File)
	{
		String var1 = dateFormat.format(new Date()).toString();
		int var3 = 1;
		while (true)
		{
			File var2 = new File(par0File, var1 + (var3 == 1 ? "" : "_" + var3) + ".png");
			if (!var2.exists())
				return var2;
			++var3;
		}
	}

	private void copyList(int[] par0ArrayOfInteger, int par1, int par2)
	{
		int[] var1 = new int[par1];
		int var2 = par2 / 2;
		for (int var3 = 0; var3 < var2; ++var3)
		{
			System.arraycopy(par0ArrayOfInteger, var3 * par1, var1, 0, par1);
			System.arraycopy(par0ArrayOfInteger, (par2 - 1 - var3) * par1, par0ArrayOfInteger, var3 * par1, par1);
			System.arraycopy(var1, 0, par0ArrayOfInteger, (par2 - 1 - var3) * par1, par1);
		}
	}
}
