package pro.reactions.util.location;

import pro.reactions.platform.RaLocation;

public class StraightLocation implements RaLocation {
	// TODO
	@Override
	public double getBlockX() {
		return getX();
	}

	@Override
	public double getBlockY() {
		return getY();
	}

	@Override
	public double getBlockZ() {
		return getZ();
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getZ() {
		return 0;
	}

	@Override
	public String getWorld() {
		return null;
	}
}
