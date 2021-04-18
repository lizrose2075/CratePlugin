package com.lizminecraft.crateplugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	private static Main instance;
	public CrateManager crateManager;
	
	@Override
	public void onEnable() {

		instance = this;
		instance.crateManager = new CrateManager();
		
		getCommand("crate").setExecutor(new CrateCommand());
		
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		
	}
	
	public static Main getInstance() {return instance;}

}
