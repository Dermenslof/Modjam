package fr.ironcraft.phonecraft.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.FMLClientHandler;
import fr.ironcraft.phonecraft.Phonecraft;

/**
 * @authors Dermenslof, DrenBx
 */
public class TextureUtils
{
	public static String getTextureNameForBlocks(String name)
	{
		return Phonecraft.MODID + ":" + name.replace("tile.", "");
	}

	public static String getTextureNameForBlocks(String name, String type)
	{
		return Phonecraft.MODID + ":" + name.replace("tile.", "") + "_" + type;
	}

	public static String getTextureNameForItems(String name)
	{
		return Phonecraft.MODID + ":" + name;
	}
	
	public static String getTextureNameForGui(String name)
	{
		return Phonecraft.MODID + ":/textures/gui/" + name + ".png";
	}
	
}
