package fr.ironcraft.phonecraft.api;

import fr.ironcraft.phonecraft.client.gui.GuiPhoneInGame;

/**
 * @author Dermenslof, DrenBx
 */
public interface PhoneApps
{
	 public abstract String appname();
	 public abstract String version();
	 public abstract GuiPhoneInGame ScreenInstance();
}
