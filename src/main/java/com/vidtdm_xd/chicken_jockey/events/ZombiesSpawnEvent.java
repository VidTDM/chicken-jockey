package com.vidtdm_xd.chicken_jockey.events;

import com.vidtdm_xd.chicken_jockey.ChickenJockey;
import com.vidtdm_xd.chicken_jockey.goal.BabyZombieJockeyGoal;
import net.minecraft.world.entity.monster.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

//import static com.vidtdm_xd.chicken_jockey.ChickenJockey.LOGGER;

@EventBusSubscriber(modid = ChickenJockey.MODID)
public class ZombiesSpawnEvent {
    private static final String IS_JOCKEY_SEEKER = "IsJockeySeeker";
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Zombie z) {
            if (z.isBaby()) {
                switch (z) {
                    case Drowned drowned when drowned.level().getRandom().nextFloat() < 0.15f -> {
                        drowned.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
                        drowned.goalSelector.addGoal(1, new BabyZombieJockeyGoal(drowned));
//                    LOGGER.info("drowned");
                    }
                    case Husk husk when husk.level().getRandom().nextFloat() < 0.15f -> {
                        husk.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
                        husk.goalSelector.addGoal(1, new BabyZombieJockeyGoal(husk));
//                    LOGGER.info("husk");
                    }
                    case ZombieVillager villager when villager.level().getRandom().nextFloat() < 0.15f -> {
                        villager.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
                        villager.goalSelector.addGoal(1, new BabyZombieJockeyGoal(villager));
//                    LOGGER.info("zombie villager");
                    }
                    case Zombie zombie when !(z instanceof ZombifiedPiglin) && zombie.level().getRandom().nextFloat() < 0.15f -> {
                        zombie.getPersistentData().putBoolean(IS_JOCKEY_SEEKER, true);
                        zombie.goalSelector.addGoal(1, new BabyZombieJockeyGoal(zombie));
//                    LOGGER.info("normal zombie");
                    }
                    default -> {
                    }
                }
            }
        }
    }
}
