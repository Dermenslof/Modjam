package fr.ironcraft.phonecraft.common.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;
import fr.ironcraft.phonecraft.client.CreaTabs;
import fr.ironcraft.phonecraft.utils.TextureUtils;

/**
 * @authors Dermenslof, DrenBx
 */
public class ICBlocks
{
	public static Block qrCode, flash;
	
	public void init()
	{
		this.configBlocks();
		this.registerBlocks();
	}

	private void registerBlocks()
	{
		GameRegistry.registerBlock(qrCode, "qrCode");
		GameRegistry.registerBlock(flash, "flash");
		//GameRegistry.registerTileEntity(TileEntityQrCode.class, "QrCode");
	}

	private void configBlocks()
	{
		qrCode = new GenericBlock(Material.cloth).setBlockName("qrCode").setBlockTextureName(TextureUtils.getTextureNameForBlocks("qrCode")).setStepSound(Block.soundTypeCloth).setCreativeTab(CreaTabs.phoneTab).setBlockUnbreakable();
		flash = new BlockFlash(Material.glass).setHardness(0.3F).setLightLevel(1.0F).setBlockName("flash").setBlockTextureName(TextureUtils.getTextureNameForBlocks("flash"));
	}
}
