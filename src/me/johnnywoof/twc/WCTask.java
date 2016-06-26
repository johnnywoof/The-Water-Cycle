package me.johnnywoof.twc;

import org.bukkit.World;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class WCTask implements Runnable {

	private final World world;
	private final Random random = new Random();

	public WCTask(World world) {
		this.world = world;
	}

	@Override
	public void run() {

		for (Villager villager : this.world.getEntitiesByClass(Villager.class)) {

			if (villager.getLocation().getBlockY() <= WaterCycle.MAX_HEIGHT
					&& (!villager.isOnGround() || this.random.nextInt(15) == 4)) {

				villager.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,
								(WaterCycle.TASK_TIMER_INTERVAL + (3 * 20)),//3 seconds
								2,
								false,
								false),
						true);

			}

		}

	}

}
