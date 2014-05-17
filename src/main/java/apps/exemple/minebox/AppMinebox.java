package apps.exemple.minebox;

import fr.ironcraft.phonecraft.api.PhoneApps;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneInGame;

public class AppMinebox implements PhoneApps
{
	public static StreamSoundThread sound;
	
	@Override
	public String appname()
	{
		return "Minebox";
	}

	@Override
	public String version()
	{
		return "1.0";
	}

	@Override
	public GuiPhoneInGame ScreenInstance()
	{
		return new GuiPhoneMinebox();
	}
}
