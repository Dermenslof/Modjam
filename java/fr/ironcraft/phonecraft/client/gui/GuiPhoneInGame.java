package fr.ironcraft.phonecraft.client.gui;

import fr.ironcraft.phonecraft.client.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiPhoneInGame  extends GuiScreen {
	
	protected static Minecraft mc;
	
	protected int shift = 0;
	protected boolean isOpen = true;
	protected boolean isAnimated = false;
	protected boolean isHome = false;
	protected float transparency;
	
	public GuiPhoneInGame (Minecraft par1Minecraft)
	{
		this.mc = par1Minecraft;
	}
	
	@Override
	public void initGui()
	{
		System.out.println("Init Phone");
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		this.drawRect(this.width - 106 + this.shift, this.height - 183, this.width - 14 + this.shift, this.height - 29, 0xff000000);
	}
	
	@Override
	protected void keyTyped(char par1, int par2)
    {
		if (par2 == 1)
			this.mc.displayGuiScreen(new GuiIngameMenu());
		
		if (par2 == KeyHandler.key_PhoneGUI.getKeyCode()) {
			this.isOpen = !this.isOpen;
		
	        if (!this.isOpen && !this.isAnimated)
	        	this.mc.displayGuiScreen(new GuiPhoneAnimation(mc, true));
		}
    }

}
