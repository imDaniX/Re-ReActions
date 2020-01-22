package pro.reactions.modules.standard.activators;

import pro.reactions.activators.Activator;
import pro.reactions.activators.ActivatorBase;
import pro.reactions.activators.ActivatorType;
import pro.reactions.activators.storages.Storage;
import pro.reactions.configuration.RaConfigSection;

public class ExecActivator extends Activator {
	public ExecActivator(ActivatorBase base) {
		super(base);
	}

	@Override
	public void saveActivator(RaConfigSection cfg) {
	}

	@Override
	public boolean checkActivator(Storage storage) {
		return true;
	}

	@Override
	public Class<? extends ActivatorType> getType() {
		return ExecActivatorType.class;
	}
}
