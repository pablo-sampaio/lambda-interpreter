package lambdac.backend;

import lambdac.ErrorReport;


/**
 * � uma vari�vel que j� foi ligada � sua defini��o em alguma
 * abstra��o de fun��o.
 * 
 * @author Pablo
 */
public class BoundVariable extends Variable {
	private final String originalName;
	
	private static int nextUnusedIdentification = 0;
	
	static void resetIdentification() {
		nextUnusedIdentification = 0;
	}
	
	public static BoundVariable createUnused(String name) {
		// pode ser melhorado para usar uma numera��o independente para cada nome
		BoundVariable p = new BoundVariable(name, nextUnusedIdentification);
		nextUnusedIdentification ++;
		return p;
	}

	private BoundVariable(String name, int id) {
		super(name + id);
		originalName = name;
		//identification = id;
	}
	
	public String getOriginalName() {
		return originalName;
	}

	@Override
	protected Expression resolveDeclarations(SymbolTable table, ErrorReport report) {
		return this;
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
		//ok, pois toda inst�ncia � criada com createUnused()
		return (object == this); 
	}

}
