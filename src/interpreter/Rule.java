package interpreter;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author agustin
 */
public class Rule {
    
    private String name;
    private Delimiters originalExprDelimiters;
    private Delimiters replacerExprDelimiters;
    private String originalExpression;
    private String replacerExpression;
    private Integer id;
    private ArrayList<Rule> subrules;
    
    public Rule(String name, Delimiters originalExprDelimiters, Delimiters replacerExprDelimiters, Integer id, Rule subrule) {
    	subrules = new ArrayList<Rule>();
        this.name = name;
        this.originalExprDelimiters = originalExprDelimiters;
        this.replacerExprDelimiters = replacerExprDelimiters;
        this.originalExpression = generateOriginalExpression();
        this.replacerExpression = generateReplacerExpression();
        this.id = id;
        if(subrule != null)
        	this.subrules.add(subrule);
    }
    
    public Rule() {
        this.name = "";
        this.originalExpression = "";
        this.replacerExpression = "";
        this.id = -1;
    }
    
    private String generateOriginalExpression(){ 
        String result = "";
        String spCharWithoutCurrToken = Utils.removeTokens(Utils.SPECIAL_CHARACTERS, originalExprDelimiters.getBeginToken());      
        spCharWithoutCurrToken =  Utils.addEscapeCharacters(spCharWithoutCurrToken);
        String beginDelimiterEscaped = Utils.addEscapeCharacters(originalExprDelimiters.getBeginToken());       
        if(originalExprDelimiters.hasEndToken()){       	
        	String endDelimiterEscaped = Utils.addEscapeCharacters(originalExprDelimiters.getEndToken());
        	
            result += beginDelimiterEscaped + "([\\p{Alnum}\\p{Space}"+ spCharWithoutCurrToken +"]*)" + endDelimiterEscaped ;
            if(originalExprDelimiters.isComposed()){
            	result += "\\(([\\p{Alnum}\\p{Space}"+ spCharWithoutCurrToken +"]*)\\)";
            }
        }
        else{
            result += beginDelimiterEscaped + "([\\p{Graph}\\p{Blank}]*)";
        }
        return result;
    }   
    
    private String generateReplacerExpression(){ //TODO: agregar if para cuando solo tenga endToken
        String result = "";
        String beginDelimiterEscaped = Utils.addEscapeCharacters(replacerExprDelimiters.getBeginToken());
        String endDelimiterEscaped = Utils.addEscapeCharacters(replacerExprDelimiters.getEndToken());
		if(!replacerExprDelimiters.getEndToken().isEmpty()){
		    result = beginDelimiterEscaped + "$1" + endDelimiterEscaped;
		    if(originalExprDelimiters.isComposed()){
		    	String[] splittedBeginDel = beginDelimiterEscaped.split("LITERAL");	    	
		    	result = splittedBeginDel[0] + "$2" + splittedBeginDel[1] + "$1" + endDelimiterEscaped;
		    }
		}
		else{
		    result += beginDelimiterEscaped + "$1";
		}
		return result;
    }

    
    /************************** Getters and Setters Section **************************/
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalExpression() {
        return originalExpression;
    }

    public void setOriginalExpression(String originalExpression) {
        this.originalExpression = originalExpression;
    }

    public String getReplacerExpression() {
        return replacerExpression;
    }

    public void setReplacerExpression(String replacerExpression) {
        this.replacerExpression = replacerExpression;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    

	public Delimiters getOriginalExprDelimiters() {
		return originalExprDelimiters;
	}

	public void setOriginalExprDelimiters(Delimiters originalExprDelimiters) {
		this.originalExprDelimiters = originalExprDelimiters;
	}

	public Delimiters getReplacerExprDelimiters() {
		return replacerExprDelimiters;
	}

	public void setReplacerExprDelimiters(Delimiters replacerExprDelimiters) {
		this.replacerExprDelimiters = replacerExprDelimiters;
	}

	public ArrayList<Rule> getSubrules() {
		return subrules;
	}

	public void setSubrules(ArrayList<Rule> subrules) {
		this.subrules = subrules;
	}
	/************************** End Getters and Setters Section **************************/
	

	@Override
    public String toString() {
		
        return "Rule{" + "name=" + name + ", originalExpression=" + originalExpression + ", replacerExpression=" + replacerExpression + ", id=" + id + '}';
    }

    
    
    
}
