package pro.reactions.configuration;

import pro.reactions.time.WaitMode;
import pro.reactions.util.data.BooleanValue;
import pro.reactions.util.data.DataValue;
import pro.reactions.util.data.EnumValue;
import pro.reactions.util.data.IntegerValue;
import pro.reactions.util.data.StringValue;

enum CoreCfg implements CfgData {
	LANG("general.language", new StringValue("en-us")),
	DEBUG("general.debug", new BooleanValue(false)),
	VARS_MODE("general.player-self-variable-file", new BooleanValue(true)),
	ASYNC_VARS("general.player-asynch-save-self-variable-file", new BooleanValue(true)),
	PH_LIMIT("general.placeholder-limit", new IntegerValue(127)),
	CLR_CHAR("general.color-character", new StringValue("&")),
	WAIT_LIMIT("reactions.wait-hours-limit", new IntegerValue(473)),
	WAIT_MODE("reactions.wait-mode-after-player-exit", new EnumValue(WaitMode.WAIT));

	private final String path;
	private final DataValue def;

	CoreCfg(String path, DataValue def) {
		this.path = path;
		this.def = def;
	}

	@Override
	public DataValue getDefault() {
		return def;
	}

	@Override
	public String getPath() {
		return path;
	}

	public static void registerAll(ConfigurationManager mgr) {
		for(CoreCfg data : CoreCfg.values())
			mgr.registerData(data.name().toLowerCase().replace('_', '-'), data);
	}
}
