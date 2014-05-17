package fr.ironcraft.phonecraft.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneCamera;

/**
 * @author Dermenslof
 */
@SideOnly(Side.CLIENT)
public class TaskFlash extends GuiPhoneCamera
{
	private static int time;
	private static long startTime;

	public TaskFlash(Minecraft mc, int time)
	{
		super(mc);
		this.startTime = System.currentTimeMillis();
		this.time = time;
	}

	public void initGui()
	{
		super.initGui();
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		if(System.currentTimeMillis() >= this.startTime+this.time)
			this.mc.displayGuiScreen(new GuiPhoneCamera(this.mc, false));

		if(System.currentTimeMillis() % 2 == 0)
			drawRect(0, 0, this.width,  this.height, 0x88ffffff);
	}

	public void keyTyped(char par1, int par2){}
}
