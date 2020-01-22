package pro.reactions.modules.standard;

import pro.reactions.actions.Action;
import pro.reactions.activators.ActivatorType;
import pro.reactions.modules.Module;
import pro.reactions.modules.ModuleDescription;
import pro.reactions.modules.ModulePart;
import pro.reactions.modules.standard.actions.ExecuteAction;
import pro.reactions.modules.standard.activators.ExecActivatorType;
import pro.reactions.modules.standard.placeholders.CalcPlaceholder;
import pro.reactions.modules.standard.placeholders.PlayerPlaceholders;
import pro.reactions.modules.standard.placeholders.RandomPlaceholder;
import pro.reactions.modules.standard.placeholders.TempvarsPlaceholder;
import pro.reactions.modules.standard.placeholders.VariablesPlaceholder;
import pro.reactions.placeholders.Placeholder;

@ModuleDescription(
		authors = {"fromgate","MaxDikiy","imDaniX"},
		description = "Default module of ReActions"
)
public class DefaultModule implements Module {
	@Override
	public String getName() {
		return "Default";
	}

	@Override
	public String[] plugins() {
		return new String[0];
	}

	@ModulePart
	public ActivatorType[] activators() {
		return new ActivatorType[]{
				new ExecActivatorType()
		};
	}

	@ModulePart
	public Action[] actions() {
		return new Action[] {
				new ExecuteAction()
		};
	}

	@ModulePart
	public Placeholder[] placeholders() {
		return new Placeholder[] {
				new CalcPlaceholder(),
				new RandomPlaceholder(),
				new TempvarsPlaceholder(),
				new VariablesPlaceholder()
		};
	}

	@ModulePart
	public Placeholder[] playerPlaceholders() {
		return PlayerPlaceholders.values();
	}
}
