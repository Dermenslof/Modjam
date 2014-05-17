package fr.ironcraft.phonecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import fr.ironcraft.phonecraft.client.gui.GuiPhoneAnimation;

/**
 * @author Dermenslof, DrenBx
 */
public class KeyHandler
{
	protected Minecraft mc = Minecraft.getMinecraft();
	
	public static final KeyBinding key_PhoneGUI = new KeyBinding("key.phone.open", Keyboard.KEY_P, "Phonecraft");
	public static final KeyBinding key_PhoneFocus = new KeyBinding("key.phone.focus", Keyboard.KEY_F, "Phonecraft");

	public KeyHandler()
	{
	    ClientRegistry.registerKeyBinding(key_PhoneGUI);
	    ClientRegistry.registerKeyBinding(key_PhoneFocus);
	    System.out.println("[Phonecraft] Keys was registered");
	}

	@SubscribeEvent
	public void input(KeyInputEvent event)
	{
		if(key_PhoneGUI.getIsKeyPressed())
		{
			mc.thePlayer.triggerAchievement(ClientProxy.achievements.openPhone);
			this.mc.displayGuiScreen(new GuiPhoneAnimation(mc, false));
		}
	}
}