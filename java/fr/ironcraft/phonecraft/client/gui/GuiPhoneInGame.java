package fr.ironcraft.phonecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiPhoneInGame  extends GuiScreen {
	
	protected static Minecraft mc;
	
	protected int shift = 0;
	protected boolean isOpen = true;
	
	public GuiPhoneInGame (Minecraft par1Minecraft)
	{
		this.mc = par1Minecraft;
	}
	
	@Override
	public void initGui()
	{
		System.out.println("Init Phone");
	}

}
