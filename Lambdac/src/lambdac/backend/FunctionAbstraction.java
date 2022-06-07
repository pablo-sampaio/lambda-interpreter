package lambdac.backend;

import java.io.PrintStream;

import lambdac.ErrorReport;


public class FunctionAbstraction extends Expression {
	private final BoundVariable formalParameter;
	private final Expression body;
	
	private Expression thisEvaluated;

	public FunctionAbstraction(String parameter, Expression expr) {
		formalParameter = BoundVariable.createUnused(parameter);
		body = expr;
	}
	
	private FunctionAbstraction(BoundVariable param, Expression expr) {
		formalParameter = param;
		body = expr;
	}
	
	public BoundVariable getFormalParameter() {
		return formalParameter;
	}
	
	public Expression getBody() {
		return body;
	}
	
	@Override
	protected Expression resolveDeclarations(SymbolTable table, ErrorReport report) {
		Expression newBody; 
		
		// alpha conversion to an unique name
		table.pushDefinition(formalParameter.getOriginalName(), formalParameter);
		newBody = body.resolveDeclarations(table, report);
		table.popBoundVariable(formalParameter.getOriginalName());
		
		return new FunctionAbstraction(formalParameter, newBody);
	}
	
	@Override
	protected Expression evaluate() {
		if (thisEvaluated != null) {
			return thisEvaluated;
		} else {
			Expression newBody = body.evaluate();
			if (newBody == body) {
				return this;
			} else {
				return new FunctionAbstraction(formalParameter, newBody);
			}
		}
//		Expression newBody = body.evaluate();
//		return (newBody == body)? this : new FunctionAbstraction(formalParameter, newBody);
	}

	@Override
	protected Expression replace(BoundVariable toReplace, Expression expr) {
		Expression result;
		Expression bodyReplaced;
		
		if (formalParameter.equals(toReplace)) {
			// não é problema (justificativa matemática relacionada à da não-necessidade
			// de renomear as variáveis da expr no replace de BoundVariable) 
			result = this;

		} else {
			//result = new FunctionAbstraction(formalParameter, body.replace(toReplace, expr));
			bodyReplaced = body.replace(toReplace, expr);
			
			if (bodyReplaced == body) {
				result = this;
			} else {
				result = new FunctionAbstraction(formalParameter, bodyReplaced);
			}
		
		}
		
		return result;
	}

	@Override
	protected long nodes() {
		return body.nodes() + 1;
	}
	
	@Override
	protected void print(PrintStream out, boolean lastIsAbstraction) {

		if (lastIsAbstraction) {
			out.print(", ");
		} else {
			out.print("\\lambda ");
		}
		
		out.print(formalParameter.getName());
		
		if (body instanceof FunctionAbstraction) {
			body.print(out, true);
		} else {
			out.print(" . ");
			body.print(out);
		}

	}

}
