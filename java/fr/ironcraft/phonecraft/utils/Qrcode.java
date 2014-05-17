package fr.ironcraft.phonecraft.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @authors Dermenslof, DrenBx
 */
@SideOnly(Side.CLIENT)
public class Qrcode
{
	private String[] types = {"Title:", "Description:", "Type:", "Teleportation:", "X:", "Y:", "Z:", "bgColor:", "fgColor:"};
	
	public File encode(File qrFile, String qrCodeText, String fileType, String bgColor, String fgColor)
	{
		int size;
		Hashtable hintMap;
		QRCodeWriter qrCodeWriter;
		BitMatrix byteMatrix;
		int matrixWidth;
		BufferedImage image;
		Graphics2D graphics;
		
		size = 256;
		hintMap = new Hashtable();
		qrCodeWriter = new QRCodeWriter();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		try
		{
			byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		}
		catch (WriterException e)
		{
			e.printStackTrace();
			return null;
		}
		matrixWidth = byteMatrix.getWidth();
		image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		graphics = (Graphics2D) image.getGraphics();
		Color bgcolor;
		Color fgcolor;
		try
		{
		    Field field = Color.class.getField(bgColor);
		    bgcolor = (Color)field.get(null);
		}
		catch (Exception e)
		{
		    bgcolor = Color.WHITE;
		}
		try
		{
		    Field field = Color.class.getField(fgColor);
		    fgcolor = (Color)field.get(null);
		}
		catch (Exception e) {
		    fgcolor = Color.BLACK;
		}
		graphics.setColor(bgcolor);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		graphics.setColor(fgcolor);
		for (int i = 0; i < matrixWidth; i++)
		{
			for (int j = 0; j < matrixWidth; j++)
			{
				if (byteMatrix.get(i, j))
					graphics.fillRect(i, j, 1, 1);
			}
		}
		try
		{
			ImageIO.write(image, fileType, qrFile);
			long s = 0;
			while(s != qrFile.length())
				s = qrFile.length();
			return qrFile;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public String decode(File file)
	{
		Result result = null;
		BinaryBitmap binaryBitmap;
		BufferedImage img = null;
		
		try
		{
			img = ImageIO.read(new FileInputStream(file.getPath()));
			binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));
			result = new MultiFormatReader().decode(binaryBitmap);
			drawResult(result.getText());
			file.delete();
			return result.getText();
		}
		catch(Exception ex)
		{
			try
			{
				for (int x = 0; x < img.getWidth(); x++)
				{
					for (int y = 0; y < img.getHeight(); y++)
					{
						int rgba = img.getRGB(x, y);
						Color col = new Color(rgba, true);
						col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue());
						img.setRGB(x, y, col.getRGB());
					}
				}
				binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));
				result = new MultiFormatReader().decode(binaryBitmap);
				drawResult(result.getText());
				file.delete();
				return result.getText();
			}
			catch(Exception ex1)
			{
				System.out.println("Decode Failed");
			}
		}
		file.delete();
		return null;
	}
	
	public void drawResult(String result)
	{
		int	len, type, line;
		
		System.out.println("---------------------------------------");
		System.out.println("Decode Successed");
		type = 0;
		line = 0;
		len = result.length();
		System.out.print(types[type] + " ");
		for (int i=0; i < len; i++)
		{
			System.out.print(result.charAt(i));
			if (result.charAt(i) == '\n')
			{	line++;
				if (line == 1 || line >= 7)
				{
					if (line == 1)
						System.out.println(types[++type]);
					else
					System.out.print(types[++type] + " ");
				}
			}
		}
		System.out.println("");
		System.out.println("---------------------------------------");
	}
}
