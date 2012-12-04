package sample.dimension.client;

import sample.dimension.SampleDimension;

import net.minecraft.src.*;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

import java.util.EnumSet;

public class PortalTickHandler implements ITickHandler
{
	/** 多重でテレポートしないため */
	private boolean hasInit;
	
	public PortalTickHandler() {}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		EntityPlayer player = null;
		
		for (Object obj : tickData)
		{
			if (obj instanceof EntityPlayer)
			{
				player = (EntityPlayer)obj;
				break;
			}
		}
		
		if (player instanceof EntityPlayerSP)
		{
			if (this.isEntityInsidePortal(player))
			{
				this.tickInPortalStart((EntityPlayerSP)player);
			}
		}
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		EntityPlayer player = null;
		
		for (Object obj : tickData)
		{
			if (obj instanceof EntityPlayer)
			{
				player = (EntityPlayer)obj;
				break;
			}
		}
		
		if (player instanceof EntityPlayerSP)
		{
			this.tickInPortalEnd((EntityPlayerSP)player);
		}
	}
	
	/**
	 * プレイヤーでのtick
	 */
	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.PLAYER);
	}
	
	@Override
	public String getLabel()
	{
		return "SamplePortal";
	}
	
	/**
	 * プレイヤーにポータルに入ったときの処理をしてもらう
	 */
	public void tickInPortalStart(EntityPlayerSP player)
	{
		if (!this.hasInit && player != null && player.ridingEntity == null)
		{
			player.setInPortal();
		}
	}
	
	/**
	 * ポータルに入って、一定の値をこえるとテレポート
	 * そのときにPacketを2回おくると2回テレポートする処理をしてしまうので、hasInitで判定
	 */
	public void tickInPortalEnd(EntityPlayerSP player)
	{
		if (player != null && player.ridingEntity == null && !this.hasInit)
		{
			if (!this.inPortal(player) && player.timeInPortal >= 1.0F)
			{
				int dimensionId = player.worldObj.provider.dimensionId != SampleDimension.getDimensionId() ? SampleDimension.getDimensionId() : 0;
				// Packetの送信、sample.dimension.common.PacketHandlerで受け取る
				PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("sampledimension", new byte[] { (byte)1 }));
				player.timeInPortal = 0.0F;
				this.hasInit = true;
				return;
			}
		}
		
		this.hasInit = false;
	}
	
	/**
	 * Entityがというか、プレイヤーが自作ポータルブロックの中にいるか否かの判定
	 */
	public boolean isEntityInsidePortal(Entity entity)
	{
		if (entity == null)
		{
			return false;
		}
		
		for (int var1 = 0; var1 < 8; ++var1)
        {
            float var2 = ((float)((var1 >> 0) % 2) - 0.5F) * entity.width * 0.8F;
            float var3 = ((float)((var1 >> 1) % 2) - 0.5F) * 0.1F;
            float var4 = ((float)((var1 >> 2) % 2) - 0.5F) * entity.width * 0.8F;
            int var5 = MathHelper.floor_double(entity.posX + (double)var2);
            int var6 = MathHelper.floor_double(entity.posY + (double)entity.getEyeHeight() + (double)var3);
            int var7 = MathHelper.floor_double(entity.posZ + (double)var4);

            if (entity.worldObj.getBlockId(var5, var6, var7) == SampleDimension.portal.blockID)
            {
                return true;
            }
        }

        return false;
	}
	
	/**
	 * inPortalのフィールドがprotectedアクセスだったので
	 */
	private boolean inPortal(Entity entity)
	{
		try
		{
			return ((Boolean)ModLoader.getPrivateValue(Entity.class, entity, 67)).booleanValue();
		}
		catch (Exception e)
		{
			return false;
		}
	}
}