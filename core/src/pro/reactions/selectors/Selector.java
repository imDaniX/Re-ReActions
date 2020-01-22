package pro.reactions.selectors;

import pro.reactions.platform.RaPlayer;

import java.util.Collection;

public interface Selector {
	Collection<RaPlayer> getPlayers(String str);

	String getName();

	String getAlias();
}
