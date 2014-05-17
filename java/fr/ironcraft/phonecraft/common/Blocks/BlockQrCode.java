package fr.ironcraft.phonecraft.common.Blocks;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.ironcraft.phonecraft.client.ClientProxy;
import fr.ironcraft.phonecraft.common.tileentities.TileEntityQrCode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Dermenslof, DrenBx
 */
public class BlockQrCode extends BlockContainer
{
	public BlockQrCode()
	{
		super(Material.cloth);
		this.setHardness(1.0F).setResistance(10.0F).setStepSound(soundTypeMetal);
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return ClientProxy.renderQrCodeID;
	}
	
	@SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return 0.2F;
    }

	public void onBlockAdded(World par1World, int x, int y, int z)
	{
		super.onBlockAdded(par1World, x, y, z);
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
    }
	
	
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0)
        {
        	if(par1World.isSideSolid(par2, par3, par4 + 1, ForgeDirection.NORTH))
        	{
        		par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        		return;
        	}
        }
        else if (l == 1)
        {
        	if(par1World.isSideSolid(par2 - 1, par3, par4, ForgeDirection.WEST))
            {
        		par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        		return;
            }
        }
        else if (l == 2)
        {
        	if(par1World.isSideSolid(par2, par3, par4 - 1, ForgeDirection.SOUTH))
        	{
        		par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        		return;
        	}
        
        }
        else if (l == 3)
        {
        	if(par1World.isSideSolid(par2 + 1, par3, par4, ForgeDirection.EAST))
        	{
        		par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        		return;
        	}
        }
        par1World.setBlockToAir(par2, par3, par4);
	}
	
	public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4) & 7;
        float f = 0.15F;
        if (l == 5)
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.02F, 1.0F, 1.0F);
        else if (l == 2)
            this.setBlockBounds(0.0F, 0.0F, 0.98F, 1.0F, 1.0F, 1.0F);
        else if (l == 3)
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.02F);
        else if (l == 4)
            this.setBlockBounds(0.98F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("qrCode");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityQrCode();
	}
}
