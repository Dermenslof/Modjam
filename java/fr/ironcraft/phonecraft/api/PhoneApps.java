package fr.ironcraft.phonecraft.api;

import fr.ironcraft.phonecraft.client.gui.GuiPhoneInGame;

public interface PhoneApps {
	
	 public abstract String appname();
	 public abstract String version();
	 public abstract GuiPhoneInGame ScreenInstance();

}
