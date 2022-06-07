package lambdac.backend;

import java.io.PrintStream;

import lambdac.ErrorReport;


public class FunctionApplication extends Expression {
	private final Expression function;
	private final Expression actualParameter;

	private Expression thisEvaluated;
	
	public FunctionApplication(Expression f, Expression x) {
		function = f;
		actualParameter = x;
	}

	public Expression getFunction() {
		return function;
	}
	
	public Expression getActualParameter() {
		return actualParameter;
	}
	
	@Override
	protected Expression resolveDeclarations(SymbolTable table, ErrorReport report) {
		Expression newFunction        = function.resolveDeclarations(table, report);
		Expression newActualParameter = actualParameter.resolveDeclarations(table, report);
		return new FunctionApplication(newFunction, newActualParameter);
	}

	
	@Override
	protected Expression evaluate() {
		if (thisEvaluated != null) {
			return thisEvaluated;
		} else {
			thisEvaluated = normalEvaluate();
			if (thisEvaluated == this) {
				thisEvaluated = null;
				return this;
			} else {
				return thisEvaluated;
			}
		}
		//return normalEvaluate(); 
		//return blockNormalEvaluate();
		//return uniformEvaluate();
		//return heuristicEvaluate();
	}

	// sempre que possível, aplica a beta, sem reduzir os parâmetros
	private Expression normalEvaluate() {
		Expression result;
		
		Expression lastFunctEval = null;
		Expression functionEvaluated = function;
		
		while (! (functionEvaluated instanceof FunctionAbstraction) && lastFunctEval != functionEvaluated) {
			lastFunctEval = functionEvaluated;
			functionEvaluated = functionEvaluated.evaluate();
		}

		Expression actualParamEvaluated = actualParameter;
		
		if (function == functionEvaluated) {
			actualParamEvaluated = actualParameter.evaluate();
		}

		FunctionAbstraction funcAbstraction;
		BoundVariable formalParameter;
		
		if (functionEvaluated instanceof FunctionAbstraction) {
			
			funcAbstraction = (FunctionAbstraction) functionEvaluated;
			formalParameter = funcAbstraction.getFormalParameter();
			
			//beta reduction
			result = funcAbstraction.getBody().replace(formalParameter, actualParamEvaluated);

		} else if (functionEvaluated != function || actualParamEvaluated != actualParameter) {
			result = new FunctionApplication(functionEvaluated, actualParamEvaluated);
			
		} else {
			result = this;
			
		}
		
		return result;
	}

	// sempre que possível, aplica a beta, sem reduzir os parâmetros
	private Expression blockNormalEvaluate() {
		Expression result = this;
		FunctionApplication thisFunc;
		
		do {
			thisFunc = (FunctionApplication)result;
		
			Expression lastFunctEval = null;
			Expression functionEvaluated = thisFunc.function;
			
			while (! (functionEvaluated instanceof FunctionAbstraction) && lastFunctEval != functionEvaluated) {
				lastFunctEval = functionEvaluated;
				functionEvaluated = functionEvaluated.evaluate();
			}
	
			Expression actualParamEvaluated = thisFunc.actualParameter;
			
			if (thisFunc.function == functionEvaluated) {
				actualParamEvaluated = thisFunc.actualParameter.evaluate();
			}
	
			FunctionAbstraction funcAbstraction;
			BoundVariable formalParameter;
			
			if (functionEvaluated instanceof FunctionAbstraction) {
				
				funcAbstraction = (FunctionAbstraction) functionEvaluated;
				formalParameter = funcAbstraction.getFormalParameter();
				
				//beta reduction
				result = funcAbstraction.getBody().replace(formalParameter, actualParamEvaluated);
	
			} else if (functionEvaluated != thisFunc.function || actualParamEvaluated != thisFunc.actualParameter) {
				result = new FunctionApplication(functionEvaluated, actualParamEvaluated);
				
			} else {
				result = this;
				
			}

		} while (result != this && (result instanceof FunctionApplication) && result.nodes() < 2*this.nodes());
		
		return result;
	}
	
	// funciona como uma BFS
	private Expression uniformEvaluate() {
		Expression result;
		
		Expression functionEvaluated = function.evaluate();
		Expression actualParamEvaluated = actualParameter.evaluate();

		FunctionAbstraction funcAbstraction;
		BoundVariable formalParameter;
		
		if (functionEvaluated instanceof FunctionAbstraction) {
			
			funcAbstraction = (FunctionAbstraction) functionEvaluated;
			formalParameter = funcAbstraction.getFormalParameter();
			
			//beta reduction
			result = funcAbstraction.getBody().replace(formalParameter, actualParamEvaluated);

		} else if (functionEvaluated != function || actualParamEvaluated != actualParameter) {
			result = new FunctionApplication(functionEvaluated, actualParamEvaluated);
			
		} else {
			result = this;
			
		}
		
		return result;
	}
	
	// aparentemente fica em loop
	private Expression heuristicEvaluate() {
		Expression result;
		
		Expression lastFunctEval = null;
		Expression functionEvaluated = function;
		Expression actualParamEvaluated = actualParameter;
		
		if (function.nodes() < actualParameter.nodes()) {
			functionEvaluated = function.evaluate();
			if (functionEvaluated == function) {
				actualParamEvaluated = actualParameter.evaluate();
			}
		} else {
			actualParamEvaluated = actualParameter.evaluate();
			if (actualParamEvaluated == actualParameter) {
				functionEvaluated = function.evaluate();
			}
		}

		FunctionAbstraction funcAbstraction;
		BoundVariable formalParameter;
		
		if (functionEvaluated instanceof FunctionAbstraction) {
			
			funcAbstraction = (FunctionAbstraction) functionEvaluated;
			formalParameter = funcAbstraction.getFormalParameter();
			
			//beta reduction
			result = funcAbstraction.getBody().replace(formalParameter, actualParamEvaluated);

		} else if (functionEvaluated != function || actualParamEvaluated != actualParameter) {
			result = new FunctionApplication(functionEvaluated, actualParamEvaluated);
			
		} else {
			result = this;
			
		}
		
		return result;
	}

	@Override
	protected Expression replace(BoundVariable toReplace, Expression exp) {
		Expression f = function.replace(toReplace, exp);
		Expression p = actualParameter.replace(toReplace, exp);
		
		if (f == function && p == actualParameter) {
			return this;
		} else {
			return new FunctionApplication(f, p);
		}
	}

	@Override
	protected long nodes() {
		long fd = function.nodes();
		long pd = actualParameter.nodes();
		return fd + pd + 1;
	}
	
	@Override
	protected void print(PrintStream out, boolean lastIsAbst) {
		function.print(out, false);
		out.print("(");
		actualParameter.print(out, false);
		out.print(")");
	}

}
