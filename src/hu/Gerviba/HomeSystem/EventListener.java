package hu.Gerviba.HomeSystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import hu.Gerviba.HomeSystem.DataStores.PlayerInfo;

public class EventListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onExit(PlayerQuitEvent event) {
		if (PlayerInfo.isExists(event.getPlayer().getName()))
			PlayerInfo.unregisterPlayer(PlayerInfo.getPlayer(event.getPlayer().getName()));
	}

}
