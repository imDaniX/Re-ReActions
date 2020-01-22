package pro.reactions.selectors;

import pro.reactions.ReActionsCore;
import pro.reactions.platform.RaPlatform;
import pro.reactions.platform.RaPlayer;
import pro.reactions.util.Parameters;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SelectorsManager {
	private static final Set<RaPlayer> NULL_PLAYER = Collections.singleton(null);
	private final Map<String, Selector> selectors;
	private final RaPlatform platform;

	public SelectorsManager() {
		this.selectors = new HashMap<>();
		this.platform = ReActionsCore.getPlatform();
	}

	public boolean register(Selector selector) {
		if(selectors.containsKey(selector.getName().toLowerCase())||
			selectors.containsKey(selector.getAlias().toLowerCase())) return false;
		selectors.put(selector.getName().toLowerCase(), selector);
		selectors.put(selector.getAlias().toLowerCase(), selector);
		return true;
	}

	public Collection<RaPlayer> parseSelectors(Parameters params) {
		String paramLine = params.getString("param-line");
		if(paramLine.equalsIgnoreCase("~all")) return platform.getPlayers();
		if(paramLine.equalsIgnoreCase("~null")) return NULL_PLAYER;
		if(isNickname(paramLine)) return Collections.singleton(platform.getPlayer(paramLine));
		Set<RaPlayer> players = new HashSet<>();
		params.forEach((key, value) -> {
			Selector selector = this.selectors.get(key);
			if(selector != null) players.addAll(selector.getPlayers(value));
		});
		return players;
	}

	// Pretty bad, but don't know how to improve
	public String replaceSelectors(String text, Collection<RaPlayer> players) {
		Parameters params = Parameters.fromString(text);
		LambdaString origin = new LambdaString(params.getOrigin());
		params.forEach((key, value) -> {
			Selector selector = this.selectors.get(key);
			if(selector != null) {
				origin.replace("\u00A7!" + value.hashCode(), "").replace("\u00A7?" + value.hashCode(), "");
				players.addAll(selector.getPlayers(value));
			} else {
				origin.replace("\u00A7!" + value.hashCode(), value).replace("\u00A7?" + value.hashCode(), "{"+value+"}");
			}
		});
		return origin.text;
	}

	private static final class LambdaString {
		String text;
		LambdaString(String text) {
			this.text = text;
		}
		LambdaString replace(String str, String replace) {
			text = text.replace(str, replace);
			return this;
		}
	}

	private static boolean isNickname(String str) {
		for(char c : str.toCharArray()) {
			if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || (c == '_')) continue;
			return false;
		}
		return true;
	}
}
