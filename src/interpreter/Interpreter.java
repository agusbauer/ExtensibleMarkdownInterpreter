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
import java.util.SplittableRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import jregex.MatchResult;

import jregex.Pattern;
import jregex.Replacer;
import jregex.Substitution;
import jregex.TextBuffer;
import parser.Lexer;
import parser.Parser;

public class Interpreter {

    public static final int RULE_NAME = 0;
    
    public static ArrayList<Rule> rules = new ArrayList<Rule>();
    public static Rule currentStaticRule = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader br = null;
        String textToTranslate = "";
        String rulesText = "";
		try {
			textToTranslate = fileToString("text.txt");
			rulesText =  fileToString("rules.txt");
			br = new BufferedReader(new FileReader("rules.txt"));

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Lexer lex = new Lexer(br);
        Parser parser = new Parser(lex);
        try {
			parser.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
        rules = parser.getRules();
        
        /*for(Rule rule : rules){
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
        
        String[] splittedHeader = rulesText.split("/header/");
        String header = "";
        String footer = "";
    	if(splittedHeader.length == 3){
    		rulesText = splittedHeader[2];
    		header = splittedHeader[1];
    	}
    	String[] splittedFooter = rulesText.split("/footer/");
    	if(splittedFooter.length == 3){
    		rulesText = splittedFooter[0];
    		footer = splittedFooter[1];
    	}
        
    	String translatedText = translate(textToTranslate);
        String result = header + translatedText + footer;
        System.out.println(result); // texto traducido
    	
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
    
    private static String translate(String textToTranslate){   	
        for ( int i = 0; i < rules.size(); i++) {   	
        	if(rules.get(i).getSubrules().size() > 0 ){  
        		currentStaticRule = rules.get(i);
                textToTranslate = Parser2.nestedRulesParser(textToTranslate);
        	}
        	else{
        		//Pattern pattern = new Pattern(rules.get(i).getOriginalExpression());
                //Replacer replacer = pattern.replacer(rules.get(i).getReplacerExpression());
                //textToTranslate = replacer.replace(textToTranslate);
        		textToTranslate = Parser2.applyRuleInText(textToTranslate, rules.get(i));
        	} 	          
        }
        return textToTranslate;       
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
