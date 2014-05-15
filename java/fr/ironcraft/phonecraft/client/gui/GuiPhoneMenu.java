package fr.ironcraft.phonecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.KeyHandler;

public class GuiPhoneMenu extends GuiPhoneInGame {

	
	
	public GuiPhoneMenu(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
	}
	
	public GuiPhoneMenu(Minecraft par1Minecraft, boolean home)
	{
		super(par1Minecraft);
		this.transparency = -0.5F;
		this.isHome = home;
	}
	
}
