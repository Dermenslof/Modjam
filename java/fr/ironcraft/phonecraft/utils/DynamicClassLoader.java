package fr.ironcraft.phonecraft.utils;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.URLClassLoader;
import java.net.URL;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * @authors Dermenslof, DrenBx
 */
public class DynamicClassLoader extends URLClassLoader
{
	HashMap<Integer, Object[]> constantVals = new HashMap<Integer, Object[]>();
	static ConcurrentHashMap<String, Reference<Class>>classCache = new ConcurrentHashMap<String, Reference<Class> >();
	static final URL[] EMPTY_URLS = new URL[]{};
	static final ReferenceQueue rq = new ReferenceQueue();

	public DynamicClassLoader()
	{
		super(EMPTY_URLS,(Thread.currentThread().getContextClassLoader() == null || Thread.currentThread().getContextClassLoader() == ClassLoader.getSystemClassLoader()) ? Compiler.class.getClassLoader():Thread.currentThread().getContextClassLoader());
	}

	public DynamicClassLoader(ClassLoader parent)
	{
		super(EMPTY_URLS,parent);
	}
	
	static public <K,V> void clearCache(ReferenceQueue rq, ConcurrentHashMap<K, Reference<V>> cache)
	{
		//cleanup any dead entries
		if(rq.poll() != null)
		{
			while(rq.poll() != null)
				;
			for(Map.Entry<K, Reference<V>> e : cache.entrySet())
			{
				Reference<V> val = e.getValue();
				if(val != null && val.get() == null)
					cache.remove(e.getKey(), val);
			}
		}
	}
	
	public Class defineClass(String name, byte[] bytes, Object srcForm)
	{
		clearCache(rq, classCache);
		Class c = defineClass(name, bytes, 0, bytes.length);
		classCache.put(name, new SoftReference(c,rq));
		return c;
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		Reference<Class> cr = classCache.get(name);
		if(cr != null)
		{
			Class c = cr.get();
			if(c != null)
				return c;
			else
				classCache.remove(name, cr);
		}
		return super.findClass(name);
	}

	public void registerConstants(int id, Object[] val)
	{
		constantVals.put(id, val);
	}

	public Object[] getConstants(int id)
	{
		return constantVals.get(id);
	}

	public void addURL(URL url)
	{
		super.addURL(url);
	}

}