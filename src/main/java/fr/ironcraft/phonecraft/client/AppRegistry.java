package fr.ironcraft.phonecraft.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import com.google.common.collect.ImmutableList;

import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.api.PhoneApps;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneImages;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneInGame;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneMap;
import fr.ironcraft.phonecraft.loader.AppServiceLoader;

/**
 * @author Dermenslof, DrenBx
 */
public class AppRegistry
{
	private static AppRegistry instance;
	private static List<PhoneApps> appsList = new ArrayList<PhoneApps>();
	private Minecraft mc;
	private AppServiceLoader appFinder;
	protected AppRegistry(Minecraft minecraft)
	{
		this.mc = minecraft;
		
		init();
		registerSystemApps();
		registerUserApps();
		System.out.println("[PhoneCraft] " + (appsList.size() - 2) + " apps found");
	}
	
	
	private void registerUserApps() {
		if(appFinder.getAppCollection() != null)
		{
			for(PhoneApps app : appFinder.getAppCollection())
			  this.appsList.add(app);	
		}
		else
			new Exception("AppServiceLoader have encontred an exception");
	}

	private void registerSystemApps()
	{
		this.appsList.add(new SystemApp("Images", "1.0.0", new GuiPhoneImages(mc)));
		this.appsList.add(new SystemApp("MAP", "1.0.0", new GuiPhoneMap(mc)));
	}

	private void init()
	{
		try
		{
			File phoneappdir = new File(this.mc.mcDataDir, Phonecraft.phoneFolder + "apps");
			appFinder = new AppServiceLoader();
			appFinder.search(phoneappdir);
		}
		catch (Exception e)
		{
			System.out.println("[PhoneCraft] No app found");
			e.printStackTrace();
		}
	}
	
	public static ImmutableList<PhoneApps> getAppsList()
	{
		return instance().appsList != null ? ImmutableList.copyOf(instance().appsList) : ImmutableList.<PhoneApps>of();
	}


	public static AppRegistry instance()
	{
	        return instance;
	}

	public static PhoneApps getAppById(int id)
	{
		if(id < appsList.size())
			return appsList.get(id);
		else
			return null;
	}

	
	public static GuiPhoneInGame getAppGuiById(int app)
	{
		return getAppById(app).ScreenInstance();
	}
}
