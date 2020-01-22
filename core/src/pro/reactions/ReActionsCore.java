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

public enum ReActionsCore {
	INSTANCE;

	private boolean init = false;
	private RaPlatform platform;
	private ConfigurationManager cfgManager;
	private ActionsManager actManager;
	private FlagsManager flgManager;
	private ActivatorsManager activManager;
	private PlaceholdersManager phManager;
	private SelectorsManager slManager;
	private ThreadHelper threader;
	private ModuleManager mdManager;

	public void initialize(RaPlatform platform) {
		if(init) throw new IllegalStateException("ReActionsCore is already initialized");
		if(platform == null) throw new IllegalArgumentException("RaPlatform cannot be null");
		this.platform = platform;
		ConfigurationManager cfgManager = platform.getConfiguration();
		if(cfgManager == null) throw new IllegalArgumentException("ConfigurationManager cannot be null");
		this.cfgManager = cfgManager;
		ThreadHelper threader = platform.getThreader();
		if(threader == null) throw new IllegalArgumentException("ThreadHelper cannot be null");
		this.threader = threader;
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

	public boolean isInitialized() {
		return init;
	}

	public ThreadHelper getThreader() {
		checkInit();
		return threader;
	}

	public ConfigurationManager getConfiguration() {
		checkInit();
		return cfgManager;
	}

	public ActionsManager getActions() {
		checkInit();
		return actManager;
	}

	public FlagsManager getFlags() {
		checkInit();
		return flgManager;
	}

	public ActivatorsManager getActivators() {
		checkInit();
		return activManager;
	}

	public PlaceholdersManager getPlaceholders() {
		return phManager;
	}

	public SelectorsManager getSelectors() {
		return slManager;
	}

	public RaPlatform getPlatform() {
		return platform;
	}

	private void checkInit() {
		if(!init) throw new IllegalStateException("Initialization of ReActionsCore was not called yet");
	}
}
