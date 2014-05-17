package apps.exemple.minebox;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import paulscode.sound.SoundSystem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.utils.SoundSystemHelper;

@SideOnly(Side.CLIENT)
public class StreamSoundThread extends Thread implements Runnable
{
	public float volume = 0.8F;
	private String RawURL;
	private Minecraft mc = Minecraft.getMinecraft();
	public SoundSystem sndSystem = SoundSystemHelper.getSoundSystem();
	private static StreamSoundThread sst;
	public static boolean isStart;

	public StreamSoundThread(String stringurl)
	{
		RawURL = stringurl;
		sst = this;
	}
	
	@Override
	public void start()
	{
//		try
//		{
//			System.out.println("Mime Type of " + RawURL + " is " + getMimeType(RawURL));
//		}
//		catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		URL url = null;
		try
		{
			url = new URL(RawURL);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		if(url != null && !this.sndSystem.playing("MineBox"))
		{
			if(this.sndSystem.playing("BgMusic"))
				this.sndSystem.removeSource("BgMusic");
			if(this.sndSystem.playing("Stream"))
				this.sndSystem.removeSource("Stream");
			this.sndSystem.newStreamingSource(true, "MineBox", url, "Minebox.mp3", true, 0.0F, 0.0F, 0.0F, 0, 0.0F);
			this.sndSystem.setVolume("MineBox", 0.5F * this.volume);
			new Thread(new MbrThreadStart(this, "MineBox")).start();
		}
	}
	
	public static String getMimeType(String fileUrl) throws java.io.IOException, MalformedURLException
	{
		String type = null;
		URL u = new URL(fileUrl);
		URLConnection uc = null;
		uc = u.openConnection();
		type = uc.getContentType();
		return type;
	}

	@Override
	public void interrupt()
	{
		if(this.sndSystem.playing("MineBox"))
			this.sndSystem.removeSource("MineBox");
		isStart = false;
	}

	public void updateSoundVolume()
	{
		if(this.sndSystem.playing("BgMusic"))
			this.sndSystem.removeSource("BgMusic");
		if(this.volume > 1F)
			this.volume = 1F;
		else if (this.volume < 0)
			this.volume = 0;
		this.sndSystem.setVolume("MineBox", 0.5F * this.volume);
	}

	public static boolean isStart()
	{
		return isStart;
	}
	
	public static StreamSoundThread getThread()
	{
		if(sst == null)
			return new StreamSoundThread("http://listen.radionomy.com/minebox-radio");
		else
			return sst;
	}
}