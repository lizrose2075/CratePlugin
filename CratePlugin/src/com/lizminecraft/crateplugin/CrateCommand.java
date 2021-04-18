package com.lizminecraft.crateplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CrateCommand implements CommandExecutor {

	/* VALID COMMANDS
	 * crate new (a new crate is started - ops only)
	 * crate add <Material> <int> (adds a prize if the amount is under 5 - ops only)
	 * crate join (Enter the giveaway)
	 * crate close (Begin the crate - ops only)
	 * crate delete (Deletes a crate before it gets started)
	 * crate active (Activate the current crate)
	 * */
	
	private Main main = Main.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(args[0].equals(null)){return false;}
		
		if(args[0].equalsIgnoreCase("new")) {
			if (sender.isOp()) {
				if (main.crateManager.isActive) {
					sender.sendMessage("Sorry, you cannot start a crate while one is active");
					sender.sendMessage("If you want to close the current one please use */crate delete*");
				} else {
					sender.sendMessage("A new crate has opened");
					sender.sendMessage("Please use the command /crate add <Material> <Amount>");
					sender.sendMessage("For help on the materials you can add, please visit the github wiki");
				} 
			}
		}else if(args[0].equalsIgnoreCase("add")) {
			if(sender.isOp() && sender instanceof Player) {
				if (main.crateManager.prizes.size() < 5) {
					Player player = (Player) sender;
					if (args[1] != null && args[2] != null) {
						main.crateManager.addPrize(args[1], args[2], player);
					}else if(args[1] != null && args[2] == null) {
						main.crateManager.addPrize(args[1], "1", player);
					}
				}else {
					sender.sendMessage("There are already 5 prizes");
					sender.sendMessage("Please use the command /crate close to start it");
				}
			}
		}else if(args[0].equalsIgnoreCase("close")) {
			if (main.crateManager.isActive) {
				if (sender.isOp()) {
					main.crateManager.closeEntry();
				} 
			}
		}else if(args[0].equalsIgnoreCase("delete")) {
			if (sender.isOp()) {
				if (main.crateManager.isActive == false) {
					sender.sendMessage("You can't end a crate that isnt active");
				} else {
					main.crateManager.refresh();
				} 
			}
		}else if(args[0].equalsIgnoreCase("join")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (main.crateManager.isActive) {
					if(main.crateManager.playersEntered.contains(player)) {
						player.sendMessage(ChatColor.RED + "You are already entered in the crate");
					}else {
						main.crateManager.addPlayer(player);
						player.sendMessage("You have been added to the prize pool");
					}
				}
			}
		}else if(args[0].equalsIgnoreCase("activate")) {
			if (sender instanceof Player && sender.isOp()) {
				Player player = (Player)sender;
				main.crateManager.attemptStartCrate(player);
			}
		}else {
			if (sender.isOp()) {
				sender.sendMessage("Please see the github wiki for valid Prizepool Commands ");
			}
		}
		
		return false;
	}

}
