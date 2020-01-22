package pro.reactions.configuration.language;

import pro.reactions.ReActionsCore;
import pro.reactions.configuration.ConfigurationManager;
import pro.reactions.configuration.RaConfigSection;
import pro.reactions.configuration.reload.ReloadType;
import pro.reactions.configuration.reload.Reloadable;
import pro.reactions.configuration.reload.ConfigurableObject;

import java.util.HashMap;
import java.util.Map;

@Reloadable(ReloadType.CONFIG)
public class LanguageManager extends ConfigurableObject {
	private final Map<String, Message> messages;
	private final ConfigurationManager cfgManager;
	private RaConfigSection langFile;

	public LanguageManager() {
		messages = new HashMap<>();
		this.cfgManager = ReActionsCore.INSTANCE.getConfiguration();
	}

	@Override
	public void reload(ReloadType type, boolean firstTime) {
		langFile = cfgManager.getConfig(cfgManager.LANGDIR_PATH +
					cfgManager.getData("lang").asString() +
					cfgManager.configSuffix());
	}

	public void register(Message message) {
		messages.put(message.getPath(), message);
	}

	public String getMessage(String key, String... phs) {
		Message message = messages.get(key);
		if(message == null) return "UNKNOWN MESSAGE";
		return message.getMsg(phs);
	}
}
