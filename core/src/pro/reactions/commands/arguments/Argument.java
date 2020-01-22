package pro.reactions.commands.arguments;

import pro.reactions.ReActionsCore;
import pro.reactions.commands.result.CommandResultType;
import pro.reactions.platform.RaPlatform;
import pro.reactions.util.Util;
import pro.reactions.util.math.NumbersUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Argument {
	private final static Set<String> NUMBERS = new HashSet<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
	private final static RaPlatform platform = ReActionsCore.getPlatform();

	private final String argument;
	private final Set<String> multiple;
	private final Argument.Type type;

	public Argument(String argument) {
		switch (argument) {
			case "~player":
				this.type = Type.PLAYER;
				multiple = new HashSet<>();
				break;
			case "~int":
				this.type = Type.INTEGER;
				multiple = NUMBERS;
				break;
			case "~float":
				this.type = Type.FLOAT;
				multiple = NUMBERS;
				break;
			case "*":
				this.type = Type.ANY;
				multiple = new HashSet<>();
				break;
			default:
				if(argument.contains("|") && !argument.contains("\\|")) {
					this.type = Type.MULTIPLE_TEXT;
					multiple = new HashSet<>(Arrays.asList(argument.split("\\|")));
				} else {
					this.type = Type.TEXT;
					if(argument.startsWith("\\~") || argument.equals("\\*")) argument = argument.substring(1);
					multiple = Collections.singleton(argument);
				}
		}
		this.argument = argument;
	}

	/**
	 * Compare argument for given string
	 * @param arg String to check
	 * @return {@link CommandResultType#DEFAULT} if everything is OK, some error if not
	 */
	public CommandResultType check(String arg) {
		switch(type) {
			case PLAYER:
				return platform.isOnline(arg) ? CommandResultType.DEFAULT : CommandResultType.OFFLINE;
			case TEXT: return argument.equalsIgnoreCase(arg) ? CommandResultType.DEFAULT : CommandResultType.BACKUP;
			case MULTIPLE_TEXT: return multiple.contains(arg) ? CommandResultType.DEFAULT : CommandResultType.BACKUP;
			case INTEGER: return NumbersUtil.INT.matcher(arg).matches() ? CommandResultType.DEFAULT : CommandResultType.NOT_INTEGER;
			case FLOAT: return NumbersUtil.FLOAT.matcher(arg).matches() ? CommandResultType.DEFAULT : CommandResultType.NOT_FLOAT;
		}
		return CommandResultType.DEFAULT;
	}

	/**
	 * Add value of argument to list of tab-complete if possible
	 * @param complete Original list
	 * @param arg Current argument
	 */
	public void complete(Collection<String> complete, String arg) {
		Util.copyPartialMatches(arg, type == Type.PLAYER ? platform.getPlayersNames() : multiple, complete);
	}

	/**
	 * Get priority of argument based on it's type
	 * @return Argument's priority
	 */
	public int getPriority() {
		return type.priority;
	}

	private enum Type {
		TEXT(10), MULTIPLE_TEXT(9), PLAYER(6), FLOAT(4), INTEGER(3), ANY(1);
		final int priority;
		Type(int priority) {
			this.priority = priority;
		}
	}
}
