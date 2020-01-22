package pro.reactions;

import pro.reactions.configuration.ConfigurationManager;
import pro.reactions.configuration.RaConfigSection;
import pro.reactions.configuration.reload.ReloadType;
import pro.reactions.configuration.reload.Reloadable;
import pro.reactions.configuration.reload.ConfigurableObject;

import java.util.Map;
import java.util.UUID;

@Reloadable({ReloadType.CONFIG, ReloadType.VARIABLES})
public class VariablesManager extends ConfigurableObject {
	private final ConfigurationManager cfgManager;

	public VariablesManager() {
		cfgManager = ReActionsCore.INSTANCE.getConfiguration();
	}

	public void saveGeneralVariables(Map<String, String> variables) {
		if(cfgManager.getData("vars-mode").asBoolean()) {
			RaConfigSection cfg = cfgManager.getConfig(cfgManager.VARGENERAL_PATH);
			for(String key : variables.keySet())
				cfg.set(key, variables.get(key));
		} else {
			RaConfigSection cfg = cfgManager.getConfig(cfgManager.VAR_PATH);
			for(String key : variables.keySet())
				cfg.set(key, "general." + variables.get(key));
		}
	}

	public void savePersonalVariables(UUID id, Map<String, String> variables) {
		if(cfgManager.getData("vars-mode").asBoolean()) {
			RaConfigSection cfg = cfgManager.getConfig(cfgManager.VARDIR_PATH + id);
			for(String key : variables.keySet())
				cfg.set(key, variables.get(key));
		} else {
			RaConfigSection cfg = cfgManager.getConfig(cfgManager.VAR_PATH);
			for(String key : variables.keySet())
				cfg.set(key, id + "." + variables.get(key));
		}
	}

	@Override
	public void reload(ReloadType type, boolean firstTime) {
		// TODO
	}
}
