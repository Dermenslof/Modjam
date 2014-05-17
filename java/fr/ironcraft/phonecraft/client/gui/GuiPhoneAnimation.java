package fr.ironcraft.phonecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.client.KeyHandler;

/**
 * @author Dermenslof, DrenBx
 */
public class GuiPhoneAnimation extends GuiPhoneInGame
{
	
	public GuiPhoneAnimation(Minecraft par1Minecraft, boolean open)
	{
		super(par1Minecraft);
		this.isOpen = !open;
		this.shift = !open ? 214 : 0;
		this.isAnimated = true;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		drawAnimation();
	}
	
    private void drawAnimation()
    {
    	if (this.isOpen && this.shift != 0)
    		this.shift = this.shift - 5 > 4 ? this.shift - 10 : 0;
    	if (this.isOpen && this.shift == 0) {
    		this.isAnimated = false;
    		this.mc.displayGuiScreen(new GuiPhoneMenu(this.mc));
    	}
    	if (!this.isOpen) {
    		this.shift = this.shift + 5 < 210 ? this.shift + 10 : 214;
    		if (this.shift == 214) {
    			this.isAnimated = false;
    			this.mc.displayGuiScreen((GuiScreen)null);
    			this.mc.setIngameFocus();
    		}
    	}
    }
	
}
