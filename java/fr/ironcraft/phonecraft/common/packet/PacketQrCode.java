package fr.ironcraft.phonecraft.common.packet;

import fr.ironcraft.phonecraft.Phonecraft;
import fr.ironcraft.phonecraft.common.tileentities.TileEntityQrCode;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketQrCode implements IBasePacket
{
	private int wName;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private String data;
	
	public PacketQrCode(){}

	public PacketQrCode(int name, int x, int y, int z, String d)
	{
		this.wName = name;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.data = d;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
	{
		buffer.writeInt(this.wName);
		buffer.writeInt(this.xCoord);
    	buffer.writeInt(this.yCoord);
    	buffer.writeInt(this.zCoord);
    	try {
    		buffer.writeStringToBuffer(this.data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
	{
		this.wName = buffer.readInt();
		this.xCoord = buffer.readInt();
		this.yCoord = buffer.readInt();
		this.zCoord = buffer.readInt();
		
		try {
			this.data = buffer.readStringFromBuffer(buffer.capacity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		World w = player.worldObj;
		TileEntity tile = w.getTileEntity(xCoord, yCoord, zCoord);
		if (tile != null)
		{
			if (tile instanceof TileEntityQrCode)
			{
				TileEntityQrCode tileQrCode = (TileEntityQrCode)tile;
				tileQrCode.textureID = -1;
				tileQrCode.data = data;
				tileQrCode.texture = Phonecraft.urlFiles + "/qrcodes/" + tileQrCode.xCoord + "_" + tileQrCode.yCoord + "_" + tileQrCode.zCoord + ".png";
				tileQrCode.img = null;
			}
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		World w = player.worldObj;
		if (w == null)
			return;
		TileEntity tile = w.getTileEntity(xCoord, yCoord, zCoord);
		if (tile != null)
		{
			if (tile instanceof TileEntityQrCode)
			{
				System.out.println("Server set Qrcode by packet");
				TileEntityQrCode tileQrCode = (TileEntityQrCode)tile;
				tileQrCode.textureID = -1;
				tileQrCode.texture = data;
				tileQrCode.img = null;
			}
		}
		Phonecraft.packetPipeline.sendToAll(this);
	}
}