package fr.ironcraft.phonecraft.client;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class KeyHandler {
	
	private KeyBinding key_PhoneGUI  = new KeyBinding("key.phone.open", Keyboard.KEY_P, "Phonecraft");
	public static boolean open_phoneGUI = false;

	public KeyHandler()
	{
	    ClientRegistry.registerKeyBinding(key_PhoneGUI);
	    System.out.println("Keys was registered");
	}

	@SubscribeEvent
	public void input(KeyInputEvent event)
	{
		System.out.println("FUCKING bouton qui marche pas !!!!");
		open_phoneGUI = false;
		if(key_PhoneGUI.getIsKeyPressed()) {
			System.out.println("P was pressed");
			open_phoneGUI = true;
		}
	}
}