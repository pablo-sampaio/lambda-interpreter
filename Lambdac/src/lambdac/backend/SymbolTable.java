package lambdac.backend;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class SymbolTable {
	private Map<String, Deque<Expression>> table;
	
	public SymbolTable() {
		table = new HashMap<String, Deque<Expression>>();
	}
	
	public void pushDefinition(String name, Expression expr) {
		Deque<Expression> stack;
		
		if (table.containsKey(name)) {
			stack = table.get(name);
		} else {
			stack = new LinkedList<Expression>();
			table.put(name, stack);
		}
		
		stack.push(expr);
	}
	
	public Expression getDefinition(String name) {
		return table.get(name).peek();
	}
	
	public boolean existsDefinition(String name) {
		return table.containsKey(name);
	}
	
	public void popBoundVariable(String name) {
		Deque<Expression> stack = table.get(name);
		
		stack.pop();
		if (stack.isEmpty()) {
			table.remove(name);
		}

	}

	public String toString() {
		return table.toString();
	}

}
