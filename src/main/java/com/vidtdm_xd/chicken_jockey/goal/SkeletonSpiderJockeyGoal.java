package com.vidtdm_xd.chicken_jockey.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Spider;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class SkeletonSpiderJockeyGoal extends Goal {
    private final AbstractSkeleton skeleton;
    private Spider targetSpider;
    private final double speed = 1.2D;
    private final int searchRadius = 16;
    private int cooldown;

    public SkeletonSpiderJockeyGoal(AbstractSkeleton skeleton) {
        this.skeleton = skeleton;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (skeleton.isPassenger()) return false;

        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        List<Spider> spiders = skeleton.level().getEntitiesOfClass(Spider.class,
                skeleton.getBoundingBox().inflate(searchRadius),
                spider -> !spider.isVehicle() && spider.isAlive());
        if (!spiders.isEmpty()) {
            spiders.sort(Comparator.comparingDouble(spider -> spider.distanceToSqr(skeleton)));
            targetSpider = spiders.getFirst();
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        if (targetSpider != null) {
            skeleton.getNavigation().moveTo(targetSpider, speed);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetSpider != null && !skeleton.isPassenger() && targetSpider.isAlive() && !targetSpider.isVehicle();
    }

    @Override
    public void tick() {
        if (targetSpider != null) {
            skeleton.getNavigation().moveTo(targetSpider, speed);
            if (skeleton.distanceToSqr(targetSpider) <= 2.0d && !targetSpider.isVehicle()) {
                skeleton.startRiding(targetSpider, true);
            }
        }
    }

    @Override
    public void stop() {
        targetSpider = null;
        cooldown = 100;
    }
}
