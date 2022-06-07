package lambdac.backend;

import lambdac.ErrorReport;


/**
 * É uma variável livre prevista e aceita no programa, que nunca vai
 * ser avaliada ou substituída por nenhuma outra expressão.
 * 
 * @author Pablo
 */
public class AtomVariable extends FreeVariable {
	
	public AtomVariable(String n) {
		super(n);
	}

	@Override
	protected Expression resolveDeclarations(SymbolTable table, ErrorReport error) {
		return this;
	}

	@Override
	protected Expression replace(BoundVariable toReplace, Expression expr) {
		return this;
	}

}
