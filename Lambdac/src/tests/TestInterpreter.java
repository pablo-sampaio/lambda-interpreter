package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lambdac.ErrorReport;
import lambdac.Interpreter;


public class TestInterpreter {

	public static void main(String[] args) throws IOException {
		Interpreter interpreter = new Interpreter();
		
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Digite o numero do programa: ");
        String arquivo = "examples\\program" + in.readLine() + ".lam";

		ErrorReport report = interpreter.interpret(arquivo);
		
		if (report.getNumMessages() > 0) {
			System.out.println("\nErros encontrados: ");
			
			for (String msg : report.getAllMessages()) {
				System.out.println(">> " + msg);
			}
		}
		
	}

}
