package sample.dimension.common;

import sample.dimension.SampleDimension;

import net.minecraft.src.*;

public class WorldProviderSample extends WorldProvider
{
	/**
	 * DimensionのIDを
	 */
	@Override
	public void registerWorldChunkManager()
	{
		super.registerWorldChunkManager();
		this.dimensionId = SampleDimension.getDimensionId();
	}
	
	/**
	 * 地上じゃないですよー
	 */
	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}
	
	/**
	 * falseをかえすとベッドが爆発する
	 */
	@Override
	public boolean canRespawnHere()
	{
		return false;
	}
	
	/**
	 * Dimensionの名前
	 */
	@Override
	public String getDimensionName()
	{
		return "Sample";
	}
}