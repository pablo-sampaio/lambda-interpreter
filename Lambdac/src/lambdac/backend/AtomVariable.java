package lambdac.backend;

import lambdac.ErrorReport;


/**
 * � uma vari�vel livre prevista e aceita no programa, que nunca vai
 * ser avaliada ou substitu�da por nenhuma outra express�o.
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
