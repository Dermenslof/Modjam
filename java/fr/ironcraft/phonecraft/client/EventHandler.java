package fr.ironcraft.phonecraft.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneCamera;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class EventHandler
{
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent event)
	{
		if (!(FMLClientHandler.instance().getClient().currentScreen instanceof GuiPhoneCamera))
			return;
		if (!event.type.equals(ElementType.ALL) && !event.type.equals(ElementType.TEXT))
			event.setCanceled(true);
	}
}
