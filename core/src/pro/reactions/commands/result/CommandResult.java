package pro.reactions.commands.result;

public class CommandResult {
	public static final CommandResult BLANK_BACKUP = new CommandResult(CommandResultType.BACKUP, null);
	private final CommandResultType type;
	private final String exec;

	public CommandResult(CommandResultType type, String exec) {
		this.type = type;
		this.exec = exec;
	}
}
