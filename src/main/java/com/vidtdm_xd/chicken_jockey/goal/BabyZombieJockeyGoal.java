package com.vidtdm_xd.chicken_jockey.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class BabyZombieJockeyGoal extends Goal {
    private final Zombie baby_zombie;
    private Mob target;
    private final double speed = 1.2D;
    private final int searchRadius = 35;
    private int cooldown;

    public BabyZombieJockeyGoal(Zombie zombie) {
        this.baby_zombie = zombie;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (baby_zombie.isPassenger()) return false;
        if (cooldown > 0) {
            cooldown--;
            return false;
        }
        List<Mob> possibleTargets = baby_zombie.level().getEntitiesOfClass(
                Mob.class,
                baby_zombie.getBoundingBox().inflate(searchRadius),
                this::isValidJockey);
        if (possibleTargets.isEmpty()) return false;
        possibleTargets.sort(Comparator.comparingDouble(mob -> mob.distanceToSqr(baby_zombie)));
        target = possibleTargets.getFirst();
        return true;
    }

    @Override
    public void start() {
        if (target != null) {
            baby_zombie.getNavigation().moveTo(target, speed);
        }
    }

    @Override
    public void stop() {
        target = null;
        cooldown = 100;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null &&
                target.isAlive() &&
                !target.isVehicle() &&
                !baby_zombie.isPassenger();
    }

    @Override
    public void tick() {
        if (target == null) return;
        baby_zombie.getNavigation().moveTo(target, speed);
        if (baby_zombie.distanceToSqr(target) <= 2.0d) {
            if (!target.isVehicle()) {
                baby_zombie.startRiding(target, true);
            }
            target = null;
        }
    }

    private boolean isValidJockey(Mob mob) {
        if (!mob.isAlive() || mob.isVehicle()) return false;
        if (mob instanceof Chicken chicken && !chicken.isBaby()) {
            return true;
        } else if (mob instanceof Pig pig && !pig.isBaby() && !pig.isSaddled()) {
            return true;
        } else if (mob instanceof Cow cow && !cow.isBaby()) {
            return true;
        } else if (mob instanceof Sheep sheep && !sheep.isBaby()) {
            return true;
        } else if (mob instanceof Zombie zombie && !zombie.isBaby()) {
            return true;
        } else if (mob instanceof Spider spider && !spider.isBaby()) {
            return true;
        } else if (mob instanceof Ocelot ocelot && !ocelot.isBaby()) {
            return true;
        } else if (mob instanceof Panda) {
            return true;
        } else if ((mob instanceof AbstractHorse horse && !horse.isBaby()) && !(horse instanceof Llama || horse instanceof Camel)) {
            return true;
        } else if (mob instanceof Cat cat && !cat.isBaby() && !cat.isTame()) {
            return true;
        } else return mob instanceof Wolf wolf && !wolf.isBaby() && !wolf.isTame();
    }
}
