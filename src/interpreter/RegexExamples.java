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


public class RegexExamples {

    private static final String RULE_SEPARATOR = "->";
    private static String TEXT = "TEXT";
    private static final int RULE_NAME = 0;
    private static final int ORIGINAL_EXPRESSION = 1;
    private static final int REPLACER_EXPRESSION = 2;
    private static final String SPECIAL_CHARACTERS = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
   // private static final int RULE_INDEX_COUNT = 3;
    public static ArrayList<Rule> rules = new ArrayList<Rule>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            readAndLoadRules();      
        } catch (IOException ex) {
            Logger.getLogger(RegexExamples.class.getName()).log(Level.SEVERE, null, ex);
        }
        String texto = "esto es texto con una pala bra en *ne  grita* otra en #cursiva# y otra en #sfdsfs  *negritacursiva* vhhhgc# y *#otra#*";
        String result = translate(texto);
        System.out.println(result);
        
    }
    
    /**/
    private static void readAndLoadRules() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("rules.txt"));
        try {
            String line = br.readLine();
            while (line != null) {
                Rule newRule = textToRule(line);
                rules.add(newRule);
                System.out.println(newRule.toString());
                line = br.readLine();
            }
        } finally {
            br.close();
        }   
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
        String originalRegExp = toRegularExpression(splittedLine[ORIGINAL_EXPRESSION],false);
        String replacerRegExp = toRegularExpression(splittedLine[REPLACER_EXPRESSION],true);
        return new Rule(name,originalRegExp,replacerRegExp,rules.size()+1);
    }
    
    private static String toRegularExpression(String commonExpression, boolean isReplacer){ //example #TEXT#
        final int INITIAL_TOKEN = 0;
        final int END_TOKEN = 1;
        String result = "";
        String[] splittedExpr = commonExpression.replace(" ","").split(TEXT); // array example = {#,#}
        String spCharWithoutCurrToken = SPECIAL_CHARACTERS.replace(splittedExpr[INITIAL_TOKEN], "");
        /*add escapes characters*/
        splittedExpr[INITIAL_TOKEN] = addEscapeCharacters(splittedExpr[INITIAL_TOKEN]);
        if(splittedExpr.length > 1){
            splittedExpr[END_TOKEN] = addEscapeCharacters(splittedExpr[END_TOKEN]);
        }
        spCharWithoutCurrToken =  addEscapeCharacters(spCharWithoutCurrToken);
        
        if(!isReplacer){
            if(splittedExpr.length > 1)
                result +=  splittedExpr[INITIAL_TOKEN] + "([\\p{Alpha}\\p{Space}"+ spCharWithoutCurrToken +"]*)" + splittedExpr[END_TOKEN];
            else
                result += splittedExpr[INITIAL_TOKEN] + "((\\p{Graph})*)";
        }
        else{
            if(splittedExpr.length > 1)
                result += splittedExpr[INITIAL_TOKEN] + "$1" + splittedExpr[END_TOKEN];
            else
                result += splittedExpr[INITIAL_TOKEN] + "$1";
        }      
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
