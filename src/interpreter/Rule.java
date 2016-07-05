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
    private String originalExpression;
    private String replacerExpression;
    private Integer id;
    private ArrayList<Rule> subrules;
    
    public Rule(String name, String originalExp, String translatedExp, Integer id, Rule subrule) {
    	subrules = new ArrayList<Rule>();
        this.name = name;
        this.originalExpression = originalExp;
        this.replacerExpression = translatedExp;
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

    
    
    public ArrayList<Rule> getSubrules() {
		return subrules;
	}

	public void setSubrules(ArrayList<Rule> subrules) {
		this.subrules = subrules;
	}

	@Override
    public String toString() {
		
        return "Rule{" + "name=" + name + ", originalExpression=" + originalExpression + ", replacerExpression=" + replacerExpression + ", id=" + id + '}';
    }

    
    
    
}
