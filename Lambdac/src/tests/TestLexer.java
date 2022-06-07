package tests;

import java_cup.runtime.Symbol;
import lambdac.frontend.Lexer;
import lambdac.frontend.sym;


public class TestLexer {

	public static void main(String[] args) throws Exception {
		Lexer lexer = null;
		Symbol token = null;

		System.out.println("\n\n\n");
		System.out.println(" == TESTE DO LEXER ==\n");
		System.out.println(" Digite alguma string terminada em \".\" e tecle ENTER:\n\n");
		System.out.print(" ");

		// passa a entrada padrão para o lexer
		// para usar arquivo, descomente a linha abaixo
		lexer = new Lexer(System.in);
		//lexer = new Lexer(new FileInputStream("prog.txt"));
		
		do {
			token = lexer.next_token();
			System.out.println("\t" + token);
		} while (token.sym != sym.POINT);
		
		System.out.println("\n == FIM ==");
		
	}
	
}
