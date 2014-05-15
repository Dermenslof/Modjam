package fr.ironcraft.phonecraft.client;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
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
		//MinecraftForge.EVENT_BUS.register(new KeyHandler());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
	
}
