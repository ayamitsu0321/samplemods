package sample.dimension.client;

// �p������̂�
import sample.dimension.common.CommonProxy;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.ITickHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void load()
	{
		// Client������ł�tick�ł̏���
		TickRegistry.registerTickHandler(new PortalTickHandler(), Side.CLIENT);
	}
}