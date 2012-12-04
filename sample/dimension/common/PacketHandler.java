package sample.dimension.common;

import sample.dimension.SampleDimension;

import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.EntityPlayerMP;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.network.IPacketHandler;

public class PacketHandler implements IPacketHandler
{
	public PacketHandler() {}
	
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player)
	{
		if (packet.channel.equals("sampledimension") && packet.data[0] == 1 && player instanceof EntityPlayerMP)
		{
			EntityPlayerMP entityplayer = (EntityPlayerMP)player;
			int dimensionId = entityplayer.worldObj.provider.dimensionId != SampleDimension.getDimensionId() ? SampleDimension.getDimensionId() : 0;
			// Ç±Ç±Ç≈ÉèÅ[ÉãÉhà⁄ìÆ
			entityplayer.mcServer.getConfigurationManager().transferPlayerToDimension(entityplayer, dimensionId, new TeleporterSample(entityplayer.mcServer.worldServerForDimension(dimensionId)));
		}
	}
}