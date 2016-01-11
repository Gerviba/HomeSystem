package hu.Gerviba.HomeSystem.DataStores;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class HomeLocation {

	private final String world;
	private final double x, y, z;
	private final float yaw, pitch;

	public HomeLocation(Location l) {
		this(l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
	}

	public HomeLocation(String world, double x, double y, double z, float yaw, float pitch) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public String getWorldName() {
		return world;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public Location toLoaction() {
		return Bukkit.getWorld(world) == null ? null : new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	@Override
	public String toString() {
		return "HomeLocation [world=" + world + ", x=" + x + ", y=" + y + ", z=" + z + ", yaw=" + yaw + ", pitch="
				+ pitch + "]";
	}
	
}
