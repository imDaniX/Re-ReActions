package pro.reactions.activators.storages;

import pro.reactions.activators.ActivatorType;
import pro.reactions.platform.RaPlayer;
import pro.reactions.util.collections.InsensitiveStringMap;
import pro.reactions.util.data.DataValue;

import java.util.HashMap;
import java.util.Map;

public abstract class Storage {
	private final RaPlayer player;
	private final boolean async;
	private Changeables changeables;
	private InsensitiveStringMap<String> tempVariables;

	public Storage(RaPlayer player) {
		this.player = player;
		this.async = false;
	}

	public Storage(RaPlayer player, boolean async) {
		this.player = player;
		this.async = async;
	}

	public final void init() {
		this.tempVariables = new InsensitiveStringMap<>(false);
		defaultTempVariables(tempVariables);
		InsensitiveStringMap<DataValue> changeables = new InsensitiveStringMap<>(false);
		defaultChangeables(changeables);
		this.changeables = Changeables.fromMap(changeables);
	}

	public void defaultChangeables(InsensitiveStringMap<DataValue> changeables) {
	}

	public void defaultTempVariables(InsensitiveStringMap<String> tempVariables) {
	}

	public abstract Class<? extends ActivatorType> getType();

	public final Context generateContext() {
		return new Context(changeables, tempVariables, player, async);
	}

	public final Changeables getChangeables() {
		return changeables;
	}
}
