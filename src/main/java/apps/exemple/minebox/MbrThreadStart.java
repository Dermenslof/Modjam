package apps.exemple.minebox;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Dermenslof
 */
@SideOnly(Side.CLIENT)
public class MbrThreadStart implements Runnable
{
	private StreamSoundThread mbrThread;
	private String source;
	
	public MbrThreadStart(StreamSoundThread par0, String par1)
	{
		this.mbrThread = par0;
		this.source = par1;
	}
	
	@Override
	public void run()
	{
		while (!mbrThread.sndSystem.playing(source))
			mbrThread.sndSystem.play(source);
		mbrThread.isStart = true;
		new Thread(new MbrThreadPlay()).start();
	}

}