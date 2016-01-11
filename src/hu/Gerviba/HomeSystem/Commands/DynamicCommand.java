package hu.Gerviba.HomeSystem.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DynamicCommand extends Command {

	private CommandExecutor ce = null;

	public DynamicCommand(String name) {
		super(name);
	}

	public boolean execute(CommandSender sender, String label, String[] args) {
		if (ce != null) {
			ce.onCommand(sender, this, label, args);
		}
		return false;
	}

	public void setExecutor(CommandExecutor exe) {
		this.ce = exe;
	}

}
