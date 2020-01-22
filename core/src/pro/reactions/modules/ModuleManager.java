package pro.reactions.modules;

import pro.reactions.configuration.reload.ReloadType;
import pro.reactions.configuration.reload.Reloadable;

import java.util.Map;

@Reloadable(ReloadType.MODULES)
public class ModuleManager {
	private Map<String, ModuleDescription> descriptions;
	// TODO
	public boolean register(Module module) {
		return true;
	}
}
