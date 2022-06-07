package tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lambdac.backend.Program;
import lambdac.frontend.Lexer;
import lambdac.frontend.Parser;


public class TestParser {

	public static void main(String args[]) throws IOException {
		Lexer lexer;
		Parser parser;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        Program program;

        System.out.print("Digite o numero do programa: ");
        String arquivo = "examples\\program" + in.readLine() + ".txt";
        System.out.println();
		
		try {
			lexer = new Lexer(new FileInputStream(arquivo));
			parser = new Parser(lexer);

			program = parser.start();

			System.out.println("\nPROGRAMA:\n");
			System.out.println(program);
		
		} catch (Exception e) {
			
			e.printStackTrace();

		}

	}
	
}
