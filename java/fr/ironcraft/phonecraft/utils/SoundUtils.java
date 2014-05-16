package fr.ironcraft.phonecraft.utils;

import fr.ironcraft.phonecraft.Phonecraft;

public class SoundUtils {

	public static String getSoundPath(String name)
	{
		return getSoundPath(name, "wav");
	}
	
	public static String getSoundPath(String name, String extend)
	{
		return Phonecraft.MODID + ":/sounds/" + name + "." + extend;
	}
	
}
