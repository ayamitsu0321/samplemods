package sample.dimension.common;

import sample.dimension.SampleDimension;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TeleporterSample extends Teleporter
{
	/**
	 * Teleporterのフィールドが全てprivateなので
	 * かわりにこうしたという
	 * 今回は土ブロック(Block.dirt)でゲートの枠をつくります
	 */
	
	public WorldServer worldObj;
	public Random random;
	public LongHashMap map = new LongHashMap();
	public List list = new ArrayList();// <Long>
	
	public TeleporterSample(WorldServer worldserver)
	{
		super(worldserver);
		this.worldObj = worldserver;
		this.random = new Random(worldserver.getSeed());
	}
	
	/**
	 * @param entity
	 * @param posX in World position X
	 * @param posY in World position Y
	 * @param posZ in World position Z
	 * @param yaw entity's rotationYaw
	 */
	@Override
	public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
	{
		if (this.worldObj.provider.dimensionId != 1)
        {
            if (!this.placeInExistingPortal(par1Entity, par2, par4, par6, par8))
            {
                this.func_85188_a(par1Entity);
                this.placeInExistingPortal(par1Entity, par2, par4, par6, par8);
            }
        }
        else
        {
            int var9 = MathHelper.floor_double(par1Entity.posX);
            int var10 = MathHelper.floor_double(par1Entity.posY) - 1;
            int var11 = MathHelper.floor_double(par1Entity.posZ);
            byte var12 = 1;
            byte var13 = 0;

            for (int var14 = -2; var14 <= 2; ++var14)
            {
                for (int var15 = -2; var15 <= 2; ++var15)
                {
                    for (int var16 = -1; var16 < 3; ++var16)
                    {
                        int var17 = var9 + var15 * var12 + var14 * var13;
                        int var18 = var10 + var16;
                        int var19 = var11 + var15 * var13 - var14 * var12;
                        boolean var20 = var16 < 0;
                        this.worldObj.setBlockWithNotify(var17, var18, var19, var20 ? Block.dirt.blockID : 0);
                    }
                }
            }

            par1Entity.setLocationAndAngles((double)var9, (double)var10, (double)var11, par1Entity.rotationYaw, 0.0F);
            par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
        }
	}
	
	/**
	 * @param entity
	 * @param posX
	 * @param posY
	 * @param posZ
	 * @param yaw
	 * @return boolean
	 */
	@Override
	public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
	{
		short var9 = 128;
        double var10 = -1.0D;
        int var12 = 0;
        int var13 = 0;
        int var14 = 0;
        int var15 = MathHelper.floor_double(par1Entity.posX);
        int var16 = MathHelper.floor_double(par1Entity.posZ);
        long var17 = ChunkCoordIntPair.chunkXZ2Int(var15, var16);
        boolean var19 = true;
        double var27;
        int var48;

        if (this.map.containsItem(var17))
        {
            PortalPosition var20 = (PortalPosition)this.map.getValueByKey(var17);
            var10 = 0.0D;
            var12 = var20.posX;
            var13 = var20.posY;
            var14 = var20.posZ;
            var20.field_85087_d = this.worldObj.getTotalWorldTime();
            var19 = false;
        }
        else
        {
            for (var48 = var15 - var9; var48 <= var15 + var9; ++var48)
            {
                double var21 = (double)var48 + 0.5D - par1Entity.posX;

                for (int var23 = var16 - var9; var23 <= var16 + var9; ++var23)
                {
                    double var24 = (double)var23 + 0.5D - par1Entity.posZ;

                    for (int var26 = this.worldObj.getActualHeight() - 1; var26 >= 0; --var26)
                    {
                        if (this.worldObj.getBlockId(var48, var26, var23) == SampleDimension.portal.blockID)
                        {
                            while (this.worldObj.getBlockId(var48, var26 - 1, var23) == SampleDimension.portal.blockID)
                            {
                                --var26;
                            }

                            var27 = (double)var26 + 0.5D - par1Entity.posY;
                            double var29 = var21 * var21 + var27 * var27 + var24 * var24;

                            if (var10 < 0.0D || var29 < var10)
                            {
                                var10 = var29;
                                var12 = var48;
                                var13 = var26;
                                var14 = var23;
                            }
                        }
                    }
                }
            }
        }

        if (var10 >= 0.0D)
        {
            if (var19)
            {
                this.map.add(var17, new PortalPosition(this, var12, var13, var14, this.worldObj.getTotalWorldTime()));
                this.list.add(Long.valueOf(var17));
            }

            double var49 = (double)var12 + 0.5D;
            double var25 = (double)var13 + 0.5D;
            var27 = (double)var14 + 0.5D;
            int var50 = -1;

            if (this.worldObj.getBlockId(var12 - 1, var13, var14) == SampleDimension.portal.blockID)
            {
                var50 = 2;
            }

            if (this.worldObj.getBlockId(var12 + 1, var13, var14) == SampleDimension.portal.blockID)
            {
                var50 = 0;
            }

            if (this.worldObj.getBlockId(var12, var13, var14 - 1) == SampleDimension.portal.blockID)
            {
                var50 = 3;
            }

            if (this.worldObj.getBlockId(var12, var13, var14 + 1) == SampleDimension.portal.blockID)
            {
                var50 = 1;
            }

            int var30 = par1Entity.func_82148_at();

            if (var50 > -1)
            {
                int var31 = Direction.field_71578_g[var50];
                int var32 = Direction.offsetX[var50];
                int var33 = Direction.offsetZ[var50];
                int var34 = Direction.offsetX[var31];
                int var35 = Direction.offsetZ[var31];
                boolean var36 = !this.worldObj.isAirBlock(var12 + var32 + var34, var13, var14 + var33 + var35) || !this.worldObj.isAirBlock(var12 + var32 + var34, var13 + 1, var14 + var33 + var35);
                boolean var37 = !this.worldObj.isAirBlock(var12 + var32, var13, var14 + var33) || !this.worldObj.isAirBlock(var12 + var32, var13 + 1, var14 + var33);

                if (var36 && var37)
                {
                    var50 = Direction.footInvisibleFaceRemap[var50];
                    var31 = Direction.footInvisibleFaceRemap[var31];
                    var32 = Direction.offsetX[var50];
                    var33 = Direction.offsetZ[var50];
                    var34 = Direction.offsetX[var31];
                    var35 = Direction.offsetZ[var31];
                    var48 = var12 - var34;
                    var49 -= (double)var34;
                    int var22 = var14 - var35;
                    var27 -= (double)var35;
                    var36 = !this.worldObj.isAirBlock(var48 + var32 + var34, var13, var22 + var33 + var35) || !this.worldObj.isAirBlock(var48 + var32 + var34, var13 + 1, var22 + var33 + var35);
                    var37 = !this.worldObj.isAirBlock(var48 + var32, var13, var22 + var33) || !this.worldObj.isAirBlock(var48 + var32, var13 + 1, var22 + var33);
                }

                float var38 = 0.5F;
                float var39 = 0.5F;

                if (!var36 && var37)
                {
                    var38 = 1.0F;
                }
                else if (var36 && !var37)
                {
                    var38 = 0.0F;
                }
                else if (var36 && var37)
                {
                    var39 = 0.0F;
                }

                var49 += (double)((float)var34 * var38 + var39 * (float)var32);
                var27 += (double)((float)var35 * var38 + var39 * (float)var33);
                float var40 = 0.0F;
                float var41 = 0.0F;
                float var42 = 0.0F;
                float var43 = 0.0F;

                if (var50 == var30)
                {
                    var40 = 1.0F;
                    var41 = 1.0F;
                }
                else if (var50 == Direction.footInvisibleFaceRemap[var30])
                {
                    var40 = -1.0F;
                    var41 = -1.0F;
                }
                else if (var50 == Direction.enderEyeMetaToDirection[var30])
                {
                    var42 = 1.0F;
                    var43 = -1.0F;
                }
                else
                {
                    var42 = -1.0F;
                    var43 = 1.0F;
                }

                double var44 = par1Entity.motionX;
                double var46 = par1Entity.motionZ;
                par1Entity.motionX = var44 * (double)var40 + var46 * (double)var43;
                par1Entity.motionZ = var44 * (double)var42 + var46 * (double)var41;
                par1Entity.rotationYaw = par8 - (float)(var30 * 90) + (float)(var50 * 90);
            }
            else
            {
                par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
            }

            par1Entity.setLocationAndAngles(var49, var25, var27, par1Entity.rotationYaw, par1Entity.rotationPitch);
            return true;
        }
        else
        {
            return false;
        }
	}
	
	@Override
	public boolean func_85188_a(Entity par1Entity)
    {
        byte var2 = 16;
        double var3 = -1.0D;
        int var5 = MathHelper.floor_double(par1Entity.posX);
        int var6 = MathHelper.floor_double(par1Entity.posY);
        int var7 = MathHelper.floor_double(par1Entity.posZ);
        int var8 = var5;
        int var9 = var6;
        int var10 = var7;
        int var11 = 0;
        int var12 = this.random.nextInt(4);
        int var13;
        double var14;
        double var17;
        int var16;
        int var19;
        int var21;
        int var20;
        int var23;
        int var22;
        int var25;
        int var24;
        int var27;
        int var26;
        double var31;
        double var32;

        for (var13 = var5 - var2; var13 <= var5 + var2; ++var13)
        {
            var14 = (double)var13 + 0.5D - par1Entity.posX;

            for (var16 = var7 - var2; var16 <= var7 + var2; ++var16)
            {
                var17 = (double)var16 + 0.5D - par1Entity.posZ;
                label274:

                for (var19 = this.worldObj.getActualHeight() - 1; var19 >= 0; --var19)
                {
                    if (this.worldObj.isAirBlock(var13, var19, var16))
                    {
                        while (var19 > 0 && this.worldObj.isAirBlock(var13, var19 - 1, var16))
                        {
                            --var19;
                        }

                        for (var20 = var12; var20 < var12 + 4; ++var20)
                        {
                            var21 = var20 % 2;
                            var22 = 1 - var21;

                            if (var20 % 4 >= 2)
                            {
                                var21 = -var21;
                                var22 = -var22;
                            }

                            for (var23 = 0; var23 < 3; ++var23)
                            {
                                for (var24 = 0; var24 < 4; ++var24)
                                {
                                    for (var25 = -1; var25 < 4; ++var25)
                                    {
                                        var26 = var13 + (var24 - 1) * var21 + var23 * var22;
                                        var27 = var19 + var25;
                                        int var28 = var16 + (var24 - 1) * var22 - var23 * var21;

                                        if (var25 < 0 && !this.worldObj.getBlockMaterial(var26, var27, var28).isSolid() || var25 >= 0 && !this.worldObj.isAirBlock(var26, var27, var28))
                                        {
                                            continue label274;
                                        }
                                    }
                                }
                            }

                            var32 = (double)var19 + 0.5D - par1Entity.posY;
                            var31 = var14 * var14 + var32 * var32 + var17 * var17;

                            if (var3 < 0.0D || var31 < var3)
                            {
                                var3 = var31;
                                var8 = var13;
                                var9 = var19;
                                var10 = var16;
                                var11 = var20 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (var3 < 0.0D)
        {
            for (var13 = var5 - var2; var13 <= var5 + var2; ++var13)
            {
                var14 = (double)var13 + 0.5D - par1Entity.posX;

                for (var16 = var7 - var2; var16 <= var7 + var2; ++var16)
                {
                    var17 = (double)var16 + 0.5D - par1Entity.posZ;
                    label222:

                    for (var19 = this.worldObj.getActualHeight() - 1; var19 >= 0; --var19)
                    {
                        if (this.worldObj.isAirBlock(var13, var19, var16))
                        {
                            while (var19 > 0 && this.worldObj.isAirBlock(var13, var19 - 1, var16))
                            {
                                --var19;
                            }

                            for (var20 = var12; var20 < var12 + 2; ++var20)
                            {
                                var21 = var20 % 2;
                                var22 = 1 - var21;

                                for (var23 = 0; var23 < 4; ++var23)
                                {
                                    for (var24 = -1; var24 < 4; ++var24)
                                    {
                                        var25 = var13 + (var23 - 1) * var21;
                                        var26 = var19 + var24;
                                        var27 = var16 + (var23 - 1) * var22;

                                        if (var24 < 0 && !this.worldObj.getBlockMaterial(var25, var26, var27).isSolid() || var24 >= 0 && !this.worldObj.isAirBlock(var25, var26, var27))
                                        {
                                            continue label222;
                                        }
                                    }
                                }

                                var32 = (double)var19 + 0.5D - par1Entity.posY;
                                var31 = var14 * var14 + var32 * var32 + var17 * var17;

                                if (var3 < 0.0D || var31 < var3)
                                {
                                    var3 = var31;
                                    var8 = var13;
                                    var9 = var19;
                                    var10 = var16;
                                    var11 = var20 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int var29 = var8;
        int var15 = var9;
        var16 = var10;
        int var30 = var11 % 2;
        int var18 = 1 - var30;

        if (var11 % 4 >= 2)
        {
            var30 = -var30;
            var18 = -var18;
        }

        boolean var33;

        if (var3 < 0.0D)
        {
            if (var9 < 70)
            {
                var9 = 70;
            }

            if (var9 > this.worldObj.getActualHeight() - 10)
            {
                var9 = this.worldObj.getActualHeight() - 10;
            }

            var15 = var9;

            for (var19 = -1; var19 <= 1; ++var19)
            {
                for (var20 = 1; var20 < 3; ++var20)
                {
                    for (var21 = -1; var21 < 3; ++var21)
                    {
                        var22 = var29 + (var20 - 1) * var30 + var19 * var18;
                        var23 = var15 + var21;
                        var24 = var16 + (var20 - 1) * var18 - var19 * var30;
                        var33 = var21 < 0;
                        this.worldObj.setBlockWithNotify(var22, var23, var24, var33 ? Block.dirt.blockID : 0);
                    }
                }
            }
        }

        for (var19 = 0; var19 < 4; ++var19)
        {
            this.worldObj.editingBlocks = true;

            for (var20 = 0; var20 < 4; ++var20)
            {
                for (var21 = -1; var21 < 4; ++var21)
                {
                    var22 = var29 + (var20 - 1) * var30;
                    var23 = var15 + var21;
                    var24 = var16 + (var20 - 1) * var18;
                    var33 = var20 == 0 || var20 == 3 || var21 == -1 || var21 == 3;
                    this.worldObj.setBlockWithNotify(var22, var23, var24, var33 ? Block.dirt.blockID : SampleDimension.portal.blockID);
                }
            }

            this.worldObj.editingBlocks = false;

            for (var20 = 0; var20 < 4; ++var20)
            {
                for (var21 = -1; var21 < 4; ++var21)
                {
                    var22 = var29 + (var20 - 1) * var30;
                    var23 = var15 + var21;
                    var24 = var16 + (var20 - 1) * var18;
                    this.worldObj.notifyBlocksOfNeighborChange(var22, var23, var24, this.worldObj.getBlockId(var22, var23, var24));
                }
            }
        }

        return true;
    }
}