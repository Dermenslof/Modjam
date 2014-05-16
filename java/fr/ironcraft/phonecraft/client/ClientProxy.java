package fr.ironcraft.phonecraft.client;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.ironcraft.phonecraft.common.CommonProxy;
import fr.ironcraft.phonecraft.utils.ImageLoader;

public class ClientProxy extends CommonProxy {

	public static PhoneAchievements achievements;
	public static ImageLoader imageLoader;

	public void init()
	{
		events();
		imageLoader = new ImageLoader();
		achievements = new PhoneAchievements();
	}
	
	public void renders()
	{
		
	}
	
	public void events()
	{
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
}
