package com.lizminecraft.crateplugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if(Main.getInstance().crateManager.playersEntered.contains(player)) {
			Main.getInstance().crateManager.removePlayer(player);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(Main.getInstance().crateManager.isActive) {
			Main.getInstance().crateManager.bossBar.addPlayer(e.getPlayer());
		}
	}
	
}
