package pro.reactions.activators;

import pro.reactions.activators.storages.Storage;
import pro.reactions.configuration.RaConfigSection;

public abstract class Activator {
	private final ActivatorBase base;

	public Activator(ActivatorBase base) {
		this.base = base;
	}

	public boolean isValid() {
		return true;
	}

	public final ActivatorBase getBase() {
		return base;
	}

	public abstract void saveActivator(RaConfigSection cfg);

	public abstract boolean checkActivator(Storage storage);

	public abstract Class<? extends ActivatorType> getType();
}
