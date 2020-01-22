package pro.reactions.configuration;

import pro.reactions.configuration.reload.ReloadType;
import pro.reactions.configuration.language.LanguageManager;
import pro.reactions.configuration.reload.ConfigurableObject;
import pro.reactions.util.Util;
import pro.reactions.util.data.DataValue;
import pro.reactions.util.data.special.VoidValue;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// TODO: Redo from scratch
public abstract class ConfigurationManager {
	// Paths
	public final String WORKSPACE_PATH;
	public final String CONFIG_PATH = "config";
	public final String DELAYS_PATH = "delays";
	public final String DELAYED_ACTIONS_PATH = "delayed-actions";
	public final String TIMERS_PATH = "timers";
	public final String LANGDIR_PATH = "lang" + File.separator;
	public final String COMMANDS_PATH = "commands";
	public final String MENUS_PATH = "menu";
	public final String LOCATIONS_PATH = "locations";
	public final String VARDIR_PATH = "variables" + File.separator;
	public final String VAR_PATH = "variables";
	public final String VARGENERAL_PATH = "variables" + File.separator + "general";
	public final String ACTIVATORS_PATH = "activators" + File.separator + "activators";

	private final Map<String, CfgData> cfgVariables;
	private final LanguageManager language;
	private static final Map<ReloadType, Set<ConfigurableObject>> toReload = new EnumMap<>(ReloadType.class);

	private final RaConfigSection defaultConfig;
	
	public ConfigurationManager(String workspace) {
		WORKSPACE_PATH = workspace.endsWith(File.separator) ? workspace : workspace + File.separator;
		cfgVariables = new HashMap<>();
		for(ReloadType type : ReloadType.values())
			toReload.put(type, new HashSet<>());
		CoreCfg.registerAll(this);
		createDefaultConfigs();
		defaultConfig = getConfig(CONFIG_PATH);
		reloadAll();
		language = new LanguageManager();
	}

	public void reloadAll() {
		reloadCfg();
		Set<ConfigurableObject> reloaded = new HashSet<>();
		for(ReloadType type : ReloadType.values()) {
			toReload.get(type).forEach(c -> {
				c.reload(type, reloaded.contains(c));
				reloaded.add(c);
			});
		}
	}

	private void reloadCfg() {
		for(CfgData data : cfgVariables.values())
			if(!defaultConfig.containsKey(data.getPath())) defaultConfig.set(data.getPath(), data.getDefault());
	}

	public final void reload(ReloadType... types) {
		if(types.length == 0) {
			reloadAll();
			return;
		}
		if(Util.arrayContains(types, ReloadType.COMMANDS)) reloadCfg();
		Set<ConfigurableObject> reloaded = new HashSet<>();
		for(ReloadType type : types) {
			toReload.get(type).forEach(c -> {
				c.reload(type, !reloaded.contains(c));
				reloaded.add(c);
			});
		}
	}

	private void createDefaultConfigs() {
		createConfig(CONFIG_PATH);
		createConfig(DELAYS_PATH);
		createConfig(DELAYED_ACTIONS_PATH);
		createConfig(TIMERS_PATH);
		createConfig(COMMANDS_PATH);
		createConfig(MENUS_PATH);
		createConfig(LOCATIONS_PATH);
		createConfig(VAR_PATH);
		createConfig(ACTIVATORS_PATH);
	}

	public final void createConfig(String fileStr) {
		File file = new File(WORKSPACE_PATH + fileStr + configSuffix());
		File dir = new File(file.getPath());
		dir.mkdirs();
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public RaConfigSection getConfig(String file) {
		return getConfig(new File(WORKSPACE_PATH + file));
	}

	public abstract RaConfigSection getConfig(File file);

	public Map<String, RaConfigSection> getConfigsFromDir(String dir) {
		return getConfigsFromDir(null, new File(WORKSPACE_PATH + dir));
	}

	public Map<String, RaConfigSection> getConfigsFromDir(String start, File dir) {
		if(!dir.exists() || !dir.isDirectory()) return Collections.emptyMap();
		File[] files = dir.listFiles();
		if(files == null) return Collections.emptyMap();
		Map<String, RaConfigSection> configs = new HashMap<>();
		start = start == null ? "" : start + File.separator;
		for(File file : files) {
			if(file.isDirectory()) {
				configs.putAll(getConfigsFromDir(start + file.getName(), file));
			} else {
				String name = file.getName();
				if(name.endsWith(configSuffix()))
					configs.put(start + name.substring(0, name.length() - configSuffix().length()), getConfig(file));
			}
		}
		return configs;
	}

	public DataValue getData(String key) {
		CfgData data = cfgVariables.get(key);
		return data != null ? data.getDefault() : VoidValue.INSTANCE;
	}

	public boolean registerData(String key, CfgData data) {
		if(cfgVariables.containsKey(key)) return false;
		cfgVariables.put(key, data);
		defaultConfig.set(data.getPath(), data.getDefault());
		return true;
	}

	public final void unregisterReloadable(ReloadType type, ConfigurableObject obj) {
		toReload.get(type).remove(obj);
	}

	public static void registerReloadable(ReloadType type, ConfigurableObject obj) {
		toReload.get(type).add(obj);
	}

	public abstract String configSuffix();
}
