package parser;

import java_cup.runtime.*;
import java.io.Reader;

%%

%public
%class Lexer

%line
%column

%cup

%{ 
	StringBuilder string = new StringBuilder();  
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

Id = [A-Za-z_|"\""|"="|"-"|"<"|">"|"!"|"¡"|"?"|"≤"|"≥"|"`"|":"|"."|"@"|"#"|"$"|"%"|"&"|"\*"|"\+"|"\["|"\]"|"/"|"\\"][A-Za-z_0-9|"\""|"="|"-"|"<"|">"|"!"|"¡"|"?"|"≤"|"≥"|"`"|":"|"."|"@"|"#"|"$"|"%"|"&"|"\*"|"\+"|"\["|"\]"|"/"|"\\"]*

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]
InputCharacter = [^\r\n]
Comment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
Header = "/header/" [^*] ~"/header/"
Footer = "/footer/" [^*] ~"/footer/"


%{
public String lexeme;
%}

%%

<YYINITIAL> {

	{WhiteSpace}	{/*skip*/}
	{Header}      {/*skip*/}
	{Footer}      {/*skip*/}
	
	//separadoress
	"("				{return symbol(sym.LPAREN);}
	")"				{return symbol(sym.RPAREN);}
	"{"				{return symbol(sym.LKEY);}
	"}"				{return symbol(sym.RKEY);}
	"->"			{return symbol(sym.SEPARATOR);}
	";"				{return symbol(sym.SEMICOLON);}
	
	//palabras reservadas
	"text"		{return symbol(sym.TEXT);}
	"begin"		{return symbol(sym.BEGIN);}
	"nested"    {return symbol(sym.NESTED);}
	"composed"  {return symbol(sym.COMPOSED);}
	"literal"   {return symbol(sym.LITERAL);}
	
	{Id}		{return symbol(sym.IDENTIFIER, yytext());} 
	
}


/* error fallback */
[^]                              { throw new RuntimeException("Illegal character \""+yytext()+"\" at line "+(yyline+1)+", column "+(yycolumn+1)); }
                                              