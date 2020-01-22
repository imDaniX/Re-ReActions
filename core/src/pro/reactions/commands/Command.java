package pro.reactions.commands;

import pro.reactions.platform.RaUser;

public abstract class Command {

	public abstract boolean execute(RaUser user, String[] args);
}
