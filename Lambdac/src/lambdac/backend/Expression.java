package lambdac.backend;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lambdac.ErrorReport;


public abstract class Expression {

	/**
	 * Modifica a expressão, substituindo todas as ocorrências de
	 * nomes de macros (da seção "declare") pela sua definição 
	 * e trocando todas as variáveis livres pelo parametro de uma 
	 * abstração ao qual ela está associada. Esta operação reporta 
	 * erros em caso de algum nome não estar associado a uma
	 * declaração.
	 * 
	 * Retorna uma nova expressão modificada, possivelmente mantendo 
	 * subárvores que não foram alteradas. (Não garante retornar
	 * a mesma referência quando a operação não provocar mudanças).
	 */
	protected abstract Expression resolveDeclarations(SymbolTable table, ErrorReport report);
	
	/**
	 * Avalia a expressão, resolvendo e expandindo as aplicações
	 * de funções.
	 * 
	 * Retorna uma nova expressão, se a operação provocar alguma 
	 * alteração. Senão, retorna a mesma referencia da expressão.
	 */
	protected abstract Expression evaluate();
	
	/**
	 * Troca todas ocorrências da variável pela expressão dada.
	 * Usada na avaliação de aplicações (redução beta).
	 * 
	 * Retorna uma nova expressão, se a operação provocar alguma 
	 * alteração. Senão, retorna a mesma referencia da expressão
	 * atual (this).
	 */
	protected abstract Expression replace(BoundVariable toReplace, Expression expr);
	
	/**
	 * Retorna o número de nós da árvore.
	 */
	protected abstract long nodes();
	
	/**
	 * Cria uma String com o código da expressão.
	 */
	public String toString() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		print(new PrintStream(out));
		return out.toString();
	}
	
	/**
	 * Imprime o código da expressão para o stream dado.  
	 */
	public void print(PrintStream out) {
		print(out, false);
	}

	/**
	 * Imprime o código da expressão. O segundo parametro indica 
	 * se a expressão anterior (pai) é uma abstração. 
	 */
	protected abstract void print(PrintStream out, boolean lastIsAbstraction);

}
