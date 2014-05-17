package fr.ironcraft.phonecraft.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Dermenslof, DrenBx
 */
public interface IBasePacket
{
    public abstract void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer);

    public abstract void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer);

    public abstract void handleClientSide(EntityPlayer player);

    public abstract void handleServerSide(EntityPlayer player);
}
