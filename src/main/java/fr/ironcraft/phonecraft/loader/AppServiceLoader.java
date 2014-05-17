package fr.ironcraft.phonecraft.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModClassLoader;
import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.api.PhoneApps;

/**
 * @authors Dermenslof, DrenBx
 */
class JarFilter implements FilenameFilter
{
	public boolean accept(File dir, String name)
	{
		return (name.endsWith(".zip") || name.endsWith(".jar"));
	}
}

/**
 * @authors Dermenslof, DrenBx
 */
public class AppServiceLoader
{
	private List<PhoneApps> appCollection;

	private ModClassLoader ucl;

	public AppServiceLoader()
	{
		appCollection = new ArrayList<PhoneApps>();
	}
	
	public void search(File dir) throws Exception
	{
		if (dir.isFile())
			return;
		else if(!dir.exists())
		{
			System.out.println("[PhoneCraft] create apps folder");
			dir.mkdirs();
		}
		File[] files = dir.listFiles(new JarFilter());
		for (File f : files)
			findClassesInJar(PhoneApps.class, f);
	}
	
	public synchronized boolean findClassesInJar(final Class<?> baseInterface, final File jarFullPath) throws Exception
	{
		
		final List<String> classesTobeReturned = new ArrayList<String>();
		if (!jarFullPath.isDirectory())
		{
			@SuppressWarnings("resource")
			JarFile jar = new JarFile(jarFullPath);
			String jarName = jar.getName().replace(".zip", "").replace(Phonecraft.phoneFolder + "apps/", "");
			String zipname = jarFullPath.getName().replace(".zip", "");
			//File resourcesFile = new File(jarFullPath.getParentFile(), "resources/" + zipname);
			File resourcesFile = new File(jarFullPath.getParentFile(), "resources/");
			
			for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();)
			{
				JarEntry entry = (JarEntry) enums.nextElement();
				String image = entry.getName().replace("resources/", "");
				if (entry.getName().endsWith(".png") || entry.getName().endsWith(".jpg"))
				{
					File dest = new File(resourcesFile, image);
					System.out.println("[PhoneCraft] extract image " + image + " to " + dest.getPath());
					if (!resourcesFile.exists())
						resourcesFile.mkdir();
					InputStream is = jar.getInputStream(entry);
					if(!dest.exists())
					{
						if(!dest.getParentFile().exists())
							dest.getParentFile().mkdirs();
						dest.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(dest);
					while (is.available() > 0)
						fos.write(is.read());
					fos.close();
					is.close();
				}
			}
			String currentThreadName = Thread.currentThread().getName();
			JarInputStream jarFile = null;
			ClassLoader ucl2 = Loader.instance().getModClassLoader();
			if(ucl2 instanceof ModClassLoader)
			{
				 ucl = (ModClassLoader) Loader.instance().getModClassLoader();
				 ucl.addFile(jarFullPath);
			}
			jarFile = new JarInputStream(new FileInputStream(jarFullPath));
			JarEntry jarEntry;
			while (true)
			{
				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null)
					break;
				if (jarEntry.getName().endsWith(".class"))
				{
					String classname = jarEntry.getName().replaceAll("/", "\\.");
					classname = classname.substring(0, classname.length() - 6);
					if (!classname.contains("$"))
					{
						try
						{
							final Class<?> myLoadedClass = Class.forName(classname, true, ucl);
							if (baseInterface.isAssignableFrom(myLoadedClass))
							{
								PhoneApps app = (PhoneApps) myLoadedClass.newInstance();
								appCollection.add(app);
								System.out.println("[PhoneApps] " + app.appname() + " "+ app.version() + " ---> DONE");
								return true;
							}
							else
							{
								Class.forName(classname, true, ucl);
								ucl.loadClass(classname);
								myLoadedClass.newInstance();
							}
						}
						catch (final ClassNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			jarFile.close();
		}
		return false;
	}

	public void search(String directory) throws Exception
	{
		File dir = new File(directory);
		search(dir);
	}
	
	public List<PhoneApps> getAppCollection()
	{
		return appCollection;
	}

	public void setAppCollection(List<PhoneApps> appCollection)
	{
		this.appCollection = appCollection;
	}
}