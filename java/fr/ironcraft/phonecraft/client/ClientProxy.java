package fr.ironcraft.phonecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.ironcraft.phonecraft.common.CommonProxy;
import fr.ironcraft.phonecraft.utils.ImageLoader;

/**
 * @author Dermenslof, DrenBx
 */
public class ClientProxy extends CommonProxy
{
	private static Minecraft mc = Minecraft.getMinecraft();
	public static PhoneAchievements achievements;
	public static ImageLoader imageLoader = new ImageLoader();
	public static CustomFonts fonts = new CustomFonts();
	public static int renderQrCodeID;
	public AppRegistry appRegistry;
	
	public void init()
	{
		appRegistry = new AppRegistry(mc);
		events();
		achievements = new PhoneAchievements();
		renderQrCodeID = RenderingRegistry.getNextAvailableRenderId();
		fonts.init();
	}
	
	public void renders()
	{
		
	}
	
	public void events()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
}
