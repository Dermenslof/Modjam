package fr.ironcraft.phonecraft.client;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyHandler {
	
	private KeyBinding key_PhoneGUI  = new KeyBinding("Phone", Keyboard.KEY_P, "?");
	public static boolean open_phoneGUI = false;

	public KeyHandler()
	{
	    ClientRegistry.registerKeyBinding(key_PhoneGUI);
	    System.out.println("Keys was registered");
	}

	@SubscribeEvent
	public void input(KeyInputEvent event) 
	{
		open_phoneGUI = false;
		if(key_PhoneGUI.getIsKeyPressed())
		   open_phoneGUI = true;
	}
}
