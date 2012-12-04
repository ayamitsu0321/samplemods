package sample.dimension.common;

import sample.dimension.SampleDimension;

import net.minecraft.src.*;

public class WorldProviderSample extends WorldProvider
{
	/**
	 * Dimension��ID��
	 */
	@Override
	public void registerWorldChunkManager()
	{
		super.registerWorldChunkManager();
		this.dimensionId = SampleDimension.getDimensionId();
	}
	
	/**
	 * �n�ザ��Ȃ��ł���[
	 */
	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}
	
	/**
	 * false���������ƃx�b�h����������
	 */
	@Override
	public boolean canRespawnHere()
	{
		return false;
	}
	
	/**
	 * Dimension�̖��O
	 */
	@Override
	public String getDimensionName()
	{
		return "Sample";
	}
}