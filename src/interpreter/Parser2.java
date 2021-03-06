package interpreter;

import java.util.List;

import jregex.MatchResult;
import jregex.Pattern;
import jregex.Replacer;
import jregex.Substitution;
import jregex.TextBuffer;

public class Parser2 {
	
	 public static String nestedRulesParser(String txtToTranslate){
	    	if(Interpreter.currentStaticRule.getOriginalExprDelimiters().areEmpties()){ // listas sin marca inicial ni final
	    		
	    		String[] replExprSPlitted = Interpreter.currentStaticRule.getReplacerExpression().split("\\$1");
	    		String patternStr = "";
	    		if(replExprSPlitted.length == 2){
	    			patternStr = replExprSPlitted[0] + Interpreter.currentStaticRule.getSubrules().get(0).getReplacerExpression() + replExprSPlitted[1];
	    		}
	    		
	    		Rule subrule = Interpreter.currentStaticRule.getSubrules().get(0);
	   
	    		if(subrule.hasSubrules()){ //primero remplaza lo de la sub sub regla
	    			Rule subsubrule = subrule.getSubrules().get(0);
	    			Pattern pattern = new Pattern(subsubrule.getOriginalExpression());
	    			Replacer rep = pattern.replacer(subsubrule.getReplacerExpression());
		    		
	    			if(subrule.getOriginalExprDelimiters().hasBeginToken() && !subrule.getOriginalExprDelimiters().hasEndToken()){
	    				
		    			String[] spltText1 = txtToTranslate.split(subrule.getOriginalExprDelimiters().getBeginToken());
		    			String r = spltText1[0];
		    			
		    			for (int i = 1; i < spltText1.length; i++) {
		    				String[] splttxt2 = spltText1[i].split("\n",2);
		    				if(splttxt2.length > 1){
		    					r += subrule.getOriginalExprDelimiters().getBeginToken() + rep.replace(splttxt2[0]) + "\n" + splttxt2[1];
		    				}
		    				else{
		    					r += subrule.getOriginalExprDelimiters().getBeginToken() + rep.replace(splttxt2[0]) + "\n";
		    				}
		    			}

		    			txtToTranslate = r;
		    			
	    			}
	    			else{
	    				txtToTranslate =  rep.replace(txtToTranslate);
	    			}
		    		
	    		}
	    		
	    		Pattern pattern = new Pattern(subrule.getOriginalExpression()); //aca reemplaza lo de la subregla
	    		Replacer rep = pattern.replacer(patternStr);
	    		txtToTranslate =  rep.replace(txtToTranslate);
	   
	    		pattern = new Pattern(replExprSPlitted[1] + "([\\p{Space}]*)" + replExprSPlitted[0]);//reemplaza por vacio los delimitadores iniciales y finales repetidos
	    		rep = pattern.replacer("$1"); 
	    		txtToTranslate =  rep.replace(txtToTranslate);
	    		
	    		pattern = new Pattern(replExprSPlitted[0]); //Es solo para indentar el delimitador inicial
	    		rep = pattern.replacer(replExprSPlitted[0] + "\n                "); 
	    		txtToTranslate =  rep.replace(txtToTranslate);
	    		
	    		pattern = new Pattern(replExprSPlitted[1]);  //Es solo para indentar el delimitador final
	    		rep = pattern.replacer("\n        " + replExprSPlitted[1]); 
	    		txtToTranslate =  rep.replace(txtToTranslate);
	    		
	    		return txtToTranslate;
	    	}
	    	else{ //listas con marca inicial y final
	    		System.out.println("con marca inicial y final");

	    		Substitution myOwnModel=new Substitution(){
	    			@Override
	    			public void appendSubstitution(MatchResult match, TextBuffer tb) {
	    				String replacedMatch = "";
	    				if (!Interpreter.currentStaticRule.getSubrules().get(0).getSubrules().isEmpty()){//SUB SUB
	    					Rule subSubRule = Interpreter.currentStaticRule.getSubrules().get(0).getSubrules().get(0);
	    					System.out.println("subsub: " + subSubRule);
	    					Pattern p = new Pattern(subSubRule.getOriginalExpression());
	    					Replacer r = p.replacer(subSubRule.getReplacerExpression());
	    					replacedMatch = r.replace(match.toString());
	    	    		}
	    				
	    	            Rule subRule = Interpreter.currentStaticRule.getSubrules().get(0);
	    	            System.out.println("sub: " + subRule);
	    				Pattern p2 = new Pattern(subRule.getOriginalExpression());
	    				Replacer r2 = p2.replacer(subRule.getReplacerExpression());
	    				if (replacedMatch != "")
	    					replacedMatch = r2.replace(replacedMatch);
	    				else
	    					replacedMatch  = r2.replace(match.toString());
	    				tb.append(replacedMatch); 
	    			}
	             };
	              Pattern pattern = new Pattern(Interpreter.currentStaticRule.getOriginalExpression());
	              Replacer myVeryOwnReplacer=new Replacer(pattern,myOwnModel);
	              //Replacer r2 = pattern.replacer(Interpreter.currentStaticRule.getReplacerExpression());
	              txtToTranslate = myVeryOwnReplacer.replace(txtToTranslate);  
	              txtToTranslate = Parser2.applyRuleInText(txtToTranslate,Interpreter.currentStaticRule);
	              //txtToTranslate = r2.replace(txtToTranslate);
	              return txtToTranslate;
	    	}	
	}
	
	public static String applyRuleInText(String txt, Rule rule){
		
    	if(rule == null){
    		return txt;
    	}
    	String result = txt;	
    	String specialString1 = generateSpecialString(rule.getOriginalExprDelimiters().getBeginToken());
		txt = txt.replace("\\" + rule.getOriginalExprDelimiters().getBeginToken(), specialString1);	
    	if(rule.getOriginalExprDelimiters().hasEndToken()){
    		String specialString2 = generateSpecialString(rule.getOriginalExprDelimiters().getEndToken());
    		txt = txt.replace("\\" + rule.getOriginalExprDelimiters().getEndToken(), specialString2);
    		if(!rule.getOriginalExprDelimiters().isComposed()){ 
    			result = applyRuleWithEndToken(txt, rule);
    		}
    		else{ //si es una compuesta. ej: link o imagen 		TODO: Aca esta el drama de que no anden los compuestos
    			
    	        String[] splittedText = txt.split("\n");	  //divide el texto por cada linea y reemplaza cada linea por separado
    	        result = ""; 									//para que no haya dramas. PD: se puede poner un link o imagen por linea
    	        for (int i = 0; i < splittedText.length; i++) {   //si hay mas puede fallar     	
    	        	Pattern pattern = new Pattern(rule.getOriginalExpression());
        	        Replacer replacer = pattern.replacer(rule.getReplacerExpression());
    	        	result += replacer.replace(splittedText[i]) + "\n";
				}
    	        
    		}
    		result = result.replace(specialString1, rule.getOriginalExprDelimiters().getBeginToken());
        	result = result.replace(specialString2, rule.getOriginalExprDelimiters().getEndToken());
    	}
	    else{
	    	result = applyRuleWithoutEndToken(txt,rule);
	    	result = result.replace(specialString1, rule.getOriginalExprDelimiters().getBeginToken());
	    }
    	
    	return result;
    }
	
	private static String applyRuleWithEndToken(String txt, Rule rule){
		String result = txt;	
    	if(txt.contains(rule.getOriginalExprDelimiters().getBeginToken())){    				
	    	if(rule.getOriginalExprDelimiters().areEquals()){ //con inicio y fin iguales
		    	result = applyRuleWithEqualsTokens(txt,rule);
	    	}
	    	else{ //con inicio y fin distintos
	    		result = applyRuleWithDifferentsTokens(txt, rule);
	    	}    	
    	}
    	return result;
	}
	
	private static String applyRuleWithoutEndToken(String txt, Rule rule){
		Pattern pattern = new Pattern(rule.getOriginalExpression());
        Replacer replacer = pattern.replacer(rule.getReplacerExpression());
        return replacer.replace(txt);
	}
	
	private static String applyRuleWithEqualsTokens(String txt, Rule rule){
		
		String result = "";
		String delimiterWithEscapes = Utils.addEscapeCharacters(rule.getOriginalExprDelimiters().getBeginToken());
    	String[] splittedTxt = txt.split(delimiterWithEscapes);
		for (int i = 0; i < splittedTxt.length; i++) {	  
    		result += splittedTxt[i];
    		if(i < splittedTxt.length-1){
	    		if ( (i & 1) == 0 ) { //si es par
	    			if(i < splittedTxt.length-2){
	    				result += rule.getReplacerExprDelimiters().getBeginToken();
	    			}
	    			else{
	    				result += rule.getOriginalExprDelimiters().getBeginToken();
	    			}
	    		} else { 
	    			result += rule.getReplacerExprDelimiters().getEndToken();
	    		}
    		}    		
		}
		return result;
	}
	
	private static String applyRuleWithDifferentsTokens(String txt, Rule rule){
		String result = "";
		String delimiterWithEscapes = Utils.addEscapeCharacters(rule.getOriginalExprDelimiters().getBeginToken());
		String endDelimiterWithEscapes = Utils.addEscapeCharacters(rule.getOriginalExprDelimiters().getEndToken());
    	String[] splittedTxt = txt.split(delimiterWithEscapes);
		if(splittedTxt.length > 0){			
			result += splittedTxt[0];
		}
		for (int i = 1; i < splittedTxt.length; i++) {
			if(splittedTxt[i].contains(rule.getOriginalExprDelimiters().getEndToken())){				
				String[] txt2 = splittedTxt[i].split(endDelimiterWithEscapes);			
				result += rule.getReplacerExprDelimiters().getBeginToken() + txt2[0] + rule.getReplacerExprDelimiters().getEndToken();
				for (int j = 1; j < txt2.length; j++) {
					if(j < txt2.length -1){
						result += txt2[j] + rule.getOriginalExprDelimiters().getEndToken();
					}
					else{
						result += txt2[j];
					}					
				}							
			}
			else{
				result += rule.getOriginalExprDelimiters().getBeginToken() + splittedTxt[i];
			}
		}
		return result;
	}
	
	private static String generateSpecialString(String token){
		String result = "";
		for (char c : token.toCharArray()) {
			result += Character.toString((char)(c+1));
		}
		result += result;
		result += result;
		return result;
	}
	
	

}
