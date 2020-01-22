package pro.reactions.platform;

import pro.reactions.configuration.ConfigurationManager;
import pro.reactions.modules.Module;

import java.util.Collection;

public interface RaPlatform {

	ConfigurationManager getConfiguration();

	ThreadHelper getThreader();

	Collection<String> getPlayersNames();

	Collection<RaPlayer> getPlayers();

	RaPlayer getPlayer(String name);

	boolean isOnline(String name);

	RaUser getConsoleUser();

	Module getPlatformModule();

	/**
	 * Time of one game time unit
	 * @return Time in milliseconds
	 */
	default long timeUnit() {
		return 50;
	}
}
