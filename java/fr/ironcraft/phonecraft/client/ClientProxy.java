package fr.ironcraft.phonecraft.client;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.ironcraft.phonecraft.common.CommonProxy;

public class ClientProxy extends CommonProxy {

	public static PhoneAchievements achievements;

	public void init()
	{
		events();
		achievements = new PhoneAchievements();
	}
	
	public void renders()
	{
		
	}
	
	public void events()
	{
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
	
}
