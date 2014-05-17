package fr.ironcraft.phonecraft.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

/**
 * @authors Dermenslof, DrenBx
 */
public class TextUtils
{
	public static String getLanguage(String str)
	{
		return StatCollector.translateToLocal(str);
	}
}
