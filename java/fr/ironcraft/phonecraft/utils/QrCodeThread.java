package fr.ironcraft.phonecraft.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.ironcraft.phonecraft.common.tileentities.TileEntityQrCode;

/**
 * @authors Dermenslof, DrenBx
 */
public class QrCodeThread implements Runnable
{
	private TileEntityQrCode tile;
	
	public QrCodeThread(TileEntityQrCode par0)
	{
		this.tile = par0;
	}
	
	@Override
	public void run()
	{
		URL url;
		try
		{
			BufferedImage img = ImageIO.read(new URL(this.tile.texture));
			if (img != null)
			this.tile.img = img;
		}
		catch (IOException e)
		{
			this.tile.img = null;
		}
	}
}
