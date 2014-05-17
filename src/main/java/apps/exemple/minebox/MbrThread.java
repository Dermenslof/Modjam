package apps.exemple.minebox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Dermenslof
 */
@SideOnly(Side.CLIENT)
public class MbrThread implements Runnable
{
	private GuiPhoneMinebox gui;
	private long prevBack = -1;

	public MbrThread(GuiPhoneMinebox par0)
	{
		this.gui = par0;
	}

	public void run()
	{
		getInfos();
		getImg();
	}

	public void getInfos()
	{
		HttpURLConnection conn;
		BufferedReader in;
		String inputLine;
		String result = "";
		URL url;
		try
		{
			url = new URL("http://api.radionomy.com/currentsong.cfm?radiouid=43b756f3-1dc4-49a0-bb63-7a0549941faa&callmeback=yes&type=json&cover=YES");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((inputLine = in.readLine()) != null)
				result += inputLine;
			in.close();
			String infos;
			String[] tab;
			if (!result.equals(""))
			{
				infos = result.toString().substring(80);
				infos = infos.substring(0, infos.length() - 20);
				tab = infos.split(",");
				for(int j=0; j < tab.length; j++)
				{
					int i= 0;
					for(int x=i;tab[j].charAt(x) == ' ' || tab[j].charAt(x) == '\t'; x++)
						i = x + 1;
					tab[j] = tab[j].substring(i, tab[j].length());
				}
				for(int i=0; i<tab.length; i++)
				{
					String[] tmp = tab[i].split(":");
					if (tmp.length > 1 && tmp[1] != null)
					{
						tab[i] = tmp[1];
						if(i != 3 && i < 6 && i != 4)
						{
							tab[i] = tmp[1].substring(1);
							tab[i] = tmp[1].substring(0, tmp[1].length() - 1);
							tab[i] = tab[i].length() > 0 ? tab[i].substring(1) : tab[i];
						}
						else
						{
							if (i != 4)
							{
								if (tmp.length > 1)
								{
									for(int j=2; j<tmp.length; j++)
										tab[i] += ":" + tmp[j];
									tab[i] = tab[i].substring(0, tab[i].length() - 1);
								}
							}
						}
						if (i == 3)
						{
							tab[i] = tab[i].substring(1);
							Calendar cal = Calendar.getInstance();
							cal.set(Calendar.YEAR, Integer.parseInt(tab[i].substring(0, 4)));
							cal.set(Calendar.MONTH, Integer.parseInt(tab[i].substring(5, 7)));
							cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tab[i].substring(8, 10)));
							cal.set(Calendar.HOUR, Integer.parseInt(tab[i].substring(11, 13)));
							cal.set(Calendar.MINUTE, Integer.parseInt(tab[i].substring(14, 16)));
							cal.set(Calendar.SECOND, Integer.parseInt(tab[i].substring(17, 19)));
							try
							{
								cal.set(Calendar.MILLISECOND, Integer.parseInt(tab[i].substring(20, tab[i].length())));
							}
							catch(Exception e){}
							tab[i] = String.valueOf(cal.getTime().getTime());
						}
					}
				}
				for (int i=0; i<tab[12].length(); i++)
				{
					if(tab[12].charAt(i) == ' ')
					{
						tab[12] = tab[12].substring(0, i);
						break;
					}
				}
				tab[6] = tab[6].replace("'", "");
				gui.delay = System.currentTimeMillis() + Long.parseLong(tab[12]);
				gui.infos = tab;
				gui.start = System.currentTimeMillis();
				gui.totalTime = Long.parseLong(tab[4]);
				gui.backTime = Long.parseLong(tab[12]);
				gui.tmp = gui.backTime;
			}
		}
		catch (MalformedURLException e){}
		catch (IOException e){}
		catch (Exception e){}
	}

	private void getImg()
	{
		URL url;
		try
		{
			url = new URL("http://www.music-story.com/recherche?q=" + gui.infos[2].replace(" ", "+") + "+" + gui.infos[1].replace(" ", "+"));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			String imgUrl = "no-image";
			String regex = "http://images.music-story.com/img/album";
			String realUrl = "";
			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine + "\n");
				if (inputLine.contains(regex))
				{
					imgUrl = inputLine;
					String tmp = "";
					for (int i=0; i< imgUrl.length()-1; i++)
					{
						tmp = imgUrl.substring(i);
						if (tmp.startsWith("http://"))
						{
							for (int j=0; j<tmp.length(); j++)
							{
								imgUrl = tmp.substring(0, j);
								if (imgUrl.endsWith(".jpg"))
								{
									realUrl = imgUrl;
									break;
								}
							}
							break;
						}
					}
				}
			}
			if(!gui.infos[6].equals(""))
				realUrl = gui.infos[6];
			gui.albumImg = realUrl.equals("") ? null : ImageIO.read(new URL(realUrl));
			in.close();
		}
		catch (MalformedURLException e)
		{
			gui.albumImg = null;
		}
		catch (IOException e)
		{
			gui.albumImg = null;
		}
		catch (Exception e)
		{
			gui.albumImg = null;
		}
	}
}