package hu.Gerviba.HomeSystem.Utils;

import java.util.Collection;

public final class Util {

	public static void createTables() {
		MySQL.getInstance().query("CREATE TABLE IF NOT EXISTS `HOMES` ( "
				+ "`ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT , "
				+ "`OWNER` VARCHAR(16) NOT NULL , "
				+ "`NAME` VARCHAR(255) NOT NULL , "
				+ "`X` DOUBLE NOT NULL , "
				+ "`Y` DOUBLE NOT NULL , "
				+ "`Z` DOUBLE NOT NULL , "
				+ "`YAW` FLOAT NOT NULL , "
				+ "`PITCH` FLOAT NOT NULL , "
				+ "`WORLD` VARCHAR(128) NOT NULL , "
				+ "PRIMARY KEY (`ID`)) ENGINE = InnoDB;");
	}
	
	public static boolean isValid(String text) {
		return text.matches("^[0-9A-Za-z_]*$");
	}

	public static String getAsString(Collection<String> collection) {
		String output = "";
		for (String s : collection) {
			output += ", " + s.charAt(0) + (s.length() > 1 ? s.substring(1).toLowerCase() : "");
		}
		return output.length() == 0 ? Config.LANG_NO_RESULT : output.substring(2);
	}
	
}
