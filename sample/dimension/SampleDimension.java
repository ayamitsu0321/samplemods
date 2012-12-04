package sample.dimension;

import sample.dimension.common.*;

import net.minecraft.src.*;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.FMLLog;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.DimensionManager;

import java.util.logging.Level;


@Mod(
	modid = "SampleDimension",
	name = "SampleDimension",
	version = "1.0.0"
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = false,
	channels = "sampledimension",
	packetHandler = sample.dimension.common.PacketHandler.class
)
public class SampleDimension
{
	@Mod.Instance("SampleDimension")
	public static SampleDimension instance;
	
	@SidedProxy(clientSide = "sample.dimension.client.ClientProxy", serverSide = "sample.dimension.common.CommonProxy")
	public static CommonProxy proxy;
	
	/** �|�[�^���u���b�N�A�l�U�[�Q�[�g�݂����ɂ��邽�� */
	public static BlockPortal portal;
	public static int portalId;
	
	/** �|�[�^�������邽�߂̃A�C�e���A�ʂ̕��@�ł��悢 */
	public static Item sampleKey;
	public static int sampleKeyId;
	
	/** ID��ێ����Ă��� */
	private static int dimensionId;
	private static int providerType;

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		try
		{
			config.load();
			this.portalId = config.getBlock("portal", 400).getInt();
			this.sampleKeyId = config.getItem("sampleKey", 12000).getInt();
			this.dimensionId = config.get("dimension", "DimensionID", 20).getInt();
			this.providerType = config.get("dimension", "ProviderType", 20).getInt();
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Error Massage");
		}
		finally
		{
			config.save();
		}
	}
	
	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		// WorldProvider�̓o�^
		DimensionManager.registerProviderType(this.providerType, sample.dimension.common.WorldProviderSample.class, false);
		// Dimension�̓o�^
		DimensionManager.registerDimension(this.dimensionId, this.providerType);
		this.portal = (BlockPortal)(new BlockPortalSample(this.portalId, Block.portal.blockIndexInTexture).setBlockName("sample.portal"));
		this.sampleKey = (new ItemSampleKey(this.sampleKeyId)).setItemName("sample.key").setCreativeTab(CreativeTabs.tabMaterials);
		proxy.load();
	}
	
	/**
	 * Dimension��ID
	 */
	public static int getDimensionId()
	{
		return dimensionId;
	}
	
	/**
	 * WorldProvider��ID
	 */
	public static int getProviderType()
	{
		return providerType;
	}
}