package fr.ironcraft.phonecraft.client;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import fr.ironcraft.phonecraft.common.CommonProxy;

public class ClientProxy extends CommonProxy {

	public void renders()
	{
		
	}
	
	public void events()
	{
		//MinecraftForge.EVENT_BUS.register(new KeyHandler());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
	
}
