package interpreter;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import parser.Lexer;
import parser.Parser;

public class Interpreter {

    public static final int RULE_NAME = 0;
    
    public static ArrayList<Rule> rules = new ArrayList<Rule>();
    public static String rulesText = "";
    public static Rule currentStaticRule = null;
    
    /**
     * @param args the command line arguments
     */
    public static String execute(String textFileName) {
 
        String textToTranslate = "";
		try {
			textToTranslate = fileToString(textFileName);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        String[] splittedHeader = rulesText.split("/header/");
        String header = "";
        String footer = "";
    	if(splittedHeader.length == 3){
    		header = splittedHeader[1];
    	}
    	String[] splittedFooter = rulesText.split("/footer/");
    	if(splittedFooter.length == 3){
    		footer = splittedFooter[1];
    	}
        
    	String translatedText = translate(textToTranslate);
        String result = header + translatedText + footer;
    
        generateHtmlFile(result, textFileName);
        
        return result; 
        
    }
    
    public static String compileRules(String rulesFileName){
    	BufferedReader br = null;
    	rulesText = "";
    	try {  		
			//rulesText =  fileToString(rulesFileName);
			br = new BufferedReader(new FileReader(rulesFileName));
			String line;
			
			while ((line = br.readLine()) != null) {
				if(line.contains("->")){
					 line = Utils.reeplaceSpecialCharactersInRules(line);	
				}

		        rulesText += line + "\n";
	       
		    }
			//System.out.println(rulesText);
			InputStream is = new ByteArrayInputStream(rulesText.getBytes());
			br = new BufferedReader(new InputStreamReader(is));

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return "file not found";
		} catch (IOException e) {
			e.printStackTrace();
			return "IOException";
		}
        Lexer lex = new Lexer(br);
       
        Parser parser = new Parser(lex);
        try {
			parser.parse();
		} catch (Exception e) {
			return e.getMessage();
		}
        rules = parser.getRules();
      
        for(Rule rule : rules){
        	
	    	/*System.out.println(rule);
	    	if(!rule.getSubrules().isEmpty()){
				System.out.println("subregla de "+rule.getName());
				System.out.println(rule.getSubrules());
				if(!rule.getSubrules().get(0).getSubrules().isEmpty()){
					System.out.println("subregla de "+rule.getSubrules().get(0).getName());
					System.out.println(rule.getSubrules().get(0).getSubrules().get(0));
					
				}
			}*/
        	
	    	Utils.putBackSpecialCharactersInRule(rule);
        }
    
		return "";
    	
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
 
    
    private static void generateHtmlFile(String result, String resultName){
    	FileWriter resultFile = null;
        PrintWriter pw = null;
        try
        {
        	String[] splittedResult = resultName.split("\\.",2);
        	
        	if(splittedResult.length > 1){
        		resultName = splittedResult[0];
        	}
        	        	
            resultFile = new FileWriter(resultName+".html");
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
