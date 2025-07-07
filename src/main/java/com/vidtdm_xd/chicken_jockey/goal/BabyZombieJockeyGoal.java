package com.vidtdm_xd.chicken_jockey.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Zombie;

import java.util.EnumSet;

public class BabyZombieJockeyGoal extends Goal {
    private final Zombie zombie;
    private Mob target;
    private final double speed = 1.2D;
    private final int searchRadius = 16;
    private int cooldown;

    public BabyZombieJockeyGoal(Zombie zombie) {
        this.zombie = zombie;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();
    }

    private boolean isValidJockey(Mob mob) {
        if (!mob.isAlive() || mob.isVehicle()) return false;
        if (mob instanceof Chicken chicken && !chicken.isBaby()) {
            return true;
        } else if (mob instanceof Pig pig && !pig.isBaby()) {
            return true;
        } else if (mob instanceof MushroomCow mooshroom && !mooshroom.isBaby()) {
            return true;
        }
        return false;
    }
}
