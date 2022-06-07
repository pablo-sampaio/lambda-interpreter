package lambdac.backend;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lambdac.ErrorReport;


public abstract class Expression {

	/**
	 * Modifica a express�o, substituindo todas as ocorr�ncias de
	 * nomes de macros (da se��o "declare") pela sua defini��o 
	 * e trocando todas as vari�veis livres pelo parametro de uma 
	 * abstra��o ao qual ela est� associada. Esta opera��o reporta 
	 * erros em caso de algum nome n�o estar associado a uma
	 * declara��o.
	 * 
	 * Retorna uma nova express�o modificada, possivelmente mantendo 
	 * sub�rvores que n�o foram alteradas. (N�o garante retornar
	 * a mesma refer�ncia quando a opera��o n�o provocar mudan�as).
	 */
	protected abstract Expression resolveDeclarations(SymbolTable table, ErrorReport report);
	
	/**
	 * Avalia a express�o, resolvendo e expandindo as aplica��es
	 * de fun��es.
	 * 
	 * Retorna uma nova express�o, se a opera��o provocar alguma 
	 * altera��o. Sen�o, retorna a mesma referencia da express�o.
	 */
	protected abstract Expression evaluate();
	
	/**
	 * Troca todas ocorr�ncias da vari�vel pela express�o dada.
	 * Usada na avalia��o de aplica��es (redu��o beta).
	 * 
	 * Retorna uma nova express�o, se a opera��o provocar alguma 
	 * altera��o. Sen�o, retorna a mesma referencia da express�o
	 * atual (this).
	 */
	protected abstract Expression replace(BoundVariable toReplace, Expression expr);
	
	/**
	 * Retorna o n�mero de n�s da �rvore.
	 */
	protected abstract long nodes();
	
	/**
	 * Cria uma String com o c�digo da express�o.
	 */
	public String toString() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		print(new PrintStream(out));
		return out.toString();
	}
	
	/**
	 * Imprime o c�digo da express�o para o stream dado.  
	 */
	public void print(PrintStream out) {
		print(out, false);
	}

	/**
	 * Imprime o c�digo da express�o. O segundo parametro indica 
	 * se a express�o anterior (pai) � uma abstra��o. 
	 */
	protected abstract void print(PrintStream out, boolean lastIsAbstraction);

}
