package fr.ironcraft.phonecraft.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import fr.ironcraft.phonecraft.Phonecraft;

/**
 * @authors Dermenslof, DrenBx
 */
public class DeleteFile implements Runnable
{
	private String data;
	
	public DeleteFile(String par0)
	{
		this.data = par0;
	}

	@Override
	public void run()
	{
		delete();
	}

	private void delete()
	{
		HttpClient httpclient = null;
		HttpPost httppost = null;
		HttpResponse response = null;
		HttpEntity resEntity = null;
		HttpGet request = null;
		BufferedReader rd = null;
		String url = Phonecraft.urlFiles + "delete_qrcode.php?name=" + this.data;
		httpclient = new DefaultHttpClient();
		request = new HttpGet(url);
		request.addHeader("User-Agent", "Mozilla/5.0");
		try
		{
			response = httpclient.execute(request);
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			httpclient.getConnectionManager().shutdown();
		}
		catch(Exception e){}
	}

}
