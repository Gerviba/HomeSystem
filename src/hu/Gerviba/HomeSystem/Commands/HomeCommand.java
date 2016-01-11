package hu.Gerviba.HomeSystem.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import hu.Gerviba.HomeSystem.DataStores.PlayerInfo;
import hu.Gerviba.HomeSystem.Utils.Config;
import hu.Gerviba.HomeSystem.Utils.Util;

public class HomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("This is not a server command!");
			return false;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase(Config.SUB_GOTO)) {
				if (!sender.hasPermission("HomeSystem.goto")) {
					sender.sendMessage(Config.LANG_NO_PERMISSION);
					return false;
				}
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				Player p = Bukkit.getPlayerExact(sender.getName());
				if (pi.hasHome("DEFAULT_" + p.getWorld().getName())) {
					Location l;
					if ((l = pi.getHome("DEFAULT_" + p.getWorld().getName()).toLoaction()) != null) {
						Bukkit.getPlayerExact(sender.getName()).teleport(l);
						sender.sendMessage(Config.LANG_WELCOME);
					} else {
						sender.sendMessage(Config.LANG_FAIL_WORLD_NOT_LOADED);
					}
				} else {
					int found = 0;
					String last = "";
					for (String name : pi.getAllHomes()) {
						if (name.startsWith("DEFAULT_")) {
							++found;
							last = name;
						}
					}
					if (found != 1) {
						sender.sendMessage(Config.LANG_FAIL_NO_DEFAULT_HERE);
					} else {
						Location l;
						if ((l = pi.getHome(last).toLoaction()) != null) {
							Bukkit.getPlayerExact(sender.getName()).teleport(l);
							sender.sendMessage(Config.LANG_WELCOME);
						} else {
							sender.sendMessage(Config.LANG_FAIL_WORLD_NOT_LOADED);
						}
					}
				}
			} else if (args[0].equalsIgnoreCase(Config.SUB_CREATE)) {
				if (!sender.hasPermission("HomeSystem.set")) {
					sender.sendMessage(Config.LANG_NO_PERMISSION);
					return false;
				}
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				Player p = Bukkit.getPlayerExact(sender.getName());
				if (pi.hasHome("DEFAULT_" + p.getWorld().getName())) {
					sender.sendMessage(Config.LANG_FAIL_NAME_USED);
					return false;
				}
				if (!Util.isValid("DEFAULT_" + p.getWorld().getName())) {
					sender.sendMessage(Config.LANG_FAIL_INVALID_NAME);
					return false;
				}
				if (("DEFAULT_" + p.getWorld().getName()).length() > 255) {
					sender.sendMessage(Config.LANG_FAIL_LONG_NAME);
					return false;
				}
				if (pi.getHomeSum(p.getWorld().getName()) >= Config.HOME_PER_WORLD) {
					sender.sendMessage(Config.LANG_FAIL_TOO_MANY);
					return false;
				}
				pi.addHome(("DEFAULT_" + p.getWorld().getName()), p.getLocation());
				sender.sendMessage(Config.LANG_SUCCESS);
			} else if (args[0].equalsIgnoreCase(Config.SUB_REMOVE)) {
				if (!sender.hasPermission("HomeSystem.remove")) {
					sender.sendMessage(Config.LANG_NO_PERMISSION);
					return false;
				}
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				Player p = Bukkit.getPlayerExact(sender.getName());
				if (pi.hasHome("DEFAULT_" + p.getWorld().getName())) {
					pi.removeHome("DEFAULT_" + p.getWorld().getName());
					sender.sendMessage(Config.LANG_REMOVED);
				} else {
					int found = 0;
					String last = "";
					for (String name : pi.getAllHomes()) {
						if (name.startsWith("DEFAULT_")) {
							++found;
							last = name;
						}
					}
					if (found != 1) {
						sender.sendMessage(Config.LANG_FAIL_NO_DEFAULT_HERE);
					} else {
						pi.removeHome(last);
						sender.sendMessage(Config.LANG_REMOVED);
					}
				}
			} else if (args[0].equalsIgnoreCase(Config.SUB_LIST)) {
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				sender.sendMessage(Config.LANG_LIST + " " + Util.getAsString(pi.getAllHomes()));
			} else if (args[0].equalsIgnoreCase(Config.SUB_HELP)) {
				sender.sendMessage(Config.LANG_HEADER);
				sender.sendMessage(Config.LANG_COMMAND_GOTO);
				sender.sendMessage(Config.LANG_COMMAND_SET);
				sender.sendMessage(Config.LANG_COMMAND_REMOVE);
				sender.sendMessage(Config.LANG_COMMAND_LIST);
			} else {
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				if (pi.hasHome(args[0])) {
					Location l;
					if ((l = pi.getHome(args[0]).toLoaction()) != null) {
						Bukkit.getPlayerExact(sender.getName()).teleport(l);
						sender.sendMessage(Config.LANG_WELCOME);
					} else {
						sender.sendMessage(Config.LANG_FAIL_WORLD_NOT_LOADED);
					}
				} else {
					sender.sendMessage(Config.LANG_FAIL_NOT_FOUND);
				}
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase(Config.SUB_GOTO)) {
				if (!sender.hasPermission("HomeSystem.goto")) {
					sender.sendMessage(Config.LANG_NO_PERMISSION);
					return false;
				}
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				if (pi.hasHome(args[1])) {
					Location l;
					if ((l = pi.getHome(args[1]).toLoaction()) != null) {
						Bukkit.getPlayerExact(sender.getName()).teleport(l);
						sender.sendMessage(Config.LANG_WELCOME);
					} else {
						sender.sendMessage(Config.LANG_FAIL_WORLD_NOT_LOADED);
					}
				} else {
					sender.sendMessage(Config.LANG_FAIL_NOT_FOUND);
				}
			} else if (args[0].equalsIgnoreCase(Config.SUB_CREATE)) {
				if (!sender.hasPermission("HomeSystem.set")) {
					sender.sendMessage(Config.LANG_NO_PERMISSION);
					return false;
				}
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				if (pi.hasHome(args[1])) {
					sender.sendMessage(Config.LANG_FAIL_NAME_USED);
					return false;
				}
				if (!Util.isValid(args[1])) {
					sender.sendMessage(Config.LANG_FAIL_INVALID_NAME);
					return false;
				}
				if (args[1].length() > 255) {
					sender.sendMessage(Config.LANG_FAIL_LONG_NAME);
					return false;
				}
				Player p = Bukkit.getPlayerExact(pi.getName());
				if (pi.getHomeSum(p.getWorld().getName()) >= Config.HOME_PER_WORLD) {
					sender.sendMessage(Config.LANG_FAIL_TOO_MANY);
					return false;
				}
				pi.addHome(args[1], p.getLocation());
				sender.sendMessage(Config.LANG_SUCCESS);
			} else if (args[0].equalsIgnoreCase(Config.SUB_REMOVE)) {
				if (!sender.hasPermission("HomeSystem.remove")) {
					sender.sendMessage(Config.LANG_NO_PERMISSION);
					return false;
				}
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				if (pi.hasHome(args[1])) {
					pi.removeHome(args[1]);
					sender.sendMessage(Config.LANG_REMOVED);
				} else {
					sender.sendMessage(Config.LANG_FAIL_NOT_FOUND);
				}
			} else if (args[0].equalsIgnoreCase(Config.SUB_LIST)) {
				PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
				sender.sendMessage(Config.LANG_LIST + " " + Util.getAsString(pi.getAllHomes()));
			} else if (args[0].equalsIgnoreCase(Config.SUB_HELP)) {
				sender.sendMessage(Config.LANG_HEADER);
				sender.sendMessage(Config.LANG_COMMAND_GOTO);
				sender.sendMessage(Config.LANG_COMMAND_SET);
				sender.sendMessage(Config.LANG_COMMAND_REMOVE);
				sender.sendMessage(Config.LANG_COMMAND_LIST);
			} else {
				sender.sendMessage(Config.LANG_COMMAND_UNKNOWN);
			}
		} else {
			if (!sender.hasPermission("HomeSystem.goto")) {
				sender.sendMessage(Config.LANG_NO_PERMISSION);
				return false;
			}
			PlayerInfo pi = PlayerInfo.getPlayer(sender.getName());
			Player p = Bukkit.getPlayerExact(sender.getName());
			if (pi.hasHome("DEFAULT_" + p.getWorld().getName())) {
				Location l;
				if ((l = pi.getHome("DEFAULT_" + p.getWorld().getName()).toLoaction()) != null) {
					Bukkit.getPlayerExact(sender.getName()).teleport(l);
					sender.sendMessage(Config.LANG_WELCOME);
				} else {
					sender.sendMessage(Config.LANG_FAIL_WORLD_NOT_LOADED);
				}
			} else {
				int found = 0;
				String last = "";
				for (String name : pi.getAllHomes()) {
					if (name.startsWith("DEFAULT_")) {
						++found;
						last = name;
					}
				}
				if (found != 1) {
					sender.sendMessage(Config.LANG_FAIL_NO_DEFAULT_HERE);
				} else {
					Location l;
					if ((l = pi.getHome(last).toLoaction()) != null) {
						Bukkit.getPlayerExact(sender.getName()).teleport(l);
						sender.sendMessage(Config.LANG_WELCOME);
					} else {
						sender.sendMessage(Config.LANG_FAIL_WORLD_NOT_LOADED);
					}
				}
			}
		}
		return false;
	}

}
