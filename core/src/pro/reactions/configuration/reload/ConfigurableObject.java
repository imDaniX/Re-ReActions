package pro.reactions.configuration.reload;

import pro.reactions.configuration.ConfigurationManager;

// TODO: Pretty sure can be done better...
public abstract class ConfigurableObject {
	private static final ReloadType[] DEFAULT_TYPE = {ReloadType.OTHER};

	public ConfigurableObject() {
		Class<? extends ConfigurableObject> obj = this.getClass();
		ReloadType[] types;
		if(obj.isAnnotationPresent(Reloadable.class)) {
			types = obj.getAnnotation(Reloadable.class).value();
		} else {
			types = DEFAULT_TYPE;
		}
		for(ReloadType type : types)
			ConfigurationManager.registerReloadable(type, this);
	}

	/**
	 * Method that will be called during reload
	 * @param cause Type of reload
	 * @param firstReload Is object was reloaded for first time of this reload(for objects with multiple types)
	 */
	public abstract void reload(ReloadType cause, boolean firstReload);
}
