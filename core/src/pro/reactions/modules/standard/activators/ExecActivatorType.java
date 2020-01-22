package pro.reactions.modules.standard.activators;

import pro.reactions.activators.Activator;
import pro.reactions.activators.ActivatorBase;
import pro.reactions.activators.ActivatorType;
import pro.reactions.configuration.RaConfigSection;
import pro.reactions.util.Alias;
import pro.reactions.util.Parameters;

@Alias({"executable", "exe"})
public class ExecActivatorType implements ActivatorType {
	@Override
	public String getName() {
		return "exec";
	}

	@Override
	public Activator create(ActivatorBase base, Parameters params) {
		return new ExecActivator(base);
	}

	@Override
	public Activator load(ActivatorBase base, RaConfigSection cfg) {
		return new ExecActivator(base);
	}
}
