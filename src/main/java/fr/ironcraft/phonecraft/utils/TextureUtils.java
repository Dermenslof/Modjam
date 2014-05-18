package fr.ironcraft.phonecraft.utils;

import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.api.PhoneApps;

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
	
	public static String getTextureNameForApp(PhoneApps app, String texture)
	{
		return Phonecraft.appResourcesPath.get(app.appname()) + ":textures/" + texture;
	}
}
