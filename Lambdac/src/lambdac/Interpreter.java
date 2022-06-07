package lambdac;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import lambdac.backend.Expression;
import lambdac.backend.Program;
import lambdac.frontend.Lexer;
import lambdac.frontend.Parser;


public class Interpreter {
	private Program lastProgram;
	//TODO: opções de impressão...

	public Interpreter() {
		
	}
	
	public ErrorReport interpret(String filePath) throws IOException {
		return interpret(new FileInputStream(filePath), System.out);
	}

	// usa a saída apenas em caso de sucesso
	// TODO: criar stream de erro? não retornar erros, imprimi-los
	public ErrorReport interpret(InputStream inputStream, OutputStream outputStream) {
		Lexer lexer = new Lexer(inputStream);
		Parser parser = new Parser(lexer);
		
		try {
			
			lastProgram = parser.start();
		
		} catch (Exception exc) {
			ErrorReport errors = new ErrorReport();
			
			errors.addMessage(exc.getMessage());
			exc.printStackTrace();
		
			return errors;
		}
		
		PrintStream out = new PrintStream(outputStream);
		
		out.println();

		if (!lastProgram.foundErrors()) {
			long start = System.currentTimeMillis();
			Expression result = lastProgram.evaluate();
			long end = System.currentTimeMillis();
		
			out.println(result);
			out.println("\nTempo decorrido: " + (end - start));
		}
		
		return lastProgram.getErrorReport();
	}
	
}
