package interpreter;

public class Utils {
	
	public static final String SPECIAL_CHARACTERS = "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~";
	
	private static String[] CHARACTERS_TO_ESCAPE_FOR_RULES = {"{","}","_","[","]","(",")","->","begin","/header/","/footer/"};
    private static String[] SPECIAL_STRINGS_FOR_RULES = {"AAAAAAAAqwertyuAAAAAAAA","BBBBBBBBqwertyuBBBBBBBB","CCCCCCCCqwertyuCCCCCCCC","DDDDDDDDqwertyuDDDDDDDD",
    													 "EEEEEEEEqwertyuEEEEEEEE","FFFFFFFFqwertyuFFFFFFFF","GGGGGGGGqwertyuGGGGGGGG","HHHHHHHHqwertyuHHHHHHHH",
    													 "IIIIIIIIqwertyuIIIIIIII","JJJJJJJJqwertyuJJJJJJJJ","KKKKKKKKqwertyuKKKKKKKK","LLLLLLLLqwertyuLLLLLLLL"};
	
	public static String addEscapeCharacters(String expression){
        String result = expression;
        String[] charactersToEscape = {"*","+","{","}","(",")","[","]","$"};
        for (String character : charactersToEscape) {
            result = result.replace(character, "\\" + character);
        }
        return result;
    }
	
    public static String removeTokens(String text, String tokens){
    	String result = text;
    	/*for (char ch: tokens.toCharArray()) {
    		result = result.replace(Character.toString(ch) ,""); 
    	}*/
    	return result.replace(tokens ,"");
    }
    
    public static String addEscapeCharactersForRules(String rule){
        String result = rule;
        String[] charactersToEscape = {"*","+","{","}","(",")","[","]","$"};
        for (String character : charactersToEscape) {
            result = result.replace(character, "\\" + character);
        }
        return result;
    }
    
    
    public static String reeplaceSpecialCharactersInRules(String rule){ 
		String result = rule;
		for(int i = 0; i < CHARACTERS_TO_ESCAPE_FOR_RULES.length; i++){
			result = result.replace("\\"+CHARACTERS_TO_ESCAPE_FOR_RULES[i], SPECIAL_STRINGS_FOR_RULES[i]);	
		}
		return result;
	}
    
    public static void putBackSpecialCharactersInRule(Rule rule){ 
    	
    	for(int i = 0; i < CHARACTERS_TO_ESCAPE_FOR_RULES.length; i++){
    		String newBeginToken = rule.getReplacerExprDelimiters().getBeginToken().replace(SPECIAL_STRINGS_FOR_RULES[i], CHARACTERS_TO_ESCAPE_FOR_RULES[i]);
    		rule.getReplacerExprDelimiters().setBeginToken(newBeginToken);
    		String newEndToken = rule.getReplacerExprDelimiters().getEndToken().replace(SPECIAL_STRINGS_FOR_RULES[i], CHARACTERS_TO_ESCAPE_FOR_RULES[i]);
    		rule.getReplacerExprDelimiters().setEndToken(newEndToken);
    		rule.setReplacerExpression(rule.getReplacerExpression().replace(SPECIAL_STRINGS_FOR_RULES[i], "\\"+CHARACTERS_TO_ESCAPE_FOR_RULES[i]));
    	}
  
	}

}
