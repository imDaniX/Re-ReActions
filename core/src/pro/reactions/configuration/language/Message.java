package pro.reactions.configuration.language;

import pro.reactions.util.Util;

public class Message {
	private final String path;
	private final String msg;
	private final String[] phs;

	public Message(String path, String msg, String... phs) {
		this.path = path.toLowerCase();
		this.msg = msg;
		this.phs = phs;
	}

	public String getPath() {
		return path;
	}

	public final String[] getPlaceholders() {
		return phs;
	}

	public final String getMsg(String... phs) {
		int max = phs.length > this.phs.length ? this.phs.length : phs.length;
		String msg = this.msg;
		for(int i = 0; i < max; i++)
			msg = msg.replace(this.phs[i], phs[i]);
		return Util.clr(msg);
	}
}
