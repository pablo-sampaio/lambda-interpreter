package lambdac.backend;

import java.io.PrintStream;

import lambdac.ErrorReport;

public class Program {
	private DeclarationSection declarations;
	private Expression body;
	
	private boolean expanded;
	private ErrorReport errors;
	
	public Program(DeclarationSection decl, Expression bd) {
		declarations = decl;
		body = bd;
		expanded = false;
		errors = new ErrorReport();
	}
	
	public Expression expandDeclarations() {
		if (expanded) {
			return body;
		}

		SymbolTable table;
		
		BoundVariable.resetIdentification();
		
		table = declarations.expandDeclarations(errors);
		body  = body.resolveDeclarations(table, errors);
		
		expanded = true;

		return body;
	}
	
	public boolean foundErrors() {
		return (errors.getNumMessages() != 0);
	}
	
	public ErrorReport getErrorReport() {
		return errors;
	}
	
	public Expression evaluate() {
		return evaluate(null);
	}
	
	public Expression evaluate(PrintStream out) {
		if (!expanded) {
			expandDeclarations();
		}
		if (foundErrors()) {
			return body;
		}
//out = System.out;		
		Expression lastBody;

		do {
			lastBody = body;
			
			if (out != null) {
				out.print("> ");
				out.print(lastBody.nodes());
				out.print(" nodes > ");
				//lastBody.print(out);
				out.println();
			}
			
			body = lastBody.evaluate(); 
			System.gc();
		
		} while ( body != lastBody );

		return body;
	}

	public String toString() {
		String result = "";

		result += "DECLARATIONS\n";
		result += declarations.toString();
		result += "BODY\n\t";
		result += body.toString();
		
		return result;
	}

}
