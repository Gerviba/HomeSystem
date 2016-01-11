package hu.Gerviba.HomeSystem;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import hu.Gerviba.HomeSystem.Commands.DynamicCommand;
import hu.Gerviba.HomeSystem.Commands.HomeCommand;
import hu.Gerviba.HomeSystem.Utils.Config;
import hu.Gerviba.HomeSystem.Utils.MySQL;
import hu.Gerviba.HomeSystem.Utils.Util;

public class Core extends JavaPlugin {

	public void onEnable() {
		CommandMap cm;
		
		try {
			Config.init();
		} catch (Exception e) {
			System.out.println("[HomeSystem] Failed to load the configuration!");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		try {
			new MySQL().init("./plugins/HomeSystem");
		} catch (Exception e) {
			System.out.println("[HomeSystem] Failed to connect to the MySQL database!");
			System.out.println("[HomeSystem] Please check the configuration file! (MySQL.yml)");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		Util.createTables();
		
		try {
			final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			f.setAccessible(true);
			cm = (CommandMap) f.get(Bukkit.getServer());
		} catch (Exception e) {
			System.out.println("[HomeSystem] Failed to register the command!");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		DynamicCommand dc = new DynamicCommand(Config.COMMAND_NAME);
		dc.setDescription(Config.DESCRIPTION);
		dc.setAliases(Config.ALIASES);
		cm.register("", dc);
		dc.setExecutor(new HomeCommand());

		Bukkit.getPluginManager().registerEvents(new EventListener(), this);

		System.out.println("[HomeSystem] HomeSystem is ready!");
	}

	@Override
	public void onDisable() {
		if(MySQL.getInstance().hasConnection())
			MySQL.getInstance().closeConnection();
	}
	
}
