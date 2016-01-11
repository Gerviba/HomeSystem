package hu.Gerviba.HomeSystem.Utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Config {

	public static String COMMAND_NAME = "homesys";
	public static List<String> ALIASES = Arrays.asList("homes", "homesystem");
	public static String DESCRIPTION = "Use the HomeSystem command";
	public static int HOME_PER_WORLD = 1;
	public static boolean DEBUG = false;
	public static String SUB_GOTO = "goto";
	public static String SUB_CREATE = "set";
	public static String SUB_REMOVE = "remove";
	public static String SUB_HELP = "help";
	public static String SUB_LIST = "list";
	
	public static String LANG_SUCCESS = "§7| §aHome set.";
	public static String LANG_LIST = "§7| §eList of your home(s):§7";
	public static String LANG_WELCOME= "§7| §aWelcome home! :)";
	public static String LANG_REMOVED= "§7| §aHome removed.";
	public static String LANG_FAIL_LONG_NAME = "§7| §cThe name is too long.";
	public static String LANG_FAIL_MYSQL = "§7| §cCannot connect to the database.";
	public static String LANG_FAIL_TOO_MANY = "§7| §cYou can't set more homes in this world.";
	public static String LANG_FAIL_NAME_USED = "§7| §cThis name is already in use.";
	public static String LANG_FAIL_WORLD_NOT_LOADED = "§7| §cWorld not loaded.";
	public static String LANG_FAIL_NOT_FOUND = "§7| §cHome not found.";
	public static String LANG_FAIL_NO_DEFAULT_HERE = "§7| §cThere is no default home in this world.";
	public static String LANG_FAIL_INVALID_NAME = "§7| §cInvalid name! You can only use alphanumeric characters.";
	public static String LANG_HEADER = "§7| §eHomeSystem §7Help:";
	public static String LANG_COMMAND_SET = "§7| Set:§e /%s %s [home]";
	public static String LANG_COMMAND_GOTO = "§7| Goto:§e /%s [%s] [home]";
	public static String LANG_COMMAND_REMOVE = "§7| Remove:§e /%s %s [home]";
	public static String LANG_COMMAND_LIST = "§7| List:§e /%s %s";
	public static String LANG_COMMAND_UNKNOWN = "§7| §cUnknown argument(s)! Use: /%s <%s|%s|%s|%s|%s> [home]";
	public static String LANG_NO_RESULT = "§o(You have no homes)";
	public static String LANG_NO_PERMISSION = "§7| §cYou have no permission to do this here.";
	
	public static void init() throws Exception {
		final File FILE = new File("plugins/HomeSystem", "Options.yml");
		final FileConfiguration cfg = YamlConfiguration.loadConfiguration(FILE);
		
		cfg.addDefault("HomeSystem.Command.COMMAND_NAME", COMMAND_NAME);
		cfg.addDefault("HomeSystem.Command.ALIASES", ALIASES);
		cfg.addDefault("HomeSystem.Command.DESCRIPTION", DESCRIPTION);
		cfg.addDefault("HomeSystem.SubCommand.GOTO", SUB_GOTO);
		cfg.addDefault("HomeSystem.SubCommand.CREATE", SUB_CREATE);
		cfg.addDefault("HomeSystem.SubCommand.REMOVE", SUB_REMOVE);
		cfg.addDefault("HomeSystem.SubCommand.LIST", SUB_LIST);
		cfg.addDefault("HomeSystem.SubCommand.HELP", SUB_HELP);
		cfg.addDefault("HomeSystem.Basics.HOME_PER_WORLD", HOME_PER_WORLD);
		cfg.addDefault("HomeSystem.Basics.DEBUG", DEBUG);
		
		cfg.addDefault("HomeSystem.Lang.SUCCESS", LANG_SUCCESS);
		cfg.addDefault("HomeSystem.Lang.LIST", LANG_LIST);
		cfg.addDefault("HomeSystem.Lang.WELCOME", LANG_WELCOME);
		cfg.addDefault("HomeSystem.Lang.REMOVED", LANG_REMOVED);
		cfg.addDefault("HomeSystem.Lang.FAIL_LONG_NAME", LANG_FAIL_LONG_NAME);
		cfg.addDefault("HomeSystem.Lang.FAIL_MYSQL", LANG_FAIL_MYSQL);
		cfg.addDefault("HomeSystem.Lang.FAIL_TOO_MANY", LANG_FAIL_TOO_MANY);
		cfg.addDefault("HomeSystem.Lang.FAIL_NAME_USED", LANG_FAIL_NAME_USED);
		cfg.addDefault("HomeSystem.Lang.FAIL_WORLD_NOT_LOADED", LANG_FAIL_WORLD_NOT_LOADED);
		cfg.addDefault("HomeSystem.Lang.FAIL_NOT_FOUND", LANG_FAIL_NOT_FOUND);
		cfg.addDefault("HomeSystem.Lang.FAIL_NO_DEFAULT_HERE", LANG_FAIL_NO_DEFAULT_HERE);
		cfg.addDefault("HomeSystem.Lang.FAIL_INVALID_NAME", LANG_FAIL_INVALID_NAME);
		cfg.addDefault("HomeSystem.Lang.HEADER", LANG_HEADER);
		cfg.addDefault("HomeSystem.Lang.COMMAND_SET", LANG_COMMAND_SET);
		cfg.addDefault("HomeSystem.Lang.COMMAND_GOTO", LANG_COMMAND_GOTO);
		cfg.addDefault("HomeSystem.Lang.COMMAND_REMOVE", LANG_COMMAND_REMOVE);
		cfg.addDefault("HomeSystem.Lang.COMMAND_LIST", LANG_COMMAND_LIST);
		cfg.addDefault("HomeSystem.Lang.COMMAND_UNKNOWN", LANG_COMMAND_UNKNOWN);
		cfg.addDefault("HomeSystem.Lang.NO_RESULT", LANG_NO_RESULT);
		cfg.addDefault("HomeSystem.Lang.NO_PERMISSION", LANG_NO_PERMISSION);
		
		cfg.options().copyDefaults(true);
		cfg.save(FILE);
			
		COMMAND_NAME = cfg.getString("HomeSystem.Command.COMMAND_NAME");
		ALIASES = cfg.getStringList("HomeSystem.Command.ALIASES");
		DESCRIPTION = cfg.getString("HomeSystem.Command.DESCRIPTION");
		SUB_GOTO = cfg.getString("HomeSystem.SubCommand.GOTO");
		SUB_CREATE = cfg.getString("HomeSystem.SubCommand.CREATE");
		SUB_REMOVE = cfg.getString("HomeSystem.SubCommand.REMOVE");
		SUB_LIST = cfg.getString("HomeSystem.SubCommand.LIST");
		SUB_HELP = cfg.getString("HomeSystem.SubCommand.HELP");
		HOME_PER_WORLD = cfg.getInt("HomeSystem.Basics.HOME_PER_WORLD");
		DEBUG = cfg.getBoolean("HomeSystem.Basics.DEBUG");
		
		LANG_SUCCESS = cfg.getString("HomeSystem.Lang.SUCCESS");
		LANG_LIST = cfg.getString("HomeSystem.Lang.LIST");
		LANG_WELCOME = cfg.getString("HomeSystem.Lang.WELCOME");
		LANG_REMOVED = cfg.getString("HomeSystem.Lang.REMOVED");
		LANG_FAIL_LONG_NAME = cfg.getString("HomeSystem.Lang.FAIL_LONG_NAME");
		LANG_FAIL_MYSQL = cfg.getString("HomeSystem.Lang.FAIL_MYSQL");
		LANG_FAIL_TOO_MANY = cfg.getString("HomeSystem.Lang.FAIL_TOO_MANY");
		LANG_FAIL_NAME_USED = cfg.getString("HomeSystem.Lang.FAIL_NAME_USED");
		LANG_FAIL_WORLD_NOT_LOADED = cfg.getString("HomeSystem.Lang.FAIL_WORLD_NOT_LOADED");
		LANG_FAIL_NOT_FOUND = cfg.getString("HomeSystem.Lang.FAIL_NOT_FOUND");
		LANG_FAIL_NO_DEFAULT_HERE = cfg.getString("HomeSystem.Lang.FAIL_NO_DEFAULT_HERE");
		LANG_FAIL_INVALID_NAME = cfg.getString("HomeSystem.Lang.FAIL_INVALID_NAME");
		LANG_HEADER = cfg.getString("HomeSystem.Lang.HEADER");
		LANG_COMMAND_SET = String.format(cfg.getString("HomeSystem.Lang.COMMAND_SET"), COMMAND_NAME, SUB_CREATE);
		LANG_COMMAND_GOTO = String.format(cfg.getString("HomeSystem.Lang.COMMAND_GOTO"), COMMAND_NAME, SUB_GOTO);
		LANG_COMMAND_REMOVE = String.format(cfg.getString("HomeSystem.Lang.COMMAND_REMOVE"), COMMAND_NAME, SUB_REMOVE);
		LANG_COMMAND_LIST = String.format(cfg.getString("HomeSystem.Lang.COMMAND_LIST"), COMMAND_NAME, SUB_LIST);
		LANG_COMMAND_UNKNOWN = String.format(cfg.getString("HomeSystem.Lang.COMMAND_UNKNOWN"), COMMAND_NAME, SUB_CREATE, SUB_GOTO, SUB_REMOVE, SUB_LIST, SUB_HELP);
		LANG_NO_RESULT = cfg.getString("HomeSystem.Lang.NO_RESULT");
		LANG_NO_PERMISSION = cfg.getString("HomeSystem.Lang.NO_PERMISSION");

		System.out.println("[HomeSystem] Configuration loaded!");
	}

}
