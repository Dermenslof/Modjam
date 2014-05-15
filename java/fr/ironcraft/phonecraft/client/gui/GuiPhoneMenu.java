package fr.ironcraft.phonecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.KeyHandler;

public class GuiPhoneMenu extends GuiPhoneInGame {

	private int button = 0;
	private int screen = 0;
	
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
	
	@Override
	public void mouseClicked(int par1, int par2, int par3)
	{
		if (!this.mc.inGameHasFocus)
		{
			this.screen = this.button > 0 ? this.button : 0;
			switch(this.button)
			{
			case 0: //Main button
				//this.app = -1;
				if(!this.isHome)
					this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc, true));
				break;
			case 1: //Camera button
				//this.isCamera = true;
				//this.mc.displayGuiScreen(new GuiPhoneCamera(this.mc));
				break;
			case 2:
				//this.mc.displayGuiScreen(new GuiPhoneMessages(this.mc));
				break;
			case 3:
				//this.mc.displayGuiScreen(new GuiPhoneContacts(this.mc));
				break;
			case 4:
				this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc, false));
				break;
			}
		}
		super.mouseClicked(par1, par2, par3);
	}
	
}