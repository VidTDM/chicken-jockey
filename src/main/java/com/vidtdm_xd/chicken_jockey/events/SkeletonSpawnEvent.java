package com.vidtdm_xd.chicken_jockey.events;

import com.vidtdm_xd.chicken_jockey.ChickenJockey;
import com.vidtdm_xd.chicken_jockey.goal.SkeletonSpiderJockeyGoal;
import net.minecraft.world.entity.monster.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

//import static com.vidtdm_xd.chicken_jockey.ChickenJockey.LOGGER;

@EventBusSubscriber(modid = ChickenJockey.MODID)
public class SkeletonSpawnEvent {
    private static final String IS_JOCKEY_SEEKER = "IsJockeySeeker";

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof AbstractSkeleton abstractSkeleton) {
            switch (abstractSkeleton) {
                case Skeleton skeleton when skeleton.level().getRandom().nextFloat() < 0.15f -> {
                    skeleton.goalSelector.addGoal(3, new SkeletonSpiderJockeyGoal(skeleton));
                    skeleton.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
//                LOGGER.info("normal skeleton");
                }
                case Stray stray when stray.level().getRandom().nextFloat() < 0.15f -> {
                    stray.goalSelector.addGoal(3, new SkeletonSpiderJockeyGoal(stray));
                    stray.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
//                LOGGER.info("stray");
                }
                case Bogged bogged when bogged.level().getRandom().nextFloat() < 0.15f -> {
                    bogged.goalSelector.addGoal(3, new SkeletonSpiderJockeyGoal(bogged));
                    bogged.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
//                LOGGER.info("bogged");
                }
                case WitherSkeleton wither when wither.level().getRandom().nextFloat() < 0.15f -> {
                    wither.goalSelector.addGoal(3, new SkeletonSpiderJockeyGoal(wither));
                    wither.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
//                LOGGER.info("wither skelly");
                }
                default -> {
                }
            }
        }
    }
}
