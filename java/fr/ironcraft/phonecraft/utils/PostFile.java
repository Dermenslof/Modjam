package fr.ironcraft.phonecraft.utils;

import java.io.BufferedReader;
import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import fr.ironcraft.phonecraft.Phonecraft;

/**
 * @authors Dermenslof, DrenBx
 */
public class PostFile implements Runnable
{
	private File file;

	public PostFile(File par0)
	{
		this.file = par0;
	}

	@Override
	public void run()
	{
		upload();
	}

	public void upload()
	{
		HttpClient httpclient = null;
		HttpPost httppost = null;
		HttpResponse response = null;
		HttpEntity resEntity = null;
		HttpGet request = null;
		BufferedReader rd = null;
		httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httppost = new HttpPost(Phonecraft.urlFiles + "upload_qrcode.php");
		MultipartEntity mpEntity = new MultipartEntity();
		ContentBody cbFile = new FileBody(this.file, "image/png");
		mpEntity.addPart("userfile", cbFile);
		httppost.setEntity(mpEntity);
		try
		{
			response = httpclient.execute(httppost);
			resEntity = response.getEntity();
			if (resEntity != null)
				EntityUtils.consume(resEntity);
			httpclient.getConnectionManager().shutdown();
		}
		catch(Exception e){}
		this.file.delete();
	}
}
