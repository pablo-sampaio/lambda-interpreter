package lambdac.backend;

import java.io.PrintStream;

import lambdac.ErrorReport;


/**
 * Abstract superclass for @FreeVariable and @BoundVariable.
 * @author Pablo
 *
 */
public abstract class Variable extends Expression {
	protected final String name;
	
	public Variable(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}

	@Override
	protected abstract Expression resolveDeclarations(SymbolTable table, ErrorReport report);

	@Override
	protected Expression evaluate() {
		return this;
	}

	@Override
	protected void print(PrintStream out, boolean lastIsAbstraction) {
		out.print(getName());
	}

	@Override
	protected abstract Expression replace(BoundVariable toReplace, Expression expr);
	
	@Override
	protected long nodes() {
		return 1;
	}
	
	@Override
	public abstract boolean equals(Object o);

}
