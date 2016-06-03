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
import java.util.logging.Level;
import java.util.logging.Logger;
import jregex.Pattern;
import jregex.Replacer;


public class Interpreter {

    private static final String RULE_SEPARATOR = "->";
    private static String TEXT = "TEXT";
    private static final int RULE_NAME = 0;
    private static final int ORIGINAL_EXPRESSION = 1;
    private static final int REPLACER_EXPRESSION = 2;
    private static final int INITIAL_TOKEN = 0;
    private static final int END_TOKEN = 1;
    private static final String SPECIAL_CHARACTERS = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static ArrayList<Rule> rules = new ArrayList<Rule>();
    
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
        rules = getRulesFrom(rulesText);
        String result = translate(textToTranslate);
        System.out.println(result);
    }
    
    /**/
    private static ArrayList<Rule> getRulesFrom(String rulesText) {
        String rulesToObtain[] = rulesText.split("\n");
        ArrayList<Rule> result = new ArrayList<Rule>();
        for (int i = 0; i < rulesToObtain.length; i++) {
        	   Rule newRule = textToRule(rulesToObtain[i]);
        	   System.out.println(newRule);
        	   result.add(newRule);
		}
        return result;
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
    
    private static String translate(String textToTranslate){
        for (int i = 0; i < rules.size(); i++) {
            Pattern pattern = new Pattern(rules.get(i).getOriginalExpression());
            Replacer replacer = pattern.replacer(rules.get(i).getReplacerExpression());
            textToTranslate = replacer.replace(textToTranslate);
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
        return new Rule(name,originalRegExp,replacerRegExp,rules.size()+1);
    }
    
    private static String[] splitCommonExpr(String commonExpression ){
    	String[] splittedExpr = commonExpression.replace(" ","").split(TEXT);
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
            result +=  splittedOriginalExpr[INITIAL_TOKEN] + "([\\p{Alpha}\\p{Space}"+ spCharWithoutCurrToken +"]*)" + splittedOriginalExpr[END_TOKEN];
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
