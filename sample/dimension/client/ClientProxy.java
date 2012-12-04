package sample.dimension.client;

// åpè≥Ç∑ÇÈÇÃÇ≈
import sample.dimension.common.CommonProxy;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.ITickHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void load()
	{
		// Clientë§å¿íËÇ≈ÇÃtickÇ≈ÇÃèàóù
		TickRegistry.registerTickHandler(new PortalTickHandler(), Side.CLIENT);
	}
}