package fr.ironcraft.phonecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import fr.ironcraft.phonecraft.gui.GuiPhone;

public class KeyHandler {
	
	protected Minecraft mc = Minecraft.getMinecraft();
	
	public static final KeyBinding key_PhoneGUI = new KeyBinding("phone.open", Keyboard.KEY_P, "Phonecraft");

	public KeyHandler()
	{
	    ClientRegistry.registerKeyBinding(key_PhoneGUI);
	    System.out.println("Keys was registered");
	}

	@SubscribeEvent
	public void input(KeyInputEvent event)
	{
		if(key_PhoneGUI.getIsKeyPressed()) {
			this.mc.displayGuiScreen(new GuiPhone(mc, true));
		}
	}
}