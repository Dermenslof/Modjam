package fr.ironcraft.phonecraft.common.tileentities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.common.Blocks.ICBlocks;
import fr.ironcraft.phonecraft.packet.PacketQrCode;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Dermenslof, DrenBx
 */
public class TileEntityQrCode extends TileEntity
{
	public String data = "";
	public String texture =	"";
	public int textureID = -1;
	public BufferedImage img;
	
    public Packet getDescriptionPacket()
	{
    	int wName = this.worldObj.getWorldInfo().getVanillaDimension();;
		String data = this.texture;
		ByteArrayOutputStream bos = new ByteArrayOutputStream(16 + data.length());
        DataOutputStream outputStream = new DataOutputStream(bos);
        try
        {
        	outputStream.writeInt(wName);
        	outputStream.writeInt(this.xCoord);
        	outputStream.writeInt(this.yCoord);
        	outputStream.writeInt(this.zCoord);
        	outputStream.writeUTF(data);
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
        C17PacketCustomPayload packet = new C17PacketCustomPayload("qrcode", bos.toByteArray());
        Phonecraft.packetPipeline.sendToAll(new PacketQrCode(wName,  this.xCoord,  this.yCoord,  this.zCoord, data));
      
        return packet;
	}
    
    @Override
    public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        markDirty();
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
    	this.texture = par1NBTTagCompound.getString("texture");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
       	super.writeToNBT(par1NBTTagCompound);
    	par1NBTTagCompound.setString("texture", this.texture);
    }
    
    @Override
    public Block getBlockType()
    {
    	return ICBlocks.qrCode;
    }
}