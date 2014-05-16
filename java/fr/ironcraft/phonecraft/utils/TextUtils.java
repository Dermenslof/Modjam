package fr.ironcraft.phonecraft.utils;

import net.minecraft.util.StatCollector;

public class TextUtils {
	
	public static String getLanguage(String str)
	{
		return StatCollector.translateToLocal(str);
	}

}
