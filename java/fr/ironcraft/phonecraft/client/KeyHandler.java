package fr.ironcraft.phonecraft.client;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class KeyHandler {
	
	private KeyBinding key_PhoneGUI  = new KeyBinding(LanguageRegistry.instance().getStringLocalization("key.phone.open", "en_US"), Keyboard.KEY_P, "Phonecraft");
	public static boolean open_phoneGUI = false;

	public KeyHandler()
	{
	    ClientRegistry.registerKeyBinding(key_PhoneGUI);
	    System.out.println("Keys was registered");
	    
	    System.out.println("TEST ----------------------------- "+LanguageRegistry.instance().getStringLocalization("key.phone.open", "en_US"));
	    System.out.println(LanguageRegistry.instance().getStringLocalization("key.phone.open", "fr_FR"));
	    System.out.println(LanguageRegistry.instance().getStringLocalization("key.phone.open", "fr_CA"));
	}

	@SubscribeEvent
	public void input(KeyInputEvent event) 
	{
		open_phoneGUI = false;
		if(key_PhoneGUI.getIsKeyPressed())
		   open_phoneGUI = true;
	}
}