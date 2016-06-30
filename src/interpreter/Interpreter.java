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
    public static ArrayList<Rule> rules = new ArrayList<Rule>();
    public static int istatic = 0;
    
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
        for (Rule rule : rules) { // esto es para printear nomas
			System.out.println(rule);
		}
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
        for ( istatic = 0; istatic < rules.size(); istatic++) {
        	
        	if(rules.get(istatic).getSubrules().size() > 0 ){
        		/* PARSEO DE LISTAS */
               /* String spCharWithoutCurrToken =  addEscapeCharacters(SPECIAL_CHARACTERS);
                spCharWithoutCurrToken = spCharWithoutCurrToken.replace("-", "");
                Pattern pattern = new Pattern("-"+ "([\\p{Alnum}\\p{Space}"+ spCharWithoutCurrToken +"]*)" + "-");*/
        		
        		//System.out.println(rules.get(istatic).getSubrules().size());
        		
                Substitution myOwnModel=new Substitution(){
        			@Override
        			public void appendSubstitution(MatchResult match, TextBuffer tb) {
        				//Pattern p = new Pattern("1."+ "([\\p{Graph}\\p{Blank}]*)");
        				//Replacer r = p.replacer("<il>"+"$1"+"</il>");
        				//System.out.println(Interpreter.istatic);
        				
        				Pattern p = new Pattern(rules.get(Interpreter.istatic).getSubrules().get(0).getOriginalExpression());
        				Replacer r = p.replacer(rules.get(Interpreter.istatic).getSubrules().get(0).getReplacerExpression());
        				String replacedMatch = r.replace(match.toString());
        				//p = new Pattern("-"+ "([\\p{Blank}]*)");
        				//r = p.replacer("$1");
        				//replacedMatch = r.replace(replacedMatch);
        				//tb.append("<ol>");
        				tb.append(replacedMatch); 
        				//match.getGroup(MatchResult.MATCH,tb);
        	            //tb.append("</ol>");
        			}
                 };
                 Pattern pattern = new Pattern(rules.get(istatic).getOriginalExpression());
                 Replacer myVeryOwnReplacer=new Replacer(pattern,myOwnModel);
                 Replacer r2 = pattern.replacer(rules.get(istatic).getReplacerExpression());
                 textToTranslate = myVeryOwnReplacer.replace(textToTranslate);  
                 textToTranslate = r2.replace(textToTranslate);
                 /* FIN PARSEO DE LISTAS */
        	}
        	else{
        		Pattern pattern = new Pattern(rules.get(istatic).getOriginalExpression());
                Replacer replacer = pattern.replacer(rules.get(istatic).getReplacerExpression());
                textToTranslate = replacer.replace(textToTranslate);
        	} 	          
        }
        return textToTranslate;
        
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
    	//System.out.println(splittedExpr.length);
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
