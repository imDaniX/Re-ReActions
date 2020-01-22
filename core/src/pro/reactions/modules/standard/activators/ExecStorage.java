package pro.reactions.modules.standard.activators;

import pro.reactions.activators.ActivatorType;
import pro.reactions.activators.storages.Context;
import pro.reactions.activators.storages.Storage;
import pro.reactions.platform.RaPlayer;
import pro.reactions.util.collections.InsensitiveStringMap;
import pro.reactions.util.data.DataValue;

public class ExecStorage extends Storage {
	private final InsensitiveStringMap<String> tempVariables;

	public ExecStorage(Context context, RaPlayer player) {
		super(player, context.isAsync());
		this.tempVariables = context.getTempVariables();
	}

	@Override
	public void defaultChangeables(InsensitiveStringMap<DataValue> changeables) {}

	@Override
	public void defaultTempVariables(InsensitiveStringMap<String> tempVariables) {
		tempVariables.putAll(this.tempVariables);
	}

	@Override
	public Class<? extends ActivatorType> getType() {
		return null;
	}
}
