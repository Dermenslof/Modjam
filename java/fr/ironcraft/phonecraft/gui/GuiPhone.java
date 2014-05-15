package fr.ironcraft.phonecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.KeyHandler;

public class GuiPhone extends GuiPhoneInGame {

	private boolean slide = false;
	private boolean isOpen = true;
	
	public GuiPhone(Minecraft par1Minecraft, boolean slide)
	{
		super(par1Minecraft);
		this.slide = slide;
		this.shift = this.slide ? 214 : 0;
		mc.thePlayer.triggerAchievement(ClientProxy.achievements.openPhone);
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		this.drawRect(this.width - 106 + this.shift, this.height - 183, this.width - 14 + this.shift, this.height - 29, 0xff000000);
		drawAnimation();
	}
	
	@Override
	protected void keyTyped(char par1, int par2)
    {
        if (par2 == KeyHandler.key_PhoneGUI.getKeyCode())
        	this.isOpen = false;
    }
	
    private void drawAnimation()
    {
    	if (this.isOpen && this.shift != 214)
    		this.shift = this.shift + 5 < 210 ? this.shift + 10 : 214;
    	else if (!this.isOpen) {
    		this.shift = this.shift + 5 < 210 ? this.shift + 10 : 214;
    		if (this.shift >= 214) {
    			this.mc.displayGuiScreen((GuiScreen)null);
    			this.mc.setIngameFocus();
    		}
    	}
    }
	
}
