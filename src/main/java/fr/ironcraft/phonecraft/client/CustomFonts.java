package fr.ironcraft.phonecraft.client;

import net.minecraft.client.Minecraft;
import fr.ironcraft.phonecraft.utils.CustomFont;

/**
 * @author Dermenslof, DrenBx
 */
public class CustomFonts
{
	public static CustomFont timenewRoman;
	
	public void init()
	{
		try
		{
			this.timenewRoman = new CustomFont(Minecraft.getMinecraft(), "TimesNewRoman", 10);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}