package fr.ironcraft.phonecraft;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.common.CommonProxy;
import fr.ironcraft.phonecraft.common.Blocks.ICBlocks;
import fr.ironcraft.phonecraft.common.packet.PacketPipeline;

/**
 * @author Dermenslof, DrenBx
 */
@Mod(modid = Phonecraft.MODID, version = Phonecraft.VERSION)
public class Phonecraft
{
	public static String urlFiles;
	public static String phoneFolder = "./PhoneCraft/";
	public static final PacketPipeline packetPipeline = new PacketPipeline();
	
    public static final String MODID = "phonecraft";
    public static final String VERSION = "0.1";
    
    @Instance(MODID)
    public static Phonecraft instance;
    
    @SidedProxy(clientSide = "fr.ironcraft."+MODID+".client.ClientProxy", serverSide = "fr.ironcraft."+MODID+".common.CommonProxy")
    public static ClientProxy clientProxy;
    public static CommonProxy commonProxy;
    
    private static ICBlocks blocks;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		urlFiles = config.get(Configuration.CATEGORY_GENERAL, "urlFiles", "http://ironcraft.local/").getString(); /* dev value */
		if (config.hasChanged())
			config.save();
    	this.blocks = new ICBlocks();
    	this.blocks.init();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	clientProxy.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	System.out.println("[PhoneCraft] Version: "+VERSION+" was loaded");
    }
    
    public ICBlocks getBlocks()
    {
    	return this.blocks;
    }
}
