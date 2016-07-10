package interpreter;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;


import jregex.MatchResult;

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
        String[] splittedText = textToTranslate.split("/body/");
    	if(splittedText.length != 3){
    		System.out.println("Error: Faltan delimitadores principales (/body/)");
    	}
        String translatedText = translate(splittedText[1]);
        String result = splittedText[0] + translatedText + splittedText[2];
        System.out.println(result); // texto traducido
        //generar archivo .html
        generateHtmlFile(result);
        
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
    	if(currentStaticRule.getOriginalExprDelimiters().areEmpties()){ // listas sin marca inicial ni final
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
        
        Delimiters orginalExpressionDelimiters = obtainDelimiters(splittedLine[ORIGINAL_EXPRESSION]);
        Delimiters replacerExpressionDelimiters = obtainDelimiters(splittedLine[REPLACER_EXPRESSION]);
        //String originalRegExp = originalExprToRegularExpr(splittedOriginalExpression);
        //String replacerRegExp = replacerExprToRegularExpr(splittedReplacerExpression);
        Rule subrule = obtainSubrule(splittedLine[ORIGINAL_EXPRESSION]);
        return new Rule(name,orginalExpressionDelimiters,replacerExpressionDelimiters,rules.size()+1,subrule);
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
    
    private static Delimiters obtainDelimiters(String commonExpression ){
    	Delimiters result = new Delimiters();
    	String[] splittedExpr = new String[0]; //si no anda poner null aca
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
    	if(splittedExpr.length >= 1){ 
    		result.setBeginToken(splittedExpr[0]);
    		if(splittedExpr.length == 2){
    			result.setEndToken(splittedExpr[1]);
    		}
    	}
    	return result;
    }
    
    private static void generateHtmlFile(String result){
    	FileWriter resultFile = null;
        PrintWriter pw = null;
        try
        {
            resultFile = new FileWriter("result.html");
            pw = new PrintWriter(resultFile);           
            pw.println(result);          

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != resultFile)
        	   resultFile.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
}
