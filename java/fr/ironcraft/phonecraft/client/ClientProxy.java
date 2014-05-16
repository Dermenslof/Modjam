package fr.ironcraft.phonecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.ironcraft.phonecraft.common.CommonProxy;
import fr.ironcraft.phonecraft.utils.CustomFont;
import fr.ironcraft.phonecraft.utils.ImageLoader;

public class ClientProxy extends CommonProxy {

	private static Minecraft mc = Minecraft.getMinecraft();
	public static PhoneAchievements achievements;
<<<<<<< Updated upstream
	public static ImageLoader imageLoader = new ImageLoader();
=======
	public static ImageLoader imageLoader;
	public static CustomFonts fonts;
>>>>>>> Stashed changes

	public void init()
	{
		events();
		achievements = new PhoneAchievements();
		fonts = new CustomFonts();
		
		fonts.init();
	}
	
	public void renders()
	{
		
	}
	
	public void events()
	{
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
}
