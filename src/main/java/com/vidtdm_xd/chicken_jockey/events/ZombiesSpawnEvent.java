package com.vidtdm_xd.chicken_jockey.events;

import com.vidtdm_xd.chicken_jockey.ChickenJockey;
import com.vidtdm_xd.chicken_jockey.goal.BabyZombieJockeyGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = ChickenJockey.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ZombiesSpawnEvent {
    private static final String IS_JOCKEY_SEEKER = "IsJockeySeeker";

    @SubscribeEvent
    public static void onEntitySpawn(FinalizeSpawnEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            if (zombie.isBaby()) {
                if (zombie.level().random.nextInt(5) == 0) {
                    zombie.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            if (zombie.isBaby()) {
                if (zombie.getPersistentData().getBoolean(IS_JOCKEY_SEEKER)) {
                    zombie.goalSelector.addGoal(2, new BabyZombieJockeyGoal(zombie));
                }
            }
        }
    }
}
