package lambdac.backend;

import lambdac.ErrorReport;

/**
 * É uma variável livre que ainda precisa ser ligada à sua definição.
 * Isso é feito durante a resolução das declarações, quando os objetos
 * FreeVariable são trocados por objetos Parameter.
 * 
 * @author Pablo
 */
public class FreeVariable extends Variable {
	
	public FreeVariable(String n) {
		super(n);
	}
	
	@Override
	protected Expression resolveDeclarations(SymbolTable table, ErrorReport report) {
		Expression result;
		
		if (table.existsDefinition(name)) {
			// expande a macro ou aplica a conversão alpha iniciada na abstraction 
			result = table.getDefinition(name); 
		} else {
			report.addMessage("ERRO: Variavel nao declarada: \"" + name + "\".");
			result = this;
		}
		
		return result;
	}

	@Override
	protected Expression replace(BoundVariable toReplace, Expression expr) {
		if (this.equals(toReplace)) {
			//não precisa renomear parametros em expr (justificativa matemática?)
			return expr;
		} else {
			return this;
		}
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else if (object instanceof FreeVariable) {
			return ((FreeVariable)object).name.equals(this.name);
		}
		return false;
	}
	
}
