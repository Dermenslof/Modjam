package fr.ironcraft.phonecraft.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

/**
 * @author Dermenslof, DrenBx
 */
public class GuiCheckBox extends GuiButton
{
	public boolean checked = false;
	public static ResourceLocation texture = new ResourceLocation("phonecraft:textures/gui/checkbox.png");
	
    public GuiCheckBox(int par1, int par2, int par3)
    {
        super(par1, par2, par3, 10, 10, "");
    }

    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.visible)
        {
        	par1Minecraft.renderEngine.bindTexture(texture);
        	GL11.glPushMatrix();
        	    GL11.glScalef(0.25F, 0.25F, 0.25F);
            	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            	if (this.checked)
            		this.drawTexturedModalRect((int)(this.xPosition/0.25F), (int)(this.yPosition/0.25F), 0, 0, 32, 33);
            	else
            		this.drawTexturedModalRect((int)(this.xPosition/0.25F), (int)(this.yPosition/0.25F), 32, 0, 32, 33);
            GL11.glPopMatrix();
        }
    }
}
