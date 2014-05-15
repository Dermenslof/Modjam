package fr.ironcraft.phonecraft.gui;

import fr.ironcraft.phonecraft.client.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiPhone extends GuiPhoneInGame {

	private boolean slide = false;
	private boolean isOpen = false;
	
	public GuiPhone(Minecraft par1Minecraft, boolean slide)
	{
		super(par1Minecraft);
		this.slide = slide;
		this.shift = this.slide ? 214 : 0;
	}
	
	public GuiPhone(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
		mc.thePlayer.triggerAchievement(ClientProxy.achievements.openPhone);
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		this.drawRect(this.width - 106 + this.shift, this.height - 183, this.width - 14 + this.shift, this.height - 29, 0xff000000);
		drawAnimation();
	}
	
    private void drawAnimation()
    {
    	if(this.slide)
    	{

    	}
    	else if (!isOpen)
    	{
    		if(this.shift < 214)
    			this.shift = this.shift + 5 < 210 ? this.shift + 10 : 214;
    		if(this.shift >= 214){
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
    		}
    	}
    }
	
}
