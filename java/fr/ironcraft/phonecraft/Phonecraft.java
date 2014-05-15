package fr.ironcraft.phonecraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.common.CommonProxy;

@Mod(modid = Phonecraft.MODID, version = Phonecraft.VERSION)
public class Phonecraft
{
    public static final String MODID = "phonecraft";
    public static final String VERSION = "0.1";
    
    @Instance(MODID)
    public static Phonecraft instance;
    
    @SidedProxy(clientSide = "fr.ironcraft.phonecraft.ClientProxy", serverSide = "fr.ironcraft.phonecraft.CommonProxy")
    public static ClientProxy clientProxy;
    public static CommonProxy commonProxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {   
    }
}
