package com.vidtdm_xd.chicken_jockey.events;

import com.vidtdm_xd.chicken_jockey.ChickenJockey;
import com.vidtdm_xd.chicken_jockey.goal.SkeletonSpiderJockeyGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = ChickenJockey.MODID)
public class SkeletonSpawnEvent {
    private static final String IS_JOCKEY_SEEKER = "IsJockeySeeker";

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof AbstractSkeleton skeleton) {
            if (skeleton.level().random.nextInt(20) < 3) {
                skeleton.goalSelector.addGoal(3, new SkeletonSpiderJockeyGoal(skeleton));
                skeleton.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
            }
        }
    }
}
