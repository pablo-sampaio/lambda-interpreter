package lambdac.backend;

import lambdac.ErrorReport;


public class Declaration {
	private String name;
	private Expression expression;
		
	public Declaration(String n, Expression e) {
		name = n;
		expression = e;
	}

	public String getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}
	
	void expandDeclarations(SymbolTable table, ErrorReport report) {
		if (table.existsDefinition(name)) {
			System.out.println("ERRO: Redeclaração do nome: \"" + name +"\".");
		}
		
		expression = expression.resolveDeclarations(table, report);

		table.pushDefinition(name, expression);
	}

	public String toString() {
		return name + " := " + expression.toString();
	}
	
}
