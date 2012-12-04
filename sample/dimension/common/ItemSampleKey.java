package sample.dimension.common;

import sample.dimension.SampleDimension;

import net.minecraft.src.*;

public class ItemSampleKey extends Item
{
	/**
	 * ポータルをつくるアイテム
	 * 他の方法でもよい
	 */
	
	public ItemSampleKey(int id)
	{
		super(id);
	}
	
	/**
	 * もうちょっといい書き方があればと
	 */
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int blockX, int blockY, int blockZ, int face, float hitX, float hitY, float hitZ)
	{
    	if (face == 0)
        {
            blockY--;// j
        }

        if (face == 1)
        {
            blockY++;// j
        }

        if (face == 2)
        {
            blockZ--;// k
        }

        if (face == 3)
        {
            blockZ++;// k
        }

        if (face == 4)
        {
            blockX--;// i
        }

        if (face == 5)
        {
            blockX++;// i
        }
    	
    	if (!player.canPlayerEdit(blockX, blockY, blockZ, face, is))
        {
            return false;
        }

        int i1 = world.getBlockId(blockX, blockY, blockZ);
    	
    	if (i1 == 0)
        {
        	// ポータルの作成
            if (this.tryToCreatePortal(world, blockX, blockY, blockZ))
            {
                //--is.stackSize;// 消費アイテムなら
                world.playSoundEffect(blockX, blockY, blockZ, "note.snare", 5.0F, 1.2F);
                return true;
            }
        }
    	
    	return true;
    }
	
	/**
	 * もうちょっとスマートにしたほうがいいかも
	 */
	private boolean tryToCreatePortal(World world, int i, int j, int k)
    {
        Block block = Block.blocksList[SampleDimension.portal.blockID];

    	// 念のための確認
        if (block instanceof BlockPortal)
        {
            BlockPortal portal = (BlockPortal)block;
            return portal.tryToCreatePortal(world, i, j, k);
        }

        return false;
    }
}