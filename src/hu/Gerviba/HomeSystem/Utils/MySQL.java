package hu.Gerviba.HomeSystem.Utils;

import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * MySQL database manager/util
 * 
 * @author Gerviba
 * @see MySQL#init()
 * @version 1.3.EN
 */
public class MySQL {

	public String host;
	public String port;
	public String database;
	public String username;
	private String password;
	public int timeout;

	private Connection conn;

	private static MySQL instance = null;

	/**
	 * Initialization
	 * 
	 * @throws Exception
	 */
	public final void init(String dir) throws Exception {
		if (instance != null) {
			throw new Exception("MySQL has already initialized!");
		}
		final File MySQL_FILE = new File(dir, "MySQL.yml");
		final FileConfiguration CFG_SQL = YamlConfiguration.loadConfiguration(MySQL_FILE);

		CFG_SQL.addDefault("MySQL.host", "127.0.0.1");
		CFG_SQL.addDefault("MySQL.port", "3306");
		CFG_SQL.addDefault("MySQL.username", "user");
		CFG_SQL.addDefault("MySQL.password", "password");
		CFG_SQL.addDefault("MySQL.database", "database");
		CFG_SQL.addDefault("MySQL.timeout", 10000);

		CFG_SQL.options().copyDefaults(true);
		CFG_SQL.save(MySQL_FILE);

		this.host = CFG_SQL.getString("MySQL.host");
		this.port = CFG_SQL.getString("MySQL.port");
		this.username = CFG_SQL.getString("MySQL.username");
		this.password = CFG_SQL.getString("MySQL.password");
		this.database = CFG_SQL.getString("MySQL.database");
		this.timeout = CFG_SQL.getInt("MySQL.password");

		this.openConnection();
		instance = this;
		System.out.println("[MySQL] This utility is created by Gerviba!");
	}

	/**
	 * Connect to the server
	 * 
	 * @return conn, if successful
	 * @throws Exception
	 */
	public Connection openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = (Connection) DriverManager.getConnection(
				"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true",
				this.username, this.password);
		this.conn = conn;
		this.conn.setAutoReconnect(true);
		this.conn.setConnectTimeout(timeout);
		return this.conn;
	}

	/**
	 * Connection getter
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			if (this.conn == null || !this.conn.isValid(timeout)) {
				System.out.println("[MySQL] Reconnecting!");
				this.openConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.conn;
	}

	/**
	 * Connection checker
	 * 
	 * @return true if it has
	 */
	public boolean hasConnection() {
		try {
			return this.conn != null || this.conn.isValid(1);
		} catch (SQLException e) {
			return false;
		}

	}

	/**
	 * Query
	 * 
	 * @param query
	 *            INSERT, DELETE, UPDATE, ...
	 */
	public void query(String query) {
		PreparedStatement st = null;
		try {
			st = (PreparedStatement) this.getConnection().prepareStatement(query);
			st.executeUpdate();
		} catch (SQLException e) {
			System.err.println("MySQL Query error! {" + query + "}");
			e.printStackTrace();
		} finally {
			this.closeResources(null, st);
		}
	}

	/**
	 * Close Resources
	 * 
	 * @param rs
	 *            ResultSet
	 * @param st
	 *            PreparedStatement
	 */
	public void closeResources(ResultSet rs, PreparedStatement st) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Close Connection
	 */
	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		this.conn = null;
	}

	/**
	 * Instance getter
	 * 
	 * @return MySQL
	 */
	public static MySQL getInstance() {
		return instance;
	}

}
