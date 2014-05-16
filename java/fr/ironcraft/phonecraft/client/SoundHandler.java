package fr.ironcraft.phonecraft.client;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SoundHandler {

	@SubscribeEvent
    public void loadSound(SoundLoadEvent event)
    {
        try
        {
        	/*
        	int i = 0;
        	while (++i <= 4)
        		event.manager.soundPoolSounds.addSound(SoundUtils.getSoundPath("clicPhoto"+i)); //Deprecated
        	 */
        	System.out.println("[PhoneCraft] all sounds are loaded");
        }
        catch (Exception e)
        {
        	System.out.println("[PhoneCraft] one or more sound can't loaded");
        	e.printStackTrace();
        }
    }
	
}
