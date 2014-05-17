package fr.ironcraft.phonecraft.client;

import fr.ironcraft.phonecraft.api.PhoneApps;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneInGame;

/**
 * @author Dermenslof, DrenBx
 */
class SystemApp implements PhoneApps
{
	private String appname;
	private String version;
	private GuiPhoneInGame gui;

	public SystemApp(String string, String s, GuiPhoneInGame g)
	{
		this.appname = string;
		this.version = s;
		this.gui = g;
	}

	@Override
	public String appname()
	{
		return appname;
	}

	@Override
	public String version()
	{
		return version;
	}

	@Override
	public GuiPhoneInGame ScreenInstance()
	{
		return gui;
	}
}