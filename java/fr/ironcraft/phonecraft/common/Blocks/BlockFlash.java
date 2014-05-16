package fr.ironcraft.phonecraft.common.Blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;

public class BlockFlash extends GenericBlock {
	
	public BlockFlash(Material par1Material) {
		super(par1Material);
	}

	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}

    public int quantityDropped(Random par1Random) {
        return 0;
    }

    public int idDropped(int par1, Random par2Random, int par3) {
    	return 0;
    }

}
