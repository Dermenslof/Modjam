package fr.ironcraft.phonecraft.api;

import fr.ironcraft.phonecraft.client.gui.GuiPhoneInGame;

/**
 * @author Dermenslof, DrenBx
 * @since Phonecraft 1.0 for Minecraft 1.7.2
 */
public interface PhoneApps
{
	 public abstract String appname();
	 public abstract String version();
	 public abstract GuiPhoneInGame ScreenInstance();
}
