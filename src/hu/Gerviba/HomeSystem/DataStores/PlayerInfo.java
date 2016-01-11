package hu.Gerviba.HomeSystem.DataStores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Location;

import com.mysql.jdbc.PreparedStatement;

import hu.Gerviba.HomeSystem.Utils.Config;
import hu.Gerviba.HomeSystem.Utils.MySQL;

public final class PlayerInfo {

	private static final HashMap<String, PlayerInfo> ALL_PLAYERS = new HashMap<String, PlayerInfo>();

	public static PlayerInfo getPlayer(String name) {
		return ALL_PLAYERS.containsKey(name) ? ALL_PLAYERS.get(name) : registerPlayer(new PlayerInfo(name));
	}

	public static PlayerInfo registerPlayer(PlayerInfo pi) {
		ALL_PLAYERS.put(pi.getName(), pi);
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			st = (PreparedStatement) MySQL.getInstance().getConnection()
					.prepareStatement("SELECT * FROM `HOMES` WHERE `OWNER`='" + pi.getName() + "' LIMIT 1;");
			rs = st.executeQuery();
			rs.first();
			if (rs.getRow() != 0) {
				do {
					pi.homes.put(rs.getString("NAME"), new HomeLocation(rs.getString("WORLD"), rs.getDouble("X"),
							rs.getDouble("Y"), rs.getDouble("Z"), rs.getFloat("YAW"), rs.getFloat("PITCH")));
				} while (rs.next());
			}
		} catch (Exception e) {
			System.out.println("[HomeSystem] Failed to load homes in player '" + pi.name + "'!");
			if (rs != null)
				try {
					System.out.println("[HomeSystem] Last home id: " + rs.getLong("ID"));
				} catch (SQLException se) {
					se.printStackTrace();
				}
			e.printStackTrace();
		} finally {
			MySQL.getInstance().closeResources(rs, st);
		}
		if (Config.DEBUG)
			System.out.println("[HomeSystem] [DEBUG] Player loaded! name:" + pi.name + " homes:" + pi.homes.size());
		return pi;
	}

	public static void unregisterPlayer(PlayerInfo pi) {
		ALL_PLAYERS.remove(pi.getName());
		if (Config.DEBUG)
			System.out.println("[HomeSystem] [DEBUG] Player loaded! name:" + pi.name + " homes:" + pi.homes.size());
	}

	public static boolean isExists(String name) {
		return ALL_PLAYERS.containsKey(name);
	}

	private final String name;
	private final HashMap<String, HomeLocation> homes = new HashMap<>();

	private PlayerInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public HomeLocation getHome(String name) {
		return homes.get(name.toUpperCase());
	}

	public boolean hasHome(String name) {
		return homes.containsKey(name.toUpperCase());
	}

	public Collection<String> getAllHomes() {
		return homes.keySet();
	}

	public void addHome(String name, Location location) {
		name = name.toUpperCase();
		homes.put(name, new HomeLocation(location));
		MySQL.getInstance()
				.query("INSERT INTO `HOMES` (`ID`, `OWNER`, `NAME`, `X`, `Y`, `Z`, `YAW`, `PITCH`, `WORLD`) VALUES (NULL, '"
						+ this.name + "', '" + name + "', '" + location.getX() + "', '" + location.getY() + "', '"
						+ location.getZ() + "', '" + location.getYaw() + "', '" + location.getPitch() + "', '"
						+ location.getWorld().getName() + "');");
		if (Config.DEBUG)
			System.out.println("[HomeSystem] [DEBUG] Home added! name:" + name + " location:" + location.toString());
	}

	public void removeHome(String name) {
		name = name.toUpperCase();
		homes.remove(name);
		MySQL.getInstance().query("DELETE FROM `HOMES` WHERE `OWNER`='" + this.name + "' AND `NAME`='" + name + "';");
		if (Config.DEBUG)
			System.out.println("[HomeSystem] [DEBUG] Home removed! name:" + name);
	}

	public int getHomeSum(String world) {
		int sum = 0;
		for (HomeLocation hl : homes.values()) {
			if (hl.getWorldName().equalsIgnoreCase(world))
				++sum;
		}
		return sum;
	}

}
