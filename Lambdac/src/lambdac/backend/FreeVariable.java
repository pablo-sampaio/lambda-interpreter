package lambdac.backend;

import lambdac.ErrorReport;

/**
 * � uma vari�vel livre que ainda precisa ser ligada � sua defini��o.
 * Isso � feito durante a resolu��o das declara��es, quando os objetos
 * FreeVariable s�o trocados por objetos Parameter.
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
			// expande a macro ou aplica a convers�o alpha iniciada na abstraction 
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
			//n�o precisa renomear parametros em expr (justificativa matem�tica?)
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
