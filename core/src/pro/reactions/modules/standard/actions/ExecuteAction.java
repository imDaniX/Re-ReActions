package pro.reactions.modules.standard.actions;

import pro.reactions.ReActionsCore;
import pro.reactions.actions.Action;
import pro.reactions.activators.ActivatorsManager;
import pro.reactions.activators.storages.Context;
import pro.reactions.modules.standard.activators.ExecStorage;
import pro.reactions.platform.RaPlayer;
import pro.reactions.platform.ThreadHelper;
import pro.reactions.selectors.SelectorsManager;
import pro.reactions.time.TimeUtil;
import pro.reactions.util.Alias;
import pro.reactions.util.Parameters;

import java.util.Collection;
import java.util.Collections;

@Alias("run")
public class ExecuteAction implements Action {
	private static final ActivatorsManager activManager = ReActionsCore.INSTANCE.getActivators();
	private static final SelectorsManager selManager = ReActionsCore.INSTANCE.getSelectors();
	private static final ThreadHelper threader = ReActionsCore.INSTANCE.getThreader();
	@Override
	public boolean execute(Context context, String value) {
		Parameters params = Parameters.fromString(value);
		if(params.containsAny("id", "activator")) {
			String id = params.contains("id") ? params.getString("id") : params.getString("activator");
			if(!activManager.containsActivator(id)) return false;
			long delay = TimeUtil.parseTime(params.getString("delay"), true);
			int repeat = params.getInteger("repeat");
			long sleep = TimeUtil.parseTime(params.getString("sleep"), true);
			boolean async = params.getBoolean("async", context.isAsync());
			String players = params.contains("players") ? params.getString("players") : params.getString("player");
			execute(context, delay, repeat, sleep, async, id, selManager.parseSelectors(Parameters.fromString(players)));
			return true;
		}
		if(!activManager.containsActivator(value)) return false;
		execute(context, 1, 0, 0, context.isAsync(), value, Collections.singleton(context.getPlayer()));
		return true;
	}

	private static void execute(Context context, long delay, int repeat, long sleep, boolean async, String id, Collection<RaPlayer> players) {
		if(delay == 0 && repeat == 0 && context.isAsync() == async) {
			players.forEach(p -> activManager.executeActivator(new ExecStorage(context, p), id));
			return;
		}
		Runnable runnable = () -> players.forEach(p -> activManager.executeActivator(new ExecStorage(context, p), id));
		if(async) {
			if(delay > 0) {
				if(repeat > 0) {
					threader.repeatAsync(delay, sleep, repeat, runnable);
				} else {
					threader.runAsync(delay, runnable);
				}
			} else {
				if(--repeat > 0) {
					threader.runAsync(runnable);
					threader.repeatAsync(sleep, sleep, repeat, runnable);
				} else {
					threader.runAsync(runnable);
				}
			}
		} else {
			if(delay > 0) {
				if(repeat > 0) {
					threader.repeatSync(delay, sleep, repeat, runnable);
				} else {
					threader.runSync(delay, runnable);
				}
			} else {
				if(--repeat > 0) {
					threader.runSync(runnable);
					threader.repeatSync(sleep, sleep, repeat, runnable);
				} else {
					threader.runSync(runnable);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "execute";
	}
}
