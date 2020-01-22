package pro.reactions.activators;

import pro.reactions.configuration.reload.ReloadType;
import pro.reactions.configuration.reload.Reloadable;
import pro.reactions.ReActionsCore;
import pro.reactions.actions.ActionsManager;
import pro.reactions.actions.StoredAction;
import pro.reactions.activators.storages.Changeables;
import pro.reactions.activators.storages.Context;
import pro.reactions.activators.storages.Storage;
import pro.reactions.configuration.ConfigurationManager;
import pro.reactions.configuration.RaConfigSection;
import pro.reactions.configuration.reload.ConfigurableObject;
import pro.reactions.flags.FlagsManager;
import pro.reactions.util.Parameters;
import pro.reactions.util.Util;
import pro.reactions.util.collections.InsensitiveStringMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Reloadable(ReloadType.ACTIVATORS)
public class ActivatorsManager extends ConfigurableObject {
	private final InsensitiveStringMap<ActivatorType> types;
	private final InsensitiveStringMap<Activator> activators;
	private final Map<Class<? extends ActivatorType>, Set<Activator>> activatorsByType;
	private final ConfigurationManager cfgManager;
	private final ActionsManager actManager;
	private final FlagsManager flgManager;

	public ActivatorsManager() {
		types = new InsensitiveStringMap<>(true);
		activators = new InsensitiveStringMap<>(false);
		activatorsByType = new HashMap<>();
		cfgManager = ReActionsCore.getConfiguration();
		actManager = ReActionsCore.getActions();
		flgManager = ReActionsCore.getFlags();
	}

	@Override
	public void reload(ReloadType cause, boolean firstTime) {
		activators.values().forEach(this::unmanage);
		activators.clear();
		activatorsByType.values().forEach(Set::clear);
		Map<String, RaConfigSection> files = cfgManager.getConfigsFromDir("activators");
		// Loop files | Configs
		for(String group : files.keySet()) {
			RaConfigSection cfg = files.get(group);
			// Loop first keys in file | Activator types
			for(String typeStr : cfg.getKeys()) {
				ActivatorType type = getType(typeStr);
				if(type == null) continue;
				RaConfigSection typeCfg = cfg.getSection(typeStr);
				// Loop second keys in file | Activator names
				for(String name : typeCfg.getKeys()) {
					try {
						loadActivator(type, name, group, typeCfg.getSection(name));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public boolean register(ActivatorType type) {
		if(type == null) return false;
		if(types.containsKey(type.getName())) return false;
		types.put(type.getName(), type);
		for(String alias : Util.getAliases(type))
			types.putIfAbsent(alias, type);
		activatorsByType.put(type.getClass(), new HashSet<>());
		return true;
	}

	public ActivatorType getType(String typeStr) {
		return types.get(typeStr);
	}

	public void loadActivator(ActivatorType type, String name, String group, RaConfigSection cfg) {
		ActivatorBase base = new ActivatorBase(name, group, cfg);
		Activator activator = type.load(base, cfg);
		if(activator == null || !activator.isValid()) return;
		addActivator(activator);
	}

	public CreationResult createActivator(String typeStr, String name, String group, Parameters params) {
		if(containsActivator(name)) return CreationResult.ALREADY_EXISTS;
		ActivatorType type = getType(typeStr);
		if(type == null) return CreationResult.UNKNOWN_TYPE;
		ActivatorBase base = new ActivatorBase(name, group);
		Activator activator = type.create(base, params);
		if(activator == null || !activator.isValid()) return CreationResult.FAILED;
		addActivator(activator);
		RaConfigSection cfg = cfgManager.getConfig(group + cfgManager.configSuffix());
		activator.saveActivator(cfg.getSection(type.getName() + "." + name));
		return CreationResult.SUCCESS;
	}

	public boolean removeActivator(String name) {
		Activator activator = activators.remove(name);
		if(activator == null) return false;
		activatorsByType.get(activator.getType()).remove(activator);
		unmanage(activator);
		return true;
	}

	private void manage(Activator activator) {
		if(activator instanceof Manageable) ((Manageable)activator).manage();
	}

	private void unmanage(Activator activator) {
		if(activator instanceof Manageable) ((Manageable)activator).unmanage();
	}

	public void addActivator(Activator activator) {
		manage(activator);
		activators.put(activator.getBase().getName(), activator);
		activatorsByType.get(activator.getType()).add(activator);
	}

	public boolean containsActivator(String name) {
		return activators.containsKey(name);
	}

	public Changeables executeActivators(Storage storage) {
		Set<Activator> activators = activatorsByType.get(storage.getType());
		if(activators == null) return Changeables.EMPTY;
		storage.init();
		activators.forEach(activator -> executeActivator(activator, storage));
		return storage.getChangeables();
	}

	public Changeables executeActivator(Storage storage, String name) {
		Activator activator = activators.get(name);
		if(activator == null || storage.getType() != activator.getType()) return Changeables.EMPTY;
		storage.init();
		executeActivator(activator, storage);
		return storage.getChangeables();
	}

	private void executeActivator(Activator activator, Storage storage) {
		if(activator.checkActivator(storage)) {
			Context context = storage.generateContext();
			ActivatorBase base = activator.getBase();
			List<StoredAction> actions;
			if(flgManager.checkFlags(base.getFlags(), context))
				actions = base.getActions();
			else
				actions = base.getReactions();
			actManager.executeActions(actions, context);
		}
	}

	public enum CreationResult {
		SUCCESS, FAILED, UNKNOWN_TYPE, ALREADY_EXISTS
	}
}
