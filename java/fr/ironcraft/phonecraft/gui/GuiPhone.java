package fr.ironcraft.phonecraft.gui;

import fr.ironcraft.phonecraft.client.ClientProxy;
import net.minecraft.client.Minecraft;

public class GuiPhone extends GuiPhoneInGame {

	public GuiPhone(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
		mc.thePlayer.triggerAchievement(ClientProxy.achievements.openPhone);
	}
	
}
