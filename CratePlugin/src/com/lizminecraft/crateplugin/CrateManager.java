package com.lizminecraft.crateplugin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrateManager {

	public boolean isActive;
	public ArrayList<Player> playersEntered;
	public ArrayList<ItemStack> prizes;
	public BossBar bossBar = Bukkit.getServer().createBossBar(
			ChatColor.AQUA + "A crate has began, do */crate join* for a chance to win!",
			BarColor.PINK, BarStyle.SOLID, BarFlag.CREATE_FOG);
	
	public CrateManager() {
		isActive = false;
		playersEntered = new ArrayList<Player>();
		prizes = new ArrayList<ItemStack>();
	}
	
	public void attemptStartCrate(Player requester) {
		//Validate if the crate can be entered by players
		if(prizes.size() == 5) {
			isActive = true;
			for(Player player : Bukkit.getOnlinePlayers()) {
				bossBar.addPlayer(player);
			}
		}else {
			requester.sendMessage("Please have 5 prizes added!");
		}
	}
	
	public void addPrize(String args1, String args2, Player sender) {
		Material material;
		int amount = 1;
		if(Material.getMaterial(args1.toUpperCase()) != null) {
			material = Material.getMaterial(args1.toUpperCase());
			if(args2.matches("-?\\d+")) {
				amount = Integer.parseInt(args2);
			}else {
				sender.sendMessage(ChatColor.RED + "Please enter a valid amount");
				return;
			}
			prizes.add(new ItemStack(material, amount));
			sender.sendMessage("prize added");
		}else {
			sender.sendMessage(ChatColor.RED + "Please enter a valid material");
			return;
		}
	}
	
	public void addPlayer(Player player) {
		playersEntered.add(player);
	}
	
	public void removePlayer(Player player) {
		playersEntered.remove(player);
		bossBar.removePlayer(player);
	}

	public void closeEntry() {
		closeBossBar();
		distributePrizes();
		isActive = false;
	}
	
	public void distributePrizes() {
		for(Player player : playersEntered) {
			int prize = PrizeLogic.prizeWon();
			ItemStack prizeItem = prizes.get(prize);
			player.getInventory().addItem(prizeItem);
			player.sendMessage(ChatColor.GREEN + "You won prize number: " + prize + 1);
			if (prizeItem.getAmount() == 1) {
				player.sendMessage(ChatColor.GREEN + "You won a " + prizeItem.getType().toString().toLowerCase());		
			}else {
				player.sendMessage(ChatColor.GREEN + "You won " + prizeItem.getAmount() + prizeItem.getType().toString().toLowerCase() + "s" );
			}
		}
	}
	
	public void showBossBar() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			bossBar.addPlayer(player);
		}
	}
	
	public void closeBossBar() {
		bossBar.removeAll();
	}
	
	public void refresh() {
		//CALLED WHEN A CRATE ENDS OR IS DELETED
		closeBossBar();
		playersEntered.clear();
		prizes.clear();
		isActive = false;
		
	}
}
