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
		open_phoneGUI = false;
		if(key_PhoneGUI.getIsKeyPressed()) {
			System.out.println("Phone Key was pressed");
			this.mc.displayGuiScreen(new GuiPhone(mc));
			open_phoneGUI = true;
		}
	}
}