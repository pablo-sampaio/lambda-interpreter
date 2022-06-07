package lambdac.backend;

import java.util.LinkedList;
import java.util.List;

import lambdac.ErrorReport;


public class DeclarationSection {
	private List<Declaration> listDeclarations;
	
	public DeclarationSection() {
		listDeclarations = new LinkedList<Declaration>();
	}
	
	public void addDeclaration(Declaration d) {
		listDeclarations.add(d);
	}

	public SymbolTable expandDeclarations(ErrorReport report) {
		SymbolTable table = new SymbolTable();
		
		for (Declaration d : listDeclarations) {
			d.expandDeclarations(table, report);
		}
		
		return table;
	}

	public String toString() {
		String result = "";
		
		for (Declaration d : listDeclarations) {
			result += "\t" + d.toString() + "\n";
		}
		
		return result;
	}
	
}
