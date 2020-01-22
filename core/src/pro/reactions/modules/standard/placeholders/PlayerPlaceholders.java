package pro.reactions.modules.standard.placeholders;

import pro.reactions.activators.storages.Context;
import pro.reactions.placeholders.Placeholder;
import pro.reactions.platform.RaPlayer;
import pro.reactions.util.Alias;

public enum PlayerPlaceholders implements Placeholder.Equality {
	@Alias("player")
	NAME {
		@Override
		public String getId() {
			return "player_name";
		}

		@Override
		public String processPlaceholder(String ph, String text, Context context) {
			RaPlayer player = context.getPlayer();
			return player == null ? "" : player.getName();
		}
	},
	@Alias({"player_id", "uuid"})
	UUID {
		@Override
		public String getId() {
			return "player_uuid";
		}

		@Override
		public String processPlaceholder(String ph, String text, Context context) {
			RaPlayer player = context.getPlayer();
			return player == null ? "" : player.getName();
		}
	},
	IP {
		@Override
		public String getId() {
			return "player_ip";
		}

		@Override
		public String processPlaceholder(String ph, String text, Context context) {
			RaPlayer player = context.getPlayer();
			return player == null ? "" : player.getIp().getHostAddress();
		}
	}
}
