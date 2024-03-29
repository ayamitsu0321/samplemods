package sample.dimension.client;

// 継承するので
import sample.dimension.common.CommonProxy;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.ITickHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void load()
	{
		// Client側限定でのtickでの処理
		TickRegistry.registerTickHandler(new PortalTickHandler(), Side.CLIENT);
	}
}