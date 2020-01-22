package pro.reactions;

import pro.reactions.activators.ActivatorsManager;
import pro.reactions.actions.ActionsManager;
import pro.reactions.configuration.ConfigurationManager;
import pro.reactions.flags.FlagsManager;
import pro.reactions.modules.ModuleManager;
import pro.reactions.placeholders.PlaceholdersManager;
import pro.reactions.platform.RaPlatform;
import pro.reactions.platform.ThreadHelper;
import pro.reactions.selectors.SelectorsManager;

public class ReActionsCore {

	private static boolean init = false;
	private static RaPlatform platform;
	private static ConfigurationManager cfgManager;
	private static ActionsManager actManager;
	private static FlagsManager flgManager;
	private static ActivatorsManager activManager;
	private static PlaceholdersManager phManager;
	private static SelectorsManager slManager;
	private static ThreadHelper threader;
	private static ModuleManager mdManager;

	public static void initialize(RaPlatform platform) {
		if(init) throw new IllegalStateException("ReActionsCore is already initialized");
		if(platform == null) throw new IllegalArgumentException("RaPlatform cannot be null");
		ReActionsCore.platform = platform;
		ConfigurationManager cfgManager = platform.getConfiguration();
		if(cfgManager == null) throw new IllegalArgumentException("ConfigurationManager cannot be null");
		ReActionsCore.cfgManager = cfgManager;
		ThreadHelper threader = platform.getThreader();
		if(threader == null) throw new IllegalArgumentException("ThreadHelper cannot be null");
		ReActionsCore.threader = threader;
		actManager = new ActionsManager();
		flgManager = new FlagsManager();
		activManager = new ActivatorsManager();
		phManager = new PlaceholdersManager();
		slManager = new SelectorsManager();
		mdManager = new ModuleManager();
		mdManager.register(platform.getPlatformModule());
		cfgManager.reloadAll();
		init = true;
	}

	public static boolean isInitialized() {
		return init;
	}

	public static ThreadHelper getThreader() {
		checkInit();
		return threader;
	}

	public static ConfigurationManager getConfiguration() {
		checkInit();
		return cfgManager;
	}

	public static ActionsManager getActions() {
		checkInit();
		return actManager;
	}

	public static FlagsManager getFlags() {
		checkInit();
		return flgManager;
	}

	public static ActivatorsManager getActivators() {
		checkInit();
		return activManager;
	}

	public static PlaceholdersManager getPlaceholders() {
		return phManager;
	}

	public static SelectorsManager getSelectors() {
		return slManager;
	}

	public static RaPlatform getPlatform() {
		return platform;
	}

	private static void checkInit() {
		if(!init) throw new IllegalStateException("Initialization of ReActionsCore was not called yet");
	}
}
