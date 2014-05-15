package fr.ironcraft.phonecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiPhoneInGame  extends GuiScreen {
	
	protected static Minecraft mc;
	
	public GuiPhoneInGame (Minecraft par1Minecraft)
	{
		this.mc = par1Minecraft;
	}
	
	public void initGui()
	{
		System.out.println("Init Phone");
	}

}
