package interpreter;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import jregex.MatchResult;
import jregex.Matcher;
import jregex.Pattern;
import jregex.Replacer;
import jregex.Substitution;
import jregex.TextBuffer;

public class Interpreter {

    public static final String RULE_SEPARATOR = "->";
    public static String TEXT = "TEXT";
    public static final int RULE_NAME = 0;
    private static final int ORIGINAL_EXPRESSION = 1;
    private static final int REPLACER_EXPRESSION = 2;
    private static final int INITIAL_TOKEN = 0;
    private static final int END_TOKEN = 1;
    private static final String SPECIAL_CHARACTERS = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    private static final String  EXPR_WITHOUT_BEGIN_AND_END = "([\\p{Alnum}\\p{Space}"+ "!\"#$%&'()\\*\\+,-./:;<=>?@[\\]^_`\\{|\\}~" +"]*)";
    public static ArrayList<Rule> rules = new ArrayList<Rule>();
    public static Rule currentStaticRule = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	String textToTranslate = "";
    	String rulesText = "";
        try {
        	textToTranslate = fileToString("text.txt");
        	rulesText = fileToString("rules.txt");    
        } catch (IOException ex) {
            Logger.getLogger(Interpreter.class.getName()).log(Level.SEVERE, null, ex);
        }
        //rules = getRulesFrom(rulesText);
        getRulesFrom(rulesText);
      /*  for (Rule rule : rules) { // esto es para printear nomas
			System.out.println(rule);
			if(!rule.getSubrules().isEmpty()){
				System.out.println("subregla de "+rule.getName());
				System.out.println(rule.getSubrules());
				if(!rule.getSubrules().get(0).getSubrules().isEmpty()){
					System.out.println("subregla de "+rule.getSubrules().get(0).getName());
					System.out.println(rule.getSubrules().get(0).getSubrules().get(0));
					
				}
			}
		}*/
        String result = translate(textToTranslate);
        System.out.println(result); // texto traducido
    }
    
    private static String fileToString(String fileName) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String result = "";
        try {
            String line = br.readLine();
            while (line != null) {
            	result += line + "\n";
                line = br.readLine();
            }
        } finally {
            br.close();
        }   
        return result;
    }
    
    /**/
    private static void getRulesFrom(String rulesText) {
        String rulesToObtain[] = rulesText.split("\n");
      //  ArrayList<Rule> result = new ArrayList<Rule>();
        for (int i = 0; i < rulesToObtain.length; i++) {
        	if(!rulesToObtain[i].isEmpty()){
	        	Rule newRule = textToRule(rulesToObtain[i]);
	        	//System.out.println(newRule);
	        	rules.add(newRule);
        	}
		}
        //return result;
    }
    
    private static String translate(String textToTranslate){
        for ( int i = 0; i < rules.size(); i++) {   	
        	if(rules.get(i).getSubrules().size() > 0 ){  
        		currentStaticRule = rules.get(i);
                textToTranslate = nestedRulesParser(textToTranslate);
        	}
        	else{
        		Pattern pattern = new Pattern(rules.get(i).getOriginalExpression());
                Replacer replacer = pattern.replacer(rules.get(i).getReplacerExpression());
                textToTranslate = replacer.replace(textToTranslate);
        	} 	          
        }
        return textToTranslate;       
    }
    
    private static String nestedRulesParser(String txtToTranslate){
    	if(currentStaticRule.getOriginalExpression().equals(EXPR_WITHOUT_BEGIN_AND_END)){ // listas sin marca inicial ni final
    		String[] replExprSPlitted = currentStaticRule.getReplacerExpression().split("\\$1");
    		String patternStr = "";
    		if(replExprSPlitted.length == 2){
    			patternStr = replExprSPlitted[0] + currentStaticRule.getSubrules().get(0).getReplacerExpression() + replExprSPlitted[1];
    		}
    		Pattern pattern = new Pattern(currentStaticRule.getSubrules().get(0).getOriginalExpression());
    		Replacer rep = pattern.replacer(patternStr);
    		txtToTranslate =  rep.replace(txtToTranslate);
    		
    		pattern = new Pattern(replExprSPlitted[1] + "([\\p{Space}]*)" + replExprSPlitted[0]);
    		rep = pattern.replacer("$1");   		
    		txtToTranslate =  rep.replace(txtToTranslate);
    		
    		return txtToTranslate;
    	}
    	else{ //listas con marca inicial y final
    		Substitution myOwnModel=new Substitution(){
    			@Override
    			public void appendSubstitution(MatchResult match, TextBuffer tb) {
    				String replacedMatch = "";
    				if (!currentStaticRule.getSubrules().get(0).getSubrules().isEmpty()){//SUB SUB
    					Rule subSubRule = currentStaticRule.getSubrules().get(0).getSubrules().get(0);
    					Pattern p = new Pattern(subSubRule.getOriginalExpression());
    					Replacer r = p.replacer(subSubRule.getReplacerExpression());
    					replacedMatch = r.replace(match.toString());
    	    		}
    	            Rule subRule = currentStaticRule.getSubrules().get(0);
    				Pattern p2 = new Pattern(subRule.getOriginalExpression());
    				Replacer r2 = p2.replacer(subRule.getReplacerExpression());
    				if (replacedMatch != "")
    					replacedMatch = r2.replace(replacedMatch);
    				else
    					replacedMatch = r2.replace(match.toString());
    				tb.append(replacedMatch); 
    			}
             };
             
              
              Pattern pattern = new Pattern(currentStaticRule.getOriginalExpression());
              Replacer myVeryOwnReplacer=new Replacer(pattern,myOwnModel);
              Replacer r2 = pattern.replacer(currentStaticRule.getReplacerExpression());
              txtToTranslate = myVeryOwnReplacer.replace(txtToTranslate);  
              txtToTranslate = r2.replace(txtToTranslate);
             
              return txtToTranslate;
    	}	
    }
    
    private static Rule textToRule(String line){
        String[] splittedLine = line.split(RULE_SEPARATOR);
        if(splittedLine.length != 3){ //a correct rule must be splitted in 3 parts
            System.out.println("No hay reglas cargadas o no respetan la convencion");
            return null;
        }
        String name = splittedLine[RULE_NAME].replace(" ","");
        
        String[] splittedOriginalExpression = splitCommonExpr(splittedLine[ORIGINAL_EXPRESSION]);
        String[] splittedReplacerExpression = splitCommonExpr(splittedLine[REPLACER_EXPRESSION]);
        String originalRegExp = originalExprToRegularExpr(splittedOriginalExpression);
        String replacerRegExp = replacerExprToRegularExpr(splittedReplacerExpression);
        Rule subrule = obtainSubrule(splittedLine[ORIGINAL_EXPRESSION]);
        return new Rule(name,originalRegExp,replacerRegExp,rules.size()+1,subrule);
    }
    
    
    private static Rule obtainSubrule(String expression){
    	Rule result = null;
    	int i = 0;
    	for (i = 0; i < rules.size() && !expression.contains(rules.get(i).getName()); i++) {
		}
    	if(i < rules.size()){ 		
    		result = rules.get(i);
    		rules.remove(i);
    	}
    	return result;
    }
    
    private static String[] splitCommonExpr(String commonExpression ){
    	String[] splittedExpr = null;
    	if(commonExpression.contains(TEXT)){
    		splittedExpr = commonExpression.replace(" ","").split(TEXT);
    	}
    	else{
    		for (Rule rule : rules) { //lo hago de vuelta para poder obtener el incio y fin de la expresion
    			if(commonExpression.contains(rule.getName())){   				
    				splittedExpr = commonExpression.replace(" ","").split(rule.getName());
    				break;
    			}
    		}
    	}
    	if(splittedExpr.length == 0){ //si es 0 es por que no tiene caracteres de principio ni fin le doy vacio para que no sea null
    		splittedExpr = new String[2];//se usa basicamente para las listas como las defina markdown
    		splittedExpr[0] = "";
    		splittedExpr[1] = "";
    	}
    	for (int i = 0; i < splittedExpr.length; i++) {
    		splittedExpr[i] = addEscapeCharacters(splittedExpr[i]);
		}
    	return splittedExpr;
    }
    
    private static String originalExprToRegularExpr(String[] splittedOriginalExpr){ //example #TEXT#
        String result = "";
        String spCharWithoutCurrToken =  addEscapeCharacters(SPECIAL_CHARACTERS);
        spCharWithoutCurrToken = spCharWithoutCurrToken.replace(splittedOriginalExpr[INITIAL_TOKEN], "");
        if(splittedOriginalExpr.length > 1)
            result +=  splittedOriginalExpr[INITIAL_TOKEN] + "([\\p{Alnum}\\p{Space}"+ spCharWithoutCurrToken +"]*)" + splittedOriginalExpr[END_TOKEN];
        else
            result += splittedOriginalExpr[INITIAL_TOKEN] + "([\\p{Graph}\\p{Blank}]*)";
        return result;
    }   
    
    private static String replacerExprToRegularExpr(String[] splittedReplacerExpr){
        String result = "";
		if(splittedReplacerExpr.length > 1)
		    result += splittedReplacerExpr[INITIAL_TOKEN] + "$1" + splittedReplacerExpr[END_TOKEN];
		else
		    result += splittedReplacerExpr[INITIAL_TOKEN] + "$1";
		 return result;
    }

    
    
    private static String addEscapeCharacters(String expression){
        String result = expression;
        String[] charactersToEscape = {"*","+","{","}"};
        for (String character : charactersToEscape) {
            result = result.replace(character, "\\" + character);
        }
        return result;
    }
}
