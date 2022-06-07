package lambdac.frontend;

%%

%class Lexer
%public
%cup
%8bit
%line
%column

%{
	//Para facilitar a criação dos tokens.
	Token newToken(int tokenId) {
    	return new Token(tokenId, yyline+1, yycolumn+1, yytext());
	}
%}

whiteSpace=([ \n\t\f\r]+)

%%

{whiteSpace} { 
	// Espaços em branco.
}

"\\lambda" { return newToken(sym.LAMBDA); }
"\."   { return newToken(sym.POINT); }
","   { return newToken(sym.COMMA); }
"("   { return newToken(sym.LEFT_PAR); }
")"   { return newToken(sym.RIGHT_PAR); }
":="  { return newToken(sym.IS); }

"DECLARATIONS"|"DECL"  { return newToken(sym.DECLARE_KW); }
"BODY"                 { return newToken(sym.BODY_KW); }

[a-z][a-z0-9\-]*    { return newToken(sym.IDENTIFIER); }
[A-Z0-9][A-Z0-9\-]* { return newToken(sym.ATOM); }

"//"[^\n]* {
	// Comentários de linha.
}

. { 
    return newToken(sym.error);
}

<<EOF>> {
	return newToken(sym.EOF);
}
