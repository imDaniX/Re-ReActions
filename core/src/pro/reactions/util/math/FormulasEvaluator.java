package pro.reactions.util.math;

import pro.reactions.util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
   All the code is inspired by Boann's answer on stackoverflow.com/questions/3422673
   You can use this code however you want to
*/

/**
 * Pretty much the same as {@link MathEvaluator}, but better performance for expressions that used more than one time
 * The main difference is that custom variables can be used
 */
public class FormulasEvaluator {
	private static final Expression ZERO = () -> 0;

	private final Expression expression;
	private final Map<String, Double> variables;

	public FormulasEvaluator(String expression) {
		this.variables = new HashMap<>();
		this.expression = thirdImportance(new PointerHolder(Util.removeSpaces(expression.toLowerCase())));
	}

	public synchronized void setVariable(String variable, Double value) {
		variables.put(variable, value);
	}

	public synchronized void setVariables(Map<String, Double> variables) {
		this.variables.putAll(variables);
	}

	public synchronized double eval() {
		return expression.eval();
	}

	private Expression thirdImportance(PointerHolder holder) {
		Expression x = secondImportance(holder);
		while(true) {
			if(holder.tryNext('+')) {
				Expression a = x;
				Expression b = secondImportance(holder);
				x = () -> a.eval() + b.eval();
			} else
			if(holder.tryNext('-')) {
				Expression a = x;
				Expression b = secondImportance(holder);
				x = () -> a.eval() - b.eval();
			} else
				return x;
		}
	}

	private Expression secondImportance(PointerHolder holder) {
		Expression x = firstImportance(holder);
		while(true) {
			if(holder.tryNext('*')) {
				Expression a = x;
				Expression b = firstImportance(holder);
				x = () -> a.eval() * b.eval();
			} else
			if(holder.tryNext('/')) {
				Expression a = x;
				Expression b = firstImportance(holder);
				x = () -> a.eval() / b.eval();
			} else
			if(holder.tryNext('%')) {
				Expression a = x;
				Expression b = firstImportance(holder);
				x = () -> a.eval() % b.eval();
			} else
				return x;
		}
	}

	private Expression firstImportance(PointerHolder holder) {
		if(holder.tryNext('-')) {
			Expression a = firstImportance(holder);
			return () -> -a.eval();
		}
		// meh
		while(holder.tryNext('+'));
		Expression x = ZERO;
		int start = holder.pointer;
		if(holder.tryNext('(')) {
			x = thirdImportance(holder);
			holder.tryNext(')');
		} else if(MathBase.isNumberChar(holder.current())) {
			while(MathBase.isNumberChar(holder.current())) holder.pointer++;
			double a = NumbersUtil.getDouble(holder.substring(start, holder.pointer), 0);
			x = () -> a;
		} else if(MathBase.isWordChar(holder.current())) {
			while(MathBase.isWordChar(holder.current()) || MathBase.isNumberChar(holder.current())) holder.pointer++;
			String str = holder.substring(start, holder.pointer);
			if(holder.tryNext('(')) {
				Expression a = thirdImportance(holder);
				Expression[] args = new Expression[0];
				while(holder.tryNext(',')) {
					args = Arrays.copyOfRange(args, 0, args.length + 1);
					args[args.length - 1] = thirdImportance(holder);
				}
				if(args.length > 0) {
					Expression[] args2 = args;
					double[] argsD = new double[args2.length];
					x = () -> {
						MathBase.Function function = MathBase.getFunction(str);
						for(int i = 0; i < args2.length; i++)
							argsD[i] = args2[i].eval();
						return function == null ? 0 : function.eval(a.eval(), argsD);
					};
				} else {
					x = () -> {
						MathBase.Function function = MathBase.getFunction(str);
						return function == null ? 0 : function.eval(a.eval());
					};
				}
				holder.tryNext(')');
			} else {
				x = () -> {
					Double var = variables.get(str);
					return var == null ? MathBase.getConstant(str) : var;
				};
			}
		}

		if(holder.tryNext('^')) {
			Expression a = x;
			Expression b = firstImportance(holder);
			x = () -> Math.pow(a.eval(), b.eval());
		}
		return x;
	}

	@FunctionalInterface
	private interface Expression {
		double eval();
	}

	/**
	 * Used in parse process to unload origin and pointer itself after ending of parse
	 */
	// Because of this class everything looks a bit more sh!tty... but still readable
	private static final class PointerHolder {
		final String origin;
		int pointer;

		PointerHolder(String origin) {
			this.origin = origin;
			this.pointer = 0;
		}
		
		String substring(int start, int end) {
			return origin.substring(start, end);
		}

		char current() {
			return origin.length() > pointer ? origin.charAt(pointer) : ' ';
		}

		boolean tryNext(char c) {
			if(current() == c) {
				pointer++;
				return true;
			}
			return false;
		}
	}
}