package pro.reactions.activators;

import pro.reactions.configuration.RaConfigSection;
import pro.reactions.util.Parameters;

public interface ActivatorType {

	String getName();

	Activator create(ActivatorBase base, Parameters params);

	Activator load(ActivatorBase base, RaConfigSection cfg);
}
