package me.johnnywoof.twc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class WaterCycle extends JavaPlugin implements Listener {

	static int MAX_HEIGHT = 500;
	static int TASK_TIMER_INTERVAL = 100;

	private final Map<String, Integer> tasks = new HashMap<>();

	@Override
	public void onEnable() {

		this.saveDefaultConfig();

		MAX_HEIGHT = this.getConfig().getInt("max-height", 500);
		TASK_TIMER_INTERVAL = (this.getConfig().getInt("task-timer-interval", 5) * 20);

		this.getServer().getPluginManager().registerEvents(this, this);

	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWeather(WeatherChangeEvent event) {

		String worldName = event.getWorld().getName();

		if (event.toWeatherState()) {

			if (!this.tasks.containsKey(worldName)) {

				int id = this.getServer().getScheduler().runTaskTimer(this, new WCTask(event.getWorld()), 0, TASK_TIMER_INTERVAL).getTaskId();

				this.tasks.put(worldName, id);

			}

		} else {

			Integer id = this.tasks.get(worldName);

			if (id != null) {

				this.getServer().getScheduler().cancelTask(id);

				this.tasks.remove(worldName);

			}

		}

	}

}
